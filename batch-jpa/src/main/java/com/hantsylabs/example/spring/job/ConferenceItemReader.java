package com.hantsylabs.example.spring.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.database.JpaPagingItemReader;

import com.hantsylabs.example.spring.model.Conference;

public class ConferenceItemReader extends JpaPagingItemReader<Conference> {
	
	 public ConferenceItemReader(EntityManagerFactory emf, String queryString){
		 setEntityManagerFactory(emf);
		 setQueryString(queryString);	 
	 }


}
