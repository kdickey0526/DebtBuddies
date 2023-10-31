package onetomany.Friends;


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
    FriendRepository Friendrepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/friends/{userName}")
    List<friend> getAllfriends(){
        return friendRepository.findAll(usrName);
    }


    @GetMapping(path = "/friends/{id}")
    friend getfriendByUserName(@PathVariable User userName){
        return friendRepository.findFriendByUserName(userName);
    }


    @PostMapping(path = "/friends/{userName1}/{userName2}")
    String createfriend(@RequestBody User userName1, User userName2){
        if (userName1 == null)
            return failure;
        if (userName2 == null)
            return failure;
        Friend friend1(userName1,userName2);
        friendRepository.save(friend);
        return success;
    }


    @PutMapping(path = "/friends/{userName}")
    friend updatefriend(@PathVariable Username, @RequestBody friend request){
        friend friend = friendRepository.findByUserName(userName);
        if(friend == null)
            return null;
        FriendRepository.save(request);
        return FriendRepository.findFriendByUserName(id);
    }


    @DeleteMapping(path = "/friends/{userName1}/{userName2}")
    String deletefriend(@PathVariable String userName1, @PathVariable String userName2){
        FriendRepository.deleteByUserName(userName1,userName2);
        return success;
    }
}

