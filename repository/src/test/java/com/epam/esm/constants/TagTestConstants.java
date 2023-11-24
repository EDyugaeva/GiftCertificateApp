package com.epam.esm.constants;

import com.epam.esm.model.Tag;

import java.util.Arrays;
import java.util.List;

public class TagTestConstants {
    public static final Tag TAG_1 = new Tag(1L, "tag_name_1", null);
    public static final Tag TAG_2 = new Tag(2L, "tag_name_2", null);
    public static final Tag TAG_3 = new Tag(3L, "tag_name_3", null);
    public static final Tag TAG_4 = new Tag(4L, "tag_name_4", null);
    public static final Tag TAG_5 = new Tag(5L, "tag_name_5", null);
    public static final List<Tag> TAG_LIST = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
    public static final Tag NEW_TAG = new Tag("new tag");
    public static final Long ABSENT_ID = 7L;
    public static final Long NEW_ID = 6L;
}
