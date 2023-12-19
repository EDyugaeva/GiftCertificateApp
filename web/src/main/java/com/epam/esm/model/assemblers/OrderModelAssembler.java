package com.epam.esm.model.assemblers;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.model.Order;
import com.epam.esm.model.OrderModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.epam.esm.constants.InitValues.PAGE;
import static com.epam.esm.constants.InitValues.SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Component
public class OrderModelAssembler extends BaseAssembler<Order, OrderModel> {
    public OrderModelAssembler() {
        super(OrderController.class, OrderModel.class, "/orders");
    }

    @Override
    public OrderModel toModel(Order entity) {
        OrderModel orderModel = instantiateModel(entity);

        orderModel.add(createSelfLink(entity.getId()),
                createPostLink(),
                createUserLink(entity.getUser().getId()),
                createGiftCertificateLink(entity.getGiftCertificate().getId()));

        setModelAttributes(orderModel, entity);
        return orderModel;
    }


    @SneakyThrows
    @Override
    public CollectionModel<OrderModel> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderModel> orderModels = super.toCollectionModel(entities);

        orderModels.add(linkTo(methodOn(TagController.class).getTags(Integer.parseInt(PAGE),
                Integer.parseInt(SIZE))).withSelfRel());

        return orderModels;
    }

    @SneakyThrows
    private Link createUserLink(Long userId) {
        return linkTo(methodOn(UserController.class).getUser(userId))
                .withRel("userOrders")
                .withType(String.valueOf(HttpMethod.GET));
    }

    @SneakyThrows
    private Link createGiftCertificateLink(Long giftCertificateId) {
        return linkTo(methodOn(GiftCertificateController.class).getGiftCertificateById(giftCertificateId))
                .withRel("giftCertificate")
                .withType(String.valueOf(HttpMethod.GET));
    }

    private void setModelAttributes(OrderModel orderModel, Order entity) {
        orderModel.setId(entity.getId());
        orderModel.setPrice(entity.getPrice());
        orderModel.setCreatedTime(entity.getCreatedTime());
        orderModel.setUserId(entity.getUser().getId());
        orderModel.setGiftCertificateId(entity.getGiftCertificate().getId());
    }
}
