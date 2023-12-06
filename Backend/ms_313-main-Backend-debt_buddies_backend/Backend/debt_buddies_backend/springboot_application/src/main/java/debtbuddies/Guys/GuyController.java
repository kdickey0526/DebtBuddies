package debtbuddies.Guys;

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

@Api(value = "guyController", description = "Rest Api used for guy object")
@RestController
public class GuyController {

    @Autowired
    GuyRepository guyRepository;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/guys")
    List<Guy> getAllguys(){
        return guyRepository.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping(path = "/guys/{guyName}")
    List<Guy> getguyById(@PathVariable String guyName){
        return guyRepository.findAllByguyName(guyName);
    }

/*    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PostMapping(path = "/guys")
    String createguy(@RequestBody(required = false) Guy guy){
        if (guy == null || guy.getuserName() == null)
            return failure;
        guyRepository.save(guy);
        return success;
    }*/

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PutMapping("/guys/{id}")
    Guy updateguy(@PathVariable int id, @RequestBody Guy request){
        Guy guy = guyRepository.findById(id);
        if(guy == null)
            return null;
        guyRepository.save(request);
        return guyRepository.findById(id);
    }

    /*@PutMapping("/guys/{guyId}/laptops/{laptopId}")
    String assignLaptopToguy(@PathVariable int guyId,@PathVariable int laptopId){
        guy guy = guyRepository.findById(guyId);
        Laptop laptop = laptopRepository.findById(laptopId);
        if(guy == null || laptop == null)
            return failure;
        laptop.setguy(guy);
        guy.setLaptop(laptop);
        guyRepository.save(guy);
        return success;
    }*/

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @DeleteMapping(path = "/guys/{id}")
    String deleteguy(@PathVariable int id){
        guyRepository.deleteById(id);
        return success;
    }
}
