package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.FRS;

public class FRSRowMapper implements RowMapper<FRS> {

	@Override
	public FRS mapRow(ResultSet rs, int rowNum) throws SQLException {

		FRS f = new FRS();

		try {
			f.setTahun(rs.getInt("tahun"));
		} catch (SQLException e) {

		}

		try {
			f.setSemester(rs.getInt("semester"));
		} catch (SQLException e) {

		}

		try {
			f.setNrp(rs.getString("nrp"));
		} catch (SQLException e) {

		}

		try {
			f.setNrpBaru(rs.getString("nrp_baru"));
		} catch (SQLException e) {

		}
		try {
			f.setNama(rs.getString("nama"));
		} catch (SQLException e) {

		}

		try {
			f.setDosenWali(rs.getString("dosen_wali"));
		} catch (SQLException e) {

		}

		try {
			f.setStatusKeaktifan(rs.getString("status_aktif"));
		} catch (SQLException e) {

		}

		try {
			f.setDisetujui(rs.getBoolean("disetujui"));
		} catch (SQLException e) {

		}

		try {
			f.setIpsLalu(rs.getDouble("ips_lalu"));
		} catch (SQLException e) {

		}

		try {
			f.setIpk(rs.getDouble("ipk"));
		} catch (SQLException e) {

		}

		try {
			f.setSksTempuh(rs.getInt("sks_tempuh"));
		} catch (SQLException e) {

		}

		try {
			f.setSksLulus(rs.getInt("sks_lulus"));
		} catch (SQLException e) {

		}

		try {
			f.setSksAmbil(rs.getInt("sks_ambil"));
		} catch (SQLException e) {

		}

		return f;
	}

}
