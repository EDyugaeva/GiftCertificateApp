package com.epam.esm.constants;

import com.epam.esm.model.Tag;

import java.util.Arrays;
import java.util.List;

public class TagTestConstants {
    public static final Tag TAG_1 = new Tag(1L, "tag_1", null);
    public static final Tag TAG_2 = new Tag(2L, "tag_2", null);
    public static final List<Tag> TAG_LIST = Arrays.asList(TAG_1, TAG_2);
}
