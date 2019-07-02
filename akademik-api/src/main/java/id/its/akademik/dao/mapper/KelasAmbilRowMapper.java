package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.KelasAmbil;

public class KelasAmbilRowMapper implements RowMapper<KelasAmbil> {

	@Override
	public KelasAmbil mapRow(ResultSet rs, int rowNum) throws SQLException {

		KelasAmbil ka = new KelasAmbil();

		try {
			ka.setId(rs.getString("id_mk"));
		} catch (SQLException e) {

		}

		try {
			ka.setKode(rs.getString("kode_mk"));
		} catch (SQLException e) {

		}

		try {
			ka.setMataKuliah(rs.getString("mk"));
		} catch (SQLException e) {

		}

		try {
			ka.setKodeProdi(rs.getString("kode_jurusan"));
		} catch (SQLException e) {

		}
		try {
			ka.setSks(rs.getInt("sks"));
		} catch (SQLException e) {

		}

		try {
			ka.setKelas(rs.getString("kelas"));
		} catch (SQLException e) {

		}

		try {
			ka.setSemester(rs.getInt("semester"));
		} catch (SQLException e) {

		}

		try {
			ka.setDosen(rs.getString("dosen"));
		} catch (SQLException e) {

		}

		try {
			ka.setNilaiHuruf(rs.getString("nilai_huruf"));
		} catch (SQLException e) {

		}

		return ka;
	}

}
