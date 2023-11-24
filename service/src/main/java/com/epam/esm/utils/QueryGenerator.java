package com.epam.esm.utils;

import com.epam.esm.exceptions.WrongParameterException;

import static com.epam.esm.constants.QueryParams.DATE;
import static com.epam.esm.constants.QueryParams.NAME;

public class QueryGenerator {
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
            query.append(AND) ;
        }
        else {
            query.append(WHERE);
            filtering = true;
        }
    }

    public void addSelectByDescription(String description) {
        checkingOnFilter();
        query.append(String.format(SELECT_BY_DESCRIPTION, "%" + description));
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
        }
        else {
            throw new WrongParameterException();
        }
    }
}
