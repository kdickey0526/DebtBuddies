package onetoone.Friends;

import onetoone.Users.User;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface FriendRepository extends JpaRepository<Friend, User> {
    public List<Friend> findByUser1(User user1);

    public Friend findByUser1InAndUser2In(User user1, User user2);



//    List<Friend> findFriendsByUserName(User userName1, User userName2);

    @Transactional
    public void deleteByUser1InAndUser2In(User user1, User user2);
}
