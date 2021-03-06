package id.its.akademik.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.AkademikDao;
import id.its.akademik.dao.mapper.AkademikRowMapper;
import id.its.akademik.dao.mapper.DaftarNilaiRowMapper;
import id.its.akademik.dao.mapper.FRSRowMapper;
import id.its.akademik.dao.mapper.FotoMahasiswaRowMapper;
import id.its.akademik.dao.mapper.IPDRowMapper;
import id.its.akademik.dao.mapper.KelasAmbilRowMapper;
import id.its.akademik.dao.mapper.KelasRowMapper;
import id.its.akademik.dao.mapper.KomentarRowMapper;
import id.its.akademik.dao.mapper.KuesionerRowMapper;
import id.its.akademik.dao.mapper.MKLanggarPrasyaratRowMapper;
import id.its.akademik.dao.mapper.MKUlangRowMapper;
import id.its.akademik.dao.mapper.MKWajibAmbilRowMapper;
import id.its.akademik.dao.mapper.MahasiswaFotoRowMapper;
import id.its.akademik.dao.mapper.MataKuliahRowMapper;
import id.its.akademik.dao.mapper.MataKuliahSyaratRowMapper;
import id.its.akademik.dao.mapper.NilaiKuliahRowMapper;
import id.its.akademik.dao.mapper.PeriodeRowMapper;
import id.its.akademik.dao.mapper.PrasyaratRowMapper;
import id.its.akademik.dao.mapper.ProdiAjarRowMapper;
import id.its.akademik.dao.mapper.SekarangRowMapper;
import id.its.akademik.dao.mapper.WaktuFRSRowMapper;
import id.its.akademik.dao.mapper.WaktuPermanenNilaiRowMapper;
import id.its.akademik.domain.Akademik;
import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.Foto;
import id.its.akademik.domain.IPD;
import id.its.akademik.domain.JadwalKuliah;
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
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.Sekarang;
import id.its.akademik.domain.TanyaJawab;
import id.its.akademik.domain.WaktuFrs;
import id.its.akademik.domain.WaktuPermanenNilai;

public class JdbcAkademikDao extends AbstractDao implements AkademikDao {

	private Logger log = LoggerFactory.getLogger(JdbcAkademikDao.class);

	@Override
	public List<Periode> getPeriodeByDepartemen(String idDepartemen) {

		String sql = "SELECT DISTINCT KE_KodeJurusan AS kode_dept, KE_Tahun AS tahun, KE_IDSemester AS semester, s.Semester+' '+KE_Tahun+'/'+CAST(KE_Tahun+1 AS VARCHAR) AS keterangan FROM Kelas JOIN val_semester s ON KE_IDSemester = s.ID WHERE KE_KodeJurusan = :kode_departemen ORDER BY KE_Tahun ASC, KE_IDSemester ASC";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kode_departemen", idDepartemen);

		List<Periode> listPeriode = jdbcTemplate.query(sql, params, new PeriodeRowMapper());

		Periode periodeAktif = this.getPeriodeAktif();

		for (Periode p : listPeriode) {
			if (p.getTahun().intValue() == periodeAktif.getTahun().intValue()
					&& p.getSemester().intValue() == periodeAktif.getSemester().intValue()) {
				p.setAktif(true);
			} else {
				p.setAktif(false);
			}
		}

		return listPeriode;
	}

	@Override
	public List<Kelas> getKelasDitawarkan(String kodeProdi, Integer tahun, Integer semester, Integer page,
			Integer perPage, String urutkan, String bahasa, Boolean isPengayaan) {
		switch (urutkan.toLowerCase()) {
		case "nama":
			urutkan = "nama_mk, kelas";
			break;
		case "semester":
			urutkan = "semester, nama_mk, kelas";
			break;
		case "kelas":
			urutkan = "kelas, id_mk, nama_mk";
			break;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM(SELECT q1.*, Row_number() OVER( ORDER BY q1." + urutkan
				+ ") AS RowNum FROM (SELECT KE_KodeJurusan AS kodejur, KE_Tahun AS tahun, kr_semester AS semester, MK_ID AS id_mk, dbo.MKID(MK_ID, MK_ThnKurikulum) AS kode_mk, CASE :bahasa WHEN 'en' THEN mk_mata_kuliahinggris ELSE MK_Mata_Kuliah END AS nama_mk, mk_mata_kuliahinggris AS nama_mk_inggris, MK_KreditKuliah AS sks, KE_Kelas AS kelas, KE_Terisi AS terisi, KE_DayaTampung AS daya_tampung, KE_PE_NIPPengajar AS nip_dosen, PE_NamaLengkap as dosen, PE_NipBaru AS nip_baru FROM derived.cacheKelasDitawarkan WHERE ");

		if (kodeProdi.equalsIgnoreCase("__tpb")) {
			sql.append("(KE_KodeJurusan = :kode_prodi) AND ");
		} else if (isPengayaan) {
			sql.append(
					"(KE_KodeJurusan <> :kode_prodi) AND coalesce(isPengayaan,0) = 1 AND KE_KodeJurusan NOT IN ('__MKU','__TPB') AND COALESCE(KE_ID_LingkupPengayaan, 0)<>3 AND ");
		} else {
			sql.append(
					"(KE_KodeJurusan = :kode_prodi) AND (coalesce(isPengayaan,0)<>1 OR COALESCE(KE_ID_LingkupPengayaan, 0)<>1) AND ");
		}

		sql.append(
				"(KE_IDSemester = :semester ) AND (KE_Tahun = :tahun ) ) AS q1) AS q2 WHERE rownum BETWEEN ( :page * :per_page ) + 1 AND ( :page + 1 ) * :per_page ");

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kode_prodi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("bahasa", bahasa);
		params.put("page", page);
		params.put("per_page", perPage);

		List<Kelas> listKelas = jdbcTemplate.query(sql.toString(), params, new KelasRowMapper());

		return listKelas;
	}

	@Override
	public List<Kelas> getKelasKuliah(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT DISTINCT k.KU_KE_Kelas AS kelas, k.KU_KE_Tahun AS tahun, k.KU_KE_IDSemester AS semester, mk.MK_ID AS id_mk, dbo.MKID(mk.MK_ID, ke.KE_KR_MK_ThnKurikulum) AS kode_mk, mk.MK_Mata_Kuliah AS nama_mk, mk.MK_Mata_KuliahInggris AS nama_mk_inggris, p.PE_Nip AS nip_dosen, p.PE_NipBaru AS nip_baru, p.PE_NamaLengkap AS dosen FROM Kuliah k JOIN kelas ke ON k.KU_KE_KR_MK_ID = ke.KE_KR_MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = ke.KE_KR_MK_ThnKurikulum and k.KU_KE_KodeJurusan = ke.KE_KodeJurusan AND k.KU_KE_Kelas = ke.KE_Kelas AND ke.KE_IDSemester = k.KU_KE_IDSemester AND k.KU_KE_Tahun = ke.KE_Tahun JOIN pegawai p ON p.PE_Nip = ke.KE_PE_NIPPengajar JOIN MataKuliah mk ON k.KU_KE_KR_MK_ID = mk.MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = mk.MK_ThnKurikulum WHERE KU_MA_Nrp = :nrp AND k.KU_KE_Tahun = :tahun AND k.KU_KE_IDSemester = :semester";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		List<Kelas> listKelas = jdbcTemplate.query(sql, params, new KelasRowMapper());

		return listKelas;
	}

	@Override
	public List<NilaiKuliah> getNilaiMahasiswa(String nrp) {

		String sql = "SELECT KU_KE_Tahun as tahun, KU_KE_IDSemester as semester, dbo.MKID(KU_KE_KR_MK_ID, KU_KE_KR_MK_ThnKurikulum) as kode, MK_Mata_Kuliah as mk, MK_KreditKuliah as sks, KU_KE_Kelas as kelas, KU_NilaiHuruf as nilai_huruf, NilaiAngka as nilai_angka FROM kuliah LEFT JOIN val_Nilai ON KU_NilaiHuruf=NilaiHuruf LEFT JOIN val_Semester ON KU_KE_IDSemester=ID, MataKuliah WHERE KU_KE_KR_MK_ThnKurikulum+KU_KE_KR_MK_ID=MK_ThnKurikulum+MK_ID AND KU_MA_NRP = :nrp ORDER BY KU_KE_Tahun, KU_KE_IDSemester, MK_Mata_Kuliah";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<NilaiKuliah> listNilai = jdbcTemplate.query(sql, params, new NilaiKuliahRowMapper());

		return listNilai;
	}

	@Override
	public List<NilaiKuliah> getNilaiMahasiswa(String nrp, Integer tahun, Integer semester) {

		String sql = "SELECT KU_KE_Tahun as tahun, KU_KE_IDSemester as semester, dbo.MKID(KU_KE_KR_MK_ID, KU_KE_KR_MK_ThnKurikulum) as kode, MK_Mata_Kuliah as mk, MK_KreditKuliah as sks, KU_KE_Kelas as kelas, KU_NilaiHuruf as nilai_huruf, NilaiAngka as nilai_angka FROM kuliah LEFT JOIN val_Nilai ON KU_NilaiHuruf=NilaiHuruf LEFT JOIN val_Semester ON KU_KE_IDSemester=ID, MataKuliah WHERE KU_KE_KR_MK_ThnKurikulum+KU_KE_KR_MK_ID=MK_ThnKurikulum+MK_ID AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester AND KU_MA_NRP = :nrp ORDER BY KU_KE_Tahun, KU_KE_IDSemester, MK_Mata_Kuliah";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<NilaiKuliah> listNilai = jdbcTemplate.query(sql, params, new NilaiKuliahRowMapper());

		return listNilai;
	}

	@Override
	public List<KelasAmbil> getKelasDiambil(String nrp, Integer tahun, Integer semester, String bahasa) {
		String sql = "SELECT * FROM (SELECT Matakuliah.MK_ID AS id_mk, dbo.MKID(Matakuliah.MK_ID, Kelas.KE_KR_MK_ThnKurikulum) AS kode_mk, CASE :bahasa WHEN 'en' THEN MataKuliah.MK_Mata_KuliahInggris ELSE MataKuliah.MK_Mata_Kuliah END AS mk, MataKuliah.MK_KreditKuliah as sks, MataKuliah.MK_ThnKurikulum as thn_kurikulum, CASE WHEN ASCII(Kelas.KE_Kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(Kelas.KE_Kelas) - 150) ELSE Kelas.KE_Kelas END AS kelas, Kelas.KE_KodeJurusan as kode_jurusan, Kurikulum.KR_Semester as semester, Pegawai.PE_NamaLengkap as dosen, Kuliah.KU_NilaiHuruf as nilai_huruf FROM Kuliah WITH (NOLOCK) INNER JOIN Kelas WITH (NOLOCK) ON Kuliah.KU_KE_KodeJurusan = Kelas.KE_KodeJurusan AND Kuliah.KU_KE_Tahun = Kelas.KE_Tahun AND Kuliah.KU_KE_IDSemester = Kelas.KE_IDSemester AND Kuliah.KU_KE_KR_MK_ID = Kelas.KE_KR_MK_ID AND Kuliah.KU_KE_KR_MK_ThnKurikulum = Kelas.KE_KR_MK_ThnKurikulum AND Kuliah.KU_KE_Kelas = Kelas.KE_Kelas INNER JOIN MataKuliah WITH (NOLOCK) ON Kelas.KE_KR_MK_ThnKurikulum = MataKuliah.MK_ThnKurikulum AND Kelas.KE_KR_MK_ID = MataKuliah.MK_ID LEFT OUTER JOIN Kurikulum WITH (NOLOCK) ON Kurikulum.KR_MK_ThnKurikulum = Kuliah.KU_KE_KR_MK_ThnKurikulum AND Kurikulum.KR_MK_ID = Kuliah.KU_KE_KR_MK_ID AND Kurikulum.KR_KodeJurusan = Kuliah.KU_KE_KodeJurusan LEFT OUTER JOIN Pegawai WITH (NOLOCK) ON Kelas.KE_PE_NIPPengajar = Pegawai.PE_Nip WHERE (Kuliah.KU_KE_Tahun = :tahun) AND (Kuliah.KU_KE_IDSemester = :semester ) AND (Kuliah.KU_MA_Nrp = :nrp)) a";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<KelasAmbil> listKelas = jdbcTemplate.query(sql, params, new KelasAmbilRowMapper());
		for (KelasAmbil k : listKelas) {
			k.setStatusAmbil(getStatusAmbil(k.getSemester(), nrp, tahun, semester));
		}

		return listKelas;
	}

	public String getStatusAmbil(int semesterMk, String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT CAST(mhs_semesterke AS int) AS semester FROM Mahasiswa_HistorisStatus WHERE mhs_ma_nrp = :nrp AND MHS_IDSemester = :semester AND MHS_ThnAjaran = :tahun";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("semester", semester);
		params.put("tahun", tahun);

		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		Integer semesterMhs = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			semesterMhs = (int) rs.get("semester");
		} catch (Exception e) {
			return "1";
		}

		if (semesterMhs == semesterMk) {
			return "1";
		} else if (semesterMhs > semesterMk) {
			return "0";
		} else {
			return "2";
		}
	}

	@Override
	public List<FRS> getListFRS(String nrp) {

		String sql = "SELECT * FROM (SELECT mh.MHS_ThnAjaran AS tahun, mh.MHS_IDSemester AS semester, mh.MHS_MA_Nrp AS nrp, v.StatusMhs AS status_aktif, l.MHS_IPS AS ips_lalu FROM Mahasiswa_HistorisStatus mh JOIN val_StatusMahasiswa v ON mh.MHS_IDStatusMhs = v.ID LEFT JOIN Mahasiswa_HistorisStatus l ON l.MHS_MA_NRP = mh.MHS_MA_Nrp AND l.MHS_ThnAjaran+l.MHS_IDSemester = dbo.getSemesterSebelumnya(mh.MHS_ThnAjaran, mh.MHS_IDSemester) UNION SELECT KU_KE_Tahun AS tahun, KU_KE_IDSemester AS semester, KU_MA_Nrp AS nrp, v.StatusMhs AS status_aktif, mh.MHS_IPS AS ips_lalu FROM kuliah k JOIN Mahasiswa_HistorisStatus mh ON k.KU_MA_Nrp = mh.MHS_MA_Nrp AND dbo.getSemesterSebelumnya(k.KU_KE_Tahun, k.KU_KE_IDSemester) = mh.MHS_ThnAjaran+ mh.MHS_IDSemester LEFT JOIN Mahasiswa_HistorisStatus ma ON k.KU_MA_Nrp = ma.MHS_MA_Nrp AND k.KU_KE_Tahun =  ma.MHS_ThnAjaran AND k.KU_KE_IDSemester = ma.MHS_IDSemester LEFT JOIN val_StatusMahasiswa v ON v.ID = ma.MHS_IDStatusMhs) a WHERE a.nrp = :nrp ORDER BY a.tahun DESC, a.semester DESC";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<FRS> listFrs = jdbcTemplate.query(sql, params, new FRSRowMapper());

		return listFrs;
	}

	@Override
	public List<MahasiswaFoto> getPesertaKelas(String kodeJurusan, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester) {
//		String key="pesertaKelas_"+kodeJurusan+"_"+idMatkul+"_"+kodeKelas+"_"+tahun+"_"+semester;
//		ListOperations<String,MahasiswaFoto> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT Mahasiswa.MA_Nrp AS id, Mahasiswa.MA_Nrp_baru AS nrp, Mahasiswa.MA_Nama AS nama, Mahasiswa.MA_Photo AS foto FROM Kuliah INNER JOIN Mahasiswa ON Kuliah.KU_MA_Nrp = Mahasiswa.MA_Nrp WHERE (Kuliah.KU_KE_KR_MK_ID = :id_mk) AND (Kuliah.KU_KE_Tahun = :tahun ) AND (CAST(Kuliah.KU_KE_IDSemester AS char(1)) = :semester ) AND (CASE WHEN ASCII(Kuliah.KU_KE_Kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(Kuliah.KU_KE_Kelas) - 150) ELSE Kuliah.KU_KE_Kelas END = :kode_kelas ) AND (Kuliah.KU_KE_KodeJurusan = :kode_jurusan ) ORDER BY Mahasiswa.MA_Nrp";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("kode_jurusan", kodeJurusan);
			params.put("id_mk", idMatkul);
			params.put("kode_kelas", kodeKelas);
			params.put("tahun", tahun);
			params.put("semester", semester);

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<MahasiswaFoto> listMhs = jdbcTemplate.query(sql, params, new MahasiswaFotoRowMapper());
		
//			List<Map<String,Object>> arrayPesertaKelas=new ArrayList<Map<String,Object>>();
//			for(MahasiswaFoto pesertaKelas:listMhs)
//			{
//				operations.rightPush("pesertaKelas_"+kodeJurusan+"_"+idMatkul+"_"+kodeKelas+"_"+tahun+"_"+semester, pesertaKelas);
//			}
//
//			return listMhs;
//		}
//		else
//		{
//			List<MahasiswaFoto> listMhs=operations.range("pesertaKelas_"+kodeJurusan+"_"+idMatkul+"_"+kodeKelas+"_"+tahun+"_"+semester, 0, -1);
			return listMhs;
//		}

}

	@Override
	public List<DaftarNilai> getTranskripMahasiswa(String nrp) {

		String sql = "SELECT KU_MA_NRP as nrp, Tahap as tahap, m.MHS_SemesterKe as semester_ambil, MK_ThnKurikulum as tahun_kurikulum, dbo.mkid(MK_ID, MK_ThnKurikulum) as id_mk, MK_Mata_Kuliah as mk, MK_Mata_KuliahInggris as mk_inggris, MK_KreditKuliah as sks, KU_KE_Tahun as tahun, KU_KE_IDSemester as semester, Singkatan as singkatan_semester, KU_NilaiHuruf as nilai_huruf, NiAkhir as nilai_akhir, NilaiAngka as nilai_angka, MK_UrutTranskrip as urutan FROM derived.dspTranskrip t JOIN Mahasiswa_HistorisStatus m ON t.KU_MA_NRP = m.MHS_MA_Nrp AND t.KU_KE_Tahun+t.KU_KE_IDSemester = m.MHS_ThnAjaran+m.MHS_IDSemester WHERE KU_MA_NRP = :nrp AND KU_NilaiHuruf <> '_' AND NiAkhir IS NOT NULL ORDER BY m.MHS_SemesterKe";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<DaftarNilai> listTranskrip = jdbcTemplate.query(sql, params, new DaftarNilaiRowMapper());

		return listTranskrip;
	}

	@Override
	public List<FRS> getListFRS(String kodeJurusan, String nip, Integer tahun, Integer semester) {

		String sql = "SELECT :tahun as tahun, :semester as semester, m.MA_NRP as nrp, MA_Nama as nama, StatusMhs as status_aktif, disetujui, l.MHS_IPS as ips_lalu, MA_IPK as ipk, MA_SKSTempuh as sks_tempuh, MA_SKSLulus as sks_lulus,(SELECT SUM(MK_KreditKuliah) FROM Kuliah k, MataKuliah mk WHERE k.KU_KE_KR_MK_ThnKurikulum+k.KU_KE_KR_MK_ID=mk.MK_ThnKurikulum+mk.MK_ID AND (k.KU_KE_KodeJurusan=substring(m.MA_NRP,1,2)+substring(m.MA_NRP,5,3) OR k.KU_KE_KodeJurusan= :kode_jurusan) AND k.KU_KE_Tahun= :tahun AND k.KU_KE_IDSemester= :semester AND k.KU_MA_NRP=m.MA_NRP )as sks_ambil FROM Mahasiswa m LEFT JOIN FRSDisetujui f ON f.MA_NRP = m.MA_NRP AND KE_Tahun = :tahun AND cast(KE_IDSemester as char(1)) = :semester LEFT JOIN Mahasiswa_HistorisStatus l ON l.MHS_MA_NRP = m.MA_NRP AND l.MHS_ThnAjaran+l.MHS_IDSemester = dbo.getSemesterSebelumnya(:tahun, :semester) LEFT JOIN Mahasiswa_HistorisStatus s ON s.MHS_MA_NRP = m.MA_NRP AND s.MHS_ThnAjaran+s.MHS_IDSemester = :tahun_semester LEFT JOIN val_StatusMahasiswa ON ID = s.MHS_IDStatusMhs WHERE MA_PE_NipWali = :nip AND substring(m.MA_NRP,1,2)+substring(m.MA_NRP,5,3)=:kode_jurusan ORDER BY m.MA_NRP ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("tahun_semester", tahun.toString() + semester.toString());
		params.put("kode_jurusan", kodeJurusan);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<FRS> listFrs = jdbcTemplate.query(sql, params, new FRSRowMapper());

		return listFrs;
	}

	@Override
	public FRS getFRS(String nrp, Integer tahun, Integer semester) {
//		String key="getFRS_"+nrp+"_"+tahun+"_"+semester;
//		
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT :tahun as tahun, :semester as semester, m.MA_NRP as nrp, m.MA_NRP_Baru as nrp_baru, MA_Nama as nama, pe.PE_NamaLengkap AS dosen_wali, StatusMhs as status_aktif, disetujui, MA_IPK as ipk, MA_SKSTempuh as sks_tempuh, MA_SKSLulus as sks_lulus,(SELECT SUM(MK_KreditKuliah) FROM Kuliah k, MataKuliah mk WHERE k.KU_KE_KR_MK_ThnKurikulum+k.KU_KE_KR_MK_ID=mk.MK_ThnKurikulum+mk.MK_ID AND k.KU_KE_Tahun= :tahun AND k.KU_KE_IDSemester= :semester AND k.KU_MA_NRP=m.MA_NRP) as sks_ambil, (SELECT mhs_ips AS ips FROM mahasiswa_historisstatus WHERE mhs_thnajaran+mhs_Idsemester IN (SELECT max(mhs_thnajaran+mhs_idsemester) FROM mahasiswa_historisstatus where MHS_MA_NRP=:nrp and mhs_idstatusmhs IN ('N','D','P') AND mhs_thnajaran+mhs_IDSemester<:tahun_semester) and mhs_ma_nrp = :nrp) AS ips_lalu FROM Mahasiswa m JOIN pegawai pe ON m.MA_PE_NipWali = pe.PE_Nip LEFT JOIN FRSDisetujui f ON f.MA_NRP = m.MA_NRP AND KE_Tahun = :tahun AND cast(KE_IDSemester as char(1)) = :semester LEFT JOIN Mahasiswa_HistorisStatus s ON s.MHS_MA_NRP = m.MA_NRP AND s.MHS_ThnAjaran+s.MHS_IDSemester = :tahun_semester LEFT JOIN val_StatusMahasiswa ON ID = s.MHS_IDStatusMhs WHERE m.MA_NRP = :nrp";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", nrp);
			params.put("tahun", tahun);
			params.put("semester", semester);
			params.put("tahun_semester", tahun.toString() + semester.toString());

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<FRS> listFrs = jdbcTemplate.query(sql, params, new FRSRowMapper());

//			if (!listFrs.isEmpty()) {
//				HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//				
//				Map<String,String> frsValue=new HashMap<>();
//				frsValue.put("nama", listFrs.get(0).getNama());
//				frsValue.put("nrp", listFrs.get(0).getNrp());
//				frsValue.put("nrp_baru", listFrs.get(0).getNrpBaru());
//				frsValue.put("dosen_wali", listFrs.get(0).getDosenWali());
//				frsValue.put("status_aktif", listFrs.get(0).getStatusKeaktifan());
//				frsValue.put("batas_sks", String.valueOf(listFrs.get(0).getBatasSks()));
//				frsValue.put("ipk", String.valueOf(listFrs.get(0).getIpk()));
//				
//				operationHash.putAll(key, frsValue);
////				this.getRedisTemplate().expire(key, 40, TimeUnit.SECONDS);
//				return listFrs.get(0);
//			} else {
//				return null;
//			}
//		}
//		else
//		{
//			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//			Map<String,String> frsRedis=operationHash.entries(key);
//			FRS frs=new FRS();
//			frs.setNama(frsRedis.get("nama"));
//			frs.setNrp(frsRedis.get("nrp"));
//			frs.setNrpBaru(frsRedis.get("nrp_baru"));
//			frs.setDosenWali(frsRedis.get("dosen_wali"));
//			frs.setStatusKeaktifan(frsRedis.get("status_aktif"));
//			frs.setBatasSks(Integer.valueOf(frsRedis.get("batas_sks")));
//			frs.setIpk(Double.valueOf(frsRedis.get("ipk")));
//			return frs;
//		}
			if (!listFrs.isEmpty())
				return listFrs.get(0);
			else
				return null;
	}

	@Override
	public Integer getTotalSks(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT SUM (MK_KreditKuliah) FROM Kuliah LEFT JOIN val_Nilai ON KU_NilaiHuruf=NilaiHuruf LEFT JOIN val_Semester ON KU_KE_IDSemester=ID, MataKuliah WHERE KU_KE_KR_MK_ThnKurikulum=MK_ThnKurikulum AND KU_KE_KR_MK_ID=MK_ID AND KU_MA_NRP = :nrp AND KU_KE_Tahun = :tahun AND cast(KU_KE_IDSemester as char(1))=:semester";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer total = jdbcTemplate.queryForInt(sql, params);
		return total;
	}

	@Override
	public List<MKUlang> getMKDiulang(String nrp) {

		String sql = "SELECT DISTINCT KR_Semester AS semester, MK_ID AS id_mk, MK_Mata_Kuliah as mk, MK_KreditKuliah as sks, KU_NilaiHuruf as nilai_huruf, KU_KE_Tahun as tahun_ambil, KU_KE_IDSemester AS semester_ambil FROM vMKHarusDiulang WHERE KU_MA_NRP= :nrp ORDER BY KR_Semester, MK_ID";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<MKUlang> listMk = jdbcTemplate.query(sql, params, new MKUlangRowMapper());

		return listMk;
	}

	@Override
	public List<MKWajibAmbil> getMKWajibAmbil(String nrp) {

		String sql = "SELECT KR_Semester AS semester, MK_ID AS id_mk, MK_Mata_Kuliah AS mk, JenisMK AS jenis FROM vMKWajibDiAmbil WHERE MA_NRP= :nrp ORDER BY KR_Semester , MK_ID";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<MKWajibAmbil> listMk = jdbcTemplate.query(sql, params, new MKWajibAmbilRowMapper());

		return listMk;
	}

	@Override
	public List<MKLanggarPrasyarat> getMKLanggarPrasyarat(Integer tahun, Integer semester, String nrp) {

		String sql = "SELECT mk.MK_ID AS id_mk, mk.MK_Mata_Kuliah AS mk, mks.MK_ID as id_mk_prasyarat, mks.MK_Mata_Kuliah AS mk_prasyarat, SY_Lulus AS keterangan FROM derived._UpdBatch_MKPrasyarat JOIN MataKuliah mk ON KR_MK_ThnKurikulum=mk.MK_ThnKurikulum AND KR_MK_ID=mk.MK_ID JOIN MataKuliah mks ON SY_MK_ThnKurikulumSyarat=mks.MK_ThnKurikulum AND SY_MK_IDSyarat=mks.MK_ID WHERE ma_nrp = :nrp AND SY_WajibAmbil<>'Bebas' AND SY_Lulus<>'Lulus' AND KR_MK_ThnKurikulum+KR_MK_ID IN (SELECT KU_KE_KR_MK_ThnKurikulum+KU_KE_KR_MK_ID FROM Kuliah WHERE KU_MA_NRP= :nrp AND KU_KE_Tahun= :tahun AND KU_KE_IDSemester= :semester)";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<MKLanggarPrasyarat> listMk = jdbcTemplate.query(sql, params, new MKLanggarPrasyaratRowMapper());

		return listMk;
	}

	@Override
	public Integer addMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan,
			Integer tahunKurikulum, String idMK, String kelas) {
		String sql = "DECLARE @OK INT; SET @OK = 0; EXEC FRS_AddMK :nrp, :tahun, :semester, :kode_jurusan, :tahun_kurikulum, :id_mk, :kelas, 0, @OK OUTPUT SELECT @OK AS ok";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kode_jurusan", kodeJurusan);
		params.put("tahun_kurikulum", tahunKurikulum);
		params.put("id_mk", idMK);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer ok = null;
		try {
			ok = jdbcTemplate.update(sql, params);
		} catch (Exception e) {
			System.out.println(e);
		}

		if (ok == 1) {
			String sqlHistori = "EXEC FRS_CatatHistorisUbah 'add', :nrp, :nrp, :tahun, :semester, :tahun_kurikulum, :id_mk, :kelas";
			jdbcTemplate.update(sqlHistori, params);
		}
		return ok;

	}

	@Override
	public Integer deleteMataKuliahFRS(String nrp, Integer tahun, Integer semester, String kodeJurusan, String idMK,
			String kelas, Integer tahunKurikulum) {

		String sql = "exec FRS_DropMK :nrp, :tahun, :semester, :kode_jurusan, :id_mk, :kelas, :tahunKurikulum";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kode_jurusan", kodeJurusan);
		params.put("id_mk", idMK);
		params.put("kelas", kelas);
		params.put("tahunKurikulum", tahunKurikulum);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer sukses = jdbcTemplate.update(sql, params);
		if (sukses == 1) {
			String sqlHistory = "EXEC FRS_CatatHistorisUbah 'del', :nrp, :nrp, :tahun, :semester, '', :id_mk, :kelas";
			jdbcTemplate.update(sqlHistory, params);
		}
		return sukses;
	}

	@Override
	public void setujuFRS(String nrp, Integer tahun, Integer semester, String nip) {

		String sql = "INSERT INTO FRSDisetujui(MA_NRP, KE_Tahun, KE_IDSemester, Disetujui, Waktu_Setuju, Disetujui_Oleh) VALUES(:nrp, :tahun, :semester, '1', :waktu_setuju, :user)";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("waktu_setuju", new Date());
		params.put("user", nip);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		jdbcTemplate.update(sql, params);
	}

	@Override
	public void batalFRS(String nrp, Integer tahun, Integer semester) {

		String sql = "DELETE FROM FRSDisetujui WHERE MA_NRP = :nrp AND KE_Tahun = :tahun AND KE_IDSemester = :semester ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		jdbcTemplate.update(sql, params);
	}

	@Override
	public Periode getPeriodeAktif() {

		String sql = "SELECT TOP 1 thnAjaran as tahun, IDSemester as semester FROM dbo._sekarang ORDER BY thnAjaran, semester DESC";

		JdbcTemplate jdbcTemplate = this.getJdbcTemplate();

		Periode periodeAktif = jdbcTemplate.queryForObject(sql, new PeriodeRowMapper());

		return periodeAktif;
	}

	@Override
	public List<WaktuPermanenNilai> getWaktuPermanenNilai(String nip, Integer tahunAjaran, Integer semester) {

		String sql = "SELECT DISTINCT kuliah.ku_ke_tahun as thn_ajaran, kuliah.ku_ke_idsemester as smt, kuliah.ku_ke_kodejurusan as kode_dept, dbo.MKID(matakuliah.mk_id,matakuliah.mk_thnkurikulum) AS kode_mk, matakuliah.mk_mata_kuliah as nama_mk, matakuliah.mk_kreditkuliah as sks_mk, kuliah.ku_ke_kelas as kelas, Mengajar.mg_nip as nip, p2.pe_nama as nama_dosen, prosentasenina.pr_waktu_verified as waktu_permanen, nilai_datelinetepatwaktu.ntw_dateline as batas_waktu, datediff(day, nilai_datelinetepatwaktu.ntw_dateline, prosentasenina.pr_waktu_verified) AS selisih_waktu FROM kuliah LEFT JOIN prosentasenina ON prosentasenina.pr_ke_kr_mk_id = kuliah.ku_ke_kr_mk_id AND prosentasenina.pr_ke_kr_mk_thnkurikulum = kuliah.ku_ke_kr_mk_thnkurikulum AND prosentasenina.pr_ke_tahun = kuliah.ku_ke_tahun AND prosentasenina.pr_ke_idsemester = kuliah.ku_ke_idsemester AND prosentasenina.pr_ke_kodejurusan = kuliah.ku_ke_kodejurusan AND prosentasenina.pr_ke_kelas = kuliah.ku_ke_kelas LEFT JOIN pegawai AS p1 ON p1.pe_nip = prosentasenina.pr_pe_nip_verifier INNER JOIN matakuliah ON kuliah.ku_ke_kr_mk_thnkurikulum = matakuliah.mk_thnkurikulum AND kuliah.ku_ke_kr_mk_id = matakuliah.mk_id INNER JOIN kelas ON kuliah.ku_ke_kr_mk_thnkurikulum = kelas.ke_kr_mk_thnkurikulum AND kuliah.ku_ke_kr_mk_id = kelas.ke_kr_mk_id AND kuliah.ku_ke_tahun = kelas.ke_tahun AND kuliah.ku_ke_idsemester = kelas.ke_idsemester AND kuliah.ku_ke_kodejurusan = kelas.ke_kodejurusan AND kuliah.ku_ke_kelas = kelas.ke_kelas INNER JOIN nilai_datelinetepatwaktu ON prosentasenina.pr_ke_tahun = nilai_datelinetepatwaktu.ntw_tahun AND prosentasenina.pr_ke_idsemester = nilai_datelinetepatwaktu.ntw_idsemester LEFT JOIN(SELECT mg_nip, mg_thnkurikulum, mg_kodejurusan, mg_mk_id, mg_tahun, mg_idsemester, mg_kelas FROM mengajar WHERE (mg_tahun = :thn_ajaran OR :thn_ajaran IS NULL) AND (mg_idsemester = :smt OR :smt IS NULL) AND (mg_nip = :nip) UNION ALL SELECT ke_pe_nippengajar, ke_kr_mk_thnkurikulum, ke_kodejurusan, ke_kr_mk_id, ke_tahun, ke_idsemester, ke_kelas FROM kelas WHERE (ke_tahun = :thn_ajaran OR :thn_ajaran IS NULL) AND (ke_idsemester = :smt OR :smt IS NULL) AND (ke_pe_nippengajar = :nip)) AS Mengajar ON prosentasenina.pr_ke_kr_mk_thnkurikulum = Mengajar.mg_thnkurikulum AND prosentasenina.pr_ke_kr_mk_id = Mengajar.mg_mk_id AND prosentasenina.pr_ke_tahun = Mengajar.mg_tahun AND prosentasenina.pr_ke_idsemester = Mengajar.mg_idsemester AND prosentasenina.pr_ke_kelas = Mengajar.mg_kelas AND prosentasenina.pr_ke_kodejurusan = Mengajar.mg_kodejurusan LEFT JOIN pegawai AS p2 ON p2.pe_nip = Mengajar.mg_nip WHERE ( kuliah.ku_ke_tahun = :thn_ajaran OR :thn_ajaran IS NULL) AND ( kuliah.ku_ke_idsemester = :smt OR :smt IS NULL ) AND ( Mengajar.mg_nip = :nip ) AND ( ( matakuliah.mk_thnkurikulum + matakuliah.mk_id ) NOT IN (SELECT mk_thnkurikulum + mk_id AS Expr1 FROM matakuliah_tdkdihitungpengumpulan) ) ORDER BY pr_waktu_verified DESC, matakuliah.mk_mata_kuliah, kuliah.ku_ke_kelas ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("thn_ajaran", tahunAjaran);
		params.put("smt", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<WaktuPermanenNilai> listPermanen = jdbcTemplate.query(sql, params, new WaktuPermanenNilaiRowMapper());

		return listPermanen;

	}

	@Override
	public Integer getEvaluasi(String nrp, Integer tahun, Integer semester) {

		String sql = "SELECT 1 AS eval FROM Evaluasi_List WHERE el_ma_nrp+el_tahun+el_semester = :nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp + tahun + semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer evaluasi = 0;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			evaluasi = (Integer) rs.get("eval");
		} catch (Exception e) {
			System.out.println(e);
		}

		return evaluasi;
	}

	@Override
	public Integer getEWS(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT 1 AS ews FROM mahasiswa_terkenaEvaluasi WHERE me_ma_nrp+me_thnAjaran+me_idsemester = :gabungan";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gabungan", nrp + tahun + semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer ews = 0;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			ews = (Integer) rs.get("ews");
		} catch (Exception e) {
		}

		return ews;
	}

	@Override
	public Boolean getFRSDisetujui(String nrp, Integer tahun, Integer semester) {
		String sql = "IF EXISTS (SELECT 1 FROM FRSDisetujui with (nolock) WHERE MA_NRP=:nrp AND KE_Tahun=:tahun AND KE_IDSemester=:semester) SELECT CAST(1 AS BIT) AS frs_disetujui else select CAST(0 AS BIT) AS frs_disetujui ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Boolean frs = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			frs = (Boolean) rs.get("frs_disetujui");
		} catch (Exception e) {
			System.out.println(e);
		}

		return frs;
	}

	@Override
	public List<WaktuFrs> getJadwalFRS(Integer tahun, Integer semester) {
		String sql = "SELECT * FROM FRS_BatasWaktuPengisian WHERE ThnAjaran = :tahun and IDSemester = :semester";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<WaktuFrs> listPermanen = jdbcTemplate.query(sql, params, new WaktuFRSRowMapper());

		return listPermanen;
	}

	@Override
	public Integer getSKSAmbil(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT SUM(MK_KreditKuliah) as totalSKS FROM Kuliah, MataKuliah with (nolock) WHERE KU_KE_KR_MK_ThnKurikulum = MK_ThnKurikulum AND KU_KE_KR_MK_ID = MK_ID AND KU_KE_Tahun=:tahun AND KU_KE_IDSemester=:semester AND KU_MA_NRP=:nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer sksAmbil = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			sksAmbil = (Integer) rs.get("totalSKS");
		} catch (Exception e) {
		}
		return sksAmbil;
	}

	@Override
	public Integer getSKS(String idMatkul, Integer tahunKurikulum) {
		StringBuffer sql = new StringBuffer();
		sql.append("select coalesce(MK_KreditKuliah,0) as sks from MataKuliah where MK_ID=:idMatkul");
		if (tahunKurikulum != null) {
			sql.append(" and MK_ThnKurikulum=:tahunKurikulum");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idMatkul", idMatkul);
		params.put("tahunKurikulum", tahunKurikulum);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer sks = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql.toString(), params);
			sks = (Integer) rs.get("sks");
		} catch (Exception e) {
		}
		return sks;
	}

	@Override
	public String getKelas(String nrp, Integer tahunKurikulum, String kodeJurusan, Integer tahun, Integer semester,
			String idMatkul) {
		String sql = "SELECT KU_KE_Kelas as kelas FROM Kuliah WHERE KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester AND KU_MA_NRP = :nrp AND KU_KE_KR_MK_ThnKurikulum = :tahunKurikulum AND KU_KE_KR_MK_ID = :idMatkul AND KU_KE_KodeJurusan = :kodeJurusan";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("nrp", nrp);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("idMatkul", idMatkul);
		params.put("kodeJurusan", kodeJurusan);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String kelas = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			kelas = (String) rs.get("kelas");
		} catch (Exception e) {
		}
		return kelas;
	}

	@Override
	public String getNamaMKKurikulum(String idMk, Integer tahunKurikulum) {
		String sql = "select MK_Mata_Kuliah AS MataKuliah from MataKuliah where MK_ID=:mkid and MK_ThnKurikulum=:tahun";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mkid", idMk);
		params.put("tahun", tahunKurikulum);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String mataKuliah = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			mataKuliah = (String) rs.get("MataKuliah");
		} catch (Exception e) {
			System.out.println(e);
		}
		return mataKuliah;
	}

	@Override
	public Integer noKelasTPB(Character kelas) {
		if (kelas.equals(""))
			return 0;
		else
			return (int) kelas - 150;
	}

	@Override
	public Boolean cekJadwalRuang(Integer tahun, Integer semester, String prodi, String idMk, String kelas,
			Boolean isPengayaan) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct 1 from SIMARU.dbo.alokasi ");
		if (isPengayaan) {
			sql.append("where periodesem=:periode and kodekelas like :kelas");
		} else {
			sql.append(
					"where periodesem=:periode and substring(kodekelas, 0, 14)=:prodi and SUBSTRING(KODEKELAS,14,15) = :kelas");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("periode", String.valueOf(tahun) + semester);
		if (isPengayaan) {
			params.put("kelas", "_____:" + idMk + ":" + kelas);
		} else {
			params.put("prodi", prodi + ":" + idMk + ":");
			params.put("kelas", kelas);
		}
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Boolean jadwalRuang = false;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql.toString(), params);
			jadwalRuang = true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return jadwalRuang;
	}

	@Override
	public String getKodeKelasUPMB(Integer tahun, Integer semester, String kodeJurusan, String idMk, String kelas) {
		String sql = "SELECT DISTINCT SUBSTRING(KODEKELAS, 14, 15) AS kelas FROM SIMARU.dbo.ALOKASI WHERE PERIODESEM = :periode AND SUBSTRING(KODEKELAS, 0, 14) = :prodi AND (CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END) = :kelas";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("periode", String.valueOf(tahun) + semester);
		params.put("prodi", kodeJurusan + ":" + idMk + ":");
		params.put("kelas", kelas);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String kls = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			kls = (String) rs.get("kelas");
			if (kelas.equalsIgnoreCase("7")) {
				kls = "";
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return kls;
	}

	@Override
	public Boolean getIsPengayaan(String kodeJurusan, String idMk) {
		String sql = "SELECT TOP 1 * FROM kelas WHERE KE_KR_MK_ID = :idMk AND KE_KodeJurusan <> :kodeJurusan AND coalesce(isPengayaan,0)=1 AND KE_KodeJurusan NOT IN ('__MKU','__TPB') AND COALESCE(KE_ID_LingkupPengayaan, 0)<>3";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeJurusan", kodeJurusan);
		params.put("idMk", idMk);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Boolean exists = false;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			exists = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return exists;
	}

	@Override
	public Integer getStatusPembayaranSpp(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT 1 FROM PembayaranSPP WHERE BS_MA_NRP  = :nrp AND BS_ThnAjaran = :tahun	AND BS_IDSemester= :semester";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer kapasitas = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			kapasitas = (Integer) rs.get("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return kapasitas;
	}

	@Override
	public Integer getKapasitasKelas(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun,
			Integer semester, Integer tahunKurikulum) {
		String sql = "exec FRS_CekKapasitasKelas :kodeProdi, :tahun, :semester, :idMatkul, :kodeKelas, :tahunKurikulum";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("idMatkul", idMatkul);
		params.put("kodeKelas", kodeKelas);
		params.put("tahunKurikulum", tahunKurikulum);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer kapasitas = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			kapasitas = (Integer) rs.get("");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return kapasitas;
	}

	@Override
	public List<Kelas> getDayaTampung(String kodeProdi, String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh) {
		String sql = "SELECT CASE WHEN ASCII(ke_kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(ke_kelas) - 150) ELSE ke_kelas END AS kelas, ke_kodejurusanboleh, ke_dayatampung AS daya_tampung FROM kelas_tampung where KE_KR_MK_ID = :idMatkul and KE_KodeJurusan = :kodeProdi and KE_Tahun = :tahun and KE_IDSemester = :semester and KE_KR_MK_ThnKurikulum=:tahunKurikulum and ke_kodejurusanboleh = :kodejurusanboleh";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("idMatkul", idMatkul);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("kodejurusanboleh", kodeJurusanBoleh);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		List<Kelas> listKelas = jdbcTemplate.query(sql, params, new KelasRowMapper());
		return listKelas;
	}

	@Override
	public Boolean getKelasTampung(String idMatkul, String kodeProdi, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh, String kelas) {
		String sql = "select ke_kelas, ke_kodejurusanboleh, ke_dayatampung from kelas_tampung where KE_KR_MK_ID = :idMatkul and KE_KodeJurusan = :kodeProdi and KE_Tahun = :tahun and KE_IDSemester = :semester and KE_KR_MK_ThnKurikulum=:tahunKurikulum and ke_kodejurusanboleh = :kodejurusanboleh and ke_kelas = :kelas";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("idMatkul", idMatkul);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("kodejurusanboleh", kodeJurusanBoleh);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Boolean exists = false;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			exists = true;
		} catch (Exception e) {

		}
		return exists;
	}

	@Override
	public Double getIpsLalu(String nrp, Integer tahun, Integer semester) {
		String sql = "SELECT mhs_ips AS ips FROM mahasiswa_historisstatus WHERE mhs_thnajaran+mhs_Idsemester IN (SELECT max(mhs_thnajaran+mhs_idsemester) FROM mahasiswa_historisstatus where MHS_MA_NRP=:nrp and mhs_idstatusmhs IN ('N','D','P') AND mhs_thnajaran+mhs_IDSemester<:tahunsemester) and mhs_ma_nrp=:nrp";
		Map<String, Object> params = new HashMap<>();
		params.put("nrp", nrp);
		params.put("tahunsemester", String.valueOf(tahun) + semester);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Double ips = 0.0;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			ips = (Double) rs.get("ips");
		} catch (Exception e) {
		}
		return ips;
	}

	@Override
	public Integer getMaxSks(Double ips, String nrp, Integer tahun, Integer semester, Integer tahunKurikulum) {
		Integer batas = 0;
		if (nrp.substring(4, 5).equalsIgnoreCase("2")) {
			if (nrp.substring(2, 4) == tahun.toString().substring(2, 4) && semester == 1) {
				batas = 15;
			} else {
				if (ips >= 3.00) {
					batas = 15;
				} else {
					batas = 12;
				}
			}
		} else if (nrp.substring(4, 5).equalsIgnoreCase("0") || nrp.substring(4, 5).equalsIgnoreCase("1")) {
			if (tahunKurikulum <= 2009) {
				if (ips >= 3.00) {
					batas = 24;
				} else if (ips >= 2.00) {
					batas = 20;
				} else {
					batas = 16;
				}
			} else if (tahunKurikulum == 2014) {

				if (ips >= 3.50) {
					batas = 24;
				} else if (ips >= 3.00) {
					batas = 22;
				} else if (ips >= 2.50) {
					batas = 20;
				} else if (ips >= 2.00) {
					batas = 18;
				} else {
					batas = 16;
				}

			} else { // 2018
				if (ips >= 3.50) {
					batas = 24;
				} else if (ips >= 3.00) {
					batas = 22;
				} else if (ips >= 2.50) {
					batas = 20;
				} else {
					batas = 18;
				}
			}
		}
		return batas;
	}

	@Override
	public Integer getJumlahKuliah(String kodeProdi, String idMatkul, String kodeKelas, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kodeJurusanBoleh) {
		String sql = "select count(*) as jumlah from kuliah where KU_KE_KR_MK_ID = :idMatkul and KU_KE_KodeJurusan = :kodeProdi and KU_KE_Tahun = :tahun and KU_KE_IDSemester = :semester and KU_KE_KR_MK_ThnKurikulum=:tahunKurikulum and KU_KE_Kelas = :kodeKelas and substring(ku_ma_nrp,1,2)+substring(ku_ma_nrp,5,3)=:kodejurusanboleh ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("idMatkul", idMatkul);
		params.put("kodeKelas", kodeKelas);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("kodejurusanboleh", kodeJurusanBoleh);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer jumlah = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			jumlah = (Integer) rs.get("jumlah");
		} catch (Exception e) {
		}
		return jumlah;
	}

	@Override
	public List<Prasyarat> getPrasyarat(String nrp, String idMK, Integer tahunKurikulum) {
		String sql = "SELECT MK_Mata_Kuliah, SY_MK_ThnKurikulumSyarat, SY_MK_IDSyarat, SY_WajibAmbil, SY_KU_NilaiHuruf, SY_Lulus FROM [derived].[_UpdBatch_MKPrasyarat] JOIN MataKuliah ON SY_MK_ThnKurikulumSyarat=MK_ThnKurikulum AND SY_MK_IDSyarat=MK_ID WHERE SY_MK_IDSyarat IS NOT NULL AND SY_WajibAmbil<>'Bebas' AND SY_Lulus<>'Lulus' AND ma_nrp=:nrp AND KR_MK_ThnKurikulum=:tahunKurikulum AND KR_MK_ID=:idMK ORDER BY KR_Semester, KR_MK_ID";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("idMK", idMK);
		params.put("tahunKurikulum", tahunKurikulum);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Prasyarat> listPrasyarat = jdbcTemplate.query(sql, params, new PrasyaratRowMapper());

		return listPrasyarat;
	}

	@Override
	public Boolean getVLstudi(String nrp) {
		String sql = "SELECT 1 FROM v_lstudi WHERE mhs_ma_nrp = :nrp AND lstudi > :batas AND SUBSTRING(mhs_ma_nrp,5,1) in ('0','1')";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("batas", 4);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Boolean check = false;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			check = true;
		} catch (Exception e) {
		}
		return check;
	}

	@Override
	public Boolean getPersiapan(String nrp, String idMK, Integer tahunKurikulum) {
		String sql = "SELECT 1 FROM Kurikulum WHERE KR_MK_ID = :idMK AND KR_MK_ThnKurikulum = :tahunKurikulum AND KR_KodeJurusan=:kodeJurusan AND KR_Semester IN ('1', '2')";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idMK", idMK);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("kodeJurusan", nrp.substring(0, 2) + nrp.substring(4, 7));
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Boolean checkMKSmt = false;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			checkMKSmt = true;
		} catch (Exception e) {
		}

		return checkMKSmt;
	}

	@Override
	public int getCheckBE(String nrp, Integer tahunKurikulum, String idMk) {
		String sql = "select cast(dbo.getThnMasuk(substring(:nrp,3,2)) AS int) AS tahun";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		int tahunMasuk = 0;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			tahunMasuk = (Integer) rs.get("tahun");
		} catch (Exception e) {
			System.out.println(e);
		}
		if (tahunMasuk < 2018 && isWajib(idMk, tahunKurikulum)) {
			// $strCheckBE = "SELECT COUNT(*) FROM Ekivalensi_MKWajibAmbil
			// WHERE EWA_MA_NRP= '".$nrp."'
			// AND EWA_KR_MK_ThnKurikulum= '".$thnKur."'
			// AND EWA_KR_MK_ID = '".$mkID."'
			// ";
			sql = "SELECT 1 AS AMBIL FROM vMKWajibDiAmbil WHERE MA_NRP = :nrp AND MK_ThnKurikulum= :tahunKurikulum AND MK_ID = :mkId";
			params.put("tahunKurikulum", tahunKurikulum);
			params.put("mkId", idMk);
			try {
				Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
				int bebas = (Integer) rs.get("AMBIL");
				return bebas;
			} catch (Exception e) {
				return 0;
			}

		}
		return 1;
	}

	private Boolean isWajib(String idMk, Integer tahunKurikulum) {
		Boolean isWajib = null;
		if (tahunKurikulum == 2014) {
			String[] kodeMkWajib = { "1", "2", "3", "4", "5", "6" };
			isWajib = Arrays.asList(kodeMkWajib).contains(idMk.substring(3, 4)) ? true : false;
		} else {
			String sql = "select distinct KR_IDWajibPilihan from Kurikulum where KR_MK_ThnKurikulum = :tahunKurikulum and KR_MK_ID = :idMk";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tahunKurikulum", tahunKurikulum);
			params.put("idMk", idMk);
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
			try {
				Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
				String wajibPilihan = (String) rs.get("KR_IDWajibPilihan");
				isWajib = wajibPilihan.equalsIgnoreCase("W") ? true : false;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return isWajib;
	}

	@Override
	public int getCheckEqTidakBolehDiulang(String nrp, Integer tahunKurikulum, String idMk) {

		String sql = "select cast(dbo.getThnMasuk(substring(:nrp,3,2)) AS int) AS tahun";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		int tahunMasuk = 0;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			tahunMasuk = (Integer) rs.get("tahun");
		} catch (Exception e) {
			System.out.println(e);
		}
		if (tahunMasuk < 2018) {
			sql = "SELECT 1 AS BEBAS FROM vEkivalensi_TidakBolehDiulang WHERE NRP = :nrp AND MK_ThnKurikulum= :tahunKurikulum AND MK_ID = :mkId";
			params.put("tahunKurikulum", tahunKurikulum);
			params.put("mkId", idMk);
			try {
				Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
				int bebas = (Integer) rs.get("BEBAS");
				return bebas;
			} catch (Exception e) {
				return 0;
			}

		}
		return 0;
	}

	@Override
	public List<MataKuliah> getMataKuliah(String kodeProdi, Integer tahun, Integer semester) {
//		String key="mataKuliah"+kodeProdi+"_"+tahun+"_"+semester;
//		ListOperations<String,MataKuliah> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false) {
			String sql = "SELECT KU_KE_KodeJurusan AS prodi, KU_KE_KR_MK_ThnKurikulum AS tahun, KU_KE_IDSemester AS semester, MK_ID AS id_mk, dbo.MKID(MK_ID,MK_ThnKurikulum) AS kode_mk, MK_Mata_Kuliah AS mk, MK_Mata_KuliahInggris AS mk_inggris, MK_KreditKuliah AS sks, KR_IDWajibPilihan AS jenis_mk FROM kuliah k JOIN matakuliah mk ON k.KU_KE_KR_MK_ID=mk.MK_ID JOIN kurikulum ku ON k.KU_KE_KodeJurusan=ku.KR_KodeJurusan AND ku.KR_MK_ID=k.KU_KE_KR_MK_ID AND mk.MK_ThnKurikulum=ku.KR_MK_ThnKurikulum WHERE KU_KE_KodeJurusan = :kodeProdi AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("kodeProdi", kodeProdi);
			params.put("tahun", tahun);
			params.put("semester", semester);
			
			List<MataKuliah> listMataKuliah = jdbcTemplate.query(sql, params, new MataKuliahRowMapper());

//			List<Map<String,Object>> arrayListMataKuliah=new ArrayList<Map<String,Object>>();
//			for(MataKuliah mataKuliah:listMataKuliah)
//			{
//				operations.rightPush("mataKuliah"+kodeProdi+"_"+tahun+"_"+semester, mataKuliah);
//			}
//			return listMataKuliah;
//		}
//		else
//		{
//			List<MataKuliah> listMataKuliah=operations.range("mataKuliah"+kodeProdi+"_"+tahun+"_"+semester, 0, -1);
			return listMataKuliah;
//		}
	
//		String sql = "SELECT KU_KE_KodeJurusan AS prodi, KU_KE_KR_MK_ThnKurikulum AS tahun, KU_KE_IDSemester AS semester, MK_ID AS id_mk, dbo.MKID(MK_ID,MK_ThnKurikulum) AS kode_mk, MK_Mata_Kuliah AS mk, MK_Mata_KuliahInggris AS mk_inggris, MK_KreditKuliah AS sks, KR_IDWajibPilihan AS jenis_mk FROM kuliah k JOIN matakuliah mk ON k.KU_KE_KR_MK_ID=mk.MK_ID JOIN kurikulum ku ON k.KU_KE_KodeJurusan=ku.KR_KodeJurusan AND ku.KR_MK_ID=k.KU_KE_KR_MK_ID AND mk.MK_ThnKurikulum=ku.KR_MK_ThnKurikulum WHERE KU_KE_KodeJurusan = :kodeProdi AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("kodeProdi", kodeProdi);
//		params.put("tahun", tahun);
//		params.put("semester", semester);
//		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
//
//		List<MataKuliah> listMk = jdbcTemplate.query(sql, params, new MataKuliahRowMapper());
//		return listMk;
	}

	@Override
	public List<IPD> getIPD(String kodeProdi, Integer tahun, Integer semester) {
		String sql = "SELECT * FROM (SELECT ipd_jur_tahun AS tahun, ipd_jur_semester AS semester, ipd_jur_kodejurusan AS kodeProdi, ipd_jur_rerata AS rata FROM IPD_RerataJurusan) ipd WHERE ipd.tahun = :tahun AND ipd.semester = :semester AND ipd.kodeProdi = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<IPD> listIPD = jdbcTemplate.query(sql, params, new IPDRowMapper());
		return listIPD;
	}

	@Override
	public List<Komentar> getKomentarIPM(String nip, String idMk, Integer tahun, Integer semester, String kelas) {
		String sql = "SELECT DISTINCT KM_KE_KodeJurusan AS kode_prodi, KM_KE_KR_MK_ID AS kode_mk, KM_KE_Kelas AS kelas, k.KE_PE_NIPPengajar AS nip_pengajar, KM_KU_MA_NRP AS nrp_mahasiswa, KM_KE_Tahun AS tahun, KM_KE_IDSemester AS semester, KM_Komentar AS komentar FROM KuesionerMK_Komentar ku JOIN kelas k ON ku.KM_KE_KR_MK_ThnKurikulum = k.KE_KR_MK_ThnKurikulum AND ku.KM_KE_KR_MK_ID = k.KE_KR_MK_ID AND ku.KM_KE_Tahun = k.KE_Tahun AND ku.KM_KE_IDSemester = k.KE_IDSemester AND ku.KM_KE_Kelas = k.KE_Kelas WHERE KE_PE_NIPPengajar=:nip AND KM_KE_KR_MK_ID=:id AND KE_Tahun=:tahun AND KE_IDSemester=:semester AND KE_Kelas= :kelas";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("id", idMk);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kelas", kelas);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Komentar> listKomentar = jdbcTemplate.query(sql, params, new KomentarRowMapper());
		return listKomentar;
	}

	@Override
	public List<Komentar> getKomentarIPD(String nip, String idMk, Integer tahun, Integer semester, String kelas) {
		String sql = "SELECT KS_KE_KodeJurusan AS kode_prodi, KS_KE_KR_MK_ID AS kode_mk, KS_KE_Kelas AS kelas, KS_KE_PE_NIPPengajar AS nip_pengajar, KS_KU_MA_NRP AS nrp_mahasiswa, KS_KE_Tahun AS tahun, KS_KE_IDSemester AS semester, KS_Komentar AS komentar FROM KuesionerDosen_Komentar WHERE KS_KE_PE_NIPPengajar = :nip AND KS_KE_KR_MK_ID = :id AND KS_KE_Tahun = :tahun AND KS_KE_IDSemester = :semester AND KS_KE_Kelas = :kelas ORDER BY KS_Log";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("id", idMk);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kelas", kelas);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Komentar> listKomentar = jdbcTemplate.query(sql, params, new KomentarRowMapper());
		return listKomentar;
	}

	@Override
	public List<Kuesioner> getKuesionerIPM(Integer tahunKurikulum) {
		String sql = "SELECT * FROM (SELECT TY_IDPertanyaan AS id, TY_ThnKurikulum AS tahunKurikulum, TY_Jenjang AS jenjang, TY_Pertanyaan AS pertanyaan FROM IPD_pertanyaan) ipd WHERE tahunKurikulum=:tahun AND id LIKE 'MK%'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahunKurikulum);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Kuesioner> list = jdbcTemplate.query(sql, params, new KuesionerRowMapper());
		return list;
	}

	@Override
	public List<Kuesioner> getKuesionerIPD(Integer tahunKurikulum) {
		String sql = "SELECT * FROM (SELECT TY_IDPertanyaan AS id, TY_ThnKurikulum AS tahunKurikulum, TY_Jenjang AS jenjang, TY_Pertanyaan AS pertanyaan FROM IPD_pertanyaan) ipd WHERE tahunKurikulum=:tahun AND id LIKE 'DO%'";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahunKurikulum);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Kuesioner> list = jdbcTemplate.query(sql, params, new KuesionerRowMapper());
		return list;
	}

	@Override
	public List<MataKuliahSyarat> getSpesifikMataKuliah(String kodeProdi, String idMk, Integer semester,
			String bahasa) {
		String sql = "SELECT DISTINCT KU_KE_KodeJurusan AS prodi, KU_KE_KR_MK_ThnKurikulum AS tahun, KR_Semester AS semester, MK_ID AS id_mk, dbo.MKID(MK_ID,MK_ThnKurikulum) AS kode_mk, CASE :bahasa WHEN 'en' THEN MK_Mata_KuliahInggris ELSE MK_Mata_Kuliah END AS mk, MK_Mata_KuliahInggris AS mk_inggris, MK_KreditKuliah AS sks, w.WajibPilihan AS jenis_mk FROM kuliah k JOIN matakuliah mk ON k.KU_KE_KR_MK_ID=mk.MK_ID JOIN kurikulum ku ON k.KU_KE_KodeJurusan=ku.KR_KodeJurusan AND ku.KR_MK_ID=k.KU_KE_KR_MK_ID AND mk.MK_ThnKurikulum=ku.KR_MK_ThnKurikulum JOIN val_WajibPilihan w ON KR_IDWajibPilihan = w.ID WHERE KU_KE_KodeJurusan = :kodeProdi AND dbo.MKID(MK_ID,MK_ThnKurikulum) = :idMk AND KR_Semester = :semester";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("idMk", idMk);
		params.put("semester", semester);
		params.put("bahasa", bahasa);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<MataKuliahSyarat> listMk = jdbcTemplate.query(sql, params, new MataKuliahSyaratRowMapper());
		return listMk;
	}

	@Override
	public List<MataKuliah> getSyaratMataKuliah(String tahunKurikulum, String kodeProdi, String idMk, String bahasa) {
//		String key="syaratMataKuliah_"+tahunKurikulum+"_"+kodeProdi+"_"+idMk+"_"+bahasa;
//		ListOperations<String,MataKuliah> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{	
			String sql = "SELECT SY_KodeJurusan AS prodi, SY_MK_ThnKurikulumSyarat AS tahun, SY_MK_IDSyarat AS id_mk, dbo.MKID(SY_MK_IDSyarat,SY_MK_ThnKurikulumSyarat) AS kode_mk, CASE :bahasa WHEN 'en' THEN MS.MK_Mata_KuliahInggris ELSE MS.MK_Mata_Kuliah END AS mk, MS.MK_Mata_KuliahInggris AS mk_inggris, KS.KR_Semester AS semester, M.MK_KreditKuliah AS sks, JenisMKSyarat AS jenis_mk FROM Kurikulum K JOIN MK_Syarat ON K.KR_MK_ThnKurikulum = SY_MK_ThnKurikulum AND K.KR_MK_ID = SY_MK_ID AND K.KR_KodeJurusan = SY_KodeJurusan JOIN MataKuliah M ON M.MK_ThnKurikulum = K.KR_MK_ThnKurikulum AND M.MK_ID = SY_MK_IDSyarat JOIN Kurikulum KS ON KS.KR_MK_ThnKurikulum = SY_MK_ThnKurikulumSyarat AND KS.KR_MK_ID = SY_MK_IDSyarat AND KS.KR_KodeJurusan = SY_KodeJurusan JOIN MataKuliah MS ON MS.MK_ThnKurikulum = SY_MK_ThnKurikulumSyarat AND MS.MK_ID = SY_MK_IDSyarat LEFT JOIN val_JenisMKSyarat ON IDJenisMKSyarat = SY_IDJenisMKSyarat WHERE K.KR_MK_ThnKurikulum = :tahun and K.KR_KodeJurusan = :kodeProdi AND dbo.mkid(SY_MK_ID, SY_MK_ThnKurikulum) = :idMk ORDER BY SY_MK_IDSyarat, KS.KR_Semester";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tahun", tahunKurikulum);
			params.put("kodeProdi", kodeProdi);
			params.put("idMk", idMk);
			params.put("bahasa", bahasa);
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<MataKuliah> listMk = jdbcTemplate.query(sql, params, new MataKuliahRowMapper());

//			List<Map<String,Object>> arraySyaratMK=new ArrayList<Map<String,Object>>();
//			for(MataKuliah syaratMK:listMk)
//			{
//				operations.rightPush("syaratMataKuliah_"+tahunKurikulum+"_"+kodeProdi+"_"+idMk+"_"+bahasa, syaratMK);
//			}
//			return listMk;
//		}
//		else
//		{
//			List<MataKuliah> listMk=operations.range("syaratMataKuliah_"+tahunKurikulum+"_"+kodeProdi+"_"+idMk+"_"+bahasa, 0, -1);
			return listMk;
//		}
	}

	@Override
	public Boolean postKuesionerMk(String nrp, KuesionerMK kuesioner, String tahun, String semester,
			String tahunKurikulum) {
		Integer res = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql;
		for (TanyaJawab list : kuesioner.getJawaban()) {
			sql = "INSERT INTO KuesionerMK VALUES (:idMk, :tahun, :idJurusan, :idSemester, :idPertanyaan, :idJawaban, :nrp, :thnKurikulum, :kelas, CONVERT(VARCHAR, GETDATE(), 20))";
			params.put("idMk", kuesioner.getIdMk());
			params.put("tahun", tahun);
			params.put("idJurusan", kuesioner.getIdJurusan());
			params.put("idSemester", semester);
			params.put("idPertanyaan", list.getIdPertanyaan());
			params.put("idJawaban", list.getIdJawaban());
			params.put("nrp", nrp);
			params.put("thnKurikulum", tahunKurikulum);
			params.put("kelas", kuesioner.getKelas());
			jdbcTemplate.update(sql, params);
			res++;
		}
		log.info("row affected = {}", res);
		if (res > 0) {
			sql = "INSERT INTO KuesionerMK_Komentar VALUES (:idMk, :idJurusan, :thnKurikulum, :tahun, :idSemester, :nrp, :kelas, :komentar, CONVERT(VARCHAR, GETDATE(), 20))";
			params.put("idMk", kuesioner.getIdMk());
			params.put("tahun", tahun);
			params.put("idJurusan", kuesioner.getIdJurusan());
			params.put("idSemester", semester);
			params.put("nrp", nrp);
			params.put("thnKurikulum", tahunKurikulum);
			params.put("kelas", kuesioner.getKelas());
			params.put("komentar", kuesioner.getKomentar());
			jdbcTemplate.update(sql, params);
			return true;
		} else
			return false;
	}

	@Override
	public Boolean postKuesionerDosen(String nrp, KuesionerDosen kuesioner, String tahun, String semester,
			String tahunKurikulum) {
		Integer res = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql;
		for (TanyaJawab list : kuesioner.getJawaban()) {
			sql = "INSERT INTO KuesionerDosen VALUES (:idMk, :tahun, :kelas, :nip, :idJurusan, :idSemester, :idPertanyaan, :idJawaban, :nrp, :thnKurikulum, CONVERT(VARCHAR, GETDATE(), 20))";
			params.put("idMk", kuesioner.getIdMk());
			params.put("tahun", tahun);
			params.put("kelas", kuesioner.getKelas());
			params.put("nip", kuesioner.getNip());
			params.put("idJurusan", kuesioner.getIdJurusan());
			params.put("idSemester", semester);
			params.put("idPertanyaan", list.getIdPertanyaan());
			params.put("idJawaban", list.getIdJawaban());
			params.put("nrp", nrp);
			params.put("thnKurikulum", tahunKurikulum);
			jdbcTemplate.update(sql, params);
			res++;
		}
		log.info("row affected = {}", res);
		if (res > 0) {
			sql = "INSERT INTO KuesionerDosen_Komentar VALUES (:idMk, :tahun, :kelas, :nip, :idJurusan, :idSemester, :nrp, :thnKurikulum, :komentar, CONVERT(VARCHAR, GETDATE(), 20))";
			params.put("idMk", kuesioner.getIdMk());
			params.put("tahun", tahun);
			params.put("kelas", kuesioner.getKelas());
			params.put("nip", kuesioner.getNip());
			params.put("idJurusan", kuesioner.getIdJurusan());
			params.put("idSemester", semester);
			params.put("nrp", nrp);
			params.put("thnKurikulum", tahunKurikulum);
			params.put("komentar", kuesioner.getKomentar());
			jdbcTemplate.update(sql, params);
			return true;
		} else
			return false;
	}

	@Override
	public Boolean tahapFRS(Integer tahun, Integer semester) {
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql = "SELECT * FROM FRS_BatasWaktuPengisian WHERE ThnAjaran = :tahun AND IDSemester = :semester AND GETDATE()>=CONCAT(CONVERT(date, TglMulaiFRS),' 07:00:00') AND GETDATE()<=CONCAT(CONVERT(date, TglAkhirFRS),' 23:59:59')";
		params.put("tahun", tahun);
		params.put("semester", semester);

		Boolean check = false;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			check = true;
		} catch (Exception e) {
		}
		return check;
	}

	@Override
	public Boolean tahapUbahFRS(Integer tahun, Integer semester) {
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql = "SELECT * FROM FRS_BatasWaktuPengisian WHERE ThnAjaran = :tahun AND IDSemester = :semester AND GETDATE()>=CONCAT(CONVERT(date, TglMulaiUbah),' 07:00:00') AND GETDATE()<=CONCAT(CONVERT(date, TglAkhirUbah),' 23:59:59')";
		params.put("tahun", tahun);
		params.put("semester", semester);

		Boolean check = false;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			check = true;
		} catch (Exception e) {
		}
		return check;
	}

	@Override
	public Boolean tahapDrop(Integer tahun, Integer semester) {
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql = "SELECT * FROM FRS_BatasWaktuPengisian WHERE ThnAjaran = :tahun AND IDSemester = :semester AND GETDATE()>=CONCAT(CONVERT(date, TglMulaiDrop),' 07:00:00') AND GETDATE()<=CONCAT(CONVERT(date, TglAkhirDrop),' 23:59:59')";
		params.put("tahun", tahun);
		params.put("semester", semester);

		Boolean check = false;

		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			check = true;
		} catch (Exception e) {
		}
		return check;
	}

	@Override
	public List<Sekarang> getSekarang() {
		
		ListOperations<String, Sekarang> operations = this.getRedisTemplate().opsForList();
		
		if(this.getRedisTemplate().hasKey("basicSekarang_")==false)
			{
				String sql = "SELECT se.IDSemester AS semester, se.thnAjaran AS tahun, se.thnAjaran+'/'+cast((se.thnAjaran+1) AS VARCHAR) AS tahun_ajaran, se.thnKurikulum AS tahun_kurikulum FROM _sekarang se";
	
				Map<String, Object> params = new HashMap<String, Object>();
	
				NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
	
				List<Sekarang> listSekarang = jdbcTemplate.query(sql, params, new SekarangRowMapper());

				List<Map<String,String>> arrayBasicSkrg = new ArrayList<Map<String,String>>();
					
				for(Sekarang basicSkrg:listSekarang)
					{
						operations.rightPush("basicSekarang_", basicSkrg);
					}
	//				this.getRedisTemplate().expire("ProdiAjarBaru", 86400, TimeUnit.SECONDS);
					return listSekarang;
			}
		else
			{
				List<Sekarang>listSekarang=operations.range("basicSekarang_", 0, -1);
				return listSekarang;
			}
	}

	@Override
	public Integer getTahunKurikulum(Integer tahun) {
		Map<String, Object> params = new HashMap<>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql = "SELECT CAST(MAX(thnKurikulum) AS int) AS kurikulum FROM val_ThnKurikulum WHERE ID<=:tahun";
		params.put("tahun", tahun);
		Integer tahunKurikulum = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			tahunKurikulum = (int) rs.get("kurikulum");

		} catch (Exception e) {
			System.out.println(e);
		}
		return tahunKurikulum;

	}

	@Override
	public List<String> getKeteranganKodeJurusanBoleh(String idMatkul, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas) {
		Map<String, Object> params = new HashMap<>();
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		String sql = "select ke_kodejurusanboleh, ke_dayatampung AS daya_tampung, SUBSTRING(ke_kodejurusanboleh, 1,2) + ' ('+CAST(KE_DayaTampung AS VARCHAR)+')' AS keterangan from kelas_tampung where KE_KR_MK_ID = :idMk and KE_Tahun = :tahun and KE_IDSemester = :semester and KE_KR_MK_ThnKurikulum = :tahunKurikulum and CASE WHEN ASCII(ke_kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(ke_kelas) - 150) ELSE ke_kelas END = :kelas";
		params.put("idMk", idMatkul);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("tahunKurikulum", tahunKurikulum);
		params.put("kelas", kelas);

		List<String> list = jdbcTemplate.query(sql, params, new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("keterangan");
			}
		});

		return list;
	}

	@Override
	public Boolean cekJadwalKuesioner() {
		String sql = "SELECT DISTINCT 1 FROM ipd_setting WHERE GETDATE()<=(SELECT nilai FROM IPD_Setting WHERE idsetting='ipd_end') AND GETDATE()>=(SELECT nilai FROM IPD_Setting WHERE idsetting = 'ipd_start')";
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Boolean jadwal = false;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			jadwal = true;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return jadwal;
	}

	@Override
	public Integer isBukaPengayaan(String kodeProdi, Integer tahun, Integer semester) {
		String sql = "select top 1 1 AS buka from kelas where ke_kodejurusan = :kodeProdi and ke_tahun = :tahun and ke_idsemester = :semester and coalesce(ispengayaan,0)=1 and KE_DayaTampung>0";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kodeProdi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer isBuka = 0;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			isBuka = (int) rs.get("buka");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return isBuka;
	}

	@Override
	public Integer getSksTempuhPengayaan(String nrp) {
		String sql = "SELECT jml=sum(MK_KreditKuliah) FROM derived.dspTranskrip dspt left join _sekarang s on dspt.KU_KE_Tahun = s.thnAjaran and dspt.KU_KE_IDSemester = s.IDSemester WHERE s.thnAjaran is null	and KU_MA_NRP = :nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer sks = 0;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			sks = (int) rs.get("jml");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return sks;
	}
}
