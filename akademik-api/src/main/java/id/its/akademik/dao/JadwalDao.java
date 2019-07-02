package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.JadwalKelas;

public interface JadwalDao {

	List<JadwalKelas> getJadwalKelas(String kodeProdi, Integer tahun, Integer semester);

	List<JadwalKelas> getJadwalKelasByIdMk(String kodeProdi, Integer tahun, Integer semester, String idMk);

	List<JadwalKelas> getJadwalKelasSpesifik(String kodeProdi, Integer tahun, Integer semester, String idMk,
			String idKelas);
}
