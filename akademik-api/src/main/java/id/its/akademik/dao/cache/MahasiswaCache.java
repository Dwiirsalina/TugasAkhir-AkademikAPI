package id.its.akademik.dao.cache;

import java.util.List;

import id.its.akademik.domain.Akademik;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.KemajuanStudi;
import id.its.akademik.domain.Kuesioner;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.Transkrip;

public interface MahasiswaCache {
	Boolean checkKey(String key);
	void setMahasiswaData(String key,List<Mahasiswa> list);
	List<Mahasiswa> getMahasiswaData(String key);
	void setJadwalKuliah(String key,List<JadwalKuliah> list);
	List<JadwalKuliah> getJadwalKuliah(String key);
	void setPeriodeMhs(String key,List<PeriodeMahasiswa> list);
	List<PeriodeMahasiswa> getPeriodeMhs(String key);
	void setKemajuanMhs(String key,List<KemajuanStudi> list);
	List<KemajuanStudi> getKemajuanMhs(String key);
	void setAkademikMhs(String key,List<Akademik> list);
	List<Akademik> getAkademikMhs(String key);
	void setMahasiswaWali(String key,List<Mahasiswa> list);
	List<Mahasiswa> getMahasiswaWali(String key);
	void setOrtuMhs(String key,List<OrangTua> list);
	List<OrangTua> getOrtuMhs(String key);
	void setPekerjaan(String key,List<Pekerjaan> list);
	List<Pekerjaan> getPekerjaan(String key);
	void setPertanyaanDosen(String key,List<Kuesioner> list);
	List<Kuesioner> getPertanyaanDosen(String key);
	void setPertanyaanMK(String key,List<Kuesioner> list);
	List<Kuesioner> getPertanyaanMK(String key);
	void setJadwalSelanjutnya(String key,List<JadwalKuliah> list);
	List<JadwalKuliah> getJadwalSelanjutnya(String key);	
	void setTranskripMhs(String key,Transkrip transkrip);
	Transkrip getTranskripMhs(String key);
}
