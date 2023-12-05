package debtbuddies.person;

import java.util.List;
import java.util.Set;

import debtbuddies.person.Person;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import debtbuddies.Settings.Setting;
import debtbuddies.Settings.SettingRepository;
import debtbuddies.GameScores.GameScore;
import debtbuddies.GameScores.GameScoreRepository;

import debtbuddies.person.Person;
import debtbuddies.person.PersonRepository;

@Api(value = "PersonController", description = "Rest Api used for user accounts")
@RestController
@RequestMapping(value="/person")
public class PersonController {
	
	@Autowired
	PersonRepository personRepo;

	@Autowired
	GameScoreRepository GameScoreRepo;

	@Autowired
	SettingRepository SettingRepo;

	private String success = "{\"message\":\"success\"}";
	private String failure = "{\"message\":\"failure\"}";


	//@ApiOperation(value = "Add a person account to the database", response = Iterable.class, tags = "addPerson")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/add")
	public void addPerson(@RequestBody Person p) {
		personRepo.save(p);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PostMapping("/addFriend")
	public void addFriend(@RequestParam String name, @RequestParam String fName) {
		Person person = personRepo.findByName(name);
		Person friend = personRepo.findByName(fName);
		if(person!= null && friend != null) {
			person.getFriends().add(friend);
			personRepo.save(person);
		}
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PutMapping("/{name}")
	Person updatePerson(@PathVariable String name, @RequestBody Person request){
		Person user = personRepo.findByName(name);
		if(user == null)
			return null;
		personRepo.save(request);
		return personRepo.findByName(name);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping(path = "/{name}")
	public Person getPersonByName(@PathVariable String name){
		return personRepo.findByName(name);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@DeleteMapping(path = "/{name}")
	public void deleteFriend(@PathVariable String name){
		personRepo.deleteByName(name);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@GetMapping("/allPeople")
	public List<Person> getAll(){
		return personRepo.findAll();
	}

/*	@GetMapping("/getAllFriends/{name}")
	public List<Person> getAllFriends(){
		return personRepo.findAll();
	}*/

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PutMapping("/persons/{userId}/GameScore/{laptopId}")
	String assignGameScoreToUser(@PathVariable int userId,@PathVariable int laptopId){
		Person user = personRepo.findById(userId);
		GameScore laptop = GameScoreRepo.findById(laptopId);
		if(user == null || laptop == null)
			return failure;
		laptop.setUser(user);
		user.setGameScore(laptop);
		personRepo.save(user);
		return success;
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@PutMapping("/persons/{userId}/Settings/{laptopId}")
	String assignSettingsToUser(@PathVariable int userId,@PathVariable int laptopId){
		Person user = personRepo.findById(userId);
		Setting laptop = SettingRepo.findById(laptopId);
		if(user == null || laptop == null)
			return failure;
		laptop.setUser(user);
		user.setSettings(laptop);
		personRepo.save(user);
		return success;
	}


}
