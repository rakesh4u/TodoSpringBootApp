package com.deloitte.todo.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.todo.dao.Tasks;
import com.deloitte.todo.dao.Users;
import com.deloitte.todo.repo.TaskRepo;
import com.deloitte.todo.repo.UsersRepo;
import com.deloitte.todo.utils.Utils;

@Service
public class ResourceService {

	@Autowired
	UsersRepo usersRepo;
	
	@Autowired
	TaskRepo taskRepo;
	
	
	public int validateUsers(String username,String pwd) {
		int userID=0;
		List<Users>	users=usersRepo.findByUserName(username,pwd);
		if(users!=null && !users.isEmpty()) {
			 userID=users.get(0).getId();
		}
		return userID;
	}

	public List<Tasks> getTask(int userId) {
		List<Tasks> tasks=taskRepo.findAllByUserID(userId);
	for(int i=0;i<tasks.size();i++) {
		System.out.println("Tasks:"+tasks.get(i));
	}
		System.out.println("GetTask.........");
		return tasks;
	}

	public void addTask(Tasks task)   {		
		task.setTaskDescription(task.getTaskDescription());	
		task.setFlag(task.getFlag());
		
		System.out.println("Inside addTasks:::::"+task.getFlag());
		task.setUserId(task.getUserId());
		Timestamp currdate=Utils.getCurrentTimeStamp();
		taskRepo.saveTask(task.getTaskDescription(), currdate, task.getFlag(),task.getUserId());
		System.out.println("Inserted into Task table...");
	}

	public void removeTask(Tasks tasks) {
		taskRepo.delete(tasks);
	}

	public void delete(int id) {
		taskRepo.deleteById(id);
	}

	public  Tasks gettask(int id) {
		// TODO Auto-generated method stub
		return taskRepo.findById(id).get();
	}

	public void updateTask(String taskDesc, int taskId, String status) {
		taskRepo.updateTask(taskDesc, taskId, status);
	}
}
