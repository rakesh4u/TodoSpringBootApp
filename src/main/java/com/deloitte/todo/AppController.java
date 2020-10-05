package com.deloitte.todo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.deloitte.todo.dao.Tasks;
import com.deloitte.todo.dao.Users;
import com.deloitte.todo.services.ResourceService;

@Controller
@SessionAttributes("userObj")
public class AppController {

	@Autowired
	private ResourceService resourceService;

	@ModelAttribute("Users")
	public Users user() {
		return new Users();
	}

	@RequestMapping("/")
	public String homePage() {

		return "login";
	}

	@RequestMapping("/new/{id}")
	public String addNewTask(Model model, @PathVariable(name = "id") String id) {
		System.out.println("------------Inside addNewTask()---------------");
		try {
			Tasks tasks = new Tasks();
			System.out.println("Inside :::" + id);
			model.addAttribute("task", tasks);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		System.out.println("------------Exiting addNewTask()---------------");

		return "addtask";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(ModelMap model, @RequestParam String username, @RequestParam String password) {
		System.out.println("------------Inside login()---------------");
		try {
			int userID = resourceService.validateUsers(username, password);
			System.out.println("UserID is::" + userID);
			if (userID == 0) {
				model.put("errorMessage", "Invalid Credentials");
				return "login";
			} else {
				model.addAttribute("userObj", userID);
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
		System.out.println("------------Exiting login()---------------");

		return "home";
	}

	@RequestMapping("/viewtask/{id}")
	public String viewTasks(ModelMap model, @PathVariable(name = "id") String id) {
		System.out.println("------------Inside viewTasks()---------------");
		System.out.println("user loggedin id:::" + id);
		
		try {
			List<Tasks> listtasks = resourceService.getTask(Integer.parseInt(id));
			System.out.println("Task size::" + listtasks.size());
			int userID = Integer.parseInt(id);
			model.put("userObj", userID);
			model.put("listtasks", listtasks);
			model.get("userObj");
		} catch (Exception e) {
			throw new RuntimeException();
		}
		System.out.println("------------Exiting viewTasks()---------------");

		return "viewtask";
	}

	@RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
	public String addTask(ModelMap model, @ModelAttribute("task") Tasks task, @PathVariable(name = "id") String id) {
		System.out.println("------------Inside addTask()---------------");		
		try {
			System.out.println("task description" + task.getTaskDescription());
			System.out.println("UsderID::" + Integer.parseInt(id));
			task.setUserId(Integer.parseInt(id));
			resourceService.addTask(task);
			model.put("userObj", Integer.parseInt(id));
		} catch (Exception e) {
			throw new RuntimeException();
		}
		System.out.println("------------Exiting addTask()---------------");		

		return "home";
	}

	@RequestMapping("/edit/{taskid}/{userid}")
	public ModelAndView showEdittask(@PathVariable(name = "taskid") String taskid,
			@PathVariable(name = "userid") String userid) {
		System.out.println("------------Inside showEdittask()---------------");		

		ModelAndView mav = new ModelAndView("edit");
		try {
			System.out.println("Tasks id::" + taskid);
			System.out.println("User id::" + userid);
			Tasks task = resourceService.gettask(Integer.parseInt(taskid));
			mav.addObject("userObj", Integer.parseInt(userid));
			mav.addObject("task", task);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		System.out.println("------------Existing showEdittask()---------------");		

		return mav;

	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	public String updateTask(ModelMap model, @RequestParam String taskId, @RequestParam String taskDesc,
			@RequestParam(required = false) String checkbox,@PathVariable(name ="id") String id) {
		System.out.println("------------Inside updateTask()---------------");		
	
		try {
			System.out.println("Inside update tasks:::::::::");
			System.out.println("Description id::" + taskDesc);
			System.out.println("Tasks id::" + taskId);
			System.out.println("Status id::" + checkbox);
			System.out.println("Users id::" + id);
			if(checkbox==null){
				checkbox="FALSE";
			}else {
			checkbox="TRUE";
			}
			System.out.println("CheckBOX::"+checkbox);
		    resourceService.updateTask(taskDesc,Integer.parseInt(taskId),checkbox);
		    System.out.println("updated Task successfully::");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("------------Exiting updateTask()---------------");		

		return "home";
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") String id) {
		System.out.println("------------inside deleteProduct()---------------");	
		try {
			System.out.println("Delete id:::" + id);
			resourceService.delete(Integer.parseInt(id));
			System.out.println("Successfully deleted record for task Id:::" + id);
		} catch (Exception e) {
			throw new RuntimeException();
		}		
		System.out.println("------------exiting deleteProduct()---------------");	
		return "home";
	}

	
	 @ExceptionHandler(value=RuntimeException.class) 
	 public String	 exceptionHandler()
	 {
		 return "Exception";
	 }
	
}
