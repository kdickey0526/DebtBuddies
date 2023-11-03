package debtbuddies.person;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/person")
public class PersonController {
	
	@Autowired
	PersonRepository personRepo;
	
	@PostMapping("/add")
	public void addPerson(@RequestBody Person p) {
		personRepo.save(p);
	}
	
	@PostMapping("/addFriend")
	public void addFriend(@RequestParam String name, @RequestParam String fName) {
		Person person = personRepo.findByName(name);
		Person friend = personRepo.findByName(fName);
		if(person!= null && friend != null) {
			person.getFriends().add(friend);
			personRepo.save(person);
		}
	}

	@GetMapping(path = "/{name}")
	public Person getPersonByName(@PathVariable String name){
		return personRepo.findByName(name);
	}

	@GetMapping(path = "/Whackamole")
	public List<Person> getWhackamole(){
		return personRepo.findTop5ByOrderByWhackDesc();
	}


	@DeleteMapping(path = "/users/{name}")
	public void deleteFriend(@PathVariable String name){
		personRepo.deleteByName(name);
	}
	
	@GetMapping("/allPeople")
	public List<Person> getAll(){
		return personRepo.findAll();
	}
}
