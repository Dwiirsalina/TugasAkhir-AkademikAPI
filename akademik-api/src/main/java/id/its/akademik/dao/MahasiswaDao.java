package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.Akademik;
import id.its.akademik.domain.CekPembayaran;
import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.DetailPembayaran;
import id.its.akademik.domain.FRSFoto;
import id.its.akademik.domain.Foto;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Jawaban;
import id.its.akademik.domain.KeaktifanMahasiswa;
import id.its.akademik.domain.KemajuanStudi;
import id.its.akademik.domain.Kuesioner;
import id.its.akademik.domain.ListKuesioner;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.Pembayaran;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.Transkrip;
import id.its.akademik.domain.WaliMahasiswa;

public interface MahasiswaDao {

	List<Mahasiswa> getMahasiswa(String id);

	List<MahasiswaFoto> getMahasiswaFoto(String id);

	List<Mahasiswa> getMahasiswaWaliByNip(String nip);

	List<FRSFoto> getMahasiswaWali(String nip, String prodi, Integer tahun, Integer semester);

	List<OrangTua> getOrtuMahasiswa(String id);

	WaliMahasiswa getWaliMahasiswa(String id);

	List<Pekerjaan> getPekerjaanMahasiswa(String id);

	List<Akademik> getAkademikMahasiswa(String id);

	List<KeaktifanMahasiswa> getKeaktifanMahasiswa(String id);

	Foto getFotoMahasiswa(String nrp);

	List<ListKuesioner> getListKuesionerMK(String nrp, String tahun, Integer semester, String bahasa);

	List<ListKuesioner> getListKuesionerDosen(String nrp, String tahun, Integer semester);

	List<Kuesioner> getPertanyaanMK(String jenjang, String tahunKurikulum, String bahasa);

	List<Kuesioner> getPertanyaanDosen(String jenjang, String tahunKurikulum, String bahasa);

	List<Jawaban> getJawaban(String jenjang, String idPertanyaan, String tahunKurikulum, String bahasa);

	List<KemajuanStudi> getKemajuanStudi(String nrp);

	List<DaftarNilai> getTranskripMahasiswa(Integer tahun, Integer semester, Integer semesterAmbil, String nrp,
			String bahasa);

	List<PeriodeMahasiswa> getPeriode(String nrp);

	List<JadwalKuliah> getJadwalKuliah(String nrp, Integer tahun, Integer semester, String kodeProdi, String bahasa);

	List<JadwalKuliah> getJadwalKuliahByIdMk(String idmk, String nrp, Integer tahun, Integer semester, String kodeProdi,
			String bahasa);

	List<JadwalKuliah> getJadwalKuliahSelanjutnya(String nrp, Integer tahun, Integer semester, Integer idHari,
			String kodeProdi, String bahasa);

	List<CekPembayaran> getCekPembayaran(String nrpLama, String nrpBaru);

	Pembayaran getPembayaran1(String id, String semester, String key2);

	Pembayaran getPembayaran2or5(String id, String semester, String key2);

	Pembayaran getPembayaran0(String id, String semester, String key2);

	List<DetailPembayaran> getDetailPembayaran(String id);

	Integer getSemesterKe(String nrp);

	List<MahasiswaFoto> getListMahasiswa(String id, String nrp, String query, String kodeProdi, Integer angkatan,
			int page, int perPage, String order);

	List<MahasiswaFoto> getListMahasiswaNonAktif(String id, String nrp, String query, String kodeProdi,
			Integer angkatan, int page, int perPage, String order);

	String getNrpBaru(String nrp);

	String getNrpLama(String nrp);

	String getStatusMahasiswa(String nrp);

	Integer getTotalSksPersiapan(String nrp, String tahap, String bahasa);

	Double getNilaiPersiapan(String nrp, String tahap, String bahasa);

	List<DaftarNilai> getDaftarNilaiPersiapan(String nrp, String tahap, String bahasa);

	Transkrip getTranskrip(String nrp);

	Integer getSksTempuh(String nrp);

	Integer getSksLulus(String nrp);

	Integer checkVerified(String nrp);
}
