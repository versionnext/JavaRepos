package com.vsnm.app.repository;

import java.util.List;

import com.vsnm.app.model.Currency;
import com.vsnm.framework.model.BaseModel;


public interface CurrencyCustomRepository  {
	


	public List<Currency> findByCustom(String name,BaseModel baseModel);


}
