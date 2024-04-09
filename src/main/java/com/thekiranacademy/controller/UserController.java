package com.thekiranacademy.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thekiranacademy.entity.User;

@RestController
@CrossOrigin("http://localhost:4200")
public class UserController 
{
	@Autowired
	SessionFactory factory;
	
	@RequestMapping("getAllUsers")
	public List<User> getAllUsers()
	{
		
		Session session=factory.openSession();
		
		Query query=session.createQuery("from User");
		
		return query.list();
		
	}
	
	@RequestMapping("getUser/{username}")
	public User getUser(@PathVariable String username)
	{

		Session session=factory.openSession();
		
		Query<User> query=session.createQuery("from User where username=:username");
		
		query.setParameter("username",username);
		
		return query.list().get(0);
		
	}

	@RequestMapping("deleteUser/{username}")
	public  void deleteUser(@PathVariable String username)
	{

		Session session=factory.openSession();
		
		Query<User> query=session.createQuery("delete from User where username=:username");
		
		query.setParameter("username",username);
		
		Transaction tx=session.beginTransaction();
		
			query.executeUpdate();
		
		tx.commit();	
		
	}
	
	@RequestMapping("updateUser/{username}")
	public void updateUser(String username) {
		
		Session session=factory.openSession();
		
		Transaction tx=session.beginTransaction();
		
		session.update(username);
		
		tx.commit();
	}
	
	
	
}
