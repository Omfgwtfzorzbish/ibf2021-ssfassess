package ibf2021.assess2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration 
public class RedConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedConfig.class);
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Bean("redconfig")
    public RedisTemplate <String,String> createRedisTemplate() {

        final RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisHost);
        redisConfig.setPort(redisPort);
        redisConfig.setDatabase(redisDatabase);
        redisConfig.setPassword(redisPassword);

        // final String redisPassword = System.getenv(Constants.REDIS_PASSWORD);
        // if (null != redisPassword) {
        //     logger.info("Setting Redis password");
        //     redisConfig.setPassword(redisPassword);
        // }
        JedisClientConfiguration jedisConfig = JedisClientConfiguration.builder().build();
        JedisConnectionFactory jedisFac = new  JedisConnectionFactory(redisConfig, jedisConfig);
        jedisFac.afterPropertiesSet();

        final RedisTemplate <String,String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
       // template.setStringSerializer(new StringRedisSerializer());
        return template;
    }
}
