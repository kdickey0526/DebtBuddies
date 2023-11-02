package com.cs309.manytomanyself.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long>{

	public Person findByName(String name);

	public List<Person> findTop5ByOrderByWhackDesc();
//	public List<Person> findTop5Bywhack();

//	List<Person> findTop2ByWhackBetween(int startScore, int endScore, Sort sort);

//	findTop2ByScoreBetween
	@Transactional
	public void deleteByName(String name);
}
