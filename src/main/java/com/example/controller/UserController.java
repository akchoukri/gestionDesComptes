package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.ClientRepositiry;
import com.example.dao.UserRepository;
import com.example.entities.Client;
import com.example.entities.User;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ClientRepositiry clientRepositiry;

	// trouver user par son username
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User getUser(String id) {

		return userRepository.findByUsername(id);

	}

	// ajouter un user
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	// mettre a jour user
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public User updateUser(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) != null
				&& userRepository.findByUsername(user.getUsername()).getIdUser() != user.getIdUser()) {
			throw new RuntimeException("Username already exist");
		}
		// user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/users/client/{id}")
	public User getUserByClient(@PathVariable String id) {
		User user = null;
		Client client = clientRepositiry.findById(id).get();
		if (client != null) {
			user = userRepository.findByClient(client);
			// System.out.println(user.getIdUser());
		}

		return user;
	}
}
