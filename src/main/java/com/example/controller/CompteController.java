package com.example.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.controller.form.OperationForm;

import com.example.dao.CompteRepository;
import com.example.dao.UserRepository;
import com.example.entities.Compte;
import com.example.entities.CompteCourant;
import com.example.entities.CompteEpargne;
import com.example.entities.User;
import com.example.metier.IBanqueMetier;

@RestController
@CrossOrigin("*")
public class CompteController {

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private IBanqueMetier banqueMetier;
	@Autowired
	private UserRepository userRepository;

	// recuperer la liste des comptes d'un client
	@RequestMapping(value = "/clients/{id}/comptes", method = RequestMethod.GET)
	public List<Compte> getClientComptes(@PathVariable String id) {
		return compteRepository.findByClient(id);
	}

	// //recuperer la liste des comptes
	// @RequestMapping(value="/comptes",method=RequestMethod.GET)
	// public List<Compte> getComptes(){
	// return compteRepository.findAll();
	// }

	// recuperer les compte par code compte

	@RequestMapping(value = "/comptes", method = RequestMethod.GET)
	public Iterable<Compte> getpageContacts(@RequestParam(name = "mc", defaultValue = "") String mc,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {

		return compteRepository.findBycodeCompteLike(mc, PageRequest.of(page, size));
	}

	// recuperer un compte
	@RequestMapping(value = "/comptes/{codeCompte}", method = RequestMethod.GET)
	public Compte consulterCompte(@PathVariable String codeCompte) {
		
			Compte compte = banqueMetier.consulterCompte(codeCompte);
			
			
			return compte;


	}

	// supprimer un Compte
	@RequestMapping(method = RequestMethod.DELETE, value = "/comptes/{codeCompte}")
	public String deleteCompte(@PathVariable String codeCompte) {

		Optional<Compte> compte = compteRepository.findById(codeCompte);
		compteRepository.delete(compte.get());

		return "";
	}

	// ajouter un comptecourant
	@RequestMapping(method = RequestMethod.POST, value = "/comptes/compteCourant")
	public Compte saveCompte(@RequestBody CompteCourant compte) {

		compte.setDateCreation(new Date());
		compte.setTypeCompte("compte Courant");
		compteRepository.save(compte);

		return compte;
	}

	// ajouter un compte epargne
	@RequestMapping(method = RequestMethod.POST, value = "/comptes/compteEpargne")
	public Compte saveCompte(@RequestBody CompteEpargne compte) {
		compte.setDateCreation(new Date());
		compte.setTypeCompte(compte.getClass().getSimpleName());
		compteRepository.save(compte);

		return compte;
	}

	// creer une operation
	@RequestMapping(method = RequestMethod.POST, value = "/operation")
	public Compte saveOperation(@RequestBody OperationForm form) {
		Compte compte = null;
		Compte compte2 = compteRepository.findBycodeCompte(form.getCp1());
		User user = userRepository.findByClient(compte2.getClient());
		if((user!=null)&&(user.getRole().equals("ADMIN"))) {
			
			if (form.getTypeOperation().equals("versement"))
				compte = banqueMetier.verser(form.getCp1(), form.getMontant());
			else if (form.getTypeOperation().equals("retrait"))
				compte = banqueMetier.retirer(form.getCp1(), form.getMontant());
		}
		else throw new RuntimeException("vous n'avez pas le droit d'effectuer cette opération ");
		if (form.getTypeOperation().equals("virement"))
			compte = banqueMetier.virement(form.getCp1(), form.getCp2(), form.getMontant());

		compte.setOperations(banqueMetier.lesOperationsCpt(compte));

		return compte;
	}

	// recuperer un compte selon user role
	@RequestMapping(value = "users/{username}/comptes/{codeCompte}", method = RequestMethod.GET)
	public Compte consulterCompte(@PathVariable String codeCompte,@PathVariable String username) {
		
		    User  user = userRepository.findByUsername(username);
		    
			Compte compte = banqueMetier.consulterCompteByclient(codeCompte, user);
			
			
			return compte;


	}
	//endpoint au backend
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

}
