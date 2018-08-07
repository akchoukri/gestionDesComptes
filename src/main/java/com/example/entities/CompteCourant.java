package com.example.entities;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "CompteCourants")
@TypeAlias("cc")
public class CompteCourant extends Compte{
	
	private double decouvert;



	public CompteCourant() {
		super();
		// TODO Auto-generated constructor stub
	}


	@PersistenceConstructor
	public CompteCourant(String codeCompte, Date dateCreation, double solde, Client client, double decouvert) {
		super(codeCompte, dateCreation, solde, client);
		this.decouvert = decouvert;
		
	}
	

	public double getDecouvert() {
		return decouvert;
	}

	

	public void setDecouvert(double decouvert) {
		this.decouvert = decouvert;
	}
	
	

}
