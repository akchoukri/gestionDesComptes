package com.example.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="clients")
public class Client implements Serializable {
	
	@Id
	private String codeClient;
	
	private String nomClient;
	private String prenomClient;
	private String emailClient;
	private Long telClient;
	private String imageClient;
	private Date dateNaissanceCleint;
	
	
	
	
	@DBRef
	private List<Compte> comptes = new ArrayList<>();
	public String getCodeClient() {
		return codeClient;
	}
	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getPrenomClient() {
		return prenomClient;
	}
	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}
	
	


	public String getEmailClient() {
		return emailClient;
	}
	public void setEmailClient(String emailClient) {
		this.emailClient = emailClient;
	}
	public Long getTelClient() {
		return telClient;
	}
	public void setTelClient(Long telClient) {
		this.telClient = telClient;
	}
	public String getImageClient() {
		return imageClient;
	}
	public void setImageClient(String imageClient) {
		this.imageClient = imageClient;
	}
	public Date getDateNaissanceCleint() {
		return dateNaissanceCleint;
	}
	public void setDateNaissanceCleint(Date dateNaissanceCleint) {
		this.dateNaissanceCleint = dateNaissanceCleint;
	}
	public List<Compte> getComptes() {
		return comptes;
	}
	public void setComptes(List<Compte> comptes) {
		this.comptes = comptes;
	}


	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client(String nomClient, String prenomClient) {
		super();
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
	}
	public Client(String nomClient, String prenomClient, String emailClient, Long telClient, String imageClient,
			Date dateNaissanceCleint, List<Compte> comptes) {
		super();
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.emailClient = emailClient;
		this.telClient = telClient;
		this.imageClient = imageClient;
		this.dateNaissanceCleint = dateNaissanceCleint;
		this.comptes = comptes;
	}

	
	
	
	

}
