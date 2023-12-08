package com.epam.esm.utils;

import com.epam.esm.dao.Constants;
import com.epam.esm.exceptions.WrongParameterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static com.epam.esm.exceptions.ExceptionCodesConstants.NOT_SUPPORTED;

@Slf4j
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
    private static final String DESC = "DESC";
    public static final String DATE = "date";
    public static final String SPACE = " ";

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
            query.append(SPACE).append(",").append(getSortParameter(sortValue));
        } else {
            query.append(ORDER_BY).append(getSortParameter(sortValue));
            sorting = true;
        }
    }

    private String getSortParameter(String sortValue) {
        StringBuilder sortingParam = new StringBuilder();
        if (StringUtils.containsIgnoreCase(sortValue, Constants.NAME)) {
            log.info("Sorting by name");
            sortingParam.append(String.format(Constants.STRUCTURE, Constants.GiftCertificateColumn.ALIAS_TABLE_NAME, Constants.NAME));
        } else {
            log.info("Sorting by date");
            sortingParam.append(Constants.GiftCertificateColumn.CREATE_DATE);
        }
        if (StringUtils.containsIgnoreCase(sortValue, DESC)) {
            log.info("Sorting desc");
            sortingParam.append(SPACE).append(DESC).append(SPACE);
        }
        return sortingParam.toString();
    }

    public void createFilter(Map<String, String> filteredBy, QueryGenerator queryGenerator) throws WrongParameterException {
        if (filteredBy != null) {
            for (Map.Entry<String, String> entry : filteredBy.entrySet()) {
                if (entry.getValue() != null) {
                    switch (entry.getKey()) {
                        case Constants.NAME:
                            queryGenerator.addSelectByName(entry.getValue());
                            break;
                        case Constants.GiftCertificateColumn.DESCRIPTION:
                            queryGenerator.addSelectByDescription(entry.getValue());
                            break;
                        case Constants.GiftCertificateColumn.TAG_NAME_QUERY:
                            queryGenerator.addSelectByTagName(entry.getValue());
                            break;
                        default:
                            log.debug("Not supported filter = {}", filteredBy);
                            throw new WrongParameterException("Non supported filtering parameter", NOT_SUPPORTED);
                    }
                }
            }
        }
    }

    public void createSorting(List<String> orderingBy, QueryGenerator queryGenerator) throws WrongParameterException {
        if (orderingBy != null) {
            for (String s : orderingBy) {
                if (!StringUtils.containsIgnoreCase(s, DATE)
                        && !StringUtils.containsIgnoreCase(s, Constants.NAME)) {
                    log.debug("Not supported ordering = {}", s);
                    throw new WrongParameterException("Not supported ordering parameter", NOT_SUPPORTED);
                }
                queryGenerator.addSorting(s);
            }
        }
    }
}
