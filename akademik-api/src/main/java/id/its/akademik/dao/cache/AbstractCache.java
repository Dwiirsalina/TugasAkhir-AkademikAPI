package id.its.akademik.dao.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.ProdiAjar;

public abstract class AbstractCache {
	
	private RedisTemplate redisTemplate;
	
	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	public Boolean checkKeyinRedis(String key)
	{
		return this.redisTemplate.hasKey(key);
	}
	
	public void setListOperation(String key,List<Object> objects)
	{
		ListOperations<String, Object> operations = this.redisTemplate.opsForList();
		for(Object object:objects)
		{
			operations.rightPush(key, object);
		}
		this.getRedisTemplate().expire(key, 3600, TimeUnit.SECONDS);
	}
	
	public List<Object> getListOperations(String key)
	{
		ListOperations<String, Object> operations = this.redisTemplate.opsForList();
		return operations.range(key, 0, -1);
	}
	
	public void setHashOperation(String key, Map<String,String> objects)
	{
		HashOperations<String,String,String> operationHash=this.redisTemplate.opsForHash();
		operationHash.putAll(key, objects);
	}
	
	public Map<String,String> getHashOperation(String key)
	{
		HashOperations<String,String,String> operationHash=this.redisTemplate.opsForHash();
		Map<String,String> objectRedis=operationHash.entries(key);
//		Object obj=new Object();
		return objectRedis;
	}
	
}
