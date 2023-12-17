package com.epam.esm.dao.mapper;

import com.epam.esm.constants.Constants;
import com.epam.esm.model.GiftCertificateTag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.esm.constants.Constants.ID;

public class GiftCertificateTagMapper implements RowMapper<GiftCertificateTag> {

    @Override
    public GiftCertificateTag mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificateTag tag = new GiftCertificateTag();
        tag.setId(rs.getLong(ID));
        tag.setGiftCertificateId(rs.getLong(Constants.GiftCertificateTagColumn.GIFT_ID));
        tag.setTagId(rs.getLong(Constants.GiftCertificateTagColumn.TAG_ID));
        return tag;
    }
}
