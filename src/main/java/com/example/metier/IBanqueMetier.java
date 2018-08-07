package com.example.metier;



import java.util.List;

import com.example.entities.Client;
import com.example.entities.Compte;
import com.example.entities.Operation;
import com.example.entities.User;

public interface IBanqueMetier {
	//recuperr un compte
	public Compte consulterCompte(String codeCompte) ;
	//versement
	public Compte verser(String compte, double montant) ;
	//retrait
	public Compte retirer(String compte,double montant);
	//virement
	 public Compte virement(String compte1,String compte2,double montant);
	 //recuperer la liste des operation
	 public List<Operation> lesOperationsCpt(Compte compte);
	//consulter un compte d'un client
		public Compte consulterCompteByclient(String codeCompte,User user) ;
}
