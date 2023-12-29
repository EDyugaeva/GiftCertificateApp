package com.epam.esm.model.assemblers;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderModel;
import com.epam.esm.model.User;
import com.epam.esm.model.UserModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.esm.constants.InitValues.PAGE;
import static com.epam.esm.constants.InitValues.SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Component
public class UserModelAssembler extends BaseAssembler<User, UserModel> {
    public UserModelAssembler() {
        super(UserController.class, UserModel.class, "/users");
    }

    @SneakyThrows
    @Override
    public UserModel toModel(User entity) {
        UserModel userModel = instantiateModel(entity);

        Link popularTag = linkTo(methodOn(TagController.class)
                .getPopularTag(entity.getId())).withRel("popular_tag");

        userModel.add(createSelfLink(entity.getId()),
                createToAllLink(),
                popularTag);

        userModel.setId(entity.getId());
        userModel.setFirstName(entity.getFirstName());
        userModel.setEmail(entity.getEmail());
        userModel.setLastName(entity.getFirstName());
        userModel.setOrderList(toOrderModel(entity.getOrderList()));

        return userModel;
    }


    @SneakyThrows
    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> userModels = super.toCollectionModel(entities);

        userModels.add(linkTo(methodOn(UserController.class).getUsers(Integer.parseInt(PAGE),
                Integer.parseInt(SIZE))).withSelfRel());

        return userModels;
    }

    private List<OrderModel> toOrderModel(List<Order> orders) {
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }

        return orders.stream()
                .map(this::mapToOrderModel)
                .collect(Collectors.toList());

    }

    @SneakyThrows
    private OrderModel mapToOrderModel(Order order) {
        return createOrderModel(order)
                .add(linkTo(OrderController.class, methodOn(OrderController.class)
                        .getOrder(order.getId()))
                        .withSelfRel());
    }

    private OrderModel createOrderModel(Order order) {
        return OrderModel.builder()
                .id(order.getId())
                .createdTime(order.getCreatedTime())
                .price(order.getPrice())
                .giftCertificateId(order.getGiftCertificate().getId())
                .userId(order.getUser().getId())
                .build();
    }
}
