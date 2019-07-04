package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.ListOperations;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.KelembagaanCache;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;

public class RedisKelembagaanDao extends AbstractCache implements KelembagaanCache{
	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}

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

	@Override
	public void setProdi(String key, List<ProgramStudi> list) {
		List<Object> objects=new ArrayList();
		for(ProgramStudi prodiProdi:list)
		{
			Object object=(ProgramStudi) prodiProdi;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<ProgramStudi> getProdi(String key) {
		List<Object> objects=this.getListOperations(key);
		List<ProgramStudi> listProdiProdi=new ArrayList();
		
		for(Object object:objects)
		{
			ProgramStudi prodiProdi=(ProgramStudi) object;
			listProdiProdi.add(prodiProdi);
		}
		return listProdiProdi;
	}

}
