package com.epam.esm.controllers;

import com.epam.esm.exceptions.ApplicationException;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.assemblers.GiftCertificateModelAssembler;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.utils.PageableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionCodesConstants.OTHER_EXCEPTION;


/**
 * Controller for CRUD operations with Gift Certificate model
 */
@RestController
@RequestMapping(value = "/certificates", produces = "application/json", consumes = "application/json")
@Slf4j
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateModelAssembler giftCertificateModelAssembler;

    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateModelAssembler giftCertificateModelAssembler) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateModelAssembler = giftCertificateModelAssembler;
    }

    /**
     * Method for getting all gift certificates (without sorting and ordering)
     *
     * @throws DataNotFoundException if the gift certificate table is empty
     */
    @GetMapping()
    public CollectionModel<GiftCertificateModel> getAllGiftCertificates(@RequestParam(defaultValue = "0", name = "page") int page,
                                                                        @RequestParam(defaultValue = "10", name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting all gift certificates");
        PageRequest pageRequest = PageRequest.of(page, size);
        return giftCertificateModelAssembler.toCollectionModel(giftCertificateService.getAll(pageRequest));
    }

    /**
     * Method for getting gift certificates by id
     *
     * @param id of Gift certificate > 0
     */
    @GetMapping(value = "/{id}")
    public GiftCertificateModel getGiftCertificateById(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Getting gift certificate by id = {}", id);
        return giftCertificateModelAssembler.toModel(giftCertificateService.getGiftCertificatesById(id));
    }

    /**
     * Method for deleting gift certificate
     *
     * @param id of Gift certificate > 0
     */
    @DeleteMapping("/{id}")
    public void deleteGiftCertificateById(@PathVariable("id") Long id) throws WrongParameterException {
        log.info("Delete gift certificate by id = {}", id);
        giftCertificateService.deleteGiftCertificate(id);
    }

    /**
     * Method for creating gift certificate
     *
     * @param giftCertificate - new Object
     */
    @PostMapping()
    public GiftCertificateModel createGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationException {
        log.info("Save gift certificate {}", giftCertificate);
        try {
            return giftCertificateModelAssembler.toModel(giftCertificateService.saveGiftCertificate(giftCertificate));
        } catch (UnexpectedRollbackException e) {
            log.warn("Transactional exception while saving new gc", e);
            throw new ApplicationException("Transactional exception while saving new gc", OTHER_EXCEPTION);
        }
    }

    /**
     * Method for updating gift Certificate
     */
    @PatchMapping(value = "/{id}")
    public GiftCertificateModel updateGiftCertificate(@PathVariable("id") Long id,
                                                      @RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException, DataNotFoundException {
        log.info("Update gift certificate with id = {}", id);
        return giftCertificateModelAssembler.toModel(giftCertificateService.updateGiftCertificate(id, giftCertificate));
    }

    /**
     * Method for updating gift Certificate duration
     */
    @PatchMapping(value = "/update/duration/{id}")
    public GiftCertificateModel updateGiftCertificateDuration(@PathVariable("id") Long id,
                                                              @RequestParam int duration)
            throws DataNotFoundException, WrongParameterException {
        log.info("Update gift certificate duration with id = {}", id);
        return giftCertificateModelAssembler.toModel(giftCertificateService.updateGiftCertificateDuration(id, duration));
    }

    /**
     * Method for updating gift Certificate price
     */
    @PatchMapping(value = "/update/price/{id}")
    public GiftCertificateModel updateGiftCertificatePrice(@PathVariable("id") Long id,
                                                           @RequestParam float price)
            throws DataNotFoundException, WrongParameterException {
        log.info("Update gift certificate duration with id = {}", id);
        return giftCertificateModelAssembler.toModel(giftCertificateService.updateGiftCertificatePrice(id, price));
    }

    /**
     * Method for searching gift certificates with sorting and filtering
     *
     * @param name        - not required, if needed filtering by name or its part
     * @param description - not required, if needed filtering by description or its part
     * @param tagName     - not required, if needed filtering by tag name
     * @return List of Gift Certificate
     */
    @GetMapping(value = "/search")
    public CollectionModel<GiftCertificateModel> getGiftCertificateByParam(@RequestParam(required = false, name = "name", defaultValue = "") String name,
                                                                           @RequestParam(required = false, name = "description", defaultValue = "") String description,
                                                                           @RequestParam(required = false, name = "tagName") Optional<String> tagName,
                                                                           @RequestParam(defaultValue = "0", name = "page") int page,
                                                                           @RequestParam(defaultValue = "10", name = "size") int size,
                                                                           @RequestParam(defaultValue = "id,asc", name = "sort") String[] sort)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting gift certificates with filtering and sorting");
        PageRequest pageRequest = PageableUtils.createPageableWithSorting(page, size, sort);
        return giftCertificateModelAssembler.toCollectionModel(giftCertificateService
                .getGiftCertificatesByParameters(pageRequest, name, description, tagName));
    }

    /**
     * Get gift certificates with tag names
     *
     * @param tagNames - searching tag names
     * @param size - size of a page
     * @param page - page number
     * @return List of Gift Certificate
     */
    @GetMapping(value = "/tags")
    public CollectionModel<GiftCertificateModel> getByTagName(@RequestParam("tags") List<String> tagNames,
                                                              @RequestParam(defaultValue = "10", name = "size") int size,
                                                              @RequestParam(defaultValue = "0", name = "page") int page)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting gift certificates with query to find tags with tagNames = {}", tagNames);
        PageRequest pageRequest = PageableUtils.createPageableWithSorting(page, size);
        return giftCertificateModelAssembler.toCollectionModel(giftCertificateService.findByTagNames(tagNames, pageRequest));
    }
}
