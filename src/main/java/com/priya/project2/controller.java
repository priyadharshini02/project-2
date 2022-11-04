package com.priya.project2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class controller {
	
	UserDao userdao;
	public controller(UserDao userdao) {
		super();
		this.userdao=userdao;
	}
	
	
	 @ResponseBody
	 @PostMapping(value="/submitlogin")
	 public ResponseEntity<?> submitlogin(@RequestParam(name="email")String email, @RequestParam(name="password")String password) {
		
		 Map<String,Object> result=new HashMap<String,Object>();
		 result.put("data","");
		
		 System.out.println(email);
		 Optional<User>userData=userdao.findById(email);
		 if(userData.isPresent()) {
			 User u1=userData.get();
			 String dbEmail=u1.getEmail(); 
			 String dbPassword=u1.getPassword();
			 
			 if(dbEmail.equals(email)&& dbPassword.equals(password)) // checking credentials
			 { 
				 HashMap<String,Object>temp=new HashMap<>();
				 	temp.put("firstName", u1.getFirstName());
				 	
				 HashMap<String,Object>user=new HashMap<>();
				 user.put("user", temp);
				 result.put("status-code",200);
				 result.put("status","success");
				 result.put("message","login successfull");
				 result.put("data",user);
				
			 }
			 else { // wrong credentials
				 result.put("status-code",403);
				 result.put("status","failed");
				 result.put("message", "invalid user name or password");
				 System.out.println("invalid user name or password");
			 }
		 }
		 else {
			 result.put("status-code",404);
			 result.put("status","failed");
			 System.out.println("user does not exist");
			 result.put("message", "user does not exist please register");
		 }
		 
		 return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
	 }
	 

	 
	/* @RequestMapping(value="/login")
	 public String login() {
		 
		 return "login";
	 }
	 */
	 
	@RequestMapping(value="/submitForm", method = RequestMethod.POST)
	
	public ResponseEntity<?> register(@Valid @ModelAttribute("userForm") User userForm, BindingResult br) {
		Map<String,Object>result=new HashMap<String,Object>();
		result.put("data","");
		//result.put("user",userForm);
		System.out.println(userForm.getFirstName());
		System.out.println(br);
		if(br.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			br.getAllErrors().forEach((error) ->{
				
				String fieldName = ((FieldError) error).getField();
				String message = error.getDefaultMessage();
				errors.put(fieldName, message);
			});
			Map<String,Object>temp=new HashMap<>();
			temp.put("errors",errors);
			result.put("message", temp);
			result.put("status-code", 500);
			result.put("status","failed");
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
		}
		if(userForm.getPassword()!=null && userForm.getConfPassword()!=null) {
			if(!userForm.getPassword().equals(userForm.getConfPassword())) {
				br.addError(new FieldError("userForm","confPassword","password did not match"));
				result.put("status","failed");
				result.put("status-code", 500);
				result.put("message", "password did not match");
			}
		}
		System.out.println(userdao.findById(userForm.getEmail()));
		 Optional<User>user=userdao.findById(userForm.getEmail());
		 if(user.isPresent()) {
			br.addError(new FieldError("userForm","email","email already exists, please use login"));
			result.put("status","failed");
			result.put("status-code", 500);
			result.put("message", "email already exists, please use login");
		}
		
		if(br.hasErrors()) {
			result.put("status-code", 500);
			result.put("status","failed");
			//result.put("message",br);
			System.out.println("form has error so redirecting");
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.OK);
			
			
		}
		else {
				userdao.save(userForm);
				result.put("status-code", 200);
				result.put("data","");
				result.put("status","success");
				result.put("message", "registration successful, please login");
				return new ResponseEntity<Map<String,Object>>(result, HttpStatus.OK);
		}
		
	}
	
}
