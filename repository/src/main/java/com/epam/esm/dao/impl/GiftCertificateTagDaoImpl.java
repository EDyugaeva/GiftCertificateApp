package com.epam.esm.dao.impl;

import com.epam.esm.dao.Column;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.mapper.GiftCertificateTagMapper;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificateTag;
import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.NOT_FOUND_PAIR;

@Repository
public class GiftCertificateTagDaoImpl implements GiftCertificateTagDao {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GiftCertificateTagDaoImpl.class);
    private final JdbcTemplate jdbcTemplateObject;
    private static final String INSERT = "insert into gift_certificate_tag (gift_id, tag_id) values (?, ?)";
    private static final String SELECT_BY_ID = "select * from gift_certificate_tag where id = ?";
    private static final String SELECT_ALL = "select * from gift_certificate_tag";
    private static final String DELETE = "delete from gift_certificate_tag where id = ?";

    public GiftCertificateTagDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public GiftCertificateTag saveGiftTag(GiftCertificateTag giftCertificateTag) {
        log.info("Saving gift certificate tag pair " + giftCertificateTag);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{Column.ID});
                    ps.setLong(1, giftCertificateTag.getGiftCertificateId());
                    ps.setLong(2, giftCertificateTag.getTagId());
                    return ps;
                },
                keyHolder);
        giftCertificateTag.setId(keyHolder.getKeyAs(Long.class));
        return giftCertificateTag;
    }

    @Override
    public GiftCertificateTag getGiftCertificateTag(Long id) {
        try {
            log.info("Trying to get pair with id = {}", id);
            return jdbcTemplateObject.queryForObject(SELECT_BY_ID, new Object[]{id}, new GiftCertificateTagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting pair with id = {}", id, e);
            throw new DataNotFoundException("message",NOT_FOUND_PAIR.getErrorCode());
        }
    }

    @Override
    public List<GiftCertificateTag> getGiftCertificateTags() {
        try {
            log.info("Trying to get all tags and certificates pairs");
            return jdbcTemplateObject.query(SELECT_ALL, new GiftCertificateTagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting all pairs", e);
            throw new DataNotFoundException("message",NOT_FOUND_PAIR.getErrorCode());
        }
    }

    @Override
    public void deleteGiftTag(Long id) {
        log.info("Trying to delete tag-gift pair with id = {}", id);
        jdbcTemplateObject.update(DELETE, id);
    }
}
