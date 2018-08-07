package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.entities.Retrait;

public interface RetraitRepository extends MongoRepository<Retrait, String>{

}
