package com.thekiranacademy.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thekiranacademy.entity.Answer;
import com.thekiranacademy.entity.Question;
import com.thekiranacademy.entity.User;

@RestController
@CrossOrigin("http://localhost:4200")
public class QuestionController {

	@Autowired
	SessionFactory factory;

	@RequestMapping("getFirstQuestion/{subject}")
	public Question getFirstQuestion(@PathVariable String subject)
	{
		Session session=factory.openSession();

		Query query=session.createQuery("from Question where subject=:subject");

		query.setParameter("subject",subject);

		List<Question> list=query.list();


		HttpSession httpsession=LoginController.httpSession;

		httpsession.setAttribute("list",list);

		Question firstQuestion=list.get(0);

		return firstQuestion;

	}

	@RequestMapping("nextQuestion")
	public Question nextQuestion()
	{
		HttpSession httpsession=LoginController.httpSession;

		List<Question> list=(List<Question>) httpsession.getAttribute("list");


		if((int)httpsession.getAttribute("questionIndex")<list.size()-1)
		{
			httpsession.setAttribute("questionIndex",(int)httpsession.getAttribute("questionIndex")+1);

			Question question=list.get((int)httpsession.getAttribute("questionIndex"));

			return question;
		}

		else
		{
			return list.get(list.size()-1);
		}

	}

	//{"qno":1,"qtext":"what is java","submittedAnswer":"A","originalAnswer":"B"}

	@RequestMapping("saveAnswer")
	public void saveAnswer(@RequestBody Answer answer)
	{

		HttpSession httpsession=LoginController.httpSession;

		HashMap<Integer,Answer> hashMap =(HashMap<Integer, Answer>) httpsession.getAttribute("submittedDetails");	

		hashMap.put(answer.getQno(),answer);

		System.out.println(hashMap);

	}

	@RequestMapping("priviousQuestion")
	public Question priviousQuestion() {

		HttpSession httpSession =LoginController.httpSession;

		List<Question> list = (List<Question>) httpSession.getAttribute("list");

		if((int)httpSession.getAttribute("questionIndex")>0) {

			httpSession.setAttribute("questionIndex",(int) httpSession.getAttribute("questionIndex")-1);

			Question question = list.get((int)httpSession.getAttribute("questionIndex"));

			return question;
		}
		else {

			return list.get(0);
		}

	}

	@RequestMapping("saveQuestion")
	public void saveQuestion(@RequestBody Question question) {

		Session session = factory.openSession();

		Transaction tx = session.beginTransaction();

		session.save(question);

		tx.commit();

		System.out.println("Data saved");

	}

	@RequestMapping("getAllQuestion")
	public List<Question> getAllQuestion(){

		Session session =factory.openSession();

		Query query =session.createQuery("from Question");

		List<Question> list =query.list();

		return list;
	}
	
	
	@RequestMapping("getAllAnswer")
	public ResponseEntity<Collection<Answer>> getAllAnswers() {

		HttpSession httpSession = LoginController.httpSession;

		HashMap<Integer, Answer> hashmap = (HashMap<Integer, Answer>) httpSession.getAttribute("submittedDetails");

		Collection<Answer> collection = hashmap.values();

		ResponseEntity<Collection<Answer>> rs= new ResponseEntity<>(collection,HttpStatus.OK);

		return rs;

	}

	@RequestMapping("endExam")
	public ResponseEntity<Integer>  endexam()
	{	
		HttpSession httpsession=LoginController.httpSession;
		
		try
		{
			HashMap<Integer,Answer>  hashMap=(HashMap<Integer, Answer>) httpsession.getAttribute("submittedDetails");
	
			Collection<Answer>   collection=hashMap.values();
		
			System.out.println(" values() gives object of class whose name is " + collection.getClass().getName());
		
		for (Answer ans : collection) 
		{
			if(ans.getSubmittedAnswer().equals(ans.getOriginalAnswer()))
			{
				httpsession.setAttribute("score",(int)httpsession.getAttribute("score")+1);//2
				
				// httpsession.setAttribute("score",10);
				
			}
		}
		

		Integer score=(Integer)httpsession.getAttribute("score");
		
		System.out.println("Total Score at Server " + score);

		httpsession.invalidate();// all attributes from HttpSession will be removed 
		
		ResponseEntity<Integer> responseEntity=new ResponseEntity<Integer>(score,HttpStatus.OK);

		return responseEntity;

		}
		
		catch(Exception e)
		{
			return null;
		}
	

				
 }

}














