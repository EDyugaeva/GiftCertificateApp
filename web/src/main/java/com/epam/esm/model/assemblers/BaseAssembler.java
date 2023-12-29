package com.epam.esm.model.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class BaseAssembler<T, D extends RepresentationModel<?>> extends RepresentationModelAssemblerSupport<T, D> {
    private static final String GET_ALL = "getAll";
    private static final String POST_LINK = "createNew";
    private static final String PATCH_LINK = "update";
    private static final String DELETE_LINK = "delete";
    private final String mapping;

    public BaseAssembler(Class<?> controllerClass, Class<D> resourceType, String mapping) {
        super(controllerClass, resourceType);
        this.mapping = mapping;
    }

    protected Link createSelfLink(Long id) {
        return linkTo(getControllerClass())
                .slash(String.format("%s/%d", mapping, id))
                .withSelfRel()
                .withType(String.valueOf(HttpMethod.GET));
    }

    protected Link createPostLink() {
        return linkTo(getControllerClass())
                .slash(String.format("%s", mapping))
                .withRel(POST_LINK)
                .withType(String.valueOf(HttpMethod.POST));
    }

    protected Link createPatchLink() {
        return linkTo(getControllerClass())
                .slash(String.format("%s", mapping))
                .withRel(PATCH_LINK)
                .withType(String.valueOf(HttpMethod.PATCH));
    }

    protected Link createDeleteLink(Long id) {
        return linkTo(getControllerClass())
                .slash(String.format("%s/%d", mapping, id))
                .withRel(DELETE_LINK)
                .withType(String.valueOf(HttpMethod.DELETE));
    }

    protected Link createToAllLink() {
        return linkTo(getControllerClass())
                .slash(String.format("%s", mapping))
                .withRel(GET_ALL)
                .withType(String.valueOf(HttpMethod.GET));
    }
}
