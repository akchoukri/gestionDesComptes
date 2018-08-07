package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.entities.CompteEpargne;

public interface CompteEpargneRepository extends MongoRepository<CompteEpargne, String>{

}
