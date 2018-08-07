package com.example.dao;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Client;


@Repository
public interface ClientRepositiry extends MongoRepository<Client, String> {

	//recuperer une liste pageable des client par nom de client
	Page<Client> findByNomClientLike(String nomClient,Pageable pageable);
	//chercher un client par son nom 
	Client findByNomClient(String nomClient);
}
