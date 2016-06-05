package org.whh.dao;

import org.springframework.data.repository.CrudRepository;
import org.whh.entity.User;

public interface UserDao extends CrudRepository<User, Long>
{
	public User findByName(String name);
}