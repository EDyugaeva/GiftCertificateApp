package com.epam.esm;

import com.epam.esm.config.AppConfig;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        TagDaoImpl tagDaoImpl = (TagDaoImpl) applicationContext.getBean("tagDaoImpl");
        GiftCertificateDaoImpl giftCertificateDao = (GiftCertificateDaoImpl) applicationContext.getBean("giftCertificateDaoImpl");

        System.out.println(tagDaoImpl.getTag(1));
        System.out.println(tagDaoImpl.getTag(2));
        System.out.println(tagDaoImpl.getTags());
    }
}