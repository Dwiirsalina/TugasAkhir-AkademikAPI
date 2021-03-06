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
import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.mapper.AkademikRowMapper;
import id.its.akademik.dao.mapper.CekPembayaranRowMapper;
import id.its.akademik.dao.mapper.DaftarNilaiRowMapper;
import id.its.akademik.dao.mapper.DetailPembayaranRowMapper;
import id.its.akademik.dao.mapper.FRSFotoRowMapper;
import id.its.akademik.dao.mapper.FotoMahasiswaRowMapper;
import id.its.akademik.dao.mapper.JadwalKuliahRowMapper;
import id.its.akademik.dao.mapper.JawabanRowMapper;
import id.its.akademik.dao.mapper.KeaktifanMahasiswaRowMapper;
import id.its.akademik.dao.mapper.KemajuanStudiRowMapper;
import id.its.akademik.dao.mapper.KuesionerRowMapper;
import id.its.akademik.dao.mapper.KurikulumRowMapper;
import id.its.akademik.dao.mapper.ListKuesionerRowMapper;
import id.its.akademik.dao.mapper.MahasiswaFotoRowMapper;
import id.its.akademik.dao.mapper.MahasiswaRowMapper;
import id.its.akademik.dao.mapper.MataKuliahRowMapper;
import id.its.akademik.dao.mapper.OrtuRowMapper;
import id.its.akademik.dao.mapper.PegawaiRowMapper;
import id.its.akademik.dao.mapper.PekerjaanRowMapper;
import id.its.akademik.dao.mapper.PembayaranRowMapper;
import id.its.akademik.dao.mapper.PeriodeMahasiswaRowMapper;
import id.its.akademik.dao.mapper.TranskripRowMapper;
import id.its.akademik.dao.mapper.WaliMahasiswaRowMapper;
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
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.ListKuesioner;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.Pembayaran;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.Transkrip;
import id.its.akademik.domain.WaliMahasiswa;

public class JdbcMahasiswaDao extends AbstractDao implements MahasiswaDao {

	final String orderFields = " KR_Semester AS semester, dbTranskrip.MK_ID AS id_mk, KU_KE_Tahun AS tahun, KU_KE_IDSemester AS semester_ambil ";
	final String tampilFields = " ,Tampil=case when (KS_KU_MA_Nrp is null or KM_KU_MA_Nrp is null) and KU_KE_Tahun >= '2010' and aa.MK_ID is null then 0 else 1 end ";
	final String tampilFieldPPs = " ,Tampil=case when (KS_KU_MA_Nrp is null or KM_KU_MA_Nrp is null) and KU_KE_Tahun >= '2011' and aa.MK_ID is null then 0 else 1 end ";
	final String leftJoin = "left join kuesionerdosen on ks_ku_ma_nrp = ku_ma_nrp and ks_mk_thnkurikulum = mk_thnkurikulum and ks_ke_tahun = ku_ke_tahun and ks_ke_kr_mk_id = mk_id and ks_ke_idsemester = ku_ke_idsemester and ks_ke_kodejurusan = substring(ku_ma_nrp,1,2)+''+substring(ku_ma_nrp,5,3) left join kuesionermk on km_ku_ma_nrp = ku_ma_nrp and km_mk_thnkurikulum = mk_thnkurikulum and km_ke_tahun = ku_ke_tahun and km_ke_kr_mk_id = mk_id and km_ke_idsemester = ku_ke_idsemester and km_ke_kodejurusan = substring(ku_ma_nrp,1,2)+''+substring(ku_ma_nrp,5,3)	left join matakuliah_tdkdihitungpengumpulan aa on aa.MK_ThnKurikulum = dbTranskrip.MK_ThnKurikulum and aa.MK_ID = dbTranskrip.MK_ID";
	final String dbtranskrip = "derived.dspTranskrip";
	final String dbdatalulus = "Mahasiswa_dataKelulusan";

	@Override
	public List<Mahasiswa> getMahasiswa(String id) {
//		String key="mahasiswa_"+id;
//		ListOperations<String,Mahasiswa> operations= this.getRedisTemplate().opsForList();
		
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, substring(ma_nrp, 1, 2)+substring(ma_nrp, 5, 3) AS kode_prodi, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.ma_idsex AS jk, p.PE_NamaLengkap AS dosen_wali, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.MA_NomorHP AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, ps.PS_Nama_Baru AS prodi, CONVERT(DECIMAL(10,2), MA_IPK) AS ipk, MA_SksLulus AS sks_lulus, YEAR(MA_TglMasukITS) AS tahun_masuk FROM mahasiswa m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id LEFT JOIN Pegawai p ON p.PE_Nip = m.MA_PE_NipWali JOIN ProgramStudi ps ON substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = ps.PS_Kode_Prodi_Lama WHERE m.ma_nrp = :id";
	
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
	
			List<Mahasiswa> listMhs = jdbcTemplate.query(sql, params, new MahasiswaRowMapper());
			
//			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//			Map<String,String> mahasiswaValue=new HashMap<>();
//			mahasiswaValue.put("id", listMhs.get(0).getId());
//			mahasiswaValue.put("nama_lengkap", listMhs.get(0).getNamaLengkap());
//			mahasiswaValue.put("", listMhs.get(0).getNrp());
//			
//			operationHash.putAll(key, mahasiswaValue);
//			List<Map<String,String>> arrayMahasiswa=new ArrayList<Map<String,String>>();
//			for(Mahasiswa mhs:listMhs)
//			{
//				operations.rightPush("mahasiswa_"+id, mhs);
//			}
//		}
//		else
//		{
//			List<Mahasiswa> listMhs=operations.range("mahasiswa_"+id, 0, -1);
//		}
		return listMhs;
//		
	}

	@Override
	public List<MahasiswaFoto> getMahasiswaFoto(String id) {
		String key="mahasiswaFoto_"+id;
		ListOperations<String,MahasiswaFoto> operations= this.getRedisTemplate().opsForList();
		if(this.getRedisTemplate().hasKey(key)==false)
		{
			String sql = "SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, substring(ma_nrp, 1, 2)+substring(ma_nrp, 5, 3) AS kode_prodi, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.MA_Photo AS foto, m.ma_idsex AS jk, p.PE_NamaLengkap AS dosen_wali, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.MA_NomorHP AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, ps.PS_Nama_Baru AS prodi, CONVERT(DECIMAL(10,2), MA_IPK) AS ipk, MA_SksLulus AS sks_lulus, YEAR(MA_TglMasukITS) AS tahun_masuk FROM mahasiswa m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id LEFT JOIN Pegawai p ON p.PE_Nip = m.MA_PE_NipWali JOIN ProgramStudi ps ON substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = ps.PS_Kode_Prodi_Lama WHERE m.ma_nrp = :id";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);

			List<MahasiswaFoto> listMhsFoto = jdbcTemplate.query(sql, params, new MahasiswaFotoRowMapper());

			List<Map<String,Object>> arrayMahasiswaFoto=new ArrayList<Map<String,Object>>();
			for(MahasiswaFoto mhsFoto:listMhsFoto)
			{
				operations.rightPush("mahasiswaFoto_"+id, mhsFoto);
			}
			return listMhsFoto;
		}
		else
		{
			List<MahasiswaFoto> listMhsFoto=operations.range("mahasiswaFoto_"+id, 0, -1);
			return listMhsFoto;
		}		
			}

	@Override
	public List<Mahasiswa> getMahasiswaWaliByNip(String nip) {
//		String key="mahasiswabyID_"+nip;
//		ListOperations<String,Mahasiswa> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT m.ma_nrp as id, m.ma_nrp_baru as nrp, m.ma_nama as nama FROM mahasiswa m WHERE m.ma_pe_nipwali = :nipwali ORDER BY m.ma_nrp";
	
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nipwali", nip);
	
			List<Mahasiswa> listMhs = jdbcTemplate.query(sql, params, new MahasiswaRowMapper());
//			List<Map<String,Object>> arrayMahasiswa=new ArrayList<Map<String,Object>>();
//			for(Mahasiswa mhs:listMhs)
//			{
//				operations.rightPush("mahasiswabyID_"+nip, mhs);
//			}
//			return listMhs;
//		}
//		else
//		{
//			List<Mahasiswa> listMhs=null;
			return listMhs;
//		}	
	}

	@Override
	public List<FRSFoto> getMahasiswaWali(String nip, String prodi, Integer tahun, Integer semester) {
		String sql = "SELECT m.MA_Photo AS foto, m.MA_NRP AS nrp, m.ma_nrp_baru AS nrp_baru, l.MHS_IPS AS ips_lalu, MA_Nama AS nama, MA_IPS, MA_IPK AS ips, MA_SKSTempuh AS sks_tempuh, MA_SKSLulus, (SELECT SUM(MK_KreditKuliah) FROM Kuliah k, MataKuliah mk WHERE k.KU_KE_KR_MK_ThnKurikulum+k.KU_KE_KR_MK_ID=mk.MK_ThnKurikulum+mk.MK_ID AND (k.KU_KE_KodeJurusan=substring(m.MA_NRP,1,2)+substring(m.MA_NRP,5,3) OR k.KU_KE_KodeJurusan='__TPB') AND k.KU_KE_Tahun=:tahun AND k.KU_KE_IDSemester=:semester AND k.KU_MA_NRP=m.MA_NRP) as totalSKS FROM Mahasiswa m LEFT JOIN FRSDisetujui f ON f.MA_NRP = m.MA_NRP AND KE_Tahun = :tahun AND cast(KE_IDSemester as char(1)) = :semester LEFT JOIN Mahasiswa_HistorisStatus l ON l.MHS_MA_NRP = m.MA_NRP AND l.MHS_ThnAjaran+l.MHS_IDSemester = dbo.getSemesterSebelumnya(:tahun, :semester) LEFT JOIN Mahasiswa_HistorisStatus s ON s.MHS_MA_NRP = m.MA_NRP AND s.MHS_ThnAjaran+s.MHS_IDSemester = :periode WHERE MA_PE_NipWali = :nip AND substring(m.MA_NRP,1,2)+substring(m.MA_NRP,5,3)=:prodi";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nip", nip);
		params.put("prodi", prodi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("periode", String.valueOf(tahun) + semester);

		List<FRSFoto> list = jdbcTemplate.query(sql, params, new FRSFotoRowMapper());

		return list;

	}

	@Override
	public List<OrangTua> getOrtuMahasiswa(String id) {
//		String key="ortuMhsByID_"+id;
//		ListOperations<String,OrangTua> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT Mahasiswa.MA_Nrp as id, Mahasiswa.MA_Nrp_baru as nrp, Mahasiswa.MA_Nama as nama, Mahasiswa.MA_NamaAyah as nama_ayah, Mahasiswa.MA_NamaIbu as nama_ibu, Mahasiswa.MA_AlamatOrtu as alamat, kab_kota_dap.NAMA AS kota, provinsi_dap.NAMA AS provinsi, Mahasiswa.MA_KodePosOrtu as kodepos, val_Pekerjaan.Pekerjaan AS pekerjaan_ayah, val_Pekerjaan_1.Pekerjaan AS pekerjaan_ibu, val_Pendapatan.Pendapatan AS pendapatan, Mahasiswa.MA_TelpOrtu as telp FROM val_Pekerjaan AS val_Pekerjaan_1 RIGHT OUTER JOIN Mahasiswa LEFT OUTER JOIN provinsi_dap INNER JOIN kab_kota_dap ON provinsi_dap.KODE = kab_kota_dap.KODE_PROV ON Mahasiswa.MA_AlamatOrtu_KodeKota COLLATE SQL_Latin1_General_CP1_CI_AI = kab_kota_dap.KODE LEFT OUTER JOIN val_Pendapatan ON Mahasiswa.MA_IDPendapatan = val_Pendapatan.ID LEFT OUTER JOIN val_Pekerjaan ON Mahasiswa.MA_IDPekerjaanAyah = val_Pekerjaan.ID ON val_Pekerjaan_1.ID = Mahasiswa.MA_IDPekerjaanIbu WHERE Mahasiswa.MA_Nrp = :nrp";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", id);

			List<OrangTua> listOrtu = jdbcTemplate.query(sql, params, new OrtuRowMapper());

//			List<Map<String,Object>> arrayOrangTua=new ArrayList<Map<String,Object>>();
//			for(OrangTua ortu:listOrtu)
//			{
//				operations.rightPush("ortuMhsByID_"+id, ortu);
//			}
//			return listOrtu;
//		}
//		else
//		{
//			List<OrangTua> listOrtu=operations.range("ortuMhsByID_"+id, 0, -1);
			return listOrtu;
//		}
	}

	@Override
	public List<Pekerjaan> getPekerjaanMahasiswa(String id) {
//		String key="mhsKerjaByID_"+id;
//		ListOperations<String,Pekerjaan> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT m.MA_Nrp as id, m.ma_nrp_baru as nrp, m.MA_Nama as nama, p.pekerjaan, m.MA_s1s2_pek_NamaInstansi as instansi, MA_s1s2_pek_AlamatInstansi as alamat_instansi, m.MA_s1s2_pek_TelpInstansi as telp_instansi, m.MA_s1s2_pek_FaxInstansi as fax_instansi, ji.JabatanInstansi as jabatan, m.MA_s1s2_pek_IDPendapatan as pendapatan FROM Mahasiswa m INNER JOIN val_Pekerjaan p ON m.MA_IDPekerjaan = p.ID LEFT JOIN val_JabatanInstansi ji ON ji.ID = m.MA_s1s2_pek_IDJabatanInstansi WHERE m.MA_Nrp = :nrp";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", id);

			List<Pekerjaan> listKerja = jdbcTemplate.query(sql, params, new PekerjaanRowMapper());
//			List<Map<String,Object>> arrayPekerjaan=new ArrayList<Map<String,Object>>();
//			for(Pekerjaan kerja:listKerja)
//			{
//				operations.rightPush("mhsKerjaByID_"+id, kerja);
//			}
//
//			return listKerja;
//		}
//		else
//		{
//			List<Pekerjaan> listKerja=operations.range("mhsKerjaByID_"+id, 0, -1);
			return listKerja;
//		}
	}

	@Override
	public List<KeaktifanMahasiswa> getKeaktifanMahasiswa(String id) {
		
		String sql = "SELECT m.ma_nrp as id, m.ma_nrp_baru as nrp, hs.MHS_ThnAjaran as tahun, hs.MHS_IDSemester as semester, CAST(hs.MHS_Tanggal as date)  as tanggal, hs.MHS_NoSurat as no_surat, hs.MHS_Keterangan as keterangan, vs.StatusMhs as status_aktif, hs.MHS_IPS as ips FROM Mahasiswa_HistorisStatus hs INNER JOIN mahasiswa m ON m.ma_nrp = hs.mhs_ma_nrp INNER JOIN val_StatusMahasiswa vs ON hs.MHS_IDStatusMhs = vs.ID WHERE hs.MHS_MA_Nrp = :nrp";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", id);

		List<KeaktifanMahasiswa> listAktif = jdbcTemplate.query(sql, params, new KeaktifanMahasiswaRowMapper());

		return listAktif;
	}

	@Override
	public Foto getFotoMahasiswa(String nrp) {
		String key="fotoMahasiswa_"+nrp;
		if(this.getRedisTemplate().hasKey(key)==false)
		{
			String sql = "SELECT ma_nrp as id, ma_nrp_baru as nrp, ma_nama as nama, ma_photo as foto FROM mahasiswa where MA_NRP = :nrp";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", nrp);

			List<Foto> listFoto = jdbcTemplate.query(sql, params, new FotoMahasiswaRowMapper());

			if (!listFoto.isEmpty()) {
				HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
				
				Map<String,String> mhsFotoValue=new HashMap<>();
				mhsFotoValue.put("nama", listFoto.get(0).getNama());
				mhsFotoValue.put("nrp", listFoto.get(0).getNrp());
				mhsFotoValue.put("foto", String.valueOf(listFoto.get(0).getFoto()) );
				
				operationHash.putAll(key, mhsFotoValue);
//				this.getRedisTemplate().expire(key, 40, TimeUnit.SECONDS);
				return listFoto.get(0);
			} else {
				return null;
			}
		}
		else
		{
			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
			Map<String,String> mhsFotoRedis=operationHash.entries(key);
			Foto mhs_foto=new Foto();
			mhs_foto.setNama(mhsFotoRedis.get("nama"));
			mhs_foto.setNrp(mhsFotoRedis.get("nrp"));
			
//			mhs_foto.setFoto(Byte.valueOf(mhsFotoRedis.get("foto")));
			return mhs_foto;
		}		
	}

	@Override
	public List<Akademik> getAkademikMahasiswa(String id) {
//		String key="akademikMhsByID_"+id;
//		ListOperations<String,Akademik> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT Mahasiswa.MA_Nrp AS id, Mahasiswa.ma_nrp_baru AS nrp, Mahasiswa.MA_Nama AS nama, val_JalurDiterimaITS.JalurDiterimaITS as jalur_terima, Mahasiswa.MA_TglMasukITS as tgl_masuk, Mahasiswa.MA_NoUjianMasuk AS no_ujian_masuk, MA_STK_Nem_Nilai AS nilai_stk_nem, MA_STK_Nilai_Fisika AS nilai_stk_fisika, MA_STK_Nilai_Matematika AS nilai_stk_mat, MA_STK_Nilai_BhsInggris AS nilai_stk_inggris, MA_STK_Kimia as nilai_stk_kimia, MA_STK_Biologi as nilai_stk_biologi, MA_STK_BhsIndonesia as nilai_stk_indo, MA_NilaiTOEFL as nilai_toefl, MA_NilaiTOEFLLulus as nilai_toefl_lulus, MA_NilaiTPA as nilai_tpa, MA_NilaiPsikotest as nilai_psikotest, sl.NAMA AS asal_sekolah FROM Mahasiswa INNER JOIN val_JalurDiterimaITS ON Mahasiswa.MA_IDJalurDiterimaITS = val_JalurDiterimaITS.ID LEFT JOIN NPSN_SLTA sl ON sl.KODE = Mahasiswa.MA_SLTA_Kode WHERE Mahasiswa.MA_Nrp = :nrp";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", id);

			List<Akademik> listAkademik = jdbcTemplate.query(sql, params, new AkademikRowMapper());

		
//			List<Map<String,Object>> arrayPekerjaan=new ArrayList<Map<String,Object>>();
//			for(Akademik akademik:listAkademik)
//			{
//				operations.rightPush("akademikMhsByID_"+id, akademik);
//			}
//			this.getRedisTemplate().expire(key, 3600, TimeUnit.SECONDS);
//			return listAkademik;
//		}
//		else
//		{
//			List<Akademik> listAkademik=operations.range("akademikMhsByID_"+id, 0, -1);
			return listAkademik;
//		}
	}


	@Override
	public List<ListKuesioner> getListKuesionerDosen(String nrp, String tahun, Integer semester) {
		String sql = "SELECT DISTINCT dbo.MKID(KU_KE_KR_MK_ID, MK_ThnKurikulum) AS id_mk, KU_KE_KR_MK_ID AS kode, mk.MK_Mata_Kuliah AS mata_kuliah, p.PE_Nip AS nip,p.PE_NamaLengkap AS nama_pengajar, k.KU_KE_KodeJurusan AS kode_jurusan, k.KU_KE_Kelas AS kelas, CASE WHEN KU_KE_KR_MK_ID IN (SELECT KS_KE_KR_MK_ID FROM KuesionerDosen_Komentar WHERE KS_KU_MA_NRP = :nrp AND KS_KE_Tahun = :tahun AND KS_KE_IDSemester = :semester) THEN 'TRUE' ELSE 'FALSE' END AS status FROM kuliah k JOIN MataKuliah mk ON k.KU_KE_KR_MK_ID = mk.MK_ID AND mk.MK_ThnKurikulum = k.KU_KE_KR_MK_ThnKurikulum JOIN Kelas ke ON ke.KE_KR_MK_ID = mk.MK_ID AND ke.KE_Tahun = k.KU_KE_Tahun AND ke.KE_IDSemester = k.KU_KE_IDSemester AND ke.KE_KodeJurusan = k.KU_KE_KodeJurusan AND ke.KE_Kelas = k.KU_KE_Kelas JOIN Pegawai p ON ke.KE_PE_NIPPengajar = p.PE_Nip WHERE KU_MA_Nrp = :nrp AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester AND KU_KE_KR_MK_ID NOT IN (SELECT MK_ID FROM MataKuliah_TdkDihitungPengumpulan)";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);

		List<ListKuesioner> kuesioner = jdbcTemplate.query(sql, params, new ListKuesionerRowMapper());

		return kuesioner;
	}

	@Override
	public List<ListKuesioner> getListKuesionerMK(String nrp, String tahun, Integer semester, String bahasa) {
		String sql = "SELECT DISTINCT dbo.MKID(KU_KE_KR_MK_ID, MK_ThnKurikulum) AS id_mk, KU_KE_KR_MK_ID AS kode, CASE :bahasa WHEN 'en' THEN mk.MK_Mata_KuliahInggris ELSE mk.MK_Mata_Kuliah END AS mata_kuliah, p.PE_Nip AS nip,p.PE_NamaLengkap AS nama_pengajar, k.KU_KE_KodeJurusan AS kode_jurusan, k.KU_KE_Kelas AS kelas, CASE WHEN KU_KE_KR_MK_ID IN (SELECT KM_KE_KR_MK_ID FROM KuesionerMK_Komentar WHERE KM_KU_MA_NRP =:nrp AND KM_KE_Tahun = :tahun AND KM_KE_IDSemester = :semester) THEN 'TRUE' ELSE 'FALSE' END AS status FROM kuliah k JOIN MataKuliah mk ON k.KU_KE_KR_MK_ID = mk.MK_ID AND mk.MK_ThnKurikulum = k.KU_KE_KR_MK_ThnKurikulum JOIN Kelas ke ON ke.KE_KR_MK_ID = mk.MK_ID AND ke.KE_Tahun = k.KU_KE_Tahun AND ke.KE_IDSemester = k.KU_KE_IDSemester AND ke.KE_KodeJurusan = k.KU_KE_KodeJurusan AND ke.KE_Kelas = k.KU_KE_Kelas JOIN Pegawai p ON ke.KE_PE_NIPPengajar = p.PE_Nip WHERE KU_MA_Nrp = :nrp AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester AND KU_KE_KR_MK_ID NOT IN (SELECT MK_ID FROM MataKuliah_TdkDihitungPengumpulan)";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("bahasa", bahasa);

		List<ListKuesioner> kuesioner = jdbcTemplate.query(sql, params, new ListKuesionerRowMapper());

		return kuesioner;
	}

	@Override
	public List<Kuesioner> getPertanyaanDosen(String jenjang, String tahunKurikulum, String bahasa) {
//		String key="pertanyaanDosen_"+jenjang+"_"+tahunKurikulum+"_"+bahasa;
//		ListOperations<String,Kuesioner> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{	
			String sql = "SELECT ty_idpertanyaan AS id, ty_thnkurikulum AS tahunKurikulum, ty_jenjang AS jenjang, ty_pertanyaan AS pertanyaan, :bahasa AS bahasa FROM ipd_pertanyaan WHERE ty_idpertanyaan LIKE 'DO%' AND ty_jenjang=:jenjang AND ty_thnkurikulum=:tahunKurikulum AND ty_isaktif=1 ORDER BY CAST(SUBSTRING(ty_idpertanyaan,3,2) AS INT)";
			NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("bahasa", bahasa);
			params.put("tahunKurikulum", tahunKurikulum);
			params.put("jenjang", jenjang);
			List<Kuesioner> kuesioner = jdbcTemplate.query(sql, params, new KuesionerRowMapper());

//			List<Map<String,Object>> arrayTahunKurikulumID=new ArrayList<Map<String,Object>>();
//			for(Kuesioner kuesioner_dosen:kuesioner)
//			{
//				operations.rightPush("pertanyaanDosen_"+jenjang+"_"+tahunKurikulum+"_"+bahasa, kuesioner_dosen);
//			}
//			return kuesioner;
//		}
//		else
//		{
//			List<Kuesioner> kuesioner=operations.range("pertanyaanDosen_"+jenjang+"_"+tahunKurikulum+"_"+bahasa, 0, -1);
			return kuesioner;
//		}
}

	@Override
	public List<Kuesioner> getPertanyaanMK(String jenjang, String tahunKurikulum, String bahasa) {
		String sql = "SELECT ty_idpertanyaan AS id, ty_thnkurikulum AS tahunKurikulum, ty_jenjang AS jenjang, ty_pertanyaan AS pertanyaan, :bahasa AS bahasa FROM ipd_pertanyaan WHERE ty_idpertanyaan LIKE 'MK%' AND ty_jenjang=:jenjang AND ty_thnkurikulum=:tahunKurikulum AND ty_isaktif=1 ORDER BY CAST(SUBSTRING(ty_idpertanyaan,3,2) as int)";
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bahasa", bahasa);
		params.put("jenjang", jenjang);
		params.put("tahunKurikulum", tahunKurikulum);
		List<Kuesioner> kuesioner = jdbcTemplate.query(sql, params, new KuesionerRowMapper());
		return kuesioner;
	}

	@Override
	public List<Jawaban> getJawaban(String jenjang, String idPertanyaan, String tahunKurikulum, String bahasa) {

		String sql = "SELECT jw_idjawaban AS id, jw_jawaban AS jawaban, :bahasa AS bahasa FROM ipd_jawaban WHERE jw_ty_idpertanyaan = :id AND jw_thnkurikulum=:tahunKurikulum AND jw_jenjang=:jenjang";
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bahasa", bahasa);
		params.put("id", idPertanyaan);
		params.put("jenjang", jenjang);
		params.put("tahunKurikulum", tahunKurikulum);
		List<Jawaban> jawaban = jdbcTemplate.query(sql, params, new JawabanRowMapper());
		return jawaban;
	}

	@Override
	public List<KemajuanStudi> getKemajuanStudi(String nrp) {
//		String key="kemajuanStudiByID_"+nrp;
//		ListOperations<String,KemajuanStudi> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT [MHS_MA_Nrp] AS id, [MHS_ThnAjaran] AS tahun, [MHS_ThnAjaran] + '/' + CAST (([MHS_ThnAjaran] + 1) AS VARCHAR) AS tahun_ajaran, [MHS_IDSemester] AS idsemester, s.Semester AS semester, sm.StatusMhs AS status, [MHS_SemesterKe] AS semester_ambil, CONVERT(DECIMAL(10,2), [MHS_IPS]) AS ips, [MHS_SKSSem] AS sks FROM [Mahasiswa_HistorisStatus] mh JOIN val_StatusMahasiswa sm ON mh.MHS_IDStatusMhs = sm.ID JOIN val_semester s ON mh.MHS_IDSemester = s.ID WHERE MHS_MA_Nrp=:nrp";
			NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", nrp);
			List<KemajuanStudi> kemajuan = jdbcTemplate.query(sql, params, new KemajuanStudiRowMapper());
		
//			List<Map<String,Object>> arrayKemajuanStudi=new ArrayList<Map<String,Object>>();
//			for(KemajuanStudi kemajuanStudi:kemajuan)
//			{
//				operations.rightPush("kemajuanStudiByID_"+nrp, kemajuanStudi);
//			}
//
//			return kemajuan;
//		}
//		else
//		{
//			List<KemajuanStudi> kemajuan=operations.range("kemajuanStudiByID_"+nrp, 0, -1);
			return kemajuan;
//		}
		
	}

	@Override
	public List<DaftarNilai> getTranskripMahasiswa(Integer tahun, Integer semester, Integer semesterAmbil, String nrp,
			String bahasa) {

		
		
		String sql = "SELECT KU_MA_NRP as nrp, Tahap as tahap, KR_Semester as semester_ambil, MK_ThnKurikulum as tahun_kurikulum, dbo.mkid(MK_ID, MK_ThnKurikulum) as id_mk, CASE :bahasa WHEN 'en' THEN MK_Mata_KuliahInggris ELSE MK_Mata_Kuliah END as mk, MK_Mata_KuliahInggris as mk_inggris, MK_KreditKuliah as sks, KU_KE_Tahun as tahun, KU_KE_IDSemester as semester, Singkatan as singkatan_semester, KU_NilaiHuruf as nilai_huruf, COALESCE(NiAkhir, '-') as nilai_akhir, NilaiAngka as nilai_angka, MK_UrutTranskrip as urutan FROM derived.dspTranskrip WHERE KU_MA_NRP = :nrp AND KU_KE_Tahun = :tahun AND KU_KE_IDSemester = :semester";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("nrp", nrp);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<DaftarNilai> listTranskrip = jdbcTemplate.query(sql, params, new DaftarNilaiRowMapper());

		return listTranskrip;
	}

	@Override
	public List<PeriodeMahasiswa> getPeriode(String nrp) {
		
//		String key="periodeMhs_"+nrp;
//		ListOperations<String,PeriodeMahasiswa> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT DISTINCT KU_KE_Tahun AS tahun, KU_KE_IDSemester AS semester, s.Semester+' '+k.KU_KE_Tahun+'/'+CAST(KU_KE_Tahun+1 AS VARCHAR) AS keterangan FROM Kuliah k JOIN val_semester s ON k.KU_KE_IDSemester = s.ID WHERE KU_MA_Nrp = :nrp ORDER BY KU_KE_Tahun";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", nrp);

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<PeriodeMahasiswa> list = jdbcTemplate.query(sql, params, new PeriodeMahasiswaRowMapper());


//			List<Map<String,Object>> arrayTahunKurikulumID=new ArrayList<Map<String,Object>>();
//			for(PeriodeMahasiswa periodeMhs:list)
//			{
//				operations.rightPush("periodeMhs_"+nrp, periodeMhs);
//			}
//
//			return list;
//		}
//		else
//		{
//			List<PeriodeMahasiswa> list=operations.range("periodeMhs_"+nrp, 0, -1);
			return list;
//		}
}

	@Override
	public List<JadwalKuliah> getJadwalKuliah(String nrp, Integer tahun, Integer semester, String kodeProdi,
			String bahasa) {
//		String key="jadwalMhs_"+nrp;
//		ListOperations<String,JadwalKuliah> operations= this.getRedisTemplate().opsForList();
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT a.tahun, a.semester, dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) as kode_mk, CASE :bahasa WHEN 'en' THEN mk.MK_Mata_KuliahInggris ELSE a.nama_mk END AS nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai, a.NAMADOSEN AS nama_dosen FROM (SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,7,6) AS kodeMk, SUBSTRING(KODEKELAS,1,5) AS kode_jurusan, A.NAMAMK AS nama_mk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.KODEHARI AS id_hari, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot, NAMADOSEN FROM SIMARU.dbo.ALOKASI A INNER join SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT JOIN Kuliah k ON a.kodeMk = k.KU_KE_KR_MK_ID AND a.tahun = k.KU_KE_Tahun AND a.kelas = k.KU_KE_Kelas AND k.KU_KE_IDSemester = a.semester JOIN MataKuliah mk ON a.kodeMk = mk.MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = mk.MK_ThnKurikulum WHERE a.tahun = :tahun AND a.semester = :semester AND k.KU_MA_Nrp = :nrp AND (a.kode_jurusan = :prodi OR a.kode_jurusan = '__TPB' OR a.kode_jurusan = '__MKU') GROUP BY a.tahun, a.semester, a.kodeMk, a.nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, a.NAMADOSEN, KU_KE_KR_MK_ThnKurikulum, MK.MK_Mata_KuliahInggris ORDER BY a.id_hari, jam_mulai";

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tahun", tahun);
			params.put("semester", semester);
			params.put("nrp", nrp);
			params.put("prodi", kodeProdi);
			params.put("bahasa", bahasa);

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			List<JadwalKuliah> list = jdbcTemplate.query(sql, params, new JadwalKuliahRowMapper());

//			List<Map<String,Object>> arrayTahunKurikulumID=new ArrayList<Map<String,Object>>();
//			for(JadwalKuliah jadwalKuliah:list)
//			{
//				operations.rightPush("jadwalMhs_"+nrp, jadwalKuliah);
//			}
//
//			return list;
//		}
//		else
//		{
//			List<JadwalKuliah> list=operations.range("jadwalMhs_"+nrp, 0, -1);
			return list;
		}
//	}

	@Override
	public List<JadwalKuliah> getJadwalKuliahByIdMk(String idmk, String nrp, Integer tahun, Integer semester,
			String kodeProdi, String bahasa) {
		String sql = "SELECT a.tahun, a.semester, dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) as kode_mk, a.nama_mk, a.kelas, a.kode_kelas, a.id_hari, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai, a.NAMADOSEN AS nama_dosen FROM (SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,7,6) AS kodeMk, SUBSTRING(KODEKELAS,1,5) AS kode_jurusan, A.NAMAMK AS nama_mk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, SUBSTRING(KODEKELAS,14,15) AS kode_kelas, H.KODEHARI AS id_hari, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot, NAMADOSEN FROM SIMARU.dbo.ALOKASI A WITH (NOLOCK) INNER join SIMARU.dbo.HARI H WITH (NOLOCK) on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL WITH (NOLOCK) ON SL.KODESLOT = a.KODESLOT JOIN Kuliah k WITH (NOLOCK) ON a.kodeMk = k.KU_KE_KR_MK_ID AND a.tahun = k.KU_KE_Tahun AND a.kode_kelas = k.KU_KE_Kelas AND k.KU_KE_IDSemester = a.semester WHERE a.tahun = :tahun AND a.semester = :semester AND k.KU_MA_Nrp = :nrp AND (a.kode_jurusan = :prodi OR a.kode_jurusan = '__TPB' OR a.kode_jurusan = '__MKU') AND dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) = :id GROUP BY a.tahun, a.semester, a.kodeMk, a.nama_mk, a.kelas, a.kode_kelas, a.id_hari, a.hari, a.ruangan, a.NAMADOSEN, KU_KE_KR_MK_ThnKurikulum ORDER BY a.id_hari, jam_mulai";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idmk);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("nrp", nrp);
		params.put("prodi", kodeProdi);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<JadwalKuliah> list = jdbcTemplate.query(sql, params, new JadwalKuliahRowMapper());

		return list;

	}

	@Override
	public List<JadwalKuliah> getJadwalKuliahSelanjutnya(String nrp, Integer tahun, Integer semester, Integer idHari,
			String kodeProdi, String bahasa) {
		String sql = "SELECT TOP 1 a.tahun, a.semester, dbo.mkid(a.kodeMk, KU_KE_KR_MK_ThnKurikulum) as kode_mk, CASE :bahasa WHEN 'en' THEN mk.MK_Mata_KuliahInggris ELSE a.nama_mk END AS nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai, a.NAMADOSEN AS nama_dosen FROM (SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,7,6) AS kodeMk, SUBSTRING(KODEKELAS,1,5) AS kode_jurusan, A.NAMAMK AS nama_mk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.KODEHARI AS id_hari, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot, NAMADOSEN FROM SIMARU.dbo.ALOKASI A INNER join SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT JOIN Kuliah k ON a.kodeMk = k.KU_KE_KR_MK_ID AND a.tahun = k.KU_KE_Tahun AND a.kelas = k.KU_KE_Kelas AND k.KU_KE_IDSemester = a.semester JOIN MataKuliah mk ON a.kodeMk = mk.MK_ID AND k.KU_KE_KR_MK_ThnKurikulum = mk.MK_ThnKurikulum WHERE a.tahun = :tahun AND a.semester = :semester AND k.KU_MA_Nrp = :nrp AND id_hari > :idHari AND (a.kode_jurusan = :prodi OR a.kode_jurusan = '__TPB' OR a.kode_jurusan = '__MKU') GROUP BY a.tahun, a.semester, a.kodeMk, a.nama_mk, a.kelas, a.id_hari, a.hari, a.ruangan, a.NAMADOSEN, KU_KE_KR_MK_ThnKurikulum, MK.MK_Mata_KuliahInggris ORDER BY a.id_hari, jam_mulai";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("nrp", nrp);
		params.put("idHari", idHari);
		params.put("prodi", kodeProdi);
		params.put("bahasa", bahasa);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<JadwalKuliah> list = jdbcTemplate.query(sql, params, new JadwalKuliahRowMapper());

		return list;
	}

	@Override
	public List<CekPembayaran> getCekPembayaran(String nrpLama, String nrpBaru) {
		String sql = "select bill_data_id, bill_key1, bill_key2, semester_dikti, bill_flag, bill_amount from ALLBANK.dbo.bill_data where bill_key2 = :nrpLama OR bill_key2 = :nrpBaru union select bill_data_id, bill_key1, bill_key2, semester_dikti, bill_flag, bill_amount from ALLBANK.dbo.pindahan_bill_data where bill_key2 = :nrpLama OR bill_key2 = :nrpBaru union select abill_data_id , abill_key1, abill_key2 as bill_key2, asemester_dikti as semester_dikti, abill_flag as bill_flag, abill_amount from ALLBANK.dbo.abill_data	where abill_key2 = :nrpLama OR abill_key2 = :nrpBaru order by semester_dikti desc";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrpLama", nrpLama);
		params.put("nrpBaru", nrpBaru);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<CekPembayaran> list = jdbcTemplate.query(sql, params, new CekPembayaranRowMapper());

		return list;
	}

	@Override
	public Pembayaran getPembayaran0(String id, String semester, String key2) {
		String sql = "SELECT BILL.*, f.FLAG_DESC AS keterangan FROM (SELECT a.bill_data_id, a.BILL_KEY2, a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG FROM ALLBANK.dbo.bill_data a WHERE BILL_DATA_ID = :id UNION SELECT a.bill_data_id, a.BILL_KEY2, a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG FROM ALLBANK.dbo.pindahan_bill_data a WHERE BILL_DATA_ID = :id UNION SELECT a.abill_data_id, a.aBILL_KEY2, a.aSEMESTER_DIKTI as SEMESTER_DIKTI, a.aBILL_AMOUNT, a.aPAID_DATE, CASE WHEN a.aBILL_FLAG = 0 OR a.aBILL_FLAG = -1 THEN 0 ELSE 1 END as BILL_FLAG FROM ALLBANK.dbo.abill_data a WHERE aSEMESTER_DIKTI = :semester AND aBILL_KEY2 = :key2) BILL JOIN ALLBANK.dbo.flag_status f ON BILL.BILL_FLAG = f.FLAG_ID ORDER BY SEMESTER_DIKTI DESC";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("semester", semester);
		params.put("key2", key2);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Pembayaran> list = jdbcTemplate.query(sql, params, new PembayaranRowMapper());

		return list.get(0);
	}

	@Override
	public Pembayaran getPembayaran1(String id, String semester, String key2) {
		String sql = "SELECT a.bill_data_id, a.BILL_KEY2 , a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG, b.BANK_NAMA AS keterangan FROM ALLBANK.dbo.bill_data a, ALLBANK.dbo.tref_bank b WHERE a.PAID_BANK=b.BANK_KODE AND  BILL_DATA_ID = :id UNION	SELECT a.bill_data_id, a.BILL_KEY2 , a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG, b.BANK_NAMA FROM ALLBANK.dbo.pindahan_bill_data a, ALLBANK.dbo.tref_bank b WHERE a.PAID_BANK=b.BANK_KODE AND  BILL_DATA_ID = :id UNION SELECT a.abill_data_id, a.aBILL_KEY2, a.aSEMESTER_DIKTI as SEMESTER_DIKTI, a.aBILL_AMOUNT, a.aPAID_DATE, CASE WHEN a.aBILL_FLAG = 0 OR a.aBILL_FLAG = -1 THEN 0 ELSE 1 END as BILL_FLAG, b.BANK_NAMA FROM ALLBANK.dbo.abill_data a, ALLBANK.dbo.tref_bank b WHERE a.aPAID_BANK=b.BANK_KODE AND aSEMESTER_DIKTI = :semester AND aBILL_KEY2 = :key2 ORDER BY SEMESTER_DIKTI DESC";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("semester", semester);
		params.put("key2", key2);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Pembayaran> list = jdbcTemplate.query(sql, params, new PembayaranRowMapper());

		return list.get(0);
	}

	@Override
	public Pembayaran getPembayaran2or5(String id, String semester, String key2) {
		String sql = "SELECT BILL.*, f.FLAG_DESC AS keterangan FROM (SELECT a.bill_data_id, a.BILL_KEY2 , a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG FROM ALLBANK.dbo.bill_data a WHERE BILL_DATA_ID = :id UNION SELECT a.bill_data_id, a.BILL_KEY2, a.SEMESTER_DIKTI, a.BILL_AMOUNT, a.PAID_DATE, CASE WHEN a.BILL_FLAG = 0 OR a.BILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG FROM ALLBANK.dbo.pindahan_bill_data a WHERE BILL_DATA_ID = :id UNION SELECT a.abill_data_id, a.aBILL_KEY2 , a.aSEMESTER_DIKTI as SEMESTER_DIKTI, a.aBILL_AMOUNT, a.aPAID_DATE, CASE WHEN a.aBILL_FLAG = 0 OR a.aBILL_FLAG = -1 THEN 0 ELSE 1 END AS BILL_FLAG FROM ALLBANK.dbo.abill_data a WHERE aSEMESTER_DIKTI = :semester AND aBILL_KEY2 = :key2) BILL JOIN ALLBANK.dbo.flag_status f ON BILL.BILL_FLAG = f.FLAG_ID ORDER BY SEMESTER_DIKTI DESC";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("semester", semester);
		params.put("key2", key2);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<Pembayaran> list = jdbcTemplate.query(sql, params, new PembayaranRowMapper());

		return list.get(0);
	}

	@Override
	public List<DetailPembayaran> getDetailPembayaran(String id) {
		String sql = "SELECT a.bill_data_id as bill_id, b.BILL_DATA_ID, b.BILL_CODE, b.BILL_AMOUNT, c.BILL_CODE, c.BILL_NAME FROM ALLBANK.dbo.bill_data a, ALLBANK.dbo.bill_details_data b, ALLBANK.dbo.bill_detail_status c WHERE b.BILL_CODE=c.BILL_CODE AND a.BILL_DATA_ID=b.BILL_DATA_ID AND c.BILL_CODE <> '0' AND a.bill_data_id = :idBill UNION SELECT a.bill_data_id as bill_id, b.BILL_DATA_ID, b.BILL_CODE, b.BILL_AMOUNT, c.BILL_CODE, c.BILL_NAME FROM ALLBANK.dbo.pindahan_bill_data a, ALLBANK.dbo.bill_details_data b , ALLBANK.dbo.bill_detail_status c WHERE b.BILL_CODE=c.BILL_CODE AND a.BILL_DATA_ID=b.BILL_DATA_ID AND c.BILL_CODE <> '0' AND a.bill_data_id = :idBill";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idBill", id);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		List<DetailPembayaran> list = jdbcTemplate.query(sql, params, new DetailPembayaranRowMapper());

		return list;
	}

	@Override
	public Integer getSemesterKe(String nrp) {
		String sql = "select CAST(max(mhs_semesterke) AS int) AS semester from Mahasiswa_HistorisStatus where MHS_MA_Nrp=:nrp";
		Map<String, Object> params = new HashMap<>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = getNamedParameterJdbcTemplate();
		Integer semester = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			semester = (int) rs.get("semester");
		} catch (Exception e) {

		}
		return semester;
	}

	@Override
	public List<MahasiswaFoto> getListMahasiswa(String id, String nrp, String query, String kodeProdi, Integer angkatan,
			int page, int perPage, String order) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM(SELECT q1.*, Row_number() OVER (ORDER BY q1.");

		if (order != null) {
			sql.append(order);
		} else {
			sql.append("id");
		}
		if (query != null)
			query = "%" + query + "%";

		sql.append(
				")  AS RowNum FROM ( SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, substring(ma_nrp, 1, 2)+substring(ma_nrp, 5, 3) AS kode_prodi, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.ma_idsex AS jk, p.PE_NamaLengkap AS dosen_wali, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.MA_NomorHP AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, ps.PS_Nama_Baru AS prodi, MA_IPK AS ipk, MA_SksLulus AS sks_lulus, YEAR(MA_TglMasukITS) AS tahun_masuk, MA_Photo AS foto FROM mahasiswa m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id LEFT JOIN Pegawai p ON p.PE_Nip = m.MA_PE_NipWali JOIN ProgramStudi ps ON (substring(ma_nrp, 1, 2)+substring(ma_nrp, 5, 3)) = ps.PS_Kode_Prodi_Lama WHERE (m.ma_nrp = :id OR :id IS NULL) AND (m.ma_nrp_baru = :nrp OR :nrp IS NULL) AND (m.ma_nama LIKE :query OR :query IS NULL) AND (substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = :kode_prodi OR :kode_prodi IS NULL)  AND (substring(m.ma_nrp, 3, 2) = :angkatan OR :angkatan IS NULL)  ) AS q1) AS q2 WHERE rownum BETWEEN ( :page * :per_page ) + 1 AND ( :page + 1 ) * :per_page ");

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("nrp", nrp);
		params.put("query", query);
		params.put("kode_prodi", kodeProdi);
		params.put("angkatan", angkatan);
		params.put("page", page);
		params.put("per_page", perPage);

		List<MahasiswaFoto> listMhs = jdbcTemplate.query(sql.toString(), params, new MahasiswaFotoRowMapper());

		return listMhs;
	}

	@Override
	public List<MahasiswaFoto> getListMahasiswaNonAktif(String id, String nrp, String query, String kodeProdi,
			Integer angkatan, int page, int perPage, String order) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * FROM(SELECT q1.*, Row_number() OVER (ORDER BY q1.");

		if (order != null) {
			sql.append(order);
		} else {
			sql.append("id");
		}

		if (query != null)
			query = "%" + query + "%";
		sql.append(
				") AS RowNum FROM ( SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.ma_idsex AS jk, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.MA_NomorHP AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, m.MA_Photo AS foto FROM mahasiswaout m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id WHERE (m.ma_nrp = :id OR :id IS NULL) AND (m.ma_nrp_baru = :nrp OR :nrp IS NULL) AND (m.ma_nama LIKE :query OR :query IS NULL) AND (substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = :kode_prodi OR :kode_prodi IS NULL)  AND (substring(m.ma_nrp, 3, 2) = :angkatan OR :angkatan IS NULL)  ) AS q1) AS q2 WHERE rownum BETWEEN ( :page * :per_page ) + 1 AND ( :page + 1 ) * :per_page ");

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("nrp", nrp);
		params.put("query", query);
		params.put("kode_prodi", kodeProdi);
		params.put("angkatan", angkatan);
		params.put("page", page);
		params.put("per_page", perPage);

		List<MahasiswaFoto> listMhs = jdbcTemplate.query(sql.toString(), params, new MahasiswaFotoRowMapper());

		return listMhs;
	}

	@Override
	public String getNrpBaru(String nrp) {
		String sql = "SELECT MA_NRP_Baru FROM mahasiswa WHERE MA_Nrp=:nrp OR MA_Nrp_Baru = :nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String nrpBaru = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			nrpBaru = (String) rs.get("MA_NRP_Baru");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nrpBaru;
	}

	@Override
	public String getNrpLama(String nrp) {
		String sql = "SELECT MA_NRP FROM mahasiswa WHERE MA_Nrp=:nrp OR MA_Nrp_Baru = :nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String nrpLama = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			nrpLama = (String) rs.get("MA_NRP");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nrpLama;
	}

	@Override
	public String getStatusMahasiswa(String nrp) {
		String sql = "SELECT TOP 1 v.StatusMhs AS status FROM Mahasiswa_HistorisStatus m JOIN val_StatusMahasiswa v ON m.MHS_IDStatusMhs = v.ID WHERE MHS_MA_Nrp = :nrp ORDER BY MHS_ThnAjaran DESC, MHS_IDSemester DESC ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		String nrpBaru = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			nrpBaru = (String) rs.get("status");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return nrpBaru;
	}

	@Override
	public Integer getTotalSksPersiapan(String nrp, String tahap, String bahasa) {
		String sql = "SELECT COALESCE(SUM(sks), SUM(sks), 0) AS sks FROM (" + getSqlstr(nrp, tahap, bahasa)
				+ ") as tblTranskrip WHERE nrp=:nrp AND nilai_huruf<>'_' AND nilai_akhir IS NOT NULL AND Tahap = :tahap";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahap", tahap);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer nilai = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			nilai = (Integer) rs.get("sks");
		} catch (Exception e) {
			return 0;
		}
		return nilai;
	}

	@Override
	public Double getNilaiPersiapan(String nrp, String tahap, String bahasa) {
		String sql = "SELECT COALESCE(SUM(sks * nilai_angka), SUM(sks * nilai_angka), 0.0) AS nilai FROM ("
				+ getSqlstr(nrp, tahap, bahasa)
				+ ") as tblTranskrip WHERE nrp=:nrp AND nilai_huruf<>'_' AND nilai_akhir IS NOT NULL AND Tahap = :tahap";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tahap", tahap);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Double nilai = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			nilai = (Double) rs.get("nilai");
		} catch (Exception e) {
		}
		return nilai;
	}

	public String getSqlstr(String nrp, String tahap, String bahasa) {
		String sql = "SELECT distinct dbTranskrip.MK_ThnKurikulum AS tahun_kurikulum, CASE '" + bahasa
				+ "' WHEN 'en' THEN MK_Mata_KuliahInggris ELSE MK_Mata_Kuliah END AS mk, MK_KreditKuliah AS sks, Singkatan, KU_NilaiHuruf AS nilai_huruf, NiAkhir AS nilai_akhir, KU_MA_NRP AS nrp, Tahap, NilaiAngka AS nilai_angka, "
				+ orderFields + " " + tampilFields + " FROM " + dbtranskrip + " dbTranskrip	" + leftJoin
				+ " WHERE KU_MA_NRP='" + nrp + "' AND KU_NilaiHuruf<>'_' AND NiAkhir IS NOT NULL AND Tahap = '" + tahap
				+ "'";
		return sql;
	}

	@Override
	public List<DaftarNilai> getDaftarNilaiPersiapan(String nrp, String tahap, String bahasa) {
		StringBuilder sql = new StringBuilder();
		sql.append(getSqlstr(nrp, tahap, bahasa));
		sql.append(" ORDER BY KR_Semester, dbTranskrip.MK_ID, KU_KE_Tahun, KU_KE_IDSemester");
		Map<String, Object> params = new HashMap<String, Object>();
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		List<DaftarNilai> daftar = jdbcTemplate.query(sql.toString(), params, new DaftarNilaiRowMapper());
		return daftar;
	}

	@Override
	public Transkrip getTranskrip(String nrp) {
//		String key="mahasiswa_transkrip_"+nrp;
//		//ListOperations<String,Mahasiswa> operations= this.getRedisTemplate().opsForList();
		
//		if(this.getRedisTemplate().hasKey(key)==false)
//		{
			String sql = "SELECT MA_NRP AS nrp, MW_MA_JUDULTA + COALESCE(MW_MA_JUDULTA2, MW_MA_JUDULTA2, '') AS judulTA, MW_MA_JUDULTA3 + COALESCE(MW_MA_JUDULTA4, MW_MA_JUDULTA4, '') AS judulTAInggris, MW_MA_PEMBIMBING1 AS pembimbing1, MW_MA_PEMBIMBING2 AS pembimbing2, MW_MA_PEMBIMBING3 AS pembimbing3 from mahasiswa m left join yudisium_kelengkapan yk on m.ma_nrp = yk.mw_ma_nrp where MA_NRP = :nrp ";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nrp", nrp);
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
			List<Transkrip> daftar = jdbcTemplate.query(sql, params, new TranskripRowMapper());
//			if (daftar.size() > 0) {
//				HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//				
//				Map<String,String> transkripValue=new HashMap<>();
//				transkripValue.put("judul_ta", daftar.get(0).getJudulTA());
//				transkripValue.put("pembimbing_1", daftar.get(0).getPembimbing1());
//				transkripValue.put("pembimbing_2", daftar.get(0).getPembimbing2());
//				transkripValue.put("ipk", String.valueOf(daftar.get(0).getIpk()));
//				transkripValue.put("nrp", daftar.get(0).getNrp());
//				transkripValue.put("sks_lulus",String.valueOf(daftar.get(0).getSksLulus()));
//				operationHash.putAll(key, transkripValue);
//				this.getRedisTemplate().expire(key, 3600, TimeUnit.SECONDS);
//				
//				return daftar.get(0);
//			} else {
//				return null;
//			}
//		}
//		else
//		{
//			HashOperations<String,String,String> operationHash=this.getRedisTemplate().opsForHash();
//			Map<String,String> transkripRedis=operationHash.entries(key);
//			Transkrip transkrip=new Transkrip();
//			transkrip.setJudulTA(transkripRedis.get("judul_ta"));
//			transkrip.setPembimbing1(transkripRedis.get("pembimbing1"));
//			transkrip.setPembimbing2(transkripRedis.get("pembimbing2"));
//			transkrip.setIpk(Double.valueOf(transkripRedis.get("ipk")));
//			transkrip.setNrp(transkripRedis.get("nrp"));
//			transkrip.setSksLulus(Integer.valueOf(transkripRedis.get("nrp")));
//			
//			return transkrip;
			if (daftar.size() > 0) {
				return daftar.get(0);
			} else {
				return null;
			}
		
	}

	@Override
	public Integer getSksTempuh(String nrp) {
		String sql = "select CAST(MA_SKSTempuh AS int) AS sks from mahasiswa where MA_NRP=:nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer sks = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			sks = (Integer) rs.get("sks");
		} catch (Exception e) {
		}
		return sks;
	}

	@Override
	public Integer getSksLulus(String nrp) {
		String sql = "select CAST(MA_SKSLulus AS int) AS sks from mahasiswa where MA_NRP=:nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer sks = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			sks = (Integer) rs.get("sks");
		} catch (Exception e) {
		}
		return sks;
	}

	@Override
	public Integer checkVerified(String nrp) {
		String sql = "SELECT CAST(MA_isVerified_Biodata AS int) AS verify FROM mahasiswa WHERE MA_NRP=:nrp";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		Integer verify = null;
		try {
			Map<String, Object> rs = jdbcTemplate.queryForMap(sql, params);
			verify = (Integer) rs.get("verify");
		} catch (Exception e) {
		}
		return verify;
	}

	@Override
	public WaliMahasiswa getWaliMahasiswa(String id) {
		String sql = "SELECT m.MA_Nrp AS id, m.MA_NRP_Baru AS nrp, MA_Nama AS nama, w.nama AS nama_ayah, w2.nama AS nama_ibu, w3.nama AS nama_wali, w.email AS email_ayah, w2.email AS email_ibu, w3.email AS email_wali, w.nik AS nik_ayah, w2.nik AS nik_ibu, w3.nik AS nik_wali, w.no_hp AS telp_ayah, w2.no_hp AS telp_ibu, w3.no_hp AS telp_wali, w.tempat_lahir + ', ' + CONVERT(VARCHAR, w.tanggal_lahir, 106) AS ttl_ayah, w2.tempat_lahir + ', ' + CONVERT(VARCHAR, w2.tanggal_lahir, 106) AS ttl_ibu, w3.tempat_lahir + ', ' + CONVERT(VARCHAR, w3.tanggal_lahir, 106) AS ttl_wali FROM Mahasiswa m LEFT JOIN Mahasiswa_Wali mw ON m.MA_Nrp = mw.ma_nrp AND mw.id_jenis_wali = 1 LEFT JOIN Wali w ON mw.id_ortuwali = w.id LEFT JOIN Mahasiswa_Wali mw2 ON m.MA_Nrp = mw2.ma_nrp AND mw2.id_jenis_wali = 2 LEFT JOIN Wali w2 ON mw2.id_ortuwali = w2.id LEFT JOIN Mahasiswa_Wali mw3 On m.MA_Nrp = mw3.ma_nrp AND mw3.id_jenis_wali = 3 LEFT JOIN Wali w3 ON mw3.id_ortuwali = w3.id WHERE m.ma_nrp = :nrp ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", id);
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
		List<WaliMahasiswa> wali = jdbcTemplate.query(sql, params, new WaliMahasiswaRowMapper());
		if (wali.size() > 0) {
			return wali.get(0);
		} else {
			return null;
		}
	}
}
