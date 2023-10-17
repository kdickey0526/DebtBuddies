package onetoone.Friends;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class FriendController {

    @Autowired
    FriendRepository FriendRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/Friends")
    List<Laptop> getAllLaptops(){
        return FriendRepository.findAll();
    }

    @GetMapping(path = "/Friend/{id}")
    Laptop getLaptopById(@PathVariable int id){
        return FriendRepository.findById(id);
    }

    @PostMapping(path = "/Friends")
    String createLaptop(@RequestBody Friend Friend1){
        if (Friend1 == null)
            return failure;
        FriendRepository.save(Friend1);
        return success;
    }

    @PutMapping(path = "/Friend/{id}")
    Laptop updateLaptop(@PathVariable int id, @RequestBody Friend request){
        Friend Friend = FriendRepository.findById(id);
        if(Friend == null)
            return null;
        FriendRepository.save(request);
        return FriendRepository.findById(id);
    }

    @DeleteMapping(path = "/Friend/{id}")
    String deleteLaptop(@PathVariable int id){
        FriendRepository.deleteById(id);
        return success;
    }
}
