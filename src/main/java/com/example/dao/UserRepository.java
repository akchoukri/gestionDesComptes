package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Client;
import com.example.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	//trouver user par son username
	public User findByUsername(String username);
	//trouver user d'un client
	public User findByClient(Client client);
}
