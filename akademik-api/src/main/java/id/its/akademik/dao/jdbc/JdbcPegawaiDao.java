package id.its.akademik.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.PegawaiDao;
import id.its.akademik.dao.mapper.FotoMahasiswaRowMapper;
import id.its.akademik.dao.mapper.JadwalKuliahRowMapper;
import id.its.akademik.dao.mapper.KurikulumRowMapper;
import id.its.akademik.dao.mapper.PegawaiRowMapper;
import id.its.akademik.dao.mapper.PeringkatIPDRowMapper;
import id.its.akademik.dao.mapper.PeriodeMahasiswaRowMapper;
import id.its.akademik.domain.Foto;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.PeringkatIPD;
import id.its.akademik.domain.PeriodeMahasiswa;

public class JdbcPegawaiDao extends AbstractDao implements PegawaiDao {

	@Override
	public List<Pegawai> getListPegawai(String nama) {

		String sql = "SELECT p.PE_Nip AS nip, p.PE_Nama as nama, p.PE_NamaLengkap_ValidasiJurusan as nama_lengkap, p.PE_Email as email, p.PE_NipBaru as nip_baru, jp.JenisPegawai as jenis_pegawai FROM pegawai p LEFT JOIN val_JenisPegawai jp ON jp.ID = p.PE_IDJenisPegawai WHERE PE_Nama LIKE CONCAT('%', :nama ,'%') OR :nama IS NULL";
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nama", nama);

		List<Pegawai> listPeg = jdbcTemplate.query(sql, params, new PegawaiRowMapper());

		return listPeg;
	}

	@Override
	public Pegawai getPegawai(String nip) {
//		String key="pegawai_"+nip;
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT p.PE_Nip AS nip, p.PE_Nama as nama, p.PE_NamaLengkap_ValidasiJurusan as nama_lengkap, p.PE_Email as email, p.PE_NipBaru as nip_baru, jp.JenisPegawai as jenis_pegawai, p.PE_TglLahir AS tanggal_lahir, p.PE_Alamat AS alamat, p.PE_Telepon AS telp, p.PE_NoHP AS no_hp FROM pegawai p LEFT JOIN val_JenisPegawai jp ON jp.ID = p.PE_IDJenisPegawai WHERE pe_nip = :nip";
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nip", nip);

			List<Pegawai> listPeg = jdbcTemplate.query(sql, params, new PegawaiRowMapper());
//
//			if (!listPeg.isEmpty()) {
//				HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//				
//				Map<String,String> pegawaiValue=new HashMap<>();
//				pegawaiValue.put("nip", listPeg.get(0).getNip());
//				pegawaiValue.put("nip_baru", listPeg.get(0).getNipBaru());
//				pegawaiValue.put("nama_lengkap", listPeg.get(0).getNamaLengkap());
//				pegawaiValue.put("nama", listPeg.get(0).getNama());
//				pegawaiValue.put("alamat", listPeg.get(0).getAlamat());
//				
//				operationHash.putAll(key, pegawaiValue);
////				this.getRedisTemplate().expire(key, 40, TimeUnit.SECONDS);
//				return listPeg.get(0);
//			} else {
//				return null;
//			}
//		}
//		else
//		{
//			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//			Map<String,String> pegawaiRedis=operationHash.entries(key);
//			Pegawai pegawai=new Pegawai();
//			pegawai.setNip(pegawaiRedis.get("nip"));
//			pegawai.setNipBaru(pegawaiRedis.get("nip_baru"));
//			pegawai.setNama(pegawaiRedis.get("nama_lengkap"));
//			pegawai.setNamaLengkap(pegawaiRedis.get("nama"));
//			pegawai.setAlamat(pegawaiRedis.get("alamat"));
//			return pegawai;
//		}
			if (!listPeg.isEmpty()) {
				return listPeg.get(0);
			} else {
				return null;
			}
	}

	@Override
	public Pegawai getPegawaiByNIPBaru(String nip) {
		String key="pegawaiNIPBARU_"+nip;
		if(this.getRedisTemplate().hasKey(key)==false)
		{
			String sql = "SELECT p.PE_Nip AS nip, p.PE_Nama as nama, p.PE_NamaLengkap_ValidasiJurusan as nama_lengkap, p.PE_Email as email, p.PE_NipBaru as nip_baru, jp.JenisPegawai as jenis_pegawai, p.PE_TglLahir AS tanggal_lahir, p.PE_Alamat AS alamat, p.PE_Telepon AS telp, p.PE_NoHP AS no_hp FROM pegawai p LEFT JOIN val_JenisPegawai jp ON jp.ID = p.PE_IDJenisPegawai WHERE pe_nipbaru = :nip";
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nip", nip);

			List<Pegawai> listPeg = jdbcTemplate.query(sql, params, new PegawaiRowMapper());

			if (!listPeg.isEmpty()) {
				HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
				
				Map<String,String> pegawaiValue=new HashMap<>();
				pegawaiValue.put("nip", listPeg.get(0).getNip());
				
				pegawaiValue.put("nip_baru", listPeg.get(0).getNipBaru());
				
				pegawaiValue.put("nama_lengkap", listPeg.get(0).getNamaLengkap());
				pegawaiValue.put("nama", listPeg.get(0).getNama());
				pegawaiValue.put("alamat", listPeg.get(0).getAlamat());
				
				operationHash.putAll(key, pegawaiValue);
				this.getRedisTemplate().expire(key, 86400, TimeUnit.SECONDS);
				return listPeg.get(0);
			} else {
				return null;
			}
		}
		else
		{
			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
			Map<String,String> pegawaiRedis=operationHash.entries(key);
			Pegawai pegawai=new Pegawai();
			pegawai.setNip(pegawaiRedis.get("nip"));
			pegawai.setNipBaru(pegawaiRedis.get("nip_baru"));
			pegawai.setNama(pegawaiRedis.get("nama_lengkap"));
			pegawai.setNamaLengkap(pegawaiRedis.get("nama"));
			pegawai.setAlamat(pegawaiRedis.get("alamat"));
			return pegawai;
		}
	}

/*
	@Override
	public Foto getFotoDosen(String nip) {
		String key="fotoPegawai_"+nip;
		if(this.getRedisTemplate().hasKey(key)==false)
		{
			String sql = "SELECT pe_nip as id, pe_nipbaru as nrp, pe_nama as nama, pe_photo as foto FROM pegawai where (pe_nip = :nip OR pe_nipbaru  = :nip)";
	
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nip", nip);

			List<Foto> listFoto = jdbcTemplate.query(sql, params, new FotoMahasiswaRowMapper());

		if (!listFoto.isEmpty()) {
			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
			
			Map<String,String> fotoPegawaiValue=new HashMap<>();
			fotoPegawaiValue.put("id", listFoto.get(0).getId());
			fotoPegawaiValue.put("nama", listFoto.get(0).getNama());
			fotoPegawaiValue.put("nrp", listFoto.get(0).getNrp());
//			fotoPegawaiValue.put("foto", listFoto.get(0).getFoto());
			return listFoto.get(0);
		} else {
			return null;
		}
	}*/	
	
	@Override
	public Foto getFotoDosen(String nip) {

		String sql = "SELECT pe_nip as id, pe_nipbaru as nrp, pe_nama as nama, pe_photo as foto FROM pegawai where (pe_nip = :nip OR pe_nipbaru  = :nip)";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);

		List<Foto> listFoto = jdbcTemplate.query(sql, params, new FotoMahasiswaRowMapper());

		if (!listFoto.isEmpty()) {
			return listFoto.get(0);
		} else {
			return null;
		}
	}


	@Override
	public List<JadwalKuliah> getJadwalAjar(String nip, Integer tahun, Integer semester, String kodeProdi,
			String bahasa) {
		String key="jadwalAjar"+nip+"_"+tahun+"_"+semester+"_"+kodeProdi+"_"+bahasa;
		ListOperations<String,JadwalKuliah> operations= this.getRedisTemplate().opsForList();
		if(this.getRedisTemplate().hasKey(key)==false)
		{	
			String sql = "SELECT a.tahun, a.semester, dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) as kode_mk, CASE :bahasa WHEN 'en' THEN mk.MK_Mata_KuliahInggris ELSE a.nama_mk END AS nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai, a.NAMADOSEN AS nama_dosen FROM (SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,7,6) AS kodeMk, SUBSTRING(KODEKELAS,1,5) AS kode_jurusan, A.NAMAMK AS nama_mk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.KODEHARI AS id_hari, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot, NAMADOSEN FROM SIMARU.dbo.ALOKASI A INNER JOIN SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT JOIN Kuliah k ON a.kodeMk = k.KU_KE_KR_MK_ID AND a.tahun = k.KU_KE_Tahun AND a.kelas = k.KU_KE_Kelas AND k.KU_KE_IDSemester = a.semester LEFT JOIN Kelas ke ON ke.KE_KR_MK_ID = k.KU_KE_KR_MK_ID AND ke.KE_Tahun = k.KU_KE_Tahun AND ke.KE_Kelas = k.KU_KE_Kelas AND ke.KE_IDSemester = k.KU_KE_IDSemester LEFT JOIN Mengajar me ON me.MG_MK_ID = k.KU_KE_KR_MK_ID AND me.MG_Tahun = k.KU_KE_Tahun AND me.MG_Kelas = k.KU_KE_Kelas AND me.MG_IDSemester = k.KU_KE_IDSemester JOIN MataKuliah mk ON a.kodeMk = mk.MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = mk.MK_ThnKurikulum WHERE a.tahun = :tahun AND a.semester = :semester AND (me.MG_NIP = :nip OR ke.KE_PE_NIPPengajar=:nip) AND (a.kode_jurusan = :prodi OR a.kode_jurusan = '__TPB' OR a.kode_jurusan = '__MKU') GROUP BY a.tahun, a.semester, a.kodeMk, a.nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, a.NAMADOSEN, KU_KE_KR_MK_ThnKurikulum, MK.MK_Mata_KuliahInggris ORDER BY a.id_hari, jam_mulai";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tahun", tahun);
			params.put("semester", semester);
			params.put("nip", nip);
			params.put("prodi", kodeProdi);
			params.put("bahasa", bahasa);

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<JadwalKuliah> list = jdbcTemplate.query(sql, params, new JadwalKuliahRowMapper());

			List<Map<String,Object>> arrayJadwalAjar=new ArrayList<Map<String,Object>>();
			for(JadwalKuliah jadwalAjar:list)
			{
				operations.rightPush("jadwalAjar"+nip+"_"+tahun+"_"+semester+"_"+kodeProdi+"_"+bahasa, jadwalAjar);
			}
			return list;
		}
		else
		{
			List<JadwalKuliah> list=operations.range("jadwalAjar"+nip+"_"+tahun+"_"+semester+"_"+kodeProdi+"_"+bahasa, 0, -1);
			return list;
		}
		
		
		
			}

	@Override
	public List<JadwalKuliah> getJadwalAjarSelanjutnya(String nip, Integer tahun, Integer semester, Integer kodeHari,
			String kodeProdi, String bahasa) {
		String sql = "SELECT TOP 1 a.tahun, a.semester, dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) as kode_mk, CASE :bahasa WHEN 'en' THEN mk.MK_Mata_KuliahInggris ELSE a.nama_mk END AS nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai, a.NAMADOSEN AS nama_dosen FROM (SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,7,6) AS kodeMk, SUBSTRING(KODEKELAS,1,5) AS kode_jurusan, A.NAMAMK AS nama_mk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.KODEHARI AS id_hari, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot, NAMADOSEN FROM SIMARU.dbo.ALOKASI A INNER JOIN SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT JOIN Kuliah k ON a.kodeMk = k.KU_KE_KR_MK_ID AND a.tahun = k.KU_KE_Tahun AND a.kelas = k.KU_KE_Kelas AND k.KU_KE_IDSemester = a.semester LEFT JOIN Kelas ke ON ke.KE_KR_MK_ID = k.KU_KE_KR_MK_ID AND ke.KE_Tahun = k.KU_KE_Tahun AND ke.KE_Kelas = k.KU_KE_Kelas AND ke.KE_IDSemester = k.KU_KE_IDSemester LEFT JOIN Mengajar me ON me.MG_MK_ID = k.KU_KE_KR_MK_ID AND me.MG_Tahun = k.KU_KE_Tahun AND me.MG_Kelas = k.KU_KE_Kelas AND me.MG_IDSemester = k.KU_KE_IDSemester JOIN MataKuliah mk ON a.kodeMk = mk.MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = mk.MK_ThnKurikulum WHERE a.tahun = :tahun AND a.semester = :semester AND (me.MG_NIP = :nip OR ke.KE_PE_NIPPengajar=:nip) AND id_hari > :idHari AND (a.kode_jurusan = :prodi OR a.kode_jurusan = '__TPB' OR a.kode_jurusan = '__MKU') GROUP BY a.tahun, a.semester, a.kodeMk, a.nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, a.NAMADOSEN, KU_KE_KR_MK_ThnKurikulum, MK.MK_Mata_KuliahInggris ORDER BY a.id_hari, jam_mulai";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("nip", nip);
		params.put("idHari", kodeHari);
		params.put("prodi", kodeProdi);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<JadwalKuliah> list = jdbcTemplate.query(sql, params, new JadwalKuliahRowMapper());

		return list;
	}

	@Override
	public List<PeriodeMahasiswa> getPeriode(String nip, String kodeProdi) {
		String sql = "SELECT DISTINCT KE_Tahun AS tahun, KE_IDSemester AS semester, s.Semester+' '+KE_Tahun+'/'+CAST(KE_Tahun+1 AS VARCHAR) AS keterangan FROM Kelas JOIN val_semester s ON KE_IDSemester = s.ID WHERE KE_PE_NIPPengajar = :nip AND KE_KodeJurusan = :prodi";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("prodi", kodeProdi);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<PeriodeMahasiswa> list = jdbcTemplate.query(sql, params, new PeriodeMahasiswaRowMapper());

		return list;
	}

	@Override
	public Double getRataIPM(String kodeProdi, String idMk, Integer tahun, Integer semester, Integer tahunKurikulum,
			String kelas) {
		String sql = "select avg(convert(float,KM_JW_IDJAwaban)) as ipm from KuesionerMK where KM_KE_KR_mk_id =:idMk and KM_KE_Tahun =:tahun and KM_KE_KodeJurusan =:prodi and KM_KE_IDSemester =:semester and KM_MK_thnkurikulum =:thnKurikulum and CASE WHEN ASCII(KM_KE_Kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(KM_KE_Kelas) - 150) ELSE KM_KE_Kelas END=:kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idMk", idMk);
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("thnKurikulum", tahunKurikulum);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Double rata = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			rata = (Double) rs.get("ipm");
		} catch (Exception e) {
			System.out.println(e);
		}
		return rata;

	}

	@Override
	public Double getRataIPD(String nip, String kodeProdi, String idMk, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas) {
		String sql = "select avg(convert(float,KS_JW_IDJAwaban)) as ipd, count(*) as jumlah from KuesionerDosen where KS_KE_KR_mk_id =:idMk and KS_KE_Tahun =:tahun and KS_KE_KodeJurusan =:prodi and KS_KE_IDSemester =:semester and KS_MK_Thnkurikulum =:thnKurikulum and KS_KE_PE_NIPPengajar =:nip and CASE WHEN ASCII(KS_KE_Kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(KS_KE_Kelas) - 150) ELSE KS_KE_Kelas END=:kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("idMk", idMk);
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("thnKurikulum", tahunKurikulum);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Double rata = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			rata = (Double) rs.get("ipd");
		} catch (Exception e) {
			System.out.println(e);
		}
		return rata;

	}

	@Override
	public Integer getJumlahPengisi(String kodeProdi, String idMk, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas) {
		String sql = "select count(distinct(km_ku_ma_nrp)) AS jumlah from KuesionerMK where KM_KE_KR_mk_id =:idMk and KM_KE_Tahun =:tahun and KM_KE_KodeJurusan =:prodi and KM_KE_IDSemester =:semester and KM_MK_thnkurikulum =:thnKurikulum and KM_KE_Kelas=:kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idMk", idMk);
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("thnKurikulum", tahunKurikulum);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer jumlah = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			jumlah = (Integer) rs.get("jumlah");
		} catch (Exception e) {
			System.out.println(e);
		}
		return jumlah;
	}

	@Override
	public Integer getJumlahResponden(String nip, String kodeProdi, String idMk, Integer tahun, Integer semester,
			Integer tahunKurikulum, String kelas) {

		String sql = "select count(distinct (ks_ku_ma_nrp)) AS jumlah from kuesionerdosen where ks_ke_kr_mk_id=:idMk and ks_mk_thnkurikulum=:thnKurikulum and ks_ke_tahun=:tahun and ks_ke_idsemester=:semester and ks_ke_kodejurusan=:prodi and ks_ke_pe_nippengajar=:nip and ks_ke_kelas=:kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("idMk", idMk);
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("thnKurikulum", tahunKurikulum);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Integer jumlah = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			jumlah = (Integer) rs.get("jumlah");
		} catch (Exception e) {
			System.out.println(e);
		}
		return jumlah;
	}

	@Override
	public List<PeringkatIPD> getPeringkatIPD(String kodeProdi, Integer tahun, Integer semester, String urutkan,
			String bahasa) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT CASE :bahasa WHEN 'en' THEN mk_mata_kuliahinggris ELSE mk_mata_kuliah END AS mk_mata_kuliah, ke_kr_mk_id, dbo.MKID(ke_kr_mk_id, ke_kr_mk_thnkurikulum) AS kode_mk, ke_kelas, ke_terisi, pe_namalengkap, pe_nama, pe_nip, ke_kodejurusan, ke_tahun, ke_idsemester, ke_kr_mk_thnkurikulum, urutan, IPMK AS rata2ipd_mk, jPengisiIPMK AS jumlah_pengisi, IPDO AS rata2ipd_kinerja_dosen, jPengisiIPDO AS responden_dosen, IPD AS rata2ipd, IPMK_PCT AS jumlah_pengisi_persen, is_permanen_nilai FROM derived.IPD_Report WHERE ke_kodejurusan = :prodi AND ke_tahun = :tahun AND ke_idsemester = :semester AND is_permanen_nilai = 1");

		if (urutkan.equalsIgnoreCase("namamk"))
			sql.append("order by mk_mata_kuliah asc, ke_kelas, urutan ");
		else if (urutkan.equalsIgnoreCase("namadosen"))
			sql.append("order by pe_nama asc, mk_mata_kuliah, ke_kelas, urutan ");
		else if (urutkan.equalsIgnoreCase("nummhs"))
			sql.append("order by ke_terisi asc, mk_mata_kuliah, ke_kelas, urutan ");
		else if (urutkan.equalsIgnoreCase("ipd")) {
			sql.append("order by ipd desc");
		} else
			sql.append("order by mk_mata_kuliah asc, ke_kelas, urutan ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<PeringkatIPD> list = jdbcTemplate.query(sql.toString(), params, new PeringkatIPDRowMapper());
		return list;

	}

	@Override
	public List<PeringkatIPD> getIPD(String nip, String kodeProdi, Integer tahun, Integer semester, String urutkan,
			String bahasa) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT CASE :bahasa WHEN 'en' THEN mk_mata_kuliahinggris ELSE mk_mata_kuliah END AS mk_mata_kuliah, ke_kr_mk_id, dbo.MKID(ke_kr_mk_id, ke_kr_mk_thnkurikulum) AS kode_mk, ke_kelas, ke_terisi, pe_namalengkap, pe_nama, pe_nip, ke_kodejurusan, ke_tahun, ke_idsemester, ke_kr_mk_thnkurikulum, urutan, IPMK AS rata2ipd_mk, jPengisiIPMK AS jumlah_pengisi, IPDO AS rata2ipd_kinerja_dosen, jPengisiIPDO AS responden_dosen, IPD AS rata2ipd, IPMK_PCT AS jumlah_pengisi_persen, is_permanen_nilai FROM derived.IPD_Report WHERE ke_kodejurusan = :prodi AND ke_tahun = :tahun AND ke_idsemester = :semester AND pe_nip = :nip AND is_permanen_nilai = 1");

		if (urutkan.equalsIgnoreCase("namamk"))
			sql.append("order by mk_mata_kuliah asc, ke_kelas, urutan ");
		else if (urutkan.equalsIgnoreCase("namadosen"))
			sql.append("order by pe_nama asc, mk_mata_kuliah, ke_kelas, urutan ");
		else if (urutkan.equalsIgnoreCase("nummhs"))
			sql.append("order by ke_terisi asc, mk_mata_kuliah, ke_kelas, urutan ");
		else
			sql.append("order by mk_mata_kuliah asc, ke_kelas, urutan ");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("nip", nip);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<PeringkatIPD> list = jdbcTemplate.query(sql.toString(), params, new PeringkatIPDRowMapper());
		return list;
	}

	@Override
	public PeringkatIPD getIPDSpesifik(String nip, String kodeProdi, Integer tahun, Integer semester, String idMk,
			String kelas) {
		String sql = "SELECT mk_mata_kuliah, ke_kr_mk_id, dbo.MKID(ke_kr_mk_id, ke_kr_mk_thnkurikulum) AS kode_mk, ke_kelas, ke_terisi, pe_namalengkap, pe_nama, pe_nip, ke_kodejurusan, ke_tahun, ke_idsemester, ke_kr_mk_thnkurikulum, urutan, IPMK AS rata2ipd_mk, jPengisiIPMK AS jumlah_pengisi, IPDO AS rata2ipd_kinerja_dosen, jPengisiIPDO AS responden_dosen, IPD AS rata2ipd, IPMK_PCT AS jumlah_pengisi_persen FROM derived.IPD_Report WHERE ke_kodejurusan = :prodi AND ke_tahun = :tahun AND ke_idsemester = :semester AND pe_nip = :nip AND ke_kr_mk_id = :mkid AND ke_kelas = :kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("nip", nip);
		params.put("mkid", idMk);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<PeringkatIPD> list = jdbcTemplate.query(sql.toString(), params, new PeringkatIPDRowMapper());
		return list.get(0);
	}

	@Override
	public Double getTerisi(String nip, String kodeProdi, Integer tahun, Integer semester, Integer tahunKurikulum,
			String idMk, String kelas) {
		String sql = "select ke_terisi from kelas where KE_KR_MK_ThnKurikulum = :thnKurikulum AND KE_KR_MK_ID = :idMk and KE_Tahun = :tahun AND KE_IDSemester = :semester and KE_KodeJurusan=:prodi and KE_PE_NIPPengajar=:nip AND CASE WHEN ASCII(ke_kelas) > 150 THEN CONVERT(VARCHAR(2), ASCII(ke_kelas) - 150) ELSE ke_kelas END = :kelas";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("prodi", kodeProdi);
		params.put("semester", semester);
		params.put("thnKurikulum", tahunKurikulum);
		params.put("nip", nip);
		params.put("idMk", idMk);
		params.put("kelas", kelas);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Short terisi = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			terisi = (Short) rs.get("ke_terisi");
		} catch (Exception e) {
			System.out.println(e);
		}

		return terisi.doubleValue();
	}

}
