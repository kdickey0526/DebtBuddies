package onetoone.Friends;

import onetoone.Users.User;


import java.util.Set;
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
    FriendRepository friendRepo;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";


    @GetMapping(path = "/friends")
    List<Friend> getAllFriends(){
        return friendRepo.findAll();
    }

    @GetMapping(path = "/friends/{userName1}")
    List<Friend> getfriendByUserName(@PathVariable User userName1){
        return friendRepo.findByUser1(userName1);
    }

    @GetMapping(path = "/friends/{userName1}/{userName2}")
    Friend getfriendByUserName(@PathVariable User userName1, @PathVariable User userName2){
        return friendRepo.findByUser1InAndUser2In(userName1, userName2);
    }


    @PostMapping(path = "/friends/{userName1}/{userName2}")
    String createfriend(@RequestBody User userName1, User userName2){
        if (userName1.getUserName() == null)
            return failure;
        if (userName2.getUserName() == null)
            return failure;
        Friend Friend1 = new Friend(userName1, userName2);
        friendRepo.save(Friend1);
        return success;
    }


/*    @PutMapping(path = "/friends/{userName}")
    friend updatefriend(@PathVariable Username, @RequestBody friend request){
        friend friend = friendRepository.findByUserName(userName);
        if(friend == null)
            return null;
        FriendRepository.save(request);
        return FriendRepository.findFriendByUserName(id);
    }*/


    @DeleteMapping(path = "/friends/{userName1}/{userName2}")
    String deletefriend(@PathVariable User userName1, @PathVariable User userName2){
        friendRepo.deleteByUser1InAndUser2In(userName1,userName2);
        return success;
    }
}

