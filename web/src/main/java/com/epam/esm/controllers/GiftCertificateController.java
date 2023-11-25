package com.epam.esm.controllers;

import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.TestException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.services.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
@AllArgsConstructor
public class GiftCertificateController {

    private GiftCertificateService giftCertificateService;

    @GetMapping()
    public List<GiftCertificate> getAllGiftCertificates() throws TestException {
        try {
            return giftCertificateService.getAll();
        } catch (TestException e) {
            throw new DataNotFoundException("method", "error");
        }
    }

    @GetMapping("/{id}")
    public GiftCertificate getGiftCertificateById(@PathVariable("id") Long id) {
        return giftCertificateService.getGiftCertificatesById(id);
    }


    @DeleteMapping("/{id}")
    public void deleteGiftCertificateById(@PathVariable("id") Long id) {
        giftCertificateService.deleteGiftCertificate(id);
    }

    @PostMapping("")
    public GiftCertificate saveGiftCertificate(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateService.saveGiftCertificate(giftCertificate);
    }

    @PutMapping("/{id}")
    public GiftCertificate saveGiftCertificate(@PathVariable("id") Long id, @RequestParam(required = false, name = "name") String name) {

        return null;
    }
}
