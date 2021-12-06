package com.vsnm.framework.repository;

import java.io.Serializable;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.BaseMongoModel;

@NoRepositoryBean
public interface BaseMongoRepository<T extends BaseMongoModel, ID extends Serializable>
		extends MongoRepository<T, ID> {
	T saveEntity(T inEntity) throws Exception;

	T findById(ID inEntityId, BaseModel baseModel) throws Exception;

	Page<T> search(T filter, ExampleMatcher matcher, Pageable pageable);

	void deleteById(ID inEntityId, BaseModel baseModel) throws Exception;

}
