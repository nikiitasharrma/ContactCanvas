package com.nikita.projects;

import org.springframework.data.repository.CrudRepository;

import com.nikita.projects.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
