package com.emilyread.events.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emilyread.events.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	User findByEmail(String email);
}
