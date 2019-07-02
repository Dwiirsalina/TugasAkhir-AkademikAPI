package id.its.akademik.service;

import java.util.List;

import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.IPD;
import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.KelasAmbil;
import id.its.akademik.domain.MKLanggarPrasyarat;
import id.its.akademik.domain.MKUlang;
import id.its.akademik.domain.MKWajibAmbil;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.NilaiKuliah;
import id.its.akademik.domain.Pembayaran;
import id.its.akademik.domain.Periode;
import id.its.akademik.domain.Transkrip;
import id.its.akademik.domain.WaktuPermanenNilai;

public interface AkademikService {

	List<Periode> getPeriodeByDepartemen(String idDepartemen);

	Periode getPeriodeAktif();

	List<Kelas> getKelasDitawarkan(String kodeProdi, Integer tahun, Integer semester, Integer page, Integer perPage,
			String urutkan, String bahasa, Boolean isPengayaan);

	List<String> getKeteranganKodeJurusanBoleh(String idMatkul, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kelas);

	List<MataKuliah> getMataKuliah(String kodeProdi, Integer tahun, Integer semester);

	List<MataKuliahSyarat> getSpesifikMataKuliah(String kodeProdi, String idMk, Integer semester, String bahasa);

	List<MataKuliah> getSyaratMataKuliah(String tahunKurikulum, String kodeProdi, String idMk, String bahasa);

	List<MahasiswaFoto> getPesertaKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester);

	Integer getKapasitasKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum);

	List<Kelas> getDayaTampung(String kodeProdi, String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh);

	Integer getJumlahKuliah(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh);

	List<NilaiKuliah> getNilaiMahasiswa(String nrp);

	List<NilaiKuliah> getNilaiMahasiswa(String nrp, Integer tahun, Integer semester);

	List<DaftarNilai> getTranskripMahasiswa(String nrp);

	List<FRS> getListFRS(String kodeJurusan, String nip, Integer tahun, Integer semester);

	FRS getFRS(String nrp, Integer tahun, Integer semester);

	List<KelasAmbil> getKelasDiambil(String nrp, Integer tahun, Integer semester, String bahasa);

	List<MKUlang> getMKDiulang(String nrp);

	List<MKWajibAmbil> getMKWajibAmbil(String nrp);

	List<MKLanggarPrasyarat> getMKLanggarPrasyarat(Integer tahun, Integer semester, String nrp);

	List<WaktuPermanenNilai> getWaktuPermanenNilai(String nip, Integer tahunAjaran, Integer semester);

	void addMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, Integer tahunKurikulum,
			String idMK, String kelas);

	void deleteMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, String idMK, String kelas,
			Integer tahunKurikulum);

	void setujuFRS(String nrp, Integer tahun, Integer semester, String nip);

	void batalFRS(String nrp, Integer tahun, Integer semester);

	List<IPD> getIPD(String departemen, Integer tahun, Integer semester);

	Integer getTahunKurikulum(Integer tahun);

	void setTranskrip(String nrp, String bahasa, Transkrip transkrip);

	void setFRS(String nrp, Integer tahun, Integer semester, String bahasa, FRS frs);

	List<Pembayaran> getPembayaran(String nrp);

}
