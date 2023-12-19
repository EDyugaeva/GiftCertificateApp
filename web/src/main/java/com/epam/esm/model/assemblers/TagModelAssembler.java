package com.epam.esm.model.assemblers;

import com.epam.esm.controllers.TagController;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.epam.esm.constants.InitValues.PAGE;
import static com.epam.esm.constants.InitValues.SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Component
public class TagModelAssembler extends BaseAssembler <Tag, TagModel> {
    public TagModelAssembler() {
        super(TagController.class, TagModel.class, "/tags");
    }

    @Override
    public TagModel toModel(Tag entity) {
        TagModel tagModel = instantiateModel(entity);

        tagModel.add(createSelfLink(entity.getId()),
                createToAllLink(),
                createPostLink(),
                createDeleteLink(entity.getId()));

        tagModel.setId(entity.getId());
        tagModel.setName(entity.getName());

        return tagModel;
    }


    @Override
    @SneakyThrows
    public CollectionModel<TagModel> toCollectionModel(Iterable<? extends Tag> entities) {
        CollectionModel<TagModel> tagModels = super.toCollectionModel(entities);

        tagModels.add(linkTo(methodOn(TagController.class).getTags(Integer.parseInt(PAGE),
                Integer.parseInt(SIZE))).withSelfRel());

        return tagModels;
    }
}
