package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.List;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.AkademikCache;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.ProdiAjar;

public class RedisAkademikDao extends AbstractCache implements AkademikCache{

	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}

	@Override
	public void setMatakuliah(String key, List<MataKuliah> list) {
		List<Object> objects=new ArrayList();
		for(MataKuliah matkul:list)
		{
			Object object=(Object) matkul;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<MataKuliah> getMataKuliah(String key) {
		List<Object> objects=this.getListOperations(key);
		List<MataKuliah> listMataKuliah=new ArrayList();
		
		for(Object object:objects)
		{
			MataKuliah matkul=(MataKuliah) object;
			listMataKuliah.add(matkul);
		}
		return listMataKuliah;
	}
	
	@Override
	public void setPesertaKelas(String key, List<MahasiswaFoto> list) {
		List<Object> objects=new ArrayList();
		for(MahasiswaFoto pesertakelas:list)
		{
			Object object=(Object) pesertakelas;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<MahasiswaFoto> getPesertaKelas(String key) {
		List<Object> objects=this.getListOperations(key);
		List<MahasiswaFoto> listPesertaKelas=new ArrayList();
		
		for(Object object:objects)
		{
			MahasiswaFoto pesertakelas=(MahasiswaFoto) object;
			listPesertaKelas.add(pesertakelas);
		}
		return listPesertaKelas;
	}

	@Override
	public void setMataKuliahSyarat(String key, List<MataKuliahSyarat> list) {
		List<Object> objects=new ArrayList();
		for(MataKuliahSyarat mataSyaratKuliah:list)
		{
			Object object=(Object) mataSyaratKuliah;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<MataKuliahSyarat> getMataKuliahSyarat(String key) {
		List<Object> objects=this.getListOperations(key);
		List<MataKuliahSyarat> listSyaratMk=new ArrayList();
		
		for(Object object:objects)
		{
			MataKuliahSyarat mataSyaratKuliah=(MataKuliahSyarat) object;
			listSyaratMk.add(mataSyaratKuliah);
		}
		return listSyaratMk;
	}

	@Override
	public void setListFrs(String key, List<FRS> list) {
		List<Object> objects=new ArrayList();
		for(FRS frs:list)
		{
			Object object=(Object) frs;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<FRS> getListFrs(String key) {
		List<Object> objects=this.getListOperations(key);
		List<FRS> listFRS=new ArrayList();
		
		for(Object object:objects)
		{
			FRS frs=(FRS) object;
			listFRS.add(frs);
		}
		return listFRS;
	}
}
