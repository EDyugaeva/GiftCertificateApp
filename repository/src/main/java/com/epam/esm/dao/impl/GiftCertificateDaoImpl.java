package com.epam.esm.dao.impl;

import com.epam.esm.dao.Constants;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.ListGiftCertificateMapper;
import com.epam.esm.exceptions.WrongParameterException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.utils.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public GiftCertificate create(GiftCertificate giftCertificate) {
        log.info("Saving gift certificate = {}", giftCertificate);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(connection -> prepareStatementForInsert(connection, INSERT, giftCertificate), keyHolder);
        giftCertificate.setId(keyHolder.getKeyAs(Long.class));
        return giftCertificate;

    }

    private PreparedStatement prepareStatementForInsert(Connection connection, String sql, GiftCertificate giftCertificate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql, new String[]{Constants.ID});
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
    public GiftCertificate updateGiftCertificate(GiftCertificate giftCertificate) {
        log.info("Updating gift certificate = {}", giftCertificate);
        jdbcTemplateObject.update(UPDATE, giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(), giftCertificate.getDuration(),
                Timestamp.valueOf(giftCertificate.getCreateDate()),
                Timestamp.valueOf(giftCertificate.getLastUpdateDate()),
                giftCertificate.getId());
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        try {
            log.info("Getting gift certificate with id = {}", id);
            String query = SELECT_ALL + SELECT_BY_ID;
            List<GiftCertificate> list = jdbcTemplateObject.query(query, new ListGiftCertificateMapper(), id);
            return list != null ? Optional.ofNullable(list.get(0)) : null;
        } catch (UncategorizedSQLException | DataIntegrityViolationException e) {
            log.warn("Gift certificate with id = {} was not found", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<GiftCertificate> getAll() {
        try {
            log.info("Getting all certificates");
            return jdbcTemplateObject.query(SELECT_ALL, new ListGiftCertificateMapper());
        }
        catch (UncategorizedSQLException e) {
            log.warn("Gift certificates were not found", e);
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting  gift certificate with id = ?");
        jdbcTemplateObject.update(DELETE, id);
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesBySortingParams(Map<String, String> filteredBy,
                                                                    List<String> orderingBy) throws WrongParameterException {
        try {
            log.info("Getting all gift certificates with parameters ");
            String fullQuery = SELECT_ALL + getQuery(filteredBy, orderingBy);
            return jdbcTemplateObject.query(fullQuery, new ListGiftCertificateMapper());
        }
        catch (UncategorizedSQLException | DataIntegrityViolationException e) {
            log.warn("Gift certificates were not found", e);
            return null;
        }
    }

    String getQuery(Map<String, String> filteredBy, List<String> orderingBy) throws WrongParameterException {
        QueryGenerator queryGenerator = new QueryGenerator();
        queryGenerator.createFilter(filteredBy, queryGenerator);
        queryGenerator.createSorting(orderingBy, queryGenerator);
        return queryGenerator.getQuery();
    }
}
