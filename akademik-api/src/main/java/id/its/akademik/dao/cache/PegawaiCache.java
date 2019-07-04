package id.its.akademik.dao.cache;

import java.util.List;
import java.util.Map;

import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.Pegawai;

public interface PegawaiCache {
	Boolean checkKey(String key);
	void setMahasiswaWali(String key,List<Mahasiswa> list);
	List<Mahasiswa> getMahasiswaWali(String key);
	void setDataPegawai(String key,Pegawai pegawai);
	Pegawai getDataPegawai(String key);
	void setJadwalDosen(String key,List<JadwalKuliah> list);
	List<JadwalKuliah> getJadwalDosen(String key);
}
