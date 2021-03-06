package id.its.akademik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConFactory= new JedisConnectionFactory();
	    jedisConFactory.setHostName("10.199.2.118");
	    jedisConFactory.setPort(6379);
	    return jedisConFactory;
	}
	 
	 @Bean
	 public RedisTemplate<String, Object> redisTemplate() {
		 final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		 template.setConnectionFactory(jedisConnectionFactory());
		 template.setEnableTransactionSupport(true);
		 template.setKeySerializer(new StringRedisSerializer());
		 template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		 return template;
   }
}