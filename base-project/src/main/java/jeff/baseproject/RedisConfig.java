package jeff.baseproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setDefaultSerializer(new UsAsciiStringSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    //\xaa 문자 등 안들어가게 DefaultSerializer 를 치환
    class UsAsciiStringSerializer extends StringRedisSerializer {
        String charset_STR = "US-ASCII";

        @Override
        public byte[] serialize(String string)
        {
            try{
                return (string == null ? null : string.getBytes(charset_STR));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }

        @Override
        public String deserialize(byte[] bytes){
            return (bytes == null ? null : new String(bytes, Charset.forName(charset_STR)));
        }
    }
}
