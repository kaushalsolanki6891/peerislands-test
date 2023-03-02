package com.peerislands.utils;

import com.peerislands.request.JoinColumnProps;
import com.peerislands.request.SearchFilter;
import com.peerislands.request.SearchQuery;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SpecificationUtil {
    public static <T> Specification<T> bySearchQuery(SearchQuery searchQuery, Class<T> clazz) {

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

            if (searchFilters != null && !searchFilters.isEmpty()) {

                for (final SearchFilter searchFilter : searchFilters) {
                    addPredicates(predicates, searchFilter, criteriaBuilder, root);
                }
            }

            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

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
        switch (searchFilter.getOperator()) {
            case "=":
                predicates.add(criteriaBuilder.equal(expression, searchFilter.getFieldValue()));
                break;
            case "LIKE":
                predicates.add(criteriaBuilder.like(expression, "%" + searchFilter.getFieldValue() + "%"));
                break;
            case "IN":
                predicates.add(criteriaBuilder.in(expression).value(searchFilter.getFieldValue()));
                break;
            case "BETWEEN":
                String[] values = ((String)searchFilter.getFieldValue()).split("\\,");
                predicates.add(criteriaBuilder.between(expression, values[0],values[1]));
                break;
            case ">":
                predicates.add(criteriaBuilder.greaterThan(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case "<":
                predicates.add(criteriaBuilder.lessThan(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case ">=":
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case "<=":
                predicates.add(criteriaBuilder.lessThanOrEqualTo(expression, (Comparable) searchFilter.getFieldValue()));
                break;
            case "!":
                predicates.add(criteriaBuilder.notEqual(expression, searchFilter.getFieldValue()));
                break;
            case "IsNull":
                predicates.add(criteriaBuilder.isNull(expression));
                break;
            case "NotNull":
                predicates.add(criteriaBuilder.isNotNull(expression));
                break;
            default:
                System.out.println("Predicate is not matched");
                throw new IllegalArgumentException(searchFilter.getOperator() + " is not a valid predicate");
        }

    }
}