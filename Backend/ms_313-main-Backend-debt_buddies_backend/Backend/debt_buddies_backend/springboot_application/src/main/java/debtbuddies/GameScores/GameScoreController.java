package debtbuddies.GameScores;

import java.util.List;

import debtbuddies.person.Person;
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
public class GameScoreController {

    @Autowired
    GameScoreRepository GameScoreRepo;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/GameScores")
    List<GameScore> getAllGameScores(){
        return GameScoreRepo.findAll();
    }

    @GetMapping(path = "/GameScores/{id}")
    GameScore getGameScoreById(@PathVariable String id){
        return GameScoreRepo.findByuserName(id);
    }

    @PostMapping(path = "/GameScores")
    String createGameScore(@RequestBody GameScore GameScore){
        if (GameScore == null)
            return failure;
        GameScoreRepo.save(GameScore);
        return success;
    }

    @GetMapping(path = "/Whackamole")
    public List<GameScore> getWhackamole(){
        return GameScoreRepo.findTop5ByOrderByWhackDesc();
    }

    @GetMapping(path = "/War")
    public List<GameScore> getWar(){
        return GameScoreRepo.findTop5ByOrderByWarWonDesc();
    }

    @GetMapping(path = "/BlackJack")
    public List<GameScore> getBlackJack(){
        return GameScoreRepo.findTop5ByOrderByBlackJackDesc();
    }

    @PutMapping(path = "/GameScores/{id}")
    GameScore updateGameScore(@PathVariable String id, @RequestBody GameScore request){
        GameScore GameScore = GameScoreRepo.findByuserName(id);
        if(GameScore == null)
            return null;
        GameScoreRepo.save(request);
        return GameScoreRepo.findByuserName(id);
    }

    @DeleteMapping(path = "/GameScores/{id}")
    String deleteGameScore(@PathVariable int id){
        GameScoreRepo.deleteById(id);
        return success;
    }
}
