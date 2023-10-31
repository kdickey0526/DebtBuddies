package onetomany.Friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface FriendRepository extends JpaRepository<Friend, String> {
    Friend findFriendByUserName(User userName1,User userName2);

    @Transactional
    void deleteByUserName(User userName1, User userName2);
}
