package com.epam.esm.dao.impl;

import com.epam.esm.dao.Column;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.ListGiftCertificateMapper;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.model.GiftCertificate;
import org.slf4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import static com.epam.esm.exceptions.ExceptionCodes.NOT_FOUND_GIFT_CERTIFICATE;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GiftCertificateDaoImpl.class);
    private JdbcTemplate jdbcTemplateObject;
    private static final String INSERT = "insert into gift_certificate (name, description, price, duration, create_date, last_update_date) values (?, ?, ?, ?, ? ,?)";
    private static final String UPDATE = "UPDATE gift_certificate SET name=?,description = ?, price=?, duration=?, create_date =?, last_update_date=? WHERE id = ?";
    private static final String SELECT_ALL = "SELECT" +
            "    gc.id AS \"gc.id\"," +
            "    gc.name AS \"gc.name\"," +
            "    gc.description AS \"gc.description\"," +
            "    gc.price AS \"gc.price\"," +
            "    gc.duration AS \"gc.duration\"," +
            "    gc.create_date AS \"gc.create_date\"," +
            "    gc.last_update_date AS \"gc.last_update_date\"," +
            "    t.id AS \"tag.id\"," +
            "    t.name AS \"tag.name\" " +
            "FROM" +
            "    gift_certificate gc" +
            "      left outer JOIN " +
            "    gift_certificate_tag gct ON gc.id = gct.gift_id" +
            "      left outer JOIN " +
            "    tag t ON gct.tag_id = t.id ";
    private static final String SELECT_BY_ID = " where gc.id = ?";
    private static final String DELETE = "delete from gift_certificate where id = ?";
    private static final String SELECT_BY_TAG_NAME = " where gc.id in (SELECT gc.id" +
            "             FROM gift_certificate gc" +
            "                      JOIN gift_certificate_tag gct ON gc.id = gct.gift_id" +
            "                      JOIN tag ON gct.tag_id = tag.id" +
            "             WHERE tag.name = ?)";
    private static final String SELECT_BY_NAME = " where gc.name ILIKE ?";
    private static final String SELECT_BY_DESCRIPTION = " where gc.description ILIKE ?";
    private static final String ORDER_BY = " order by ";
    private static final String DESC = "DESC";


    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    @Transactional
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) {
        log.info("Saving gift certificate = {}", giftCertificate);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT, new String[]{Column.ID});
                    ps.setString(1, giftCertificate.getName());
                    ps.setString(2, giftCertificate.getDescription());
                    ps.setFloat(3, giftCertificate.getPrice());
                    ps.setInt(4, giftCertificate.getDuration());
                    ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));
                    ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
                    return ps;
                },
                keyHolder);
        giftCertificate.setId(keyHolder.getKeyAs(Long.class));
        return giftCertificate;
    }

    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) {
        log.info("Updating gift certificate = {}", giftCertificate);
        jdbcTemplateObject.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(), giftCertificate.getDuration(),
                Timestamp.valueOf(giftCertificate.getCreateDate()), Timestamp.valueOf(giftCertificate.getLastUpdateDate()), giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public GiftCertificate getGiftCertificate(long id) {
        log.info("Getting gift certificate with id = {}", id);
        String query = SELECT_ALL + SELECT_BY_ID;
        try {
            List<GiftCertificate> list = jdbcTemplateObject.query(query, new Object[]{id}, new ListGiftCertificateMapper());
            return list.get(0);
        } catch (RuntimeException e) {
            log.error("Error while getting gift certificate with id = {}", id, e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    @Override
    public List<GiftCertificate> getGiftCertificates() {
        return getGiftCertificates(false, false, false);
    }

    @Override
    public List<GiftCertificate> getGiftCertificates(boolean sortingByName, boolean sortingByDate, boolean descOrdering) {
        log.info("Getting all gift certificates");
        String query = SELECT_ALL + getOrderBy(sortingByName, sortingByDate, descOrdering);
        try {
            return jdbcTemplateObject.query(query, new ListGiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    @Override
    @Transactional
    public void deleteGiftCertificate(long id) {
        log.info("Deleting  gift certificate with id = ?");
        jdbcTemplateObject.update(DELETE, id);
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByTagName(String tagName, boolean sortingByName,
                                                              boolean sortingByDate, boolean descOrdering) {
        log.info("Getting all gift certificates with tag name = {}", tagName);
        String query = SELECT_ALL + SELECT_BY_TAG_NAME + getOrderBy(sortingByName, sortingByDate, descOrdering);
        try {
            return jdbcTemplateObject.query(query, new Object[]{tagName}, new ListGiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByName(String name, boolean sortingByName,
                                                           boolean sortingByDate, boolean descOrdering) {
        log.info("Getting all gift certificates with name like {}", name);
        String query = SELECT_ALL + SELECT_BY_NAME + getOrderBy(sortingByName, sortingByDate, descOrdering);
        String param = "%" + name + "%";
        try {
            return jdbcTemplateObject.query(query, new Object[]{param}, new ListGiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByQuery(String query) {
        log.info("Getting all gift certificates with parameters ");
        String fullQuery = SELECT_ALL + query;
        try {
            return jdbcTemplateObject.query(fullQuery, new ListGiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    public List<GiftCertificate> getGiftCertificatesByDescription(String description, boolean sortingByName,
                                                                  boolean sortingByDate, boolean descOrdering) {
        log.info("Getting all gift certificates with description like {}", description);
        String query = SELECT_ALL + SELECT_BY_DESCRIPTION + getOrderBy(sortingByName, sortingByDate, descOrdering);
        String param = "%" + description + "%";
        try {
            System.out.println(query);
            return jdbcTemplateObject.query(query, new Object[]{param}, new ListGiftCertificateMapper());
        } catch (EmptyResultDataAccessException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException(NOT_FOUND_GIFT_CERTIFICATE.getErrorCode());
        }
    }

    private static String getOrderBy(boolean name, boolean date, boolean desc) {
        log.info("Adding ordering to get request: by name = {}, by date = {}, desc = {}", name, date, desc);
        StringBuilder stringBuilder = new StringBuilder();
        if (name || date) {
            stringBuilder.append(ORDER_BY);
        }
        if (name) {
            stringBuilder.append(String.format(Column.STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME, Column.NAME));
            if (date) {
                stringBuilder.append(", ").append(Column.GiftCertificateColumn.CREATE_DATE);
            }
        }
        if (date && !name) {
            stringBuilder.append(Column.GiftCertificateColumn.CREATE_DATE);
        }
        if (desc) {
            stringBuilder.append(" ").append(DESC);
        }
        return stringBuilder.toString();
    }


}
