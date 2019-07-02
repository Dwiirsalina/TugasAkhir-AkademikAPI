package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.Foto;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.PeringkatIPD;
import id.its.akademik.domain.PeriodeMahasiswa;

public interface PegawaiDao {

	List<Pegawai> getListPegawai(String nama);

	Pegawai getPegawai(String nip);

	Pegawai getPegawaiByNIPBaru(String nip);

	Foto getFotoDosen(String nip);

	List<JadwalKuliah> getJadwalAjar(String nip, Integer tahun, Integer semester, String kodeProdi, String bahasa);

	List<JadwalKuliah> getJadwalAjarSelanjutnya(String nip, Integer tahun, Integer semester, Integer kodeHari,
			String kodeProdi, String bahasa);

	List<PeriodeMahasiswa> getPeriode(String nip, String kodeProdi);

	List<PeringkatIPD> getPeringkatIPD(String kodeProdi, Integer tahun, Integer semester, String urutkan,
			String bahasa);

	List<PeringkatIPD> getIPD(String nip, String kodeProdi, Integer tahun, Integer semester, String urutkan,
			String bahasa);

	PeringkatIPD getIPDSpesifik(String nip, String kodeProdi, Integer tahun, Integer semester, String idMk,
			String kelas);

	Double getRataIPM(String kodeProdi, String idMk, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kelas);

	Double getRataIPD(String nip, String kodeProdi, String idMk, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas);

	Integer getJumlahPengisi(String kodeProdi, String idMk, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kelas);

	Integer getJumlahResponden(String nip, String kodeProdi, String idMk, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas);

	Double getTerisi(String nip, String kodeProdi, Integer tahun, Integer semester, Integer tahunKurikulum, String idMk,
			String kelas);

}
