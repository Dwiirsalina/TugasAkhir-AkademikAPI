package id.its.akademik.dao.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.JadwalDao;
import id.its.akademik.dao.mapper.JadwalKelasRowMapper;
import id.its.akademik.domain.JadwalKelas;

public class JdbcJadwalDao extends AbstractDao implements JadwalDao {

	@Override
	public List<JadwalKelas> getJadwalKelas(String kodeProdi, Integer tahun, Integer semester) {

		String sql = "SELECT a.tahun, a.semester, a.departemen, a.kodeMk as kode_mk, a.kelas, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai FROM(SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,0,6) AS departemen, SUBSTRING(KODEKELAS,7,6) AS kodeMk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot FROM SIMARU.dbo.ALOKASI A WITH (NOLOCK) INNER join SIMARU.dbo.HARI H WITH (NOLOCK) on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL WITH (NOLOCK) ON SL.KODESLOT = a.KODESLOT WHERE a.tahun = :tahun AND a.semester = :semester GROUP BY a.tahun, a.semester, a.departemen, a.kodeMk,a.kelas, a.hari, a.ruangan";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		// params.put("kode_prodi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);

		List<JadwalKelas> listJadwalKelas = jdbcTemplate.query(sql, params, new JadwalKelasRowMapper());

		return listJadwalKelas;
	}

	@Override
	public List<JadwalKelas> getJadwalKelasByIdMk(String kodeProdi, Integer tahun, Integer semester, String idMk) {
		String sql = "SELECT a.tahun, a.semester, a.departemen, a.kodeMk as kode_mk, a.kelas, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai FROM(SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,0,6) AS departemen, SUBSTRING(KODEKELAS,7,6) AS kodeMk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot FROM SIMARU.dbo.ALOKASI A INNER join SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT WHERE a.tahun = :tahun AND a.semester = :semester AND a.departemen = :kode_prodi AND a.kodeMk = :kode_mk GROUP BY a.tahun, a.semester, a.departemen, a.kodeMk,a.kelas, a.hari, a.ruangan";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kode_prodi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kode_mk", idMk);

		List<JadwalKelas> listJadwalKelas = jdbcTemplate.query(sql, params, new JadwalKelasRowMapper());

		return listJadwalKelas;
	}

	@Override
	public List<JadwalKelas> getJadwalKelasSpesifik(String kodeProdi, Integer tahun, Integer semester, String idMk,
			String idKelas) {
		String sql = "SELECT a.tahun, a.semester, a.departemen, a.kodeMk as kode_mk, a.kelas, a.hari, a.ruangan, MIN(JAMMULAI) AS jam_mulai, MAX(JAMSELESAI) AS jam_usai FROM(SELECT SUBSTRING(PERIODESEM,0,5) AS tahun, SUBSTRING(PERIODESEM,5,6) AS semester, SUBSTRING(KODEKELAS,0,6) AS departemen, SUBSTRING(KODEKELAS,7,6) AS kodeMk, CASE WHEN ASCII(SUBSTRING(KODEKELAS,14,15)) > 150 THEN CONVERT(VARCHAR(2), ASCII(SUBSTRING(KODEKELAS,14,15)) - 150) ELSE SUBSTRING(KODEKELAS,14,15) END AS kelas, H.NAMAHARI AS hari, KODERUANG AS ruangan, a.kodeslot AS kodeslot FROM SIMARU.dbo.ALOKASI A INNER join SIMARU.dbo.HARI H on a.kodehari = H.kodehari) a INNER JOIN SIMARU.dbo.SLOTKULIAH SL ON SL.KODESLOT = a.KODESLOT WHERE a.tahun = :tahun AND a.semester = :semester AND a.departemen = :kode_prodi AND a.kodeMk = :kode_mk AND a.kelas = :kode_kelas GROUP BY a.tahun, a.semester, a.departemen, a.kodeMk,a.kelas, a.hari, a.ruangan";

		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("kode_prodi", kodeProdi);
		params.put("tahun", tahun);
		params.put("semester", semester);
		params.put("kode_mk", idMk);
		params.put("kode_kelas", idKelas);

		List<JadwalKelas> listJadwalKelas = jdbcTemplate.query(sql, params, new JadwalKelasRowMapper());

		return listJadwalKelas;
	}
}
