package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.AkademikCache;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.Sekarang;
import id.its.akademik.domain.Transkrip;

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

	@Override
	public void setBasicSekarang(String key, List<Sekarang> list) {
		List<Object> objects=new ArrayList();
		for(Sekarang thnSekarang:list)
		{
			Object object=(Object) thnSekarang;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Sekarang> getBasicSekarang(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Sekarang> sekarang=new ArrayList();
		
		for(Object object:objects)
		{
			Sekarang thSekarang=(Sekarang) object;
			sekarang.add(thSekarang);
		}
		return sekarang;
	}

	@Override
	public void setDataFrs(String key, FRS frs) {
		Map<String,String> dataFrsValue=new HashMap<>();
		dataFrsValue.put("nrp", frs.getNrp());
		dataFrsValue.put("nrpBaru", frs.getNrpBaru());
		dataFrsValue.put("nama", frs.getNama());
		dataFrsValue.put("dosenWali", frs.getDosenWali());
		dataFrsValue.put("statusKeaktifan", frs.getStatusKeaktifan());
		dataFrsValue.put("batasSks", String.valueOf( frs.getBatasSks()));
		dataFrsValue.put("ipk", String.valueOf(frs.getIpk()));
		dataFrsValue.put("ipsLalu", String.valueOf(frs.getIpsLalu()));
//		dataFrsValue.put("ipk", String.valueOf(frs.getLimitSKS(String.valueOf(ips))));
//		dataFrsValue.put("kelasAmbil", String.valueOf(frs.getKelasAmbil()));
		dataFrsValue.put("semester", String.valueOf(frs.getSemester()));
		dataFrsValue.put("sksAmbil", String.valueOf(frs.getSksAmbil()));
		dataFrsValue.put("sisaSks", String.valueOf(frs.getSisaSks()));
		dataFrsValue.put("sksLulus", String.valueOf(frs.getSksLulus()));
		dataFrsValue.put("sksTempuh", String.valueOf(frs.getSksTempuh()));
		dataFrsValue.put("tahun", String.valueOf(frs.getTahun()));
		this.setHashOperation(key, dataFrsValue);
	}

	@Override
	public FRS getDataFrs(String key) {
		Map<String,String> dataFrsRedis=this.getHashOperation(key);
		FRS frs=new FRS();
		frs.setNrp(dataFrsRedis.get("nrp"));
		frs.setNrpBaru(dataFrsRedis.get("nrpBaru"));
		frs.setNama(dataFrsRedis.get("nama"));
		frs.setDosenWali(dataFrsRedis.get("dosenWali"));
		frs.setStatusKeaktifan(dataFrsRedis.get("statusKeaktifan"));
		frs.setBatasSks(Integer.valueOf(dataFrsRedis.get("batasSks")));
		frs.setIpk(Double.valueOf(dataFrsRedis.get("ipk")));
		frs.setIpsLalu(Double.valueOf(dataFrsRedis.get("ipsLalu")));
//		frs.setKelasAmbil(List<> dataFrsRedis.get("ipsLalu"));
		frs.setSemester(Integer.valueOf(dataFrsRedis.get("semester")));
		frs.setSksAmbil(Integer.valueOf(dataFrsRedis.get("sksAmbil")));
		frs.setSisaSks(Integer.valueOf(dataFrsRedis.get("sisaSks")));
		frs.setSksLulus(Integer.valueOf(dataFrsRedis.get("sksLulus")));
		frs.setSksTempuh(Integer.valueOf(dataFrsRedis.get("sksTempuh")));
		frs.setTahun(Integer.valueOf(dataFrsRedis.get("tahun")));
		return frs;
		
	}
}
