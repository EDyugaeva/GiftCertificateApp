package com.epam.esm.utils;

import com.epam.esm.constants.QueryParams;
import com.epam.esm.exceptions.WrongParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.epam.esm.constants.QueryParams.*;
import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_SUPPORTED;

public class QueryGenerator {

    Logger logger = LoggerFactory.getLogger(QueryGenerator.class);

    private static final String SELECT_BY_TAG_NAME = " gc.id in (SELECT gc.id" +
            "             FROM gift_certificate gc" +
            "                      JOIN gift_certificate_tag gct ON gc.id = gct.gift_id" +
            "                      JOIN tag ON gct.tag_id = tag.id" +
            "             WHERE tag.name = '%s' )";
    private static final String SELECT_BY_NAME = " gc.name ILIKE '%s'";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String SELECT_BY_DESCRIPTION = " gc.description ILIKE '%s'";
    private static final String ORDER_BY = " order by ";
    private static final String DESC = " DESC";
    private static final String NAME_COLUMN = "gc.name";
    private static final String DATE_COLUMN = "create_date";

    private StringBuilder query;
    private boolean sorting;
    private boolean filtering;

    public QueryGenerator() {
        this.query = new StringBuilder();
        sorting = false;
        filtering = false;
    }

    public String getQuery() {
        return query.toString();
    }

    public void addSelectByName(String name) {
        checkingOnFilter();
        query.append(String.format(SELECT_BY_NAME, "%" + name + "%"));
    }

    private void checkingOnFilter() {
        if (filtering) {
            query.append(AND);
        } else {
            query.append(WHERE);
            filtering = true;
        }
    }

    public void addSelectByDescription(String description) {
        checkingOnFilter();
        query.append(String.format(SELECT_BY_DESCRIPTION, "%" + description + "%"));
    }

    public void addSelectByTagName(String tagName) {
        checkingOnFilter();
        query.append(String.format(SELECT_BY_TAG_NAME, tagName));
    }

    public void addSorting(String sortValue) {
        if (sorting) {
            query.append(" ,").append(getSortParameter(sortValue));
        } else {
            query.append(ORDER_BY).append(getSortParameter(sortValue));
            sorting = true;
        }
    }

    public void addDescOrdering() {
        query.append(DESC);
    }

    private String getSortParameter(String sortValue) {
        if (sortValue.equalsIgnoreCase(NAME)) {
            return NAME_COLUMN;
        } else if (sortValue.equalsIgnoreCase(DATE)) {
            return DATE_COLUMN;
        } else {
            throw new WrongParameterException("Not supported sorting parameter", NOT_SUPPORTED);
        }
    }

    public void createFilter(Map<String, String> filteredBy, QueryGenerator queryGenerator) {
        if (filteredBy != null) {
            for (Map.Entry<String, String> entry : filteredBy.entrySet()) {
                if (entry.getValue() != null) {
                    switch (entry.getKey()) {
                        case QueryParams.NAME:
                            queryGenerator.addSelectByName(entry.getValue());
                            break;
                        case QueryParams.DESCRIPTION:
                            queryGenerator.addSelectByDescription(entry.getValue());
                            break;
                        case QueryParams.TAG_NAME:
                            queryGenerator.addSelectByTagName(entry.getValue());
                            break;
                        default:
                            logger.debug("Not supported filter = {}", filteredBy);
                            throw new WrongParameterException("Non supported filtering parameter", NOT_SUPPORTED);
                    }
                }
            }
        }
    }

    public void createSorting(List<String> orderingBy, QueryGenerator queryGenerator) {
        if (orderingBy != null) {
            for (String s : orderingBy) {
                if (!s.equalsIgnoreCase(QueryParams.DATE) && !s.equalsIgnoreCase(QueryParams.NAME)) {
                    logger.debug("Not supported ordering = {}", s);
                    throw new WrongParameterException("Not supported ordering parameter", NOT_SUPPORTED);
                }
                queryGenerator.addSorting(s);
            }
        }
    }

    public static void createOrder(String order, QueryGenerator queryGenerator) {
        if (order != null && order.equalsIgnoreCase("desc")) {
            queryGenerator.addDescOrdering();
        }
    }
}
