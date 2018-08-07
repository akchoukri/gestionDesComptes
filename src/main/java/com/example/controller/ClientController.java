package com.example.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ClientRepositiry;
import com.example.dao.UserRepository;
import com.example.entities.Client;
import com.example.entities.User;
import com.example.storage.StorageService;





@RestController
@CrossOrigin("*")
public class ClientController {

	@Autowired
	private ClientRepositiry clientRepositiry;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	StorageService storageService;
	
	// ajouter un client 
			@RequestMapping(method=RequestMethod.POST, value="/clients/new")
		    public Client saveClient(@RequestBody Client client) {				
		        clientRepositiry.save(client);

		        return client;
		    }
			
			// recuperer un client by id
			@RequestMapping(method=RequestMethod.GET, value="/clients/{id}")
		    public Client getClient(@PathVariable String id) {
				User user = null;
				Client client = clientRepositiry.findById(id).get();
				if(client!=null ) {
					user = userRepository.findByClient(client);
					//System.out.println(user.getIdUser());
				}
			
		        return client;
		    }
			// recuperer un client by name
						@RequestMapping(method=RequestMethod.GET, value="/clients/name/{nomClient}")
					    public Client getClientByName(@PathVariable String nomClient) {
					        return clientRepositiry.findByNomClient(nomClient);
					    }
			//supprimer un client
			  @RequestMapping(method=RequestMethod.DELETE, value="/clients/{id}")
			    public String deleteClient(@PathVariable String id) {
			        
				  Optional<Client> client =clientRepositiry.findById(id);
				  clientRepositiry.delete(client.get());
			        

			        return "";
			    }
			  // mettre a jour client
			  @RequestMapping(value="/clients/{id}",method=RequestMethod.PUT)
			  public void updateClient(@PathVariable String id,@RequestBody Client client) {
				  Optional<Client> c = clientRepositiry.findById(id);
				  if(client.getNomClient()!=null)
					  c.get().setNomClient(client.getNomClient());
				  if(client.getPrenomClient()!=null)
					  c.get().setPrenomClient(client.getPrenomClient());
				  clientRepositiry.save(client);
			  }
				//chercher des client par un mot cle 	
				@RequestMapping(value="/clients",method=RequestMethod.GET)
				public Iterable<Client> getpageContacts(
						@RequestParam(name="mc",defaultValue="") String mc,
						@RequestParam(name="page",defaultValue="0") int page,
						@RequestParam(name="size",defaultValue="4") int size){
					
					return  clientRepositiry.findByNomClientLike(mc, PageRequest.of(page, size));
				}
				//recuperer  la liste des clients 	
				@RequestMapping(value="/allclients",method=RequestMethod.GET)
				public Iterable<Client> getClients(){
					return clientRepositiry.findAll();
				}
				
				//joindre un fichier 
				@RequestMapping(value="/upload", method = RequestMethod.POST)
			    public  String upload(@RequestParam("file") MultipartFile file) throws Exception {

					try {
						storageService.store(file);
						//files.add(file.getOriginalFilename());
						return "You successfully uploaded - " + file.getOriginalFilename();
					} catch (Exception e) {
						throw new Exception("FAIL! uploaded");
					}
			}
}
