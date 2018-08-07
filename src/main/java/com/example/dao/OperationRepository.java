package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.entities.Compte;
import com.example.entities.Operation;

public interface OperationRepository  extends MongoRepository<Operation, String> {

	//recuperer la liste des operation d'un compte
	List<Operation> findByCompte(Compte compte);
	//recuperer la liste pageable des operations d'un compte
	Page<Operation> findByCompte(Compte compte,Pageable pageable);
}
