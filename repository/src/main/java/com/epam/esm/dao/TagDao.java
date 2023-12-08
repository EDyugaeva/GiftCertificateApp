package com.epam.esm.dao;

import com.epam.esm.model.Tag;

public interface TagDao extends CRDDao<Tag> {
    Tag getTagByName(String name)  ;
}
