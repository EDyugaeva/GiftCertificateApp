package com.epam.esm.controllers;

import com.epam.esm.exceptions.ApplicationException;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import com.epam.esm.utils.PageableUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * Controller for CRUD operations with Gift Certificate model
 */
@RestController
@RequestMapping(value = "/certificates", produces = "application/json", consumes = "application/json")
@Slf4j
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method for getting all gift certificates (without sorting and ordering)
     *
     * @throws DataNotFoundException if the gift certificate table is empty
     */
    @GetMapping()
    public List<GiftCertificate> getAllGiftCertificates(@RequestParam(defaultValue = "0", name = "page") int page,
                                                        @RequestParam(defaultValue = "10", name = "size") int size)
            throws DataNotFoundException {
        log.info("Getting all gift certificates");
        PageRequest pageRequest = PageRequest.of(page, size);
        return giftCertificateService.getAll(pageRequest);
    }

    /**
     * Method for getting all gift certificates by id
     *
     * @param id of Gift certificate > 0
     */
    @GetMapping(value = "/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable("id") Long id) throws DataNotFoundException {
        log.info("Getting gift certificate by id = {}", id);
        return giftCertificateService.getGiftCertificatesById(id);
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
    public GiftCertificate updateGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationException {
        log.info("Save gift certificate {}", giftCertificate);
        return giftCertificateService.saveGiftCertificate(giftCertificate);
    }

    /**
     * Method for updating gift Certificate
     */
    @PatchMapping(value = "/{id}")
    public GiftCertificate updateGiftCertificate(@PathVariable("id") Long id,
                                                 @RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException, DataNotFoundException {
        log.info("Update gift certificate with id = {}", id);
        return giftCertificateService.updateGiftCertificate(id, giftCertificate);
    }

    /**
     * Method for updating gift Certificate duration
     */
    @PatchMapping(value = "/update/duration/{id}")
    public GiftCertificate updateGiftCertificateDuration(@PathVariable("id") Long id,
                                                         @RequestParam int duration)
            throws DataNotFoundException {
        log.info("Update gift certificate duration with id = {}", id);
        return giftCertificateService.updateGiftCertificateDuration(id, duration);
    }

    /**
     * Method for updating gift Certificate price
     */
    @PatchMapping(value = "/update/price/{id}")
    public GiftCertificate updateGiftCertificatePrice(@PathVariable("id") Long id,
                                                      @RequestParam float price)
            throws DataNotFoundException {
        log.info("Update gift certificate duration with id = {}", id);
        return giftCertificateService.updateGiftCertificatePrice(id, price);
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
    public List<GiftCertificate> getGiftCertificateByParam(@RequestParam(required = false, name = "name", defaultValue = "") String name,
                                                           @RequestParam(required = false, name = "description", defaultValue = "") String description,
                                                           @RequestParam(required = false, name = "tagName") Optional<String> tagName,
                                                           @RequestParam(defaultValue = "0", name = "page") int page,
                                                           @RequestParam(defaultValue = "10", name = "size") int size,
                                                           @RequestParam(defaultValue = "id,asc", name = "sort") String[] sort)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting gift certificates with filtering and sorting");
        PageRequest pageRequest = PageableUtils.createPageableWithSorting(page,size,sort);

        log.info("Page request is {} ", pageRequest);
        return giftCertificateService.getGiftCertificatesByParameters(pageRequest, name, description, tagName);
    }


}
