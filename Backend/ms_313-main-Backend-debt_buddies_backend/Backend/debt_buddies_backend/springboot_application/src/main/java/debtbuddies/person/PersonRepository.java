package debtbuddies.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Person findByName(String name);
	Person findById(int id);

	@Transactional
	void deleteById(int id);


//	List<Person> findTop2ByWhackBetween(int startScore, int endScore, Sort sort);

//	findTop2ByScoreBetween
	@Transactional
	void deleteByName(String name);
}
