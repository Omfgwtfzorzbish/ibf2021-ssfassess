package ibf2021.assess2.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepo {
    @Autowired
    @Qualifier("redconfig")
    private RedisTemplate<String,String> template;

    public void save(String id, String value){
        template.opsForValue().set(id.trim().toLowerCase(), value, 10L, TimeUnit.MINUTES);
    }
    public Optional<String> get(String id){
        String value = template.opsForValue().get(id);
        return Optional.ofNullable(value);  //returns optional of object if it exists, else returns null
    }


}
