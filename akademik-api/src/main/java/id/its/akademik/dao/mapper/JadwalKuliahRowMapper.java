package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.JadwalKuliah;

public class JadwalKuliahRowMapper implements RowMapper<JadwalKuliah> {

	@Override
	public JadwalKuliah mapRow(ResultSet rs, int rowNum) throws SQLException {

		JadwalKuliah jk = new JadwalKuliah();

		try {
			jk.setTahun(rs.getInt("tahun"));
		} catch (SQLException e) {

		}

		try {
			jk.setSemester(rs.getInt("semester"));
		} catch (SQLException e) {

		}

		try {
			jk.setIdMk(rs.getString("kode_mk"));
		} catch (SQLException e) {

		}

		try {
			jk.setNamaMk(rs.getString("nama_mk"));
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
			jk.setNamaDosen(rs.getString("nama_dosen"));
		} catch (SQLException e) {

		}

		return jk;
	}

}
