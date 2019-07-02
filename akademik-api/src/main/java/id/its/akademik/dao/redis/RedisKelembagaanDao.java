package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.ListOperations;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.KelembagaanCache;
import id.its.akademik.domain.ProdiAjar;

public class RedisKelembagaanDao extends AbstractCache implements KelembagaanCache{
	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}
	
//	@Override
//	public void setListOperations(String key,List<ProdiAjar> prodiAjar) {
//		ListOperations<String, ProdiAjar> operations =this.getRedisTemplate().opsForList();
//		for(ProdiAjar object:prodiAjar)
//		{
//			operations.rightPush(key, object);
//		}
//	}
	
//	@Override
//	public List<ProdiAjar> getListOperations(String key)
//	{
//		ListOperations<String, ProdiAjar> operations = this.getRedisTemplate().opsForList();
//		return operations.range(key, 0, -1);
//	}

	@Override
	public void setProdiAjar(String key,List<ProdiAjar> list) {
		List<Object> objects=new ArrayList();
		for(ProdiAjar prodi:list)
		{
			Object object=(ProdiAjar) prodi;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<ProdiAjar> getProdiAjar(String key) {
		List<Object> objects=this.getListOperations(key);
		List<ProdiAjar> listProdi=new ArrayList();
		
		for(Object object:objects)
		{
			ProdiAjar prodi=(ProdiAjar) object;
			listProdi.add(prodi);
		}
		return listProdi;
	}

}
