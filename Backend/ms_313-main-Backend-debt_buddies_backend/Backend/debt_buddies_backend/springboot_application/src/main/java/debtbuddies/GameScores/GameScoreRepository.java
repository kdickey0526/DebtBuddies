package debtbuddies.GameScores;

import debtbuddies.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface GameScoreRepository extends JpaRepository<GameScore, Long> {
    GameScore findById(int id);

    List<GameScore> findTop5ByOrderByWhackDesc();

    List<GameScore> findTop5ByOrderByWarWonDesc();

    List<GameScore> findTop5ByOrderByBlackJackDesc();

    @Transactional
    void deleteById(int id);
}
