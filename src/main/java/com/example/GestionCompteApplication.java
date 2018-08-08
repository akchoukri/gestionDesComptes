package com.example;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dao.ClientRepositiry;

import com.example.dao.CompteRepository;
import com.example.dao.OperationRepository;
import com.example.dao.UserRepository;
import com.example.entities.Client;
import com.example.entities.Compte;
import com.example.entities.CompteCourant;
import com.example.entities.CompteEpargne;
import com.example.entities.Operation;

import com.example.entities.User;

import com.example.metier.IBanqueMetier;
import com.example.storage.StorageService;

@SpringBootApplication
public class GestionCompteApplication implements CommandLineRunner {

	@Autowired
	private ClientRepositiry clientRepositiry;

	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueMetier banqueMetier;
	@Autowired
	private UserRepository userRepository;
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(GestionCompteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		// storageService.init();
		clientRepositiry.deleteAll();
		Client client1 = new Client("choukri", "akram");
		Client client2 = new Client("hiyane", "youssef");
		Client client3 = new Client("rajim", "abdo");

		User user = new User("tromed", "admin", "ADMIN", client1);
		User user2 = new User("hyoussef", "user", "USER", client2);
		User user3 = new User("rabdo", "user", "USER", client3);

		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user2.setPassword(new BCryptPasswordEncoder().encode(user2.getPassword()));
		user3.setPassword(new BCryptPasswordEncoder().encode(user3.getPassword()));

		clientRepositiry.save(client1);
		clientRepositiry.save(client2);
		clientRepositiry.save(client3);
		userRepository.deleteAll();

		userRepository.save(user);
		userRepository.save(user2);
		userRepository.save(user3);

		compteRepository.deleteAll();
		Compte compteC = new CompteCourant("cp1", new Date(), 2500, client1, 23);
		compteC.setTypeCompte(compteC.getClass().getSimpleName());
		Compte compteE = new CompteEpargne("cp2", new Date(), 4500, client2, 5.5);
		compteE.setTypeCompte(compteE.getClass().getSimpleName());
		client1.setComptes(Arrays.asList(compteC));
		client2.setComptes(Arrays.asList(compteE));

		compteRepository.save(compteC);
		compteRepository.save(compteE);
		// clientRepositiry.save(client1);
		// clientRepositiry.save(client2);

		operationRepository.deleteAll();

		System.out.println("compte solde avant versement = " + compteC.getSolde());
		// Operation operationV = new Versement(new Date(), 50, compteC);
		// operationRepository.save(operationV);
		banqueMetier.verser(compteC.getCodeCompte(), 100);
		System.out.println("compte solde apres versement = " + compteC.getSolde());
		System.out.println("*******************");
		System.out.println("compte solde avant retrait = " + compteC.getSolde());
		// Operation operationR = new Retrait(new Date(), 10, compteC);
		// operationRepository.save(operationR);
		banqueMetier.retirer(compteC.getCodeCompte(), 50);
		// banqueMetier.virement("cp1", "cp2",19999999);

		System.out.println("compte solde apres retrait = " + compteC.getSolde());
		//
		// List<Compte> comptes =
		// compteRepository.findByClient(client1.getCodeClient());
		// client1.setComptes(comptes);
		// clientRepositiry.save(client1);

		// System.out.println(client1.getComptes().size());
		clientRepositiry.findAll().forEach(
				p -> System.out.println(p.getCodeClient() + " " + p.getNomClient() + " " + p.getPrenomClient()));
		Compte compte = compteRepository.findBycodeCompte("cp1");
		if (compte != null)
			System.out.println("compte trouvé : " + compte.getClass().getSimpleName() + " " + compte.getCodeCompte());
		System.out.println("operation sur compte ");
		List<Operation> operations = operationRepository.findByCompte(compteC);
		System.out.println("operation sur compte size : " + operations.size());
		// User user1 = userRepository.findByUsername("tromed");
		// System.out.println("user " + user1.getAuthorities());

	}
}
