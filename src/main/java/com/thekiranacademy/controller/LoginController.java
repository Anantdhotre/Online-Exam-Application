package com.thekiranacademy.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thekiranacademy.entity.Answer;
import com.thekiranacademy.entity.User;

@RestController
@CrossOrigin("http://localhost:4200")
public class LoginController {

	@Autowired
	SessionFactory factory;

	
	static HttpSession httpSession;

	@PostMapping("validate")
	public boolean validate(@RequestBody User userFromAngular,HttpServletRequest request ) {

		Session session= factory.openSession();

		String usernameFromAngular = userFromAngular.username;

		User userFromDatabase = session.get(User.class, usernameFromAngular);

		if(userFromDatabase==null) {
			return false;
		}
		else if(userFromDatabase.password.equals(userFromAngular.password)){

			httpSession = request.getSession();

			httpSession.setAttribute("score", 0);

			httpSession.setAttribute("questionIndex", 0);

			HashMap<Integer, Answer> hashMap = new HashMap<Integer,Answer>();

			httpSession.setAttribute("submittedDetails", hashMap);

			return true;

		}

		else {
			return false;
		}
	}

	@RequestMapping("registerUser")
	public void registerUser(@RequestBody User user) {
		
		  Session session = factory.openSession();
		  
		  Transaction tx = session.beginTransaction();
		  
		  session.save(user);
		  
		  tx.commit();
		  
		  System.out.println("Data Saved");
		
	}
	
}











