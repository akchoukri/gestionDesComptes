package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entities.CompteCourant;

public interface CompteCourantRepository extends MongoRepository<CompteCourant, String> {

}
