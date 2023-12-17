package com.epam.esm.dao.mapper;

import com.epam.esm.constants.Constants;
import com.epam.esm.model.Tag;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        tag.setId(rs.getLong(Constants.ID));
        tag.setName(rs.getString(Constants.NAME));
        return tag;
    }
}
