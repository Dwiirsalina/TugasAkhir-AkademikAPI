package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.WaliMahasiswa;

public class WaliMahasiswaRowMapper implements RowMapper<WaliMahasiswa> {

	@Override
	public WaliMahasiswa mapRow(ResultSet rs, int rowNum) throws SQLException {

		WaliMahasiswa w = new WaliMahasiswa();

		try {
			w.setId(rs.getString("id"));
		} catch (SQLException e) {

		}
		try {
			w.setNrp(rs.getString("nrp"));
		} catch (SQLException e) {

		}

		try {
			w.setNama(rs.getString("nama"));
		} catch (SQLException e) {

		}
		try {
			w.setNamaAyah(rs.getString("nama_ayah"));
		} catch (SQLException e) {

		}

		try {
			w.setNamaIbu(rs.getString("nama_ibu"));
		} catch (SQLException e) {

		}

		try {
			w.setNamaWali(rs.getString("nama_wali"));
		} catch (SQLException e) {

		}

		try {
			w.setEmailAyah(rs.getString("email_ayah"));
		} catch (SQLException e) {

		}

		try {
			w.setEmailIbu(rs.getString("email_ibu"));
		} catch (SQLException e) {

		}

		try {
			w.setEmailWali(rs.getString("email_wali"));
		} catch (SQLException e) {

		}

		try {
			w.setNikAyah(rs.getString("nik_ayah"));
		} catch (SQLException e) {

		}

		try {
			w.setNikIbu(rs.getString("nik_ibu"));
		} catch (SQLException e) {

		}

		try {
			w.setNikWali(rs.getString("nik_wali"));
		} catch (SQLException e) {

		}

		try {
			w.setTelpAyah(rs.getString("telp_ayah"));
		} catch (SQLException e) {

		}

		try {
			w.setTelpIbu(rs.getString("telp_ibu"));
		} catch (SQLException e) {

		}

		try {
			w.setTelpWali(rs.getString("telp_wali"));
		} catch (SQLException e) {

		}

		try {
			w.setTtlAyah(rs.getString("ttl_ayah"));
		} catch (SQLException e) {

		}

		try {
			w.setTtlIbu(rs.getString("ttl_ibu"));
		} catch (SQLException e) {

		}

		try {
			w.setTtlWali(rs.getString("ttl_wali"));
		} catch (SQLException e) {

		}
		return w;
	}
}
