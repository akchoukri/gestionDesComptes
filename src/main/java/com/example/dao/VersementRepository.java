package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.entities.Versement;

public interface VersementRepository extends MongoRepository<Versement, String>{

}
