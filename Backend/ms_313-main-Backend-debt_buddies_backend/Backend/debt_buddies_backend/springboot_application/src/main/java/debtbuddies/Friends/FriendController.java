package debtbuddies.Friends;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

@Api(value = "FriendController", description = "Rest Api used for Friend object")
@RestController
public class FriendController {

    @Autowired
    FriendRepository friendrepo;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/Friends")
    List<Friend> getAllFriends(){
        return friendrepo.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/Friends/{personName}")
    List<Friend> getFriendById( @PathVariable String personName){
        return friendrepo.findAllByPersonName(personName);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping(path = "/Friends")
    String createFriend(@RequestBody(required = false) Friend Friend){
        if (Friend == null)
            return failure;
        friendrepo.save(Friend);
        return success;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PutMapping("/Friends/{id}")
    Friend updateFriend(@PathVariable int id, @RequestBody Friend request){
        Friend Friend = friendrepo.findById(id);
        if(Friend == null)
            return null;
        friendrepo.save(request);
        return friendrepo.findById(id);
    }

    /*@PutMapping("/Friends/{FriendId}/laptops/{laptopId}")
    String assignLaptopToFriend(@PathVariable int FriendId,@PathVariable int laptopId){
        Friend Friend = friendrepo.findById(FriendId);
        Laptop laptop = laptoprepo.findById(laptopId);
        if(Friend == null || laptop == null)
            return failure;
        laptop.setFriend(Friend);
        Friend.setLaptop(laptop);
        friendrepo.save(Friend);
        return success;
    }*/

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping(path = "/Friends/{id}")
    String deleteFriend(@PathVariable int id){
        friendrepo.deleteById(id);
        return success;
    }
}
