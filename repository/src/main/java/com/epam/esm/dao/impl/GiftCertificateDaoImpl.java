package com.epam.esm.dao.impl;

import com.epam.esm.dao.Column;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.ListGiftCertificateMapper;
import com.epam.esm.exceptions.DataNotFoundException;
import com.epam.esm.exceptions.OtherDatabaseException;
import com.epam.esm.exceptions.WrongModelParameterException;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.utils.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.epam.esm.exceptions.ExceptionCodesConstants.*;

@Component
@Slf4j
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplateObject;
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

    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    @Override
    public GiftCertificate saveGiftCertificate(GiftCertificate giftCertificate) throws WrongModelParameterException, OtherDatabaseException {
        log.info("Saving gift certificate = {}", giftCertificate);
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplateObject.update(connection -> prepareStatementForInsert(connection, INSERT, giftCertificate), keyHolder);
            giftCertificate.setId(keyHolder.getKeyAs(Long.class));
            return giftCertificate;
        } catch (EmptyResultDataAccessException e) {
            log.error("Exception while saving new gift certificate");
            throw new WrongModelParameterException("Parameters are not correct", WRONG_DATA_PARAMETER);
        } catch (Exception e) {
            log.error("Exception while saving new gift certificate");
            throw new OtherDatabaseException("Exception while saving new gift certificate", OTHER_EXCEPTION);
        }
    }

    private PreparedStatement prepareStatementForInsert(Connection connection, String sql, GiftCertificate giftCertificate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{Column.ID});
        ps.setString(1, giftCertificate.getName());
        ps.setString(2, giftCertificate.getDescription());
        ps.setFloat(3, giftCertificate.getPrice());
        ps.setInt(4, giftCertificate.getDuration());
        ps.setTimestamp(5, Timestamp.valueOf(giftCertificate.getCreateDate()));

        if (giftCertificate.getLastUpdateDate() != null) {
            ps.setTimestamp(6, Timestamp.valueOf(giftCertificate.getLastUpdateDate()));
        } else {
            ps.setTimestamp(6, null);
        }
        return ps;
    }

    @Override
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) throws DataNotFoundException {
        log.info("Updating gift certificate = {}", giftCertificate);
        try {
            jdbcTemplateObject.update(UPDATE, giftCertificate.getName(),
                    giftCertificate.getDescription(),
                    giftCertificate.getPrice(), giftCertificate.getDuration(),
                    Timestamp.valueOf(giftCertificate.getCreateDate()),
                    Timestamp.valueOf(giftCertificate.getLastUpdateDate()),
                    giftCertificate.getId());
        } catch (DataAccessException e) {
            log.error("Exception while updating recourse with id = {}", giftCertificate.getId(), e);
            throw new DataNotFoundException(String.format("Requested resource for updating not found (id = %d)",
                    giftCertificate.getId()), NOT_FOUND_GIFT_CERTIFICATE);
        }
        return giftCertificate;
    }

    @Override
    public GiftCertificate getGiftCertificate(long id) throws DataNotFoundException {
        log.info("Getting gift certificate with id = {}", id);
        String query = SELECT_ALL + SELECT_BY_ID;
        try {
            List<GiftCertificate> list = jdbcTemplateObject.query(query, new Object[]{id},
                    new ListGiftCertificateMapper());
            return list.get(0);
        } catch (RuntimeException e) {
            log.error("Error while getting gift certificate with id = {}", id, e);
            throw new DataNotFoundException(String.format("Requested resource not found (id = %d)", id),
                    NOT_FOUND_GIFT_CERTIFICATE);
        }
    }

    @Override
    public List<GiftCertificate> getGiftCertificates() throws DataNotFoundException {
        try {
            return getGiftCertificatesByQuery(null, null, null);
        } catch (WrongParameterException e) {
            log.error("Parameter exception");
            throw new RuntimeException("Exception while getting all gift certificates");
        }
    }

    @Override
    public void deleteGiftCertificate(long id) throws OtherDatabaseException {
        log.info("Deleting  gift certificate with id = ?");
        try {
            jdbcTemplateObject.update(DELETE, id);
        } catch (Exception e) {
            log.error("Deleting gift certificate with id = {} failed", id, e);
            throw new OtherDatabaseException(String.format("Deleting gift certificate with id  %d failed", id),
                    OTHER_EXCEPTION);
        }
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesByQuery(Map<String, String> filteredBy,
                                                            List<String> orderingBy,
                                                            String order)
            throws DataNotFoundException, WrongParameterException {
        log.info("Getting all gift certificates with parameters ");
        String fullQuery = SELECT_ALL + getQuery(filteredBy, orderingBy, order);
        try {
            return jdbcTemplateObject.query(fullQuery, new ListGiftCertificateMapper());
        } catch (RuntimeException e) {
            log.error("Error while getting gift certificates", e);
            throw new DataNotFoundException("Requested resource not found ", NOT_FOUND_GIFT_CERTIFICATE);
        }
    }

    String getQuery(Map<String, String> filteredBy, List<String> orderingBy, String order) throws WrongParameterException {
        QueryGenerator queryGenerator = new QueryGenerator();
        queryGenerator.createFilter(filteredBy, queryGenerator);
        queryGenerator.createSorting(orderingBy, queryGenerator);
        queryGenerator.createOrder(order, queryGenerator);
        return queryGenerator.getQuery();
    }
}
