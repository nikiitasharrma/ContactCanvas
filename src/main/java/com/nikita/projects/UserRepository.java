package com.nikita.projects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nikita.projects.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByUsername(String email);

}
