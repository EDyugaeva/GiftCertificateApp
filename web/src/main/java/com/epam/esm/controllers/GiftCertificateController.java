package com.epam.esm.controllers;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;

/**
 * Controller for CRUD operations with Gift Certificate model
 */
@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {
    private Logger logger = LoggerFactory.getLogger(GiftCertificateController.class);

    private GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    /**
     * Method for getting all gift certificates (without sorting and ordering)
     *
     * @return list of GiftCertificate
     */
    @GetMapping()
    public List<GiftCertificate> getAllGiftCertificates() {
        logger.info("Getting all gift certificates");
        return giftCertificateService.getAll();
    }

    /**
     * Method for getting all gift certificates by id
     *
     * @param id of Gift certificate, > 0
     * @return Gift certificate
     */
    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable("id") Long id) {
        logger.info("Getting gift certificate by id = {}", id);
        return giftCertificateService.getGiftCertificatesById(id);
    }

    /**
     * Method for deleting gift certificate
     *
     * @param id of Gift certificate, > 0
     */
    @DeleteMapping("/{id}")
    public void deleteGiftCertificateById(@PathVariable("id") Long id) {
        logger.info("Delete gift certificate by id = {}", id);
        giftCertificateService.deleteGiftCertificate(id);
    }

    /**
     * Method for creating gift certificate
     *
     * @param giftCertificate - new Object
     * @return giftCertificate from DB
     */
    @PostMapping()
    public GiftCertificate saveGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        logger.info("Save gift certificate {}", giftCertificate);
        return giftCertificateService.saveGiftCertificate(giftCertificate);
    }

    /**
     * Method for updating gift Certificate
     *
     * @param id              of giftCertificate, > 0
     * @param giftCertificate - request body
     * @return giftCertificate from db
     */
    @PutMapping("/{id}")
    public GiftCertificate saveGiftCertificate(@PathVariable("id") Long id,
                                               @RequestBody GiftCertificate giftCertificate) {
        logger.info("Update gift certificate {}", giftCertificate.toString());
        return giftCertificateService.updateGiftCertificate(id, giftCertificate);
    }

    /**
     * Method for searching gift certificates with sorting and filtering
     *
     * @param name        - not required, if needed filtering by name or its part
     * @param description - not required, if needed filtering by description or its part
     * @param tagName     - not required, if needed filtering by tag name
     * @param ordering    - order of results (variants: name, date)
     * @param order       - if needed desc, then "desc"
     * @return List of Gift Certificate
     */
    @GetMapping("/search")
    public List<GiftCertificate> getGiftCertificateByParam(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String description,
                                                           @RequestParam(required = false) String tagName,
                                                           @RequestParam(required = false) List<String> ordering,
                                                           @RequestParam(required = false) String order) {
        Map<String, String> params = new HashMap<>();
        params.put(NAME, name);
        params.put(DESCRIPTION, description);
        params.put(TAG_NAME, tagName);
        return giftCertificateService.getGiftCertificatesByParameter(params, ordering, order);
    }
}
