package debtbuddies.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByPersonName(String PersonName);
    Friend findById(int id);

    //User findBy(String userName);
    @Transactional
    void deleteById(int id);
}
