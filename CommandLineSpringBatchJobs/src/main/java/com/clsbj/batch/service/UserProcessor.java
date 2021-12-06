package com.clsbj.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import com.clsbj.batch.model.User;

@Service
public class UserProcessor implements ItemProcessor<User, User> {


	private static final Logger LOGGER = LoggerFactory.getLogger(UserProcessor.class);

	@Override
	public User process(User user) throws Exception {
		
		return user;
	}


}