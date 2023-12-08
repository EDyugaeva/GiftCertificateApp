package com.epam.esm.dao;

public class Column {
    public static final String STRUCTURE = "%s.%s";
    public static final String ID = "id";
    public static final String NAME = "name";

    public static class GiftCertificateColumn {
        //TODO переименовать, т.к. это short name
        public static final String TABLE_NAME = "gc";
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String DURATION = "duration";
        public static final String CREATE_DATE = "create_date";
        public static final String LAST_UPDATE_DATE = "last_update_date";
    }

    public static class TagColumn{
        public static final String TABLE_NAME = "tag";

    }

    public static class GiftCertificateTagColumn {
        public static final String TABLE_NAME = "gct";

        public static final String TAG_ID = "tag_id";
        public static final String GIFT_ID = "gift_id";
    }
}
