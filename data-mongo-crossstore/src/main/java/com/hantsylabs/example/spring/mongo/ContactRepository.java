package com.hantsylabs.example.spring.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hantsylabs.example.spring.model.Contact;

@Repository
public interface ContactRepository extends MongoRepository<Contact, String> {

}
