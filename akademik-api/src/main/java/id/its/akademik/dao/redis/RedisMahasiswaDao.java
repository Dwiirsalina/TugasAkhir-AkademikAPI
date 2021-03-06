package id.its.akademik.dao.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.its.akademik.dao.cache.AbstractCache;
import id.its.akademik.dao.cache.MahasiswaCache;
import id.its.akademik.domain.Akademik;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.KemajuanStudi;
import id.its.akademik.domain.Kuesioner;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.Transkrip;

public class RedisMahasiswaDao extends AbstractCache implements MahasiswaCache {

	@Override
	public Boolean checkKey(String key) {
		return this.checkKeyinRedis(key);
	}

	@Override
	public void setJadwalKuliah(String key, List<JadwalKuliah> list) {
		List<Object> objects=new ArrayList();
		for(JadwalKuliah jadwalKuliah:list)
		{
			Object object=(Object) jadwalKuliah;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<JadwalKuliah> getJadwalKuliah(String key) {
		List<Object> objects=this.getListOperations(key);
		List<JadwalKuliah> jadwalKuliah=new ArrayList();
		
		for(Object object:objects)
		{
			JadwalKuliah jadwal_=(JadwalKuliah) object;
			jadwalKuliah.add(jadwal_);
		}
		return jadwalKuliah;
	}
	
	@Override
	public void setPeriodeMhs(String key, List<PeriodeMahasiswa> list) {
		List<Object> objects=new ArrayList();
		for(PeriodeMahasiswa periodeMhs:list)
		{
			Object object=(Object) periodeMhs;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<PeriodeMahasiswa> getPeriodeMhs(String key) {
		List<Object> objects=this.getListOperations(key);
		List<PeriodeMahasiswa> periodeMhs=new ArrayList();
		
		for(Object object:objects)
		{
			PeriodeMahasiswa periode_mhs=(PeriodeMahasiswa) object;
			periodeMhs.add(periode_mhs);
		}
		return periodeMhs;
	}

	@Override
	public void setKemajuanMhs(String key, List<KemajuanStudi> list) {
		List<Object> objects=new ArrayList();
		for(KemajuanStudi kemajuan_studi:list)
		{
			Object object=(Object) kemajuan_studi;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<KemajuanStudi> getKemajuanMhs(String key) {
		List<Object> objects=this.getListOperations(key);
		List<KemajuanStudi> kemajuan_studi=new ArrayList();
		
		for(Object object:objects)
		{
			KemajuanStudi progressMhs=(KemajuanStudi) object;
			kemajuan_studi.add(progressMhs);
		}
		return kemajuan_studi;
	}
	
	@Override
	public void setAkademikMhs(String key,List<Akademik> list) {
		List<Object> objects=new ArrayList();
		for(Akademik akademik:list)
		{
			Object object=(Object) akademik;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Akademik> getAkademikMhs(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Akademik> listAkademik=new ArrayList();
		
		for(Object object:objects)
		{
			Akademik akademik=(Akademik) object;
			listAkademik.add(akademik);
		}
		return listAkademik;
	}

	@Override
	public void setMahasiswaWali(String key, List<Mahasiswa> list) {
		List<Object> objects=new ArrayList();
		for(Mahasiswa mhswali:list)
		{
			Object object=(Object) mhswali;
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
			Mahasiswa mhswali=(Mahasiswa) object;
			listMhsWali.add(mhswali);
		}
		return listMhsWali;
	}

	@Override
	public void setOrtuMhs(String key, List<OrangTua> list) {
		List<Object> objects=new ArrayList();
		for(OrangTua orangtua:list)
		{
			Object object=(Object) orangtua;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<OrangTua> getOrtuMhs(String key) {
		List<Object> objects=this.getListOperations(key);
		List<OrangTua> ortuMhs=new ArrayList();
		
		for(Object object:objects)
		{
			OrangTua orangtua=(OrangTua) object;
			ortuMhs.add(orangtua);
		}
		return ortuMhs;
	}

	@Override
	public void setPekerjaan(String key, List<Pekerjaan> list) {
		List<Object> objects=new ArrayList();
		for(Pekerjaan pekerjaan:list)
		{
			Object object=(Object) pekerjaan;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Pekerjaan> getPekerjaan(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Pekerjaan> pekerjaanMhs=new ArrayList();
		
		for(Object object:objects)
		{
			Pekerjaan pekerjaan=(Pekerjaan) object;
			pekerjaanMhs.add(pekerjaan);
		}
		return pekerjaanMhs;
	}

	@Override
	public void setPertanyaanDosen(String key, List<Kuesioner> list) {
		List<Object> objects=new ArrayList();
		for(Kuesioner kuesioner:list)
		{
			Object object=(Object) kuesioner;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Kuesioner> getPertanyaanDosen(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Kuesioner> kuesionerDosen=new ArrayList();
		
		for(Object object:objects)
		{
			Kuesioner kuesioner=(Kuesioner) object;
			kuesionerDosen.add(kuesioner);
		}
		return kuesionerDosen;
	}

	@Override
	public void setPertanyaanMK(String key, List<Kuesioner> list) {
		List<Object> objects=new ArrayList();
		for(Kuesioner kuesionerMk:list)
		{
			Object object=(Object) kuesionerMk;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Kuesioner> getPertanyaanMK(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Kuesioner> kuesionerMataKuliah=new ArrayList();
		
		for(Object object:objects)
		{
			Kuesioner kuesionerMk=(Kuesioner) object;
			kuesionerMataKuliah.add(kuesionerMk);
		}
		return kuesionerMataKuliah;
	}

	@Override
	public void setJadwalSelanjutnya(String key, List<JadwalKuliah> list) {
		List<Object> objects=new ArrayList();
		for(JadwalKuliah jadwalKuliah:list)
		{
			Object object=(Object) jadwalKuliah;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<JadwalKuliah> getJadwalSelanjutnya(String key) {
		List<Object> objects=this.getListOperations(key);
		List<JadwalKuliah> jadwal_kuliah=new ArrayList();
		
		for(Object object:objects)
		{
			JadwalKuliah jadwalKuliah=(JadwalKuliah) object;
			jadwal_kuliah.add(jadwalKuliah);
		}
		return jadwal_kuliah;
	}

	@Override
	public void setTranskripMhs(String key, Transkrip transkrip) {
		Map<String,String> transkripValue=new HashMap<>();
		transkripValue.put("nrp", transkrip.getNrp());
		transkripValue.put("judulTA", transkrip.getJudulTA());
		transkripValue.put("judulTaInggris", transkrip.getJudulTaInggris());
		transkripValue.put("pembimbing1", transkrip.getPembimbing1());
		transkripValue.put("pembimbing2", transkrip.getPembimbing2());
		transkripValue.put("pembimbing3", transkrip.getPembimbing3());
		transkripValue.put("ipk", String.valueOf(transkrip.getIpk()));
		transkripValue.put("sks", String.valueOf(transkrip.getSks()));
		transkripValue.put("skslulus", String.valueOf(transkrip.getSksLulus()));
		transkripValue.put("skstempuh", String.valueOf(transkrip.getSksTempuh()));
		transkripValue.put("tahap", String.valueOf(transkrip.getTahap()));
		
//		}
		
//		transkripValue.put("email", pegawai.getEmail());
//		transkripValue.put("alamat", pegawai.getAlamat());
		
		this.setHashOperation(key, transkripValue);
		
	}

	@Override
	public Transkrip getTranskripMhs(String key) {
		Map<String,String> transkripRedis=this.getHashOperation(key);
		Transkrip transkrip=new Transkrip();
		transkrip.setNrp(transkripRedis.get("nrp"));
		transkrip.setJudulTA(transkripRedis.get("judulTA"));
		transkrip.setJudulTaInggris(transkripRedis.get("judulTaInggris"));
		transkrip.setPembimbing1(transkripRedis.get("pembimbing1"));
		transkrip.setPembimbing2(transkripRedis.get("pembimbing2"));
		transkrip.setPembimbing3(transkripRedis.get("pembimbing3"));
		transkrip.setIpk(Double.valueOf(transkripRedis.get("ipk")));
		transkrip.setSks(Integer.valueOf(transkripRedis.get("sks")));
		transkrip.setSksLulus(Integer.valueOf(transkripRedis.get("skslulus")));
		transkrip.setSksTempuh(Integer.valueOf(transkripRedis.get("skstempuh")));
//		transkrip.setTahap( transkripRedis("tahap")));
		
		return transkrip;
	}
	
	@Override
	public void setMahasiswaData(String key, List<Mahasiswa> list) {
		List<Object> objects=new ArrayList();
		for(Mahasiswa dataMhs:list)
		{
			Object object=(Object) dataMhs;
			objects.add(object);
		}
		this.setListOperation(key, objects);
		
	}

	@Override
	public List<Mahasiswa> getMahasiswaData(String key) {
		List<Object> objects=this.getListOperations(key);
		List<Mahasiswa> dataMahasiswa=new ArrayList();
		
		for(Object object:objects)
		{
			Mahasiswa dataMhs=(Mahasiswa) object;
			dataMahasiswa.add(dataMhs);
		}
		return dataMahasiswa;
	}

}
