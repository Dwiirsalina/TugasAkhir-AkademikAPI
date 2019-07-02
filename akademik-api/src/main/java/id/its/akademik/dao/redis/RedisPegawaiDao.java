package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.PegawaiCache;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.ProdiAjar;

public class RedisPegawaiDao extends AbstractCache implements PegawaiCache {

	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}

	@Override
	public void setMahasiswaWali(String key, List<Mahasiswa> list) {
		List<Object> objects=new ArrayList();
		for(Mahasiswa mhsWali:list)
		{
			Object object=(Object) mhsWali;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Mahasiswa> getMahasiswaWali(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Mahasiswa> listMhsWali=new ArrayList();
		
		for(Object object:objects)
		{
			Mahasiswa mhsWali=(Mahasiswa) object;
			listMhsWali.add(mhsWali);
		}
		return listMhsWali;
	}

	@Override
	public void setDataPegawai(String key, Pegawai pegawai) {
		Map<String,String> pegawaiValue=new HashMap<>();
		pegawaiValue.put("nip", pegawai.getNip());
		pegawaiValue.put("nama", pegawai.getNama());
		pegawaiValue.put("email", pegawai.getEmail());
		pegawaiValue.put("alamat", pegawai.getAlamat());
		
		this.setHashOperation(key, pegawaiValue);

	}

	@Override
	public Pegawai getDataPegawai(String key) {
		Map<String,String> pegawaiRedis=this.getHashOperation(key);
		Pegawai pegawai=new Pegawai();
		pegawai.setNip(pegawaiRedis.get("nip"));
		pegawai.setNama(pegawaiRedis.get("nama"));
		pegawai.setEmail(pegawaiRedis.get("email"));
		pegawai.setAlamat(pegawaiRedis.get("alamat"));
		
		return pegawai;
	}

	

}
