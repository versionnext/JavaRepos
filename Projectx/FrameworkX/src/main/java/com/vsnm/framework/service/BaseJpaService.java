package com.vsnm.framework.service;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.vsnm.framework.model.BaseJpaModel;
import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.model.XPage;
import com.vsnm.framework.repository.BaseJpaRepository;

@Transactional
public class BaseJpaService<E extends BaseJpaModel, ID extends Serializable>
		extends BaseService<E> {

	private BaseJpaRepository<E, ID> baseJpaRepository;

	protected BaseJpaService(final BaseJpaRepository<E, ID> inRepository) {
		baseJpaRepository = inRepository;
	}

	public E save(final E inEntity) throws Exception {
		return baseJpaRepository.saveEntity(inEntity);
	}

	public E find(final ID inEntityId, BaseModel baseModel) throws Exception {
		return baseJpaRepository.findById(inEntityId, baseModel);
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
		Page<E> pageImpl = baseJpaRepository.search(filter,
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

	public void delete(final ID inEntityId, BaseModel baseModel)
			throws Exception {
		baseJpaRepository.deleteById(inEntityId, baseModel);
	}

}
