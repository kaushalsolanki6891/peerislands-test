package com.peerislands.utils;

import com.peerislands.request.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecificationUtil {

    private static Logger log = LoggerFactory.getLogger(SpecificationUtil.class);

    public static <T> Specification<T> prepareSearchQuery(SearchQueryRequest searchQueryRequest, Class<T> clazz) {
        List<SearchQuery> searchQueries = searchQueryRequest.getSearchQueries();
        if (!CollectionUtils.isEmpty(searchQueries)) {
            SearchQuery parentSearchQuery = searchQueries.get(0);
            Specification specification = Specification.where(bySearchQuery(parentSearchQuery, clazz));
            searchQueries.remove(parentSearchQuery);

            for (final SearchQuery searchQuery : searchQueries) {
                String parentOperator = searchQuery.getParentOperator().toUpperCase();
                QueryOperator queryOperator = QueryOperator.valueOf(parentOperator);
                if (StringUtils.isNotEmpty(parentOperator)) {
                    switch (queryOperator) {
                        case OR:
                            specification = specification.or(bySearchQuery(searchQuery, clazz));
                            break;
                        case AND:
                            specification = specification.and(bySearchQuery(searchQuery, clazz));
                            break;
                        default:
                            log.info("SpecificationUtil.prepareSearchQuery() --> Exit");
                            throw new IllegalArgumentException(parentOperator + "is not valida");
                    }
                }
            }
            return specification;
        }
        return Specification.where(bySearchQuery(searchQueries.get(0), clazz));
    }

    public static <T> Specification<T> bySearchQuery(SearchQuery searchQuery, Class<T> clazz) {
        log.info("SpecificationUtil.bySearchQuery() --> start");

        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Add Predicates for tables to be joined
            List<JoinColumnProps> joinColumnProps = searchQuery.getJoinColumnProps();

            if (joinColumnProps != null && !joinColumnProps.isEmpty()) {
                for (JoinColumnProps joinColumnProp : joinColumnProps) {
                    addJoinColumnProps(predicates, joinColumnProp, criteriaBuilder, root);
                }
            }

            List<SearchFilter> searchFilters = searchQuery.getSearchFilter();

            if (Objects.nonNull(searchFilters) && !searchFilters.isEmpty()) {
                for (final SearchFilter searchFilter : searchFilters) {
                    addPredicates(predicates, searchFilter, criteriaBuilder, root);
                }
            }

            if (!CollectionUtils.isEmpty(searchQuery.getJoinColumnProps())) {
                criteriaQuerySortOnJoinColumn(root, query, criteriaBuilder, searchQuery.getJoinColumnProps());
            }

            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            if (QueryOperator.OR.name().equals(searchQuery.getChildOperator()))
                return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };
    }

    private static <T> void addJoinColumnProps(List<Predicate> predicates, JoinColumnProps joinColumnProp,
                                               CriteriaBuilder criteriaBuilder, Root<T> root) {

        SearchFilter searchFilter = joinColumnProp.getSearchFilter();
        Join<Object, Object> joinParent = root.join(joinColumnProp.getJoinColumnName());

        String property = searchFilter.getFieldName();
        Path expression = joinParent.get(property);

        addPredicate(predicates, searchFilter, criteriaBuilder, expression);

    }

    private static <T> void addPredicates(List<Predicate> predicates, SearchFilter searchFilter,
                                          CriteriaBuilder criteriaBuilder, Root<T> root) {
        String property = searchFilter.getFieldName();
        Path expression = root.get(property);

        addPredicate(predicates, searchFilter, criteriaBuilder, expression);

    }

    private static void addPredicate(List<Predicate> predicates, SearchFilter searchFilter,
                                     CriteriaBuilder criteriaBuilder, Path expression) {
        QueryOperator queryOperator = QueryOperator.valueOf(searchFilter.getOperator().toUpperCase());
        switch (queryOperator) {
            case EQUALS:
                predicates.add(criteriaBuilder.equal(expression, searchFilter.getFieldValue()));
                break;
            case LIKE:
                predicates.add(criteriaBuilder.like(expression, "%" + searchFilter.getFieldValue() + "%"));
                break;
            case IN:
                predicates.add(criteriaBuilder.in(expression).value(searchFilter.getFieldValue()));
                break;
            case GRATER_THAN:
                predicates.add(criteriaBuilder.greaterThan(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case LESS_THAN:
                predicates.add(criteriaBuilder.lessThan(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case GRATER_THAN_EQ:
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case LESS_THAN_EQ:
                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case NOT_EQ:
                predicates.add(criteriaBuilder.notEqual(expression, searchFilter.getFieldValue()));
                break;
            case BETWEEN:
                String[] values = ((String) searchFilter.getFieldValue()).split("\\,");
                predicates.add(criteriaBuilder.between(expression, Integer.parseInt(values[0]), Integer.parseInt(values[1])));
                break;
            case IS_NULL:
                predicates.add(criteriaBuilder.isNull(expression));
                break;
            case NOT_NULL:
                predicates.add(criteriaBuilder.isNotNull(expression));
                break;
            case NOT_IN:
                predicates.add(criteriaBuilder.in(expression).value(searchFilter.getFieldValue()).not());
                break;
            default:
                System.out.println("Predicate is not matched");
                throw new IllegalArgumentException(searchFilter.getOperator() + " is not a valid predicate");
        }

    }

    private static <T> void criteriaQuerySortOnJoinColumn(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<JoinColumnProps> joinColumnProps) {
        for (JoinColumnProps joinColumnProp : joinColumnProps) {
            SearchFilter searchFilter = joinColumnProp.getSearchFilter();
            QueryOperator queryOperator = QueryOperator.valueOf(searchFilter.getOperator().toUpperCase());
            switch (queryOperator) {
                case ASC:
                    query.orderBy(criteriaBuilder.asc(root.join(joinColumnProp.getJoinColumnName()).get(searchFilter.getFieldName())));
                case DESC:
                    query.orderBy(criteriaBuilder.desc(root.join(joinColumnProp.getJoinColumnName()).get(searchFilter.getFieldName())));
            }
        }
    }


    public static PageRequest getPageRequest(SearchQueryRequest searchQueryRequest) {

        int pageNumber = searchQueryRequest.getPageNumber();
        int pageSize = searchQueryRequest.getPageSize();

        List<Sort.Order> orders = new ArrayList<>();

        List<String> ascProps = searchQueryRequest.getSortOrder().getAscendingOrder();

        if (ascProps != null && !ascProps.isEmpty()) {
            for (String prop : ascProps) {
                orders.add(Sort.Order.asc(prop));
            }
        }

        List<String> descProps = searchQueryRequest.getSortOrder().getDescendingOrder();

        if (descProps != null && !descProps.isEmpty()) {
            for (String prop : descProps) {
                orders.add(Sort.Order.desc(prop));
            }

        }

        Sort sort = Sort.by(orders);

        return PageRequest.of(pageNumber, pageSize, sort);

    }

}