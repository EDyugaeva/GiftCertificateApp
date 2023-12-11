package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.ApplicationException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;

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
    public List<GiftCertificate> getAllGiftCertificates() throws DataNotFoundException {
        log.info("Getting all gift certificates");
        return giftCertificateService.getAll();
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
    public GiftCertificate saveGiftCertificate(@RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException, ApplicationException {
        log.info("Save gift certificate {}", giftCertificate);
        return giftCertificateService.saveGiftCertificate(giftCertificate);
    }

    /**
     * Method for updating gift Certificate
     *
     */
    @PatchMapping(value = "/{id}")
    public GiftCertificate saveGiftCertificate(@PathVariable("id") Long id,
                                               @RequestBody GiftCertificate giftCertificate)
            throws WrongParameterException {
        log.info("Update gift certificate {}", giftCertificate.toString());
        return giftCertificateService.updateGiftCertificate(id, giftCertificate);
    }

    /**
     * Method for searching gift certificates with sorting and filtering
     *
     * @param name        - not required, if needed filtering by name or its part
     * @param description - not required, if needed filtering by description or its part
     * @param tagName     - not required, if needed filtering by tag name
     * @param ordering    - not required, order of results (variants: name (desc if needed), date (desc if needed))
     * @return List of Gift Certificate
     */
    @GetMapping(value = "/search")
    public List<GiftCertificate> getGiftCertificateByParam(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String description,
                                                           @RequestParam(required = false) String tagName,
                                                           @RequestParam(required = false) List<String> ordering)
            throws DataNotFoundException, WrongParameterException {
        Map<String, String> params = new HashMap<>();
        params.put(NAME, name);
        params.put(DESCRIPTION, description);
        params.put(TAG_NAME, tagName);
        return giftCertificateService.getGiftCertificatesByParameter(params, ordering);
    }
}
