package id.its.akademik.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.KelembagaanDao;
import id.its.akademik.dao.mapper.DepartemenRowMapper;
import id.its.akademik.dao.mapper.FakultasRowMapper;
import id.its.akademik.dao.mapper.KalenderRowMapper;
import id.its.akademik.dao.mapper.ProdiAjarRowMapper;
import id.its.akademik.dao.mapper.ProgramStudiRowMapper;
import id.its.akademik.domain.Departemen;
import id.its.akademik.domain.Fakultas;
import id.its.akademik.domain.Kalender;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;

public class JdbcKelembagaanDao extends AbstractDao implements KelembagaanDao {

	@Override
	public List<Fakultas> getFakultas(String id, String query) {
		List<Fakultas> listFakultas = null;

		String sql = "SELECT FA_ID AS id, FA_Nama_Baru AS nama, FA_NamaInggris_Baru AS nama_inggris, FA_Singkatan_Baru AS singkatan FROM Fakultas WHERE ((FA_Nama_Baru LIKE CONCAT('%', :query ,'%') OR :query IS NULL) OR (FA_NamaInggris_Baru LIKE CONCAT('%', :query ,'%') OR :query IS NULL) OR (FA_Singkatan_Baru LIKE CONCAT('%', :query ,'%') OR :query IS NULL)) AND (FA_ID = :id_fakultas OR :id_fakultas IS NULL) ORDER BY FA_ID";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id_fakultas", id);
		params.put("query", query);

		listFakultas = jdbcTemplate.query(sql, params, new FakultasRowMapper());

		return listFakultas;
	}

	@Override
	public List<Departemen> getDepartemen(String id, String idFakultas, String query, int page, int perPage) {

		List<Departemen> listDept = null;

		String sql = "SELECT * FROM(SELECT q1.*, Row_number() OVER ( ORDER BY q1.id) AS RowNum FROM (SELECT JU_FA_ID+JU_ID AS id, JU_Nama AS nama, JU_NamaInggris AS nama_inggris FROM Jurusan WHERE ((JU_Nama LIKE CONCAT('%', :query ,'%') OR :query IS NULL) OR (JU_NamaInggris LIKE CONCAT('%', :query ,'%') OR :query IS NULL)) AND (JU_FA_ID = :id_fakultas OR :id_fakultas IS NULL) AND (JU_FA_ID+JU_ID = :id_departemen OR :id_departemen IS NULL) ) AS q1) AS q2 WHERE rownum BETWEEN ( :page * :per_page ) + 1 AND ( :page + 1 ) * :per_page ";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id_fakultas", idFakultas);
		params.put("id_departemen", id);
		params.put("query", query);
		params.put("page", page);
		params.put("per_page", perPage);

		listDept = jdbcTemplate.query(sql, params, new DepartemenRowMapper());

		return listDept;

	}

	@Override
	public List<ProgramStudi> getProgramStudi(String id, String idFakultas, String idDepartemen, String query, int page,
			int perPage) {
		List<ProgramStudi> listProdi = null;
//		String key="ProgramStudi";

		String sql = "SELECT * FROM(SELECT q1.*, Row_number() OVER ( ORDER BY q1.id) AS RowNum FROM (SELECT PS_FA_ID+PS_JU_ID+PS_ID as id, PS_Kode_Prodi as kode_prodi, PS_Nama_Baru as nama  FROM ProgramStudi WHERE (PS_Nama_Baru LIKE CONCAT('%', :query ,'%') OR :query IS NULL) AND (PS_FA_ID = :id_fakultas OR :id_fakultas IS NULL) AND (PS_FA_ID+PS_JU_ID = :id_departemen OR :id_departemen IS NULL) AND (PS_FA_ID+PS_JU_ID+PS_ID = :id_prodi OR :id_prodi IS NULL) ) AS q1) AS q2 WHERE rownum BETWEEN ( :page * :per_page ) + 1 AND ( :page + 1 ) * :per_page ";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id_fakultas", idFakultas);
		params.put("id_departemen", idDepartemen);
		params.put("id_prodi", id);
		params.put("query", query);
		params.put("page", page);
		params.put("per_page", perPage);

		listProdi = jdbcTemplate.query(sql, params, new ProgramStudiRowMapper());
		
		return listProdi;

	}

	@Override
	public List<ProdiAjar> getProdiAjar() {
		
		List<ProdiAjar> listProdi = null;
		String sql = "SELECT ProgramStudi.PS_Kode_Prodi_Lama kodejur, ProgramStudi.PS_Kode_Prodi kodeprodi, ProgramStudi.PS_Nama_Baru prodi_baru, Fakultas.FA_Singkatan_Baru fakultas_baru, ProgramStudi.PS_ID_Fakultas fakultas_id FROM ProgramStudi INNER JOIN Fakultas ON ProgramStudi.PS_ID_Fakultas = Fakultas.FA_ID WHERE PS_Nama_Baru IS NOT NULL ORDER BY ProgramStudi.PS_Kode_Prodi";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();

		listProdi = jdbcTemplate.query(sql, params, new ProdiAjarRowMapper());
		
		return listProdi;
	}

	@Override
	public List<Kalender> getKalenderAkademik(String tahun, String semester) {
		String sql = "SELECT * FROM(SELECT t.TglMulaiFRS, t.TglAkhirFRS, t.TglMulaiUbah, t.TglAkhirUbah, t.TglMulaiDrop, t.TglAkhirDrop, t.TglAkhirKuliah FROM FRS_BatasWaktuPengisian AS t WHERE t.IDSemester = :semester AND ThnAjaran= :tahun) AS SourceTable UNPIVOT(Value FOR Col IN (TglMulaiFRS, TglAkhirFRS, TglMulaiUbah, TglAkhirUbah, TglMulaiDrop, TglAkhirDrop, TglAkhirKuliah)) AS unpvt ORDER BY Value";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tahun", tahun);
		params.put("semester", semester);

		List<Kalender> listKalender = jdbcTemplate.query(sql, params, new KalenderRowMapper());

		return listKalender;

	}

}
