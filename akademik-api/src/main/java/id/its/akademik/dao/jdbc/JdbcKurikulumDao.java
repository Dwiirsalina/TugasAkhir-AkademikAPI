package id.its.akademik.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.KurikulumDao;
import id.its.akademik.dao.mapper.KurikulumRowMapper;
import id.its.akademik.dao.mapper.MataKuliahRowMapper;
import id.its.akademik.dao.mapper.PekerjaanRowMapper;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.Pekerjaan;

public class JdbcKurikulumDao extends AbstractDao implements KurikulumDao {

	@Override
	public List<Kurikulum> getKurikulum(String prodi, Integer tahun) {
			
			String sql = "SELECT KR_KodeJurusan AS prodi, KR_MK_ThnKurikulum AS tahun, KR_Semester AS semester, SUM(MK_KreditKuliah) AS sks FROM Kurikulum JOIN MataKuliah ON MK_ID = KR_MK_ID AND MK_ThnKurikulum = KR_MK_ThnKurikulum WHERE KR_KodeJurusan = :prodi AND KR_MK_ThnKurikulum = :tahun GROUP BY KR_KodeJurusan, KR_MK_ThnKurikulum, KR_Semester";
	
			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();
	
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("prodi", prodi);
			params.put("tahun", tahun);
	
			List<Kurikulum> list = jdbcTemplate.query(sql, params, new KurikulumRowMapper());

			return list;
	}

	@Override
	public List<MataKuliah> getMatakuliahKurikulum(String prodi, Integer tahun, Integer semester, String bahasa) {

			String sql = "SELECT KR_KodeJurusan AS prodi, KR_MK_ThnKurikulum AS tahun, KR_Semester AS semester, MK_ID AS id_mk, dbo.MKID(MK_ID,MK_ThnKurikulum) AS kode_mk, CASE :bahasa WHEN 'en' THEN MK_Mata_KuliahInggris ELSE MK_Mata_Kuliah END AS mk, MK_Mata_KuliahInggris AS mk_inggris, MK_KreditKuliah AS sks, WajibPilihan AS jenis_mk FROM Kurikulum JOIN MataKuliah ON MK_ID = KR_MK_ID AND MK_ThnKurikulum = KR_MK_ThnKurikulum JOIN val_WajibPilihan ON KR_IDWajibPilihan = ID WHERE KR_KodeJurusan = :prodi AND KR_MK_ThnKurikulum = :tahun AND (KR_Semester = :semester OR :semester IS NULL) ORDER BY tahun DESC, semester ASC, MK_Mata_Kuliah";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("prodi", prodi);
			params.put("tahun", tahun);
			params.put("semester", semester);
			params.put("bahasa", bahasa);

			List<MataKuliah> listMk = jdbcTemplate.query(sql, params, new MataKuliahRowMapper());

			return listMk;

	}

	@Override
	public List<Kurikulum> getTahunKurikulum(String idProdi) {
		
			String sql = "SELECT DISTINCT KR_MK_ThnKurikulum AS tahun FROM Kurikulum WHERE KR_KodeJurusan = :id";

			NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", idProdi);

			List<Kurikulum> list = jdbcTemplate.query(sql, params, new KurikulumRowMapper());

			return list;
	}
}
