package com.epam.esm.model.assemblers;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.Tag;
import com.epam.esm.model.TagModel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.constants.InitValues.PAGE;
import static com.epam.esm.constants.InitValues.SIZE;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Component
public class GiftCertificateModelAssembler extends BaseAssembler<GiftCertificate, GiftCertificateModel> {
    public GiftCertificateModelAssembler() {
        super(GiftCertificateController.class, GiftCertificateModel.class, "/certificates");
    }

    @Override
    public GiftCertificateModel toModel(GiftCertificate entity) {
        GiftCertificateModel giftCertificateModel = instantiateModel(entity);

        giftCertificateModel.add(createSelfLink(entity.getId()),
                createPatchLink(),
                createDeleteLink(entity.getId()),
                createPostLink(),
                createToAllLink());

        setModelAttributes(giftCertificateModel, entity);
        return giftCertificateModel;
    }


    @SneakyThrows
    @Override
    public CollectionModel<GiftCertificateModel> toCollectionModel(Iterable<? extends GiftCertificate> entities) {
        CollectionModel<GiftCertificateModel> userModels = super.toCollectionModel(entities);
        userModels.add(linkTo(methodOn(GiftCertificateController.class)
                .getAllGiftCertificates(Integer.parseInt(PAGE), Integer.parseInt(SIZE)))
                .withSelfRel());
        return userModels;
    }

    private Set<TagModel> toTagModel(Set<Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptySet();
        }

        return tags.stream()
                .map(this::mapToTagModel)
                .collect(Collectors.toSet());

    }

    @SneakyThrows
    private TagModel mapToTagModel(Tag tag) {
        return createTagModel(tag)
                .add(linkTo(TagController.class, methodOn(TagController.class)
                        .getTag(tag.getId()))
                        .withSelfRel());
    }

    private TagModel createTagModel(Tag tag) {
        return TagModel.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    private void setModelAttributes(GiftCertificateModel model, GiftCertificate entity) {
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDescription(entity.getDescription());
        model.setDuration(entity.getDuration());
        model.setPrice(entity.getPrice());
        model.setCreateDate(entity.getCreateDate());

        if (entity.getLastUpdateDate() != null) {
            model.setLastUpdateDate(entity.getLastUpdateDate());
        }

        model.setTagSet(toTagModel(entity.getTagSet()));
    }
}
