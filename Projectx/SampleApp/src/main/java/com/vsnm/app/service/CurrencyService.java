package com.vsnm.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vsnm.app.model.Currency;
import com.vsnm.app.repository.CurrencyRepository;
import com.vsnm.framework.model.BaseModel;
import com.vsnm.framework.service.BaseMongoService;

@Service
public class CurrencyService extends BaseMongoService<Currency, String> {

	@Autowired
	private CurrencyRepository currencyRepository;
	
	protected CurrencyService(CurrencyRepository inRepository) {
		super(inRepository);
	}

	public List<Currency> findByName(String name,BaseModel baseModel) {

		return currencyRepository.findByCustom(name,baseModel);
	}

}
