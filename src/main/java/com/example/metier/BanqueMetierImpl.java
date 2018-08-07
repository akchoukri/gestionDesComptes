package com.example.metier;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CompteRepository;
import com.example.dao.OperationRepository;
import com.example.entities.Compte;
import com.example.entities.CompteCourant;
import com.example.entities.Operation;
import com.example.entities.Retrait;
import com.example.entities.User;
import com.example.entities.Versement;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier{

	@Autowired
	private CompteRepository compteRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	//recuperer un compte par son code et recuperer aussi sa liste des operation 
	@Override
	public Compte consulterCompte(String codeCompte) {
		
		Compte compte = compteRepository.findBycodeCompte(codeCompte);
		if(compte==null) throw new RuntimeException("compte nn trouvé  ");
		else {
			List<Operation> operations = operationRepository.findByCompte(compte);
			compte.setOperations(operations);
		}

		
			return compte;
	}

	// versement de montant a un compte 
	@Override
	public Compte verser(String cp, double montant) {
		 Compte compte = consulterCompte(cp);
		 if(montant<=0) throw new RuntimeException("montant  insuffisant ");
		 
			Versement versement = new Versement(new Date(), montant, compte);
			versement.setTypeOperation("versement");
			operationRepository.save(versement);
			compte.setSolde(compte.getSolde()+montant);
	
			compteRepository.save(compte);
						return compte;
			
			
		
	}

	//permet la retrait 
	@Override
	public Compte retirer(String cp, double montant) {
		Compte compte = consulterCompte(cp);
		if((compte instanceof CompteCourant)&&(compte.getSolde()+((CompteCourant)compte).getDecouvert()<montant))
			throw new RuntimeException("solde insuffisant  ");
			
		Retrait retrait = new Retrait(new Date(), montant, compte);
		retrait.setTypeOperation("retrait");
		operationRepository.save(retrait);
		compte.setSolde(compte.getSolde()- montant);
		
		compteRepository.save(compte);
		
		return compte;
	}

	//la virement 
	@Override
	public Compte virement(String compte1, String compte2, double montant) {
		Compte cp1 =retirer(compte1, montant);
		Compte cp2=verser(compte2, montant);
		return cp1;
	}

	//recuperer la liste des operations d'un compte
	@Override
	public List<Operation> lesOperationsCpt(Compte compte) {
		
		return operationRepository.findByCompte(compte);
	}

	@Override
	public Compte consulterCompteByclient(String codeCompte, User user) {
		Compte compte = compteRepository.findBycodeCompte(codeCompte);
		if (compte == null)
			throw new RuntimeException("compte nn trouvé  ");
		else {
			List<Operation> operations = operationRepository.findByCompte(compte);
			compte.setOperations(operations);
		}
		System.out.println("role "+user.getRole());
		if (user.getRole().equals("ADMIN"))
			return compte;
		else {
			 if (compte.getClient().getCodeClient().equals(user.getClient().getCodeClient()))
				{
				 
				 return compte;
				}
			else
				throw new RuntimeException(" ce n'est votre compte ");
		}



	}

	



}
