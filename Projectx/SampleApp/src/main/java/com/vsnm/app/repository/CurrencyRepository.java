package com.vsnm.app.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vsnm.app.model.Currency;
import com.vsnm.framework.repository.BaseMongoRepository;

@Component
public interface CurrencyRepository extends
		BaseMongoRepository<Currency, String>, CurrencyCustomRepository {
	List<Currency> findByCode(String code);
}
