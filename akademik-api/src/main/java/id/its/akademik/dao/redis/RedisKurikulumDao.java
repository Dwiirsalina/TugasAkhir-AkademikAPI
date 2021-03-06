package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.List;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.KurikulumCache;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MataKuliah;

public class RedisKurikulumDao extends AbstractCache implements KurikulumCache{

	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}

	@Override
	public void setMkKurikulum(String key, List<Kurikulum> list) {
		List<Object> objects=new ArrayList();
		for(Kurikulum matkul_kurikulum:list)
		{
			Object object=(Object) matkul_kurikulum;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Kurikulum> getMkKurikulum(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Kurikulum> listKurikulum=new ArrayList();
		
		for(Object object:objects)
		{
			Kurikulum matkul_kurikulum=(Kurikulum) object;
			listKurikulum.add(matkul_kurikulum);
		}
		return listKurikulum;
	}
	
	@Override
	public void setTahunKurikulum(String key, List<Kurikulum> list) {
		List<Object> objects=new ArrayList();
		for(Kurikulum tahunKurikulum:list)
		{
			Object object=(Object) tahunKurikulum;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Kurikulum> getTahunKurikulum(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Kurikulum> listTahun=new ArrayList();
		
		for(Object object:objects)
		{
			Kurikulum tahunKurikulum=(Kurikulum) object;
			listTahun.add(tahunKurikulum);
		}
		return listTahun;
	}

}
