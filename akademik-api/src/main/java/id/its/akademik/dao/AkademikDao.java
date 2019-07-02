package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.IPD;
import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.KelasAmbil;
import id.its.akademik.domain.Komentar;
import id.its.akademik.domain.Kuesioner;
import id.its.akademik.domain.KuesionerDosen;
import id.its.akademik.domain.KuesionerMK;
import id.its.akademik.domain.MKLanggarPrasyarat;
import id.its.akademik.domain.MKUlang;
import id.its.akademik.domain.MKWajibAmbil;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.NilaiKuliah;
import id.its.akademik.domain.Periode;
import id.its.akademik.domain.Prasyarat;
import id.its.akademik.domain.Sekarang;
import id.its.akademik.domain.WaktuFrs;
import id.its.akademik.domain.WaktuPermanenNilai;

public interface AkademikDao {

	List<Periode> getPeriodeByDepartemen(String idDepartemen);

	Periode getPeriodeAktif();

	List<Kelas> getKelasDitawarkan(String kodeProdi, Integer tahun, Integer semester, Integer page, Integer perPage,
			String urutkan, String bahasa, Boolean isPengayaan);

	List<Kelas> getKelasKuliah(String nrp, Integer tahun, Integer semester);

	List<MahasiswaFoto> getPesertaKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester);

	List<NilaiKuliah> getNilaiMahasiswa(String nrp);

	List<NilaiKuliah> getNilaiMahasiswa(String nrp, Integer tahun, Integer semester);

	List<DaftarNilai> getTranskripMahasiswa(String nrp);

	List<FRS> getListFRS(String kodeJurusan, String nip, Integer tahun, Integer semester);

	List<FRS> getListFRS(String nrp);

	FRS getFRS(String nrp, Integer tahun, Integer semester);

	Integer getTotalSks(String nrp, Integer tahun, Integer semester);

	List<KelasAmbil> getKelasDiambil(String nrp, Integer tahun, Integer semester, String bahasa);

	List<MKUlang> getMKDiulang(String nrp);

	List<MKWajibAmbil> getMKWajibAmbil(String nrp);

	List<MKLanggarPrasyarat> getMKLanggarPrasyarat(Integer tahun, Integer semester, String nrp);

	List<WaktuPermanenNilai> getWaktuPermanenNilai(String nip, Integer tahunAjaran, Integer semester);

	Integer addMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, Integer tahunKurikulum,
			String idMK, String kelas);

	Integer deleteMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, String idMK,
			String kelas, Integer tahunKurikulum);

	void setujuFRS(String nrp, Integer tahun, Integer semester, String nip);

	void batalFRS(String nrp, Integer tahun, Integer semester);

	Integer getStatusPembayaranSpp(String nrp, Integer tahun, Integer semester);

	Integer getEvaluasi(String nrp, Integer tahun, Integer semester);

	Integer getEWS(String nrp, Integer tahun, Integer semester);

	Boolean getFRSDisetujui(String nrp, Integer tahun, Integer semester);

	List<WaktuFrs> getJadwalFRS(Integer tahun, Integer semester);

	Integer getSKSAmbil(String nrp, Integer tahun, Integer semester);

	Integer getSKS(String idMatkul, Integer tahunKurikulum);

	String getKelas(String nrp, Integer tahunKurikulum, String kodeJurusan, Integer tahun, Integer semester,
			String idMatkul);

	String getNamaMKKurikulum(String idMk, Integer tahunKurikulum);

	Integer noKelasTPB(Character kelas);

	Boolean cekJadwalRuang(Integer tahun, Integer semester, String prodi, String idMk, String kelas,
			Boolean isPengayaan);

	Integer getKapasitasKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum);

	List<Kelas> getDayaTampung(String kodeProdi, String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh);

	Boolean getKelasTampung(String idMatkul, String kodeProdi, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kodeJurusanBoleh, String kelas);

	Integer getJumlahKuliah(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh);

	Double getIpsLalu(String nrp, Integer tahun, Integer semester);

	Integer getMaxSks(Double ips, String nrp, Integer tahun, Integer semester, Integer tahunKurikulum);

	List<Prasyarat> getPrasyarat(String nrp, String idMK, Integer tahunKurikulum);

	Boolean getVLstudi(String nrp);

	Boolean getPersiapan(String nrp, String idMK, Integer tahunKurikulum);

	int getCheckBE(String nrp, Integer tahunKurikulum, String idMk);

	int getCheckEqTidakBolehDiulang(String nrp, Integer tahunKurikulum, String idMk);

	List<MataKuliah> getMataKuliah(String kodeProdi, Integer tahun, Integer semester);

	List<IPD> getIPD(String kodeProdi, Integer tahun, Integer semester);

	List<Komentar> getKomentarIPM(String nip, String idMk, Integer tahun, Integer semester, String kelas);

	List<Komentar> getKomentarIPD(String nip, String idMk, Integer tahun, Integer semester, String kelas);

	List<Kuesioner> getKuesionerIPM(Integer tahunKurikulum);

	List<Kuesioner> getKuesionerIPD(Integer tahunKurikulum);

	List<MataKuliahSyarat> getSpesifikMataKuliah(String kodeProdi, String idMk, Integer semester, String bahasa);

	List<MataKuliah> getSyaratMataKuliah(String tahunKurikulum, String kodeProdi, String idMk, String bahasa);

	Boolean postKuesionerMk(String nrp, KuesionerMK kuesioner, String tahum, String semester, String tahunKurikulum);

	Boolean postKuesionerDosen(String nrp, KuesionerDosen kuesioner, String tahun, String semester,
			String tahunKurikulum);

	Boolean tahapFRS(Integer tahun, Integer semester);

	Boolean tahapUbahFRS(Integer tahun, Integer semester);

	Boolean tahapDrop(Integer tahun, Integer semester);

	List<Sekarang> getSekarang();

	Integer getTahunKurikulum(Integer tahun);

	String getKodeKelasUPMB(Integer tahun, Integer semester, String kodeJurusan, String idMk, String kelas);

	Boolean getIsPengayaan(String kodeJurusan, String idMk);

	List<String> getKeteranganKodeJurusanBoleh(String idMatkul, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kelas);

	Boolean cekJadwalKuesioner();

	Integer isBukaPengayaan(String kodeProdi, Integer tahun, Integer semester);

	Integer getSksTempuhPengayaan(String nrp);

}
