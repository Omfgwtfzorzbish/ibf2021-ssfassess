package ibf2021.assess2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2021.assess2.Repository.BookRepo;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RepoService {
    private static final Logger logger = LoggerFactory.getLogger(RepoService.class);

    @Autowired
    private BookRepo bookrepo;

    public void saveToRedis(String id, String title, String Ex, String des, String cov){
        String s = title + "/" + Ex+ "/" + des+ "/" + cov; 
        bookrepo.save(id, s);
    }

    public Optional<String> getCache(String id){
        Optional<String> opt = bookrepo.get(id);
        if(opt.isEmpty()){return Optional.empty();}

    return opt;
    }

    
}
