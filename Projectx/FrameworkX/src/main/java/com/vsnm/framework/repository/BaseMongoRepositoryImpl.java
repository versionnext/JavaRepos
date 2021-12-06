package com.vsnm.framework.repository;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.BaseMongoModel;

@NoRepositoryBean
public class BaseMongoRepositoryImpl<T extends BaseMongoModel, ID extends Serializable>
		extends SimpleMongoRepository<T, ID> implements
		BaseMongoRepository<T, ID> {

	private final MongoEntityInformation<T, ID> entityInformation;

	private final MongoOperations mongoOperations;

	public BaseMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.entityInformation = metadata;
		this.mongoOperations = mongoOperations;
	}

	@Override
	@Deprecated
	public void delete(T entity) {
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
	public void deleteAll(Iterable<? extends T> entities) {
	}

	@Override
	@Deprecated
	public <S extends T> S save(S entity) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return null;
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
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> Optional<S> findOne(Example<S> example) {
		return null;
	}

	@Override
	@Deprecated
	public Optional<T> findById(ID id) {
		return null;
	}

	@Override
	@Deprecated
	public Iterable<T> findAllById(Iterable<ID> ids) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll(Sort sort) {
		return null;
	}

	@Override
	@Deprecated
	public Page<T> findAll(Pageable pageable) {
		return null;
	}

	@Override
	@Deprecated
	public List<T> findAll() {
		return null;
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
	public <S extends T> long count(Example<S> example) {
		return 0;
	}

	@Override
	@Deprecated
	public long count() {
		return 0;
	}

	@Override
	@Deprecated
	public <S extends T> List<S> insert(Iterable<S> entities) {
		return null;
	}

	@Override
	@Deprecated
	public <S extends T> S insert(S entity) {
		return null;
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
				mongoOperations.insert(inEntity);
			} else {
				inEntity.setCreatedBy(oldEntity.getCreatedBy());
				inEntity.setCreatedOn(oldEntity.getCreatedOn());
				mongoOperations.save(inEntity);
			}
		} else {
			inEntity.setCreatedOn(new Date());
			mongoOperations.insert(inEntity);
		}
		return inEntity;
	}

	@Override
	public T findById(ID inEntityId, BaseModel baseModel) throws Exception {
		T entity = mongoOperations.findOne(
				Query.query(Criteria.where("id").is(inEntityId).and("deleted")
						.is(false)), entityInformation.getJavaType());
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
	public Page<T> search(T filter, ExampleMatcher matcher, Pageable pageable) {
		return super.findAll(getTenancyExample(filter, matcher), pageable);
		/*mongoOperations.find(
				Query.query(Criteria.where("clientId")
						.is(baseModel.getClientId()).and("tenantId")
						.is(baseModel.getTenantId()).and("deleted").is(false)),
				entityInformation.getJavaType());*/
	}

	@Override
	public void deleteById(ID inEntityId, BaseModel baseModel) throws Exception {
		T entity = findById(inEntityId, baseModel);
		if (entity != null) {
			entity.setDeleted(true);
			mongoOperations.save(entity);
		}
	}
	
	private Example<T> getTenancyExample(T filter, ExampleMatcher matcher) {
		matcher = matcher.withMatcher("clientId", exact().ignoreCase())
				.withMatcher("tenantId", exact().ignoreCase());
		return Example.of(filter, matcher);
	}



}
