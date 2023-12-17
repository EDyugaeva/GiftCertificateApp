package com.epam.esm.dao.impl;

import com.epam.esm.config.AppTestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@ActiveProfiles("test")
public class BaseTest {

    @Autowired
    private DataSource dataSource;

    @AfterTestClass
    public void afterClass() {
        Resource initData = new ClassPathResource("schema.sql");
        Resource fillData = new ClassPathResource("test-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initData, fillData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
}
