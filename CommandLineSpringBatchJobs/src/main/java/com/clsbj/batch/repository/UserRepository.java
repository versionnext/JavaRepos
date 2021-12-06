package com.clsbj.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clsbj.batch.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, String> {

	
	
}
