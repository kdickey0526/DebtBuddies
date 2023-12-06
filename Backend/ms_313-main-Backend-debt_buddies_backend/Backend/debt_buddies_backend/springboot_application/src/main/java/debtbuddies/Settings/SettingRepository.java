package debtbuddies.Settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Setting findByUserName(String userName);

    @Transactional
    void deleteById(int id);
}
