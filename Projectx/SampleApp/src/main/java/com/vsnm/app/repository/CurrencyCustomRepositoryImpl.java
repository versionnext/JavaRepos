package com.vsnm.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.vsnm.app.model.Currency;
import com.vsnm.framework.model.BaseModel;

public class CurrencyCustomRepositoryImpl implements CurrencyCustomRepository {

	@Autowired
	MongoOperations mongoOperations;

	public List<Currency> findByCustom(String name, BaseModel baseModel) {
		return mongoOperations.find(
				Query.query(Criteria.where("clientId")
						.is(baseModel.getClientId()).and("tenantId")
						.is(baseModel.getTenantId()).and("deleted").is(false)
						.and("name").is(name)), Currency.class);
	}

}
