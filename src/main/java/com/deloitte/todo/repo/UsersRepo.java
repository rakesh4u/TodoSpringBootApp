package com.deloitte.todo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.deloitte.todo.dao.Users;

public interface UsersRepo extends CrudRepository<Users,Integer>
{
	@Query(value ="select * from users where username= :username and password=:password ", nativeQuery=true)
	List<Users> findByUserName( @Param("username")String username, @Param("password")String password);
	
}
