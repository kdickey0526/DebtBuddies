package debtbuddies.Settings;

import java.util.List;

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
public class SettingController {

    @Autowired
    SettingRepository SettingRepo;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/Settings")
    List<Setting> getAllSettings(){
        return SettingRepo.findAll();
    }

    @GetMapping(path = "/Settings/{id}")
    Setting getSettingById(@PathVariable String id){
        return SettingRepo.findByUserName(id);
    }

    @PostMapping(path = "/Settings")
    String createSetting(@RequestBody Setting Setting){
        if (Setting == null)
            return failure;
        SettingRepo.save(Setting);
        return success;
    }

    @PutMapping(path = "/Settings/{id}")
    Setting updateSetting(@PathVariable String id, @RequestBody Setting request){
        Setting Setting = SettingRepo.findByUserName(id);
        if(Setting == null)
            return null;
        SettingRepo.save(request);
        return SettingRepo.findByUserName(id);
    }

    @DeleteMapping(path = "/Settings/{id}")
    String deleteSetting(@PathVariable int id){
        SettingRepo.deleteById(id);
        return success;
    }
}
