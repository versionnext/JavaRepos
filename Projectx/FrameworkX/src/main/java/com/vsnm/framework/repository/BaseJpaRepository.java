package com.vsnm.framework.repository;

import java.io.Serializable;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.vsnm.framework.model.BaseJpaModel;
import com.vsnm.framework.model.BaseModel;

@NoRepositoryBean
public interface BaseJpaRepository<T extends BaseJpaModel, ID extends Serializable>
		extends JpaRepository<T, ID> {

	T saveEntity(T inEntity) throws Exception;

	T findById(ID inEntityId, BaseModel baseModel) throws Exception;

	void deleteById(ID inEntityId, BaseModel baseModel) throws Exception;

	Page<T> search(T filter, ExampleMatcher matcher, Pageable pageable);

}
