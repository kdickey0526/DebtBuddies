package debtbuddies.Guys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface GuyRepository extends JpaRepository<Guy, Long> {

    List<Guy> findAllByguyName(String guyName);
    Guy findById(int id);

    //guy findBy(String guyName);
    @Transactional
    void deleteById(int id);
}
