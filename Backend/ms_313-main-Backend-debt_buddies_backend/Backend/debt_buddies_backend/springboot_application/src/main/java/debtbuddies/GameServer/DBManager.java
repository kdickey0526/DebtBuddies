package debtbuddies.GameServer;

import debtbuddies.person.Person;
import debtbuddies.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DBManager {

    private PersonRepository personRepository;

    @Autowired
    public DBManager(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional
    public Person getPerson(String name){
        return personRepository.findByName(name);
    }

    @Transactional
    public void updatePerson(String name, int coins) {

        Person person = personRepository.findByName(name);
        if(person == null){
            return;
        }
        person.setCoins(coins);
        personRepository.save(person);
    }

    @Transactional
    public void updatePersons(List<Long> ids, List<Integer> coins){
        List<Person> persons = personRepository.findAllById(ids);
        List<Person> updatePersons = new ArrayList<>();
        for(int i = 0; i < ids.size() - 1; i++){
            Person p = persons.get(i);
            if(p == null){
                continue;
            }
            p.setCoins(coins.get(i));
            updatePersons.add(p);
        }
        personRepository.saveAll(updatePersons);
    }

}
