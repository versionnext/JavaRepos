package com.vsnm.framework.repository;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vsnm.framework.model.BaseJpaModel;
import com.vsnm.framework.model.BaseModel;

@NoRepositoryBean
public class BaseJpaRepositoryImpl<T extends BaseJpaModel, ID extends Serializable>
		extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

	private final JpaEntityInformation<T, ID> entityInformation;

	private final EntityManager entityManager;

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation,
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	@Override
	@Deprecated
	public void deleteById(ID id) {
	}

	@Override
	@Deprecated
	public void deleteAll() {
	}

	@Override
	@Deprecated
	public void deleteAllInBatch() {
	}

	@Override
	@Deprecated
	public void deleteInBatch(Iterable<T> entities) {

	}

	@Override
	@Deprecated
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> S saveAndFlush(S entity) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> long count(Example<S> example) {
		return 0;
	}

	@Override
	@Deprecated
	public long count(Specification<T> spec) {
		return 0;
	}

	@Override
	@Deprecated
	public long count() {
		return 0;
	}

	@Override
	@Deprecated
	public <S extends T> boolean exists(Example<S> example) {
		return false;
	}

	@Override
	@Deprecated
	public boolean existsById(ID id) {
		return false;
	}

	@Override
	@Deprecated
	public <S extends T> List<S> findAll(Example<S> example) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll(Specification<T> spec) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll(Specification<T> spec, Sort sort) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAllById(Iterable<ID> ids) {
		return null;
	}

	@Override
	@Deprecated
	public Optional<T> findById(ID id) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> Optional<S> findOne(Example<S> example) {
		return null;
	}

	@Override
	@Deprecated
	public Optional<T> findOne(Specification<T> spec) {
		return null;
	}

	@Override
	@Deprecated
	public T getOne(ID id) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll() {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> S save(S entity) {
		return null;
	}

	@Override
	@Deprecated
	public Page<T> findAll(Pageable pageable) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll(Sort sort) {
		return null;
	}

	@Override
	@Deprecated
	public void deleteAll(Iterable<? extends T> entities) {

	}

	@Override
	@Deprecated
	public void delete(T entity) {

	}

	@Override
	public T saveEntity(T inEntity) throws Exception {
		inEntity.setDeleted(false);
		inEntity.setUpdatedOn(new Date());
		ID id = entityInformation.getId(inEntity);
		if (id != null) {
			BaseModel baseModel = new BaseModel();
			baseModel.setClientId(inEntity.getClientId());
			baseModel.setTenantId(inEntity.getTenantId());
			T oldEntity = findById(id, baseModel);
			if (oldEntity == null) {
				inEntity.setCreatedOn(new Date());
				entityManager.persist(inEntity);
			} else {
				inEntity.setCreatedBy(oldEntity.getCreatedBy());
				inEntity.setCreatedOn(oldEntity.getCreatedOn());
				entityManager.merge(inEntity);
			}
		} else {
			inEntity.setCreatedOn(new Date());
			entityManager.persist(inEntity);
		}
		entityManager.flush();
		return inEntity;
	}

	@Override
	public T findById(ID inEntityId, BaseModel baseModel) throws Exception {
		T entity = entityManager.find(getDomainClass(), inEntityId);
		if (entity != null
				&& baseModel != null
				&& entity.getClientId() != null
				&& baseModel.getClientId() != null
				&& entity.getClientId().equalsIgnoreCase(
						baseModel.getClientId())
				&& entity.getTenantId() != null
				&& baseModel.getTenantId() != null
				&& entity.getTenantId().equalsIgnoreCase(
						baseModel.getTenantId())) {
			return entity;
		}
		return null;
	}

	@Override
	public void deleteById(ID inEntityId, BaseModel baseModel) throws Exception {
		T entity = findById(inEntityId, baseModel);
		if (entity != null) {
			entity.setDeleted(true);
			entityManager.merge(entity);
		}
	}

	@Override
	@Deprecated
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	@Deprecated
	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return null;
	}

	@Override
	public Page<T> search(T filter, ExampleMatcher matcher, Pageable pageable) {

		return super.findAll(getTenancyExample(filter, matcher), pageable);
	}

	private Example<T> getTenancyExample(T filter, ExampleMatcher matcher) {
		matcher = matcher.withMatcher("clientId", exact().ignoreCase())
				.withMatcher("tenantId", exact().ignoreCase());
		return Example.of(filter, matcher);
	}

	/*private Specification<T> getTenancySpec(BaseModel baseModel) {
		Specification<T> spec = new Specification<T>() {

			private static final long serialVersionUID = 3824728377904432826L;

			@Override
			public Predicate toPredicate(Root<T> entityRoot,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				ArrayList<Predicate> predicates = new ArrayList<Predicate>();
				if (baseModel != null) {
					predicates.add(cb.equal(
							cb.lower(entityRoot.<String> get("clientId")),
							baseModel.getClientId().toLowerCase()));
					predicates.add(cb.equal(
							cb.lower(entityRoot.<String> get("tenantId")),
							baseModel.getTenantId().toLowerCase()));
				}
				return cb.and(predicates.toArray(new Predicate[0]));
			}
		};
		return spec;
	}*/

}
