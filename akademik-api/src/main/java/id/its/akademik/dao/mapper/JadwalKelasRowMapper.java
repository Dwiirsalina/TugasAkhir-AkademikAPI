package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.JadwalKelas;

public class JadwalKelasRowMapper implements RowMapper<JadwalKelas> {

	@Override
	public JadwalKelas mapRow(ResultSet rs, int rowNum) throws SQLException {

		JadwalKelas jk = new JadwalKelas();

		try {
			jk.setKodeMk(rs.getString("kode_mk"));
		} catch (SQLException e) {

		}

		try {
			jk.setKelas(rs.getString("kelas"));
		} catch (SQLException e) {

		}

		try {
			jk.setHari(rs.getString("hari"));
		} catch (SQLException e) {

		}

		try {
			jk.setRuangan(rs.getString("ruangan"));
		} catch (SQLException e) {

		}

		try {
			jk.setJamMulai(rs.getString("jam_mulai"));
		} catch (SQLException e) {

		}

		try {
			jk.setJamUsai(rs.getString("jam_usai"));
		} catch (SQLException e) {

		}
		try {
			jk.setIdProdi(rs.getString("departemen"));
		} catch (Exception e) {
		}

		return jk;
	}

}
