package com.vsnm.framework.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.BaseMongoModel;
import com.vsnm.framework.model.XPage;
import com.vsnm.framework.repository.BaseMongoRepository;

@Transactional
public class BaseMongoService<E extends BaseMongoModel, ID extends Serializable>
		extends BaseService<E> {

	private BaseMongoRepository<E, ID> baseMongoRepository;

	protected BaseMongoService(final BaseMongoRepository<E, ID> inRepository) {
		baseMongoRepository = inRepository;
	}

	public E save(final E inEntity) throws Exception {
		return baseMongoRepository.saveEntity(inEntity);
	}

	public E find(final ID inEntityId, BaseModel baseModel) throws Exception {
		return baseMongoRepository.findById(inEntityId, baseModel);
	}

	public XPage<E> search(XPage<E> page) throws Exception {
		E filter = page.getFilter();

		Sort sort = null;
		if (page.getOrderBy() != null) {
			if ("DESC".equalsIgnoreCase(page.getOrder()))
				sort = new Sort(Sort.Direction.DESC, page.getOrderBy());
			else
				sort = new Sort(Sort.Direction.ASC, page.getOrderBy());
		} else
			sort = new Sort(Sort.Direction.DESC, "updatedOn");
		Page<E> pageImpl = baseMongoRepository.search(filter,
				getExampleMatcher(filter), PageRequest.of(
						page.getPageNumber(),
						page.getSize() == 0 ? Integer.MAX_VALUE : page
								.getSize(), sort));
		page.setContent(pageImpl.getContent());
		page.setTotalElements(pageImpl.getTotalElements());
		page.setNumberOfElements(pageImpl.getNumberOfElements());
		page.setTotalPages(pageImpl.getTotalPages());
		return page;
	}

	public void delete(final ID inId, BaseModel baseModel) throws Exception {
		baseMongoRepository.deleteById(inId, baseModel);
	}

}
