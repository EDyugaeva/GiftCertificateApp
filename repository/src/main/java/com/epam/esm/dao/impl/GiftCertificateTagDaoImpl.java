package com.epam.esm.dao.impl;

import com.epam.esm.constants.Constants;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.mapper.GiftCertificateTagMapper;
import com.epam.esm.model.GiftCertificateTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {
    private final JdbcTemplate jdbcTemplateObject;
    private static final String INSERT = "insert into gift_certificate_tag (gift_id, tag_id) values (?, ?)";
    private static final String SELECT_BY_ID = "select * from gift_certificate_tag where id = ?";
    private static final String SELECT_ALL = "select * from gift_certificate_tag";
    private static final String DELETE = "delete from gift_certificate_tag where id = ?";
    private static final String DELETE_BY_IDS = "delete from gift_certificate_tag where gift_id = ? AND tag_id = ?";

    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public GiftCertificateTag create(GiftCertificateTag giftCertificateTag) {
        log.info("Saving gift certificate tag pair " + giftCertificateTag);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{Constants.ID});
                    ps.setLong(1, giftCertificateTag.getGiftCertificateId());
                    ps.setLong(2, giftCertificateTag.getTagId());
                    return ps;
                },
                keyHolder);
        giftCertificateTag.setId(keyHolder.getKeyAs(Long.class));
        return giftCertificateTag;

    }

    @Override
    public Optional<GiftCertificateTag> getById(long id) {
        try {
            log.info("Trying to get pair with id = {}", id);
            return Optional.ofNullable(jdbcTemplateObject.queryForObject(SELECT_BY_ID, new GiftCertificateTagMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Gift certificate - tag pair with id = {} was not found", id, e);
            return Optional.empty();
        }
    }


    @Override
    public List<GiftCertificateTag> getAll() {
        try {
            log.info("Trying to get all tags and certificates pairs");
            return jdbcTemplateObject.query(SELECT_ALL, new GiftCertificateTagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.warn("Tags and certificates pairs were not found");
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(long id) {
        log.info("Trying to delete tag-gift pair with id = {}", id);
        jdbcTemplateObject.update(DELETE, id);
    }

    @Override
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) {
        log.info("Trying to delete tag-gift pair with gift certificate id = {} and tag id = {}", giftCertificateId, tagId);
        jdbcTemplateObject.update(DELETE_BY_IDS, giftCertificateId, tagId);
    }
}
