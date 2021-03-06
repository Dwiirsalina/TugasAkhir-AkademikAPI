package id.its.akademik.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.dao.JadwalDao;
import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.cache.AkademikCache;
import id.its.akademik.dao.mapper.KurikulumRowMapper;
import id.its.akademik.domain.CekPembayaran;
import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.DetailPembayaran;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.IPD;
import id.its.akademik.domain.JadwalKelas;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.KelasAmbil;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MKLanggarPrasyarat;
import id.its.akademik.domain.MKUlang;
import id.its.akademik.domain.MKWajibAmbil;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.NilaiKuliah;
import id.its.akademik.domain.Pembayaran;
import id.its.akademik.domain.Periode;
import id.its.akademik.domain.Persiapan;
import id.its.akademik.domain.Transkrip;
import id.its.akademik.domain.WaktuPermanenNilai;

public class AkademikServiceImpl implements AkademikService {

	private AkademikDao akademikDao;
	private JadwalDao jadwalDao;
	private MahasiswaDao mahasiswaDao;
	private AkademikCache akademikCache;

	public AkademikDao getAkademikDao() {
		return akademikDao;
	}

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}

	public JadwalDao getJadwalDao() {
		return jadwalDao;
	}

	public void setJadwalDao(JadwalDao jadwalDao) {
		this.jadwalDao = jadwalDao;
	}

	public MahasiswaDao getMahasiswaDao() {
		return mahasiswaDao;
	}

	public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
		this.mahasiswaDao = mahasiswaDao;
	}
	
	public void setAkademikCache(AkademikCache akademikCache) {
		this.akademikCache = akademikCache;
	}
	
	public AkademikCache getAkademikCahe() {
		return akademikCache;
	}

	@Override
	public List<Periode> getPeriodeByDepartemen(String idDepartemen) {
		return akademikDao.getPeriodeByDepartemen(idDepartemen);
	}

	@Override
	public Periode getPeriodeAktif() {
		return akademikDao.getPeriodeAktif();
	}


	@Override
	public List<Kelas> getKelasDitawarkan(String kodeProdi, Integer tahun, Integer semester, Integer page,
			Integer perPage, String urutkan, String bahasa, Boolean isPengayaan) {
		List<Kelas> listKelas = akademikDao.getKelasDitawarkan(kodeProdi, tahun, semester, page, perPage, urutkan,
				bahasa, isPengayaan);
		List<JadwalKelas> listJadwal = jadwalDao.getJadwalKelas(kodeProdi, tahun, semester);

		for (Kelas k : listKelas) {

			List<JadwalKelas> jadwalPerKelas = new ArrayList<JadwalKelas>();

			for (JadwalKelas jk : listJadwal) {
				if ((jk.getKodeMk() != null && jk.getKodeMk().equals(k.getIdMk())) && jk.getKelas() != null
						&& jk.getKelas().equals(k.getKelas()) && jk.getIdProdi().equals(k.getIdProdi())) {

					jadwalPerKelas.add(jk);
				}
			}

			if (jadwalPerKelas.size() > 0) {
				Collections.reverse(jadwalPerKelas);
				k.setJadwal(jadwalPerKelas);
			}

		}

		return listKelas;
	}

	@Override
	public List<String> getKeteranganKodeJurusanBoleh(String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas) {
		return akademikDao.getKeteranganKodeJurusanBoleh(idMatkul, tahun, semester, tahunKurikulum, kelas);
	}

	@Override
	public Integer getTahunKurikulum(Integer tahun) {
		return akademikDao.getTahunKurikulum(tahun);
	}
	// @Override
	// public List<Kelas> getKelasDitawarkan(String kodeProdi, Integer tahun,
	// Integer semester, Integer page,
	// Integer perPage) {
	// List<Kelas> listKelas = akademikDao.getKelasDitawarkan(kodeProdi, tahun,
	// semester, page, perPage);
	//
	// for (Kelas k : listKelas) {
	// List<JadwalKelas> jadwalPerKelas =
	// jadwalDao.getJadwalKelasByIdMk(kodeProdi, tahun, semester, k.getIdMk());
	// k.setJadwal(jadwalPerKelas);
	// }
	//
	// return listKelas;
	// }

	@Override
	public List<MahasiswaFoto> getPesertaKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester) {
		return akademikDao.getPesertaKelas(kodeProdi, idMatkul, kodeKelas, tahun, semester);
	}

	@Override
	public List<NilaiKuliah> getNilaiMahasiswa(String nrp) {
		return akademikDao.getNilaiMahasiswa(nrp);
	}

	@Override
	public List<NilaiKuliah> getNilaiMahasiswa(String nrp, Integer tahun, Integer semester) {
		return akademikDao.getNilaiMahasiswa(nrp, tahun, semester);
	}

	@Override
	public List<DaftarNilai> getTranskripMahasiswa(String nrp) {
		return akademikDao.getTranskripMahasiswa(nrp);
	}

	@Override
	public List<FRS> getListFRS(String kodeJurusan, String nip, Integer tahun, Integer semester) {
		return akademikDao.getListFRS(kodeJurusan, nip, tahun, semester);
	}

	@Override
	public FRS getFRS(String nrp, Integer tahun, Integer semester) {
		return akademikDao.getFRS(nrp, tahun, semester);
	}

	@Override
	public List<KelasAmbil> getKelasDiambil(String nrp, Integer tahun, Integer semester, String bahasa) {
		return akademikDao.getKelasDiambil(nrp, tahun, semester, bahasa);
	}

	@Override
	public List<MKUlang> getMKDiulang(String nrp) {
		return akademikDao.getMKDiulang(nrp);
	}

	@Override
	public List<MKWajibAmbil> getMKWajibAmbil(String nrp) {
		return akademikDao.getMKWajibAmbil(nrp);
	}

	@Override
	public List<MKLanggarPrasyarat> getMKLanggarPrasyarat(Integer tahun, Integer semester, String nrp) {
		return akademikDao.getMKLanggarPrasyarat(tahun, semester, nrp);
	}

	@Override
	public List<WaktuPermanenNilai> getWaktuPermanenNilai(String nip, Integer tahunAjaran, Integer semester) {
		return akademikDao.getWaktuPermanenNilai(nip, tahunAjaran, semester);
	}

	@Override
	public void addMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan,
			Integer tahunKurikulum, String idMK, String kelas) {
		akademikDao.addMataKuliahFRS(nrp, tahun, semester, kodeJurusan, tahunKurikulum, idMK, kelas);
	}

	@Override
	public void deleteMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, String idMK,
			String kelas, Integer tahunKurikulum) {
		akademikDao.deleteMataKuliahFRS(nrp, tahun, semester, kodeJurusan, idMK, kelas, tahunKurikulum);

	}

	@Override
	public void setujuFRS(String nrp, Integer tahun, Integer semester, String nip) {
		akademikDao.setujuFRS(nrp, tahun, semester, nip);
	}

	@Override
	public void batalFRS(String nrp, Integer tahun, Integer semester) {
		akademikDao.batalFRS(nrp, tahun, semester);
	}

	@Override
	public Integer getKapasitasKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester, Integer tahunKurikulum) {
		return akademikDao.getKapasitasKelas(kodeProdi, idMatkul, kodeKelas, tahun, semester, tahunKurikulum);
	}

	@Override
	public List<Kelas> getDayaTampung(String kodeProdi, String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh) {
		return akademikDao.getDayaTampung(kodeProdi, idMatkul, tahun, semester, tahunKurikulum, kodeJurusanBoleh);
	}

	@Override
	public Integer getJumlahKuliah(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh) {
		return akademikDao.getJumlahKuliah(kodeProdi, idMatkul, kodeKelas, tahun, semester, tahunKurikulum,
				kodeJurusanBoleh);
	}

	@Override
	public List<MataKuliah> getMataKuliah(String kodeProdi, Integer tahun, Integer semester) {
		return akademikDao.getMataKuliah(kodeProdi, tahun, semester);
	}

	@Override
	public List<IPD> getIPD(String kodeProdi, Integer tahun, Integer semester) {
		return akademikDao.getIPD(kodeProdi, tahun, semester);
	}

	@Override
	public List<MataKuliahSyarat> getSpesifikMataKuliah(String kodeProdi, String idMk, Integer semester,
			String bahasa) {
		return akademikDao.getSpesifikMataKuliah(kodeProdi, idMk, semester, bahasa);
	}

	@Override
	public List<MataKuliah> getSyaratMataKuliah(String tahunKurikulum, String kodeProdi, String idMk, String bahasa) {
		return akademikDao.getSyaratMataKuliah(tahunKurikulum, kodeProdi, idMk, bahasa);
	}

	@Override
	public void setTranskrip(String nrp, String bahasa, Transkrip transkrip) {
		String tahap1 = "Persiapan";

		if (nrp.substring(4, 6).equalsIgnoreCase("03")) {
			String tahap2 = "Diploma";
			Integer totSksPersiapan = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap1, bahasa);
			Double totNilaiPersiapan = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap1, bahasa);

			List<DaftarNilai> daftarPersiapan = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap1, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();
			Persiapan p = new Persiapan();
			p.setDaftarNilai(daftarPersiapan);
			p.setIpTahap(totNilaiPersiapan / totSksPersiapan);
			p.setTotalSks(totSksPersiapan);
			p.setKeterangan(tahap1);
			persiapan.add(p);

			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);

			transkrip.setSks(totSksPersiapan + totSksTahap);
			transkrip.setIpk((totNilaiPersiapan + totNilaiTahap) / (totSksPersiapan + totSksTahap));
		} else if (nrp.substring(4, 6).equalsIgnoreCase("10")) {
			String tahap2 = "Sarjana";

			Integer totSksPersiapan = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap1, bahasa);
			Double totNilaiPersiapan = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap1, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();

			List<DaftarNilai> daftarPersiapan = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap1, bahasa);
			Persiapan p = new Persiapan();
			p.setDaftarNilai(daftarPersiapan);
			p.setIpTahap(totNilaiPersiapan / totSksPersiapan);
			p.setTotalSks(totSksPersiapan);
			p.setKeterangan(tahap1);
			persiapan.add(p);

			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);
			transkrip.setSks(totSksPersiapan + totSksTahap);
			transkrip.setIpk((totNilaiPersiapan + totNilaiTahap) / (totSksPersiapan + totSksTahap));
		} else if (nrp.substring(4, 6).equalsIgnoreCase("04")) {
			String tahap2 = "Diploma";
			Integer totSksPersiapan = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap1, bahasa);
			Double totNilaiPersiapan = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap1, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();

			List<DaftarNilai> daftarPersiapan = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap1, bahasa);
			Persiapan p = new Persiapan();
			p.setDaftarNilai(daftarPersiapan);
			p.setIpTahap(totNilaiPersiapan / totSksPersiapan);
			p.setTotalSks(totSksPersiapan);
			p.setKeterangan(tahap1);
			persiapan.add(p);

			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);
			transkrip.setSks(totSksPersiapan + totSksTahap);
			transkrip.setIpk((totNilaiPersiapan + totNilaiTahap) / (totSksPersiapan + totSksTahap));
		} else if (nrp.substring(4, 6).equalsIgnoreCase("20")) {
			String tahap2 = "Magister";
			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();
			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);
			transkrip.setSks(totSksTahap);
			transkrip.setIpk((totNilaiTahap) / (totSksTahap));
		} else if (nrp.substring(4, 6).equalsIgnoreCase("11")) {
			String tahap2 = "Profesi";
			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();

			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);
			transkrip.setSks(totSksTahap);
			transkrip.setIpk((totNilaiTahap) / (totSksTahap));
		} else if (nrp.substring(4, 6).equalsIgnoreCase("30")) {
			String tahap2 = "Doktor";
			Integer totSksTahap = this.mahasiswaDao.getTotalSksPersiapan(nrp, tahap2, bahasa);
			Double totNilaiTahap = this.mahasiswaDao.getNilaiPersiapan(nrp, tahap2, bahasa);
			List<Persiapan> persiapan = new ArrayList<>();

			List<DaftarNilai> daftarTahap = this.mahasiswaDao.getDaftarNilaiPersiapan(nrp, tahap2, bahasa);
			Persiapan tahap = new Persiapan();
			tahap.setDaftarNilai(daftarTahap);
			tahap.setIpTahap(totNilaiTahap / totSksTahap);
			tahap.setTotalSks(totSksTahap);
			tahap.setKeterangan(tahap2);
			persiapan.add(tahap);

			transkrip.setTahap(persiapan);
			transkrip.setSks(totSksTahap);
			transkrip.setIpk((totNilaiTahap) / (totSksTahap));
		}
	}

	@Override
	public void setFRS(String nrp, Integer tahun, Integer semester, String bahasa, FRS frs) {

		boolean mhspaket = false;
		boolean mhss3 = false;
		boolean mhss2 = false;
		if (nrp.substring(4, 5).equalsIgnoreCase("3"))
			mhss3 = true;
		if (nrp.substring(4, 5).equalsIgnoreCase("2"))
			mhss2 = true;
		if (nrp.substring(2, 4).equalsIgnoreCase(tahun.toString().substring(2, 4)) && !mhss3 && !mhss2)
			mhspaket = true;

		Integer batas = 0;
		if (nrp.substring(4, 5).equalsIgnoreCase("2")) {
			if (nrp.substring(2, 4) == tahun.toString().substring(2, 4) && semester == 1) {
				batas = 15;
			} else {
				if (frs.getIpsLalu() >= 3.00) {
					batas = 15;
				} else {
					batas = 12;
				}
			}
		} else if (nrp.substring(4, 5).equalsIgnoreCase("0") || nrp.substring(4, 5).equalsIgnoreCase("1")) {
			int tahunKurikulum = this.akademikDao.getTahunKurikulum(tahun);
			if (tahunKurikulum <= 2009) {
				if (frs.getIpsLalu() >= 3.00) {
					batas = 24;
				} else if (frs.getIpsLalu() >= 2.00) {
					batas = 20;
				} else {
					batas = 16;
				}
			} else {
				if (frs.getIpsLalu() >= 3.50) {
					batas = 24;
				} else if (frs.getIpsLalu() >= 3.00) {
					batas = 22;
				} else if (frs.getIpsLalu() >= 2.50) {
					batas = 20;
				} else if (frs.getIpsLalu() >= 2.00) {
					batas = 18;
				} else {
					batas = 16;
				}
			}
		}
		frs.setBatasSks(batas);
		Integer totalSks = this.akademikDao.getTotalSks(nrp, tahun, semester);
		frs.setSisaSks(batas - totalSks);
		frs.setKelasAmbil(this.akademikDao.getKelasDiambil(nrp, tahun, semester, bahasa));
		for (KelasAmbil kelas : frs.getKelasAmbil()) {
			List<JadwalKuliah> jadwal = this.mahasiswaDao.getJadwalKuliahByIdMk(kelas.getKode(), nrp, tahun, semester,
					kelas.getKodeProdi(), bahasa);
			if (mhspaket && (kelas.getSemester() == 1 || kelas.getSemester() == 2)) {
				kelas.setCanDelete(false);
			}
			kelas.setJadwal(jadwal);
		}
	}

	@Override
	public List<Pembayaran> getPembayaran(String nrp) {
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		String nrpBaru = this.mahasiswaDao.getNrpBaru(nrp);

		List<CekPembayaran> pembayaran = this.mahasiswaDao.getCekPembayaran(nrpLama, nrpBaru);

		List<Pembayaran> list = new ArrayList<>();
		Pembayaran bayar = new Pembayaran();
		for (CekPembayaran cp : pembayaran) {
			switch (cp.getFlag()) {
			case "1":
			case "4":
			case "7":
				bayar = this.mahasiswaDao.getPembayaran1(cp.getId(), cp.getSemesterDikti(), cp.getKey2());
				break;
			case "5":
			case "2":
				bayar = this.mahasiswaDao.getPembayaran2or5(cp.getId(), cp.getSemesterDikti(), cp.getKey2());
				break;
			case "0":
				bayar = this.mahasiswaDao.getPembayaran0(cp.getId(), cp.getSemesterDikti(), cp.getKey2());
				break;
			}

			List<DetailPembayaran> detail = this.mahasiswaDao.getDetailPembayaran(bayar.getId());
			bayar.setDetailPembayaran(detail);
			list.add(bayar);
		}
		return list;
	}
}