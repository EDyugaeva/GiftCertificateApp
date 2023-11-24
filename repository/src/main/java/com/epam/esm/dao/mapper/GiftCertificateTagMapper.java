package com.epam.esm.dao.mapper;

import com.epam.esm.dao.Column;
import com.epam.esm.model.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.dao.Column.ID;

public class GiftCertificateTagMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificateTag tag = new GiftCertificateTag();
        tag.setId(rs.getLong(ID));
        tag.setGiftCertificateId(rs.getLong(Column.GiftCertificateTagColumn.GIFT_ID));
        tag.setTagId(rs.getLong(Column.GiftCertificateTagColumn.TAG_ID));
        return tag;
    }
}
