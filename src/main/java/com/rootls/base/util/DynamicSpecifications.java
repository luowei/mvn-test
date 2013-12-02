package com.rootls.base.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-18
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public abstract class DynamicSpecifications {

    private static final ConversionService conversionService = new GenericConversionService();

    public static <T> Specification<T> byPropertyFilter(final Collection<UrlBuilder.PropertyFilter> filterList, final Class<T> clazz) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
//                query = cb.createQuery(clazz);
//                tRoot = query.from(clazz);
                for (UrlBuilder.PropertyFilter filter : filterList) {
                    Path expression = tRoot.get(filter.getName());
                    Class attributeClass = expression.getJavaType();
                    if (!attributeClass.equals(String.class) && filter.getValue() instanceof String
                            && conversionService.canConvert(String.class, attributeClass)) {
                        filter.setValue(conversionService.convert(filter.getValue(), attributeClass));
                    }

                    switch (filter.getType()) {

                        case EQ:
                            predicates.add(cb.equal(expression, filter.getValue()));
                            break;
                        case LIKE:
                            predicates.add(cb.like(expression, "%" + filter.getValue() + "%"));
                            break;
                        case GT:
                            predicates.add(cb.greaterThan(expression, (Comparable) filter.getValue()));
                            break;
                        case GE:
                            predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                        case LT:
                            predicates.add(cb.lessThan(expression, (Comparable) filter.getValue()));
                            break;
                        case LE:
                            predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                    }

                }
                if (predicates.size() > 0) {
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return cb.conjunction();
//                return cb.createQuery(clazz).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

            }
        };
    }

    public static <T> Specification<T> byPropertyFilter2(final Collection<UrlBuilder.PropertyFilter> filterList, final Class<T> clazz) {

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> tRoot, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate where = cb.conjunction();
                query = cb.createQuery(clazz);
                tRoot = query.from(clazz);
                for (UrlBuilder.PropertyFilter filter : filterList) {
                    Path expression = tRoot.get(filter.getName());
                    Class attributeClass = expression.getJavaType();
                    if (!attributeClass.equals(String.class) && filter.getValue() instanceof String
                            && conversionService.canConvert(String.class, attributeClass)) {
                        filter.setValue(conversionService.convert(filter.getValue(), attributeClass));
                    }


                    switch (filter.getType()) {

                        case EQ:
                            where = cb.and(where, cb.equal(expression, filter.getValue()));
                            break;
                        case LIKE:
                            where = cb.and(where, (cb.like(expression, "%" + filter.getValue() + "%")));
                            break;
                        case GT:
                            where = cb.and(where, cb.greaterThan(expression, (Comparable) filter.getValue()));
                            break;
                        case GE:
                            where = cb.and(where, cb.greaterThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                        case LT:
                            where = cb.and(where, cb.lessThan(expression, (Comparable) filter.getValue()));
                            break;
                        case LE:
                            where = cb.and(where, cb.lessThanOrEqualTo(expression, (Comparable) filter.getValue()));
                            break;
                    }

                }
//                query.where(where);
                return where;
//                return cb.createQuery(clazz).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

            }
        };
    }

}
