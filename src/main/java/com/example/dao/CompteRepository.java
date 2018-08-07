package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Client;
import com.example.entities.Compte;

@Repository
public interface CompteRepository extends MongoRepository<Compte, String>{
	
	//recuperer la liste des compte d'un client
	List<Compte> findByClient(Client client);
	//recuperer la liste des comptes d'un client seulment par son code 
	List<Compte> findByClient(String codeClient);
	//recuperer la liste pageable des compte par son code 
	Page<Compte> findBycodeCompteLike(String codeCompte,Pageable pageable);
	//chercher un compte par son code
	Compte findBycodeCompte(String codeCompte);

}
