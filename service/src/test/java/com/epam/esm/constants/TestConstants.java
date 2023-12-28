package com.epam.esm.constants;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.constants.TestConstants.GiftCertificatesTestConstants.*;
import static com.epam.esm.constants.TestConstants.TagTestConstants.TAG_SET;

public class TestConstants {
    public static final Long ACTUAL_ID = 1L;
    public static final Long NOT_EXISTED_ID = 100L;
    public static final PageRequest PAGEABLE = PageRequest.of(0, 5);

    public static class GiftCertificatesTestConstants {
        public static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1L, "gift_certificate_1", "description 1", 150.5f, 5,
                LocalDateTime.of(2018, 1, 1, 0, 0), null, TAG_SET);
        public static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2L, "gift_certificate_2", "description 2", 150.5f, 5,
                LocalDateTime.of(2018, 2, 1, 0, 0), LocalDateTime.of(2018, 2, 5, 0, 5), TAG_SET);
        public static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3L, "gift_certificate_3", "description 3", 140f, 4,
                LocalDateTime.of(2018, 3, 1, 0, 0), LocalDateTime.of(2018, 3, 9, 0, 5), null);
        public static final GiftCertificate GIFT_CERTIFICATE_2_BEFORE_UPDATE = new GiftCertificate(2L, "gift_certificate_2_before", "description 2", 150.5f, 5,
                LocalDateTime.of(2018, 2, 1, 0, 0), null, TAG_SET);

        public static final GiftCertificate GIFT_CERTIFICATE_2_TO_UPDATE = new GiftCertificate(2L, "gift_certificate_2", "description 2", 150.5f, 5,
                LocalDateTime.of(2018, 2, 1, 0, 0), null, TAG_SET);
        public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        public static final Page<GiftCertificate> GIFT_CERTIFICATE_PAGE = new PageImpl<>(GIFT_CERTIFICATE_LIST);
        public static final float POSITIVE_PRICE = 50f;
        public static final int POSITIVE_DURATION = 10;
    }

    public static class TagTestConstants {
        public static final Tag TAG_1 = new Tag(1L, "tag_1");
        public static final Tag TAG_2 = new Tag(2L, "tag_2");
        public static final Set<Tag> TAG_SET = Arrays.stream(new Tag[]{TAG_1, TAG_2}).collect(Collectors.toSet());
        public static final List<Tag> TAG_LIST = Arrays.stream(new Tag[]{TAG_1, TAG_2}).collect(Collectors.toList());
        public static final Page<Tag> TAG_PAGE = new PageImpl<>(new ArrayList<>(TAG_SET));
    }

    public static class UserConstants {
        public static final User MOCK_USER_1 = new User(1L, "John", "Doe", "john@example.com",
                "password1", new ArrayList<>());

        public static final User MOCK_USER_2 = new User(2L, "Jane", "Doe", "jane@example.com",
                "password2", new ArrayList<>());

        public static final User MOCK_USER_3 = new User(3L, "Alice", "Smith", "alice@example.com",
                "password3", new ArrayList<>());

        public static final User MOCK_USER_4 = new User(4L, "Bob", "Johnson", "bob@example.com",
                "password4", new ArrayList<>());

        public static final User MOCK_USER_5 = new User(5L, "Eva", "Williams", "eva@example.com",
                "password5", new ArrayList<>());
        public static final List<User> USER_LIST = Arrays.asList(MOCK_USER_1, MOCK_USER_2, MOCK_USER_3, MOCK_USER_4, MOCK_USER_5);
        public static final Page<User> USER_PAGE = new PageImpl<>(USER_LIST);
    }

    public static class OrderConstants {
        public static final Order ORDER_1 = new Order(1L, LocalDateTime.now(), 50.0f,
                GIFT_CERTIFICATE_1, UserConstants.MOCK_USER_1);

        public static final Order ORDER_2 = new Order(2L, LocalDateTime.now(), 75.0f,
                GIFT_CERTIFICATE_2, UserConstants.MOCK_USER_1);

        public static final Order ORDER_3 = new Order(3L, LocalDateTime.now(), 100.0f,
                GIFT_CERTIFICATE_3, UserConstants.MOCK_USER_1);
        public static final List<Order> ORDER_LIST = Arrays.asList(ORDER_1, ORDER_2, ORDER_3);
        public static final Page<Order> ORDER_PAGE = new PageImpl<>(ORDER_LIST);

    }
}
