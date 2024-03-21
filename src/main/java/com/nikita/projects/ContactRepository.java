package com.nikita.projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.nikita.projects.entities.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer> {
	
	@Query("from Contact as c where c.user.id = :userId")
	public Page<Contact> getContactsByUser(@Param("userId") int userId, Pageable pageable);
	
}
