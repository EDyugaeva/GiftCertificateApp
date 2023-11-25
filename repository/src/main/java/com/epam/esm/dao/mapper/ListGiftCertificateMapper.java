package com.epam.esm.dao.mapper;

import com.epam.esm.dao.Column;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.dao.Column.*;

public class ListGiftCertificateMapper implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId(rs.getLong(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME, ID)));
            giftCertificate.setName(rs.getString(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME, NAME)));
            giftCertificate.setDescription(rs.getString(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME,
                    Column.GiftCertificateColumn.DESCRIPTION)));
            giftCertificate.setPrice(rs.getFloat(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME,
                    Column.GiftCertificateColumn.PRICE)));
            giftCertificate.setDuration(rs.getInt(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME,
                    Column.GiftCertificateColumn.DURATION)));
            giftCertificate.setCreateDate(rs.getTimestamp(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME,
                    Column.GiftCertificateColumn.CREATE_DATE)).toLocalDateTime());
            Timestamp lastUpdate =  rs.getTimestamp(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME,
                            Column.GiftCertificateColumn.LAST_UPDATE_DATE));
            if (lastUpdate != null) {
                giftCertificate.setLastUpdateDate(lastUpdate.toLocalDateTime());
            }

            List<Tag> tags = new ArrayList<>();
            while (!rs.isAfterLast() && rs.getInt(String.format(STRUCTURE, Column.GiftCertificateColumn.TABLE_NAME, ID)) == giftCertificate.getId()) {
                long tagId = rs.getLong(String.format(STRUCTURE, Column.TagColumn.TABLE_NAME, ID));
                String tagName = rs.getString(String.format(STRUCTURE, Column.TagColumn.TABLE_NAME, NAME));
                if (tagId != 0) {
                    tags.add(new Tag(tagId, tagName, null));
                }
                rs.next();
            }
            if (!tags.isEmpty()) {
                giftCertificate.setTagList(tags);
            }
            giftCertificates.add(giftCertificate);
        }
        return giftCertificates;
    }
}
