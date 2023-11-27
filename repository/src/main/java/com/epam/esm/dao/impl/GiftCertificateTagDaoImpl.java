package com.epam.esm.dao.impl;

import com.epam.esm.dao.Column;
import com.epam.esm.dao.GiftCertificateTagDao;
import com.epam.esm.dao.mapper.GiftCertificateTagMapper;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificateTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodesConstants.*;


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
    public GiftCertificateTag saveGiftTag(GiftCertificateTag giftCertificateTag) throws OtherDatabaseException, WrongParameterException {
        log.info("Saving gift certificate tag pair " + giftCertificateTag);
        try {
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
        catch (EmptyResultDataAccessException e) {
            log.error("Exception while saving new gift certificate tag pair");
            throw new WrongParameterException("Parameters are not correct", WRONG_DATA_PARAMETER);
        } catch (Exception e) {
            log.error("Exception while saving new gift certificate tag pair");
            throw new OtherDatabaseException("Exception while saving new gift certificate tag pair", OTHER_EXCEPTION);
        }
    }

    @Override
    public GiftCertificateTag getGiftCertificateTag(Long id) throws DataNotFoundException {
        try {
            log.info("Trying to get pair with id = {}", id);
            return jdbcTemplateObject.queryForObject(SELECT_BY_ID, new Object[]{id}, new GiftCertificateTagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting pair with id = {}", id, e);
            throw new DataNotFoundException(String.format("Requested resource not found (id = %d)", id), NOT_FOUND_PAIR);
        }
    }

    @Override
    public List<GiftCertificateTag> getGiftCertificateTags() throws DataNotFoundException {
        try {
            log.info("Trying to get all tags and certificates pairs");
            return jdbcTemplateObject.query(SELECT_ALL, new GiftCertificateTagMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting all pairs", e);
            throw new DataNotFoundException("Requested resource not found", NOT_FOUND_PAIR);
        }
    }

    @Override
    public void deleteGiftTag(Long id) throws OtherDatabaseException {
        log.info("Trying to delete tag-gift pair with id = {}", id);
        try {
            jdbcTemplateObject.update(DELETE, id);
        }
        catch (Exception e) {
            log.error("Exception while deleting tag-gift pair with gift certificate id = {} ", id, e);
            throw new OtherDatabaseException("Database exception while deleting tag-gift pair", NOT_FOUND_PAIR);
        }
    }

    @Override
    public void deleteGiftCertificateTagByTagAndGiftCertificateId(long giftCertificateId, long tagId) throws OtherDatabaseException {
        log.info("Trying to delete tag-gift pair with gift certificate id = {} and tag id = {}", giftCertificateId, tagId);
        try {
            jdbcTemplateObject.update(DELETE_BY_IDS, giftCertificateId, tagId);
        }
        catch (Exception e) {
            log.error("Other db exception while deleting tag-gift pair with gift certificate id = {} and tag id = {}", giftCertificateId, tagId, e);
            throw new OtherDatabaseException("Other db exception while deleting tag-gift pair", OTHER_EXCEPTION);
        }
    }
}
