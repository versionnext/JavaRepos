package com.vsnm.framework.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.lang.reflect.Field;

import org.springframework.data.domain.ExampleMatcher;

public class BaseService<E> {

	protected ExampleMatcher getExampleMatcher(E filter) throws Exception {
		ExampleMatcher matcher = ExampleMatcher.matching();
		for (Field field : filter.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(filter);
			if (value != null) {
				matcher.withMatcher(field.getName(), exact());
			}
		}
		return matcher;
	}
	
	/*
	 protected Specification<E> getSpec(Map<String, Object> filters) {

		Specification<E> spec = new Specification<E>() {
			private static final long serialVersionUID = 3824728377904432826L;

			@Override
			public Predicate toPredicate(Root<E> entityRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> predicates = new ArrayList<Predicate>();
				if (filters != null) {
					for (String key : filters.keySet()) {
						if (key != null) {
							if (filters.get(key) instanceof String)
								predicates.add(cb.equal(cb.lower(entityRoot
										.<String> get(key)), ((String) filters
										.get(key)).toLowerCase()));
							else
								predicates.add(cb.equal(
										entityRoot.<String> get(key),
										filters.get(key)));
						}
					}
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		return spec;
	} */
}
