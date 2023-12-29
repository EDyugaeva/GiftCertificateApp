package com.epam.esm.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "user")
@Relation(collectionRelation = "users")
public class UserModel extends RepresentationModel<UserModel> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderModel> orderList;
}
