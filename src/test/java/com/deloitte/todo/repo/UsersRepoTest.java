package com.deloitte.todo.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.deloitte.todo.dao.Tasks;
import com.deloitte.todo.dao.Users;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepoTest {
	
	@Autowired
	private UsersRepo userreo;
	
	@Autowired
	private TaskRepo taskrepo;

	@Test
	public void testFindByUserName() {
		//fail("Not yet implemented");
		
		List<Users> count=userreo.findByUserName("foo", "test");
		assertThat(count.size()==0);
		
	}
	
	@Test
	public void testfindAllByUserID() {
		List<Tasks> tasks=taskrepo.findAllByUserID(1);
		System.out.println("Task size::"+tasks.size());
		assertThat(tasks.size()==1);
	}

}
