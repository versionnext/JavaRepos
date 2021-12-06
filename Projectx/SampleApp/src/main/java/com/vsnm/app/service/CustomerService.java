package com.vsnm.app.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.vsnm.app.model.Customer;
import com.vsnm.app.repository.CustomerRepository;
import com.vsnm.framework.service.BaseJpaService;

@Service
public class CustomerService extends BaseJpaService<Customer, Long> {

	@Autowired
	private CustomerRepository customerRepository;

	protected CustomerService(CustomerRepository inRepository) {
		super(inRepository);
	}

	public List<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}

	protected ExampleMatcher getExampleMatcher(Customer filter)
			throws Exception {
		ExampleMatcher matcher = ExampleMatcher.matching();
		if (filter.getName() != null) {
			matcher = matcher.withMatcher("name", contains().ignoreCase());
		}
		return matcher;
	}

	/*protected Specification<Customer> getSpec(Map<String, Object> filters) {
		Specification<Customer> spec = new Specification<Customer>() {

			private static final long serialVersionUID = 3824728377904432826L;

			@Override
			public Predicate toPredicate(Root<Customer> entityRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> predicates = new ArrayList<Predicate>();
				if (filters != null) {
					for (String key : filters.keySet()) {
						if (key != null) {
							if ("name".equalsIgnoreCase(key)) {
								predicates.add(cb.like(cb.lower(entityRoot
										.<String> get(key)), ("%"
										+ (String) filters.get(key) + "%")
										.toLowerCase()));
							} else {
								if (filters.get(key) instanceof String)
									predicates.add(cb.equal(cb.lower(entityRoot
											.<String> get(key)),
											((String) filters.get(key))
													.toLowerCase()));
								else
									predicates.add(cb.equal(
											entityRoot.<String> get(key),
											filters.get(key)));
							}
						}
					}
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		return spec;
	}*/
}
