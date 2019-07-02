package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.WaliBiodata;

public class WaliRowMapper implements RowMapper<WaliBiodata> {

	@Override
	public WaliBiodata mapRow(ResultSet rs, int rowNum) throws SQLException {

		WaliBiodata w = new WaliBiodata();

		try {
			w.setId(rs.getString("id"));
		} catch (SQLException e) {

		}

		try {
			w.setNama(rs.getString("nama"));
		} catch (SQLException e) {

		}
		try {
			w.setEmail(rs.getString("email"));
		} catch (SQLException e) {

		}

		try {
			w.setNik(rs.getString("nik"));
		} catch (SQLException e) {

		}

		try {
			w.setTempatLahir(rs.getString("tempat_lahir"));
		} catch (SQLException e) {

		}
		try {
			w.setTanggalLahir(rs.getDate("tanggal_lahir"));
		} catch (SQLException e) {

		}
		try {
			w.setTtl(rs.getString("ttl"));
		} catch (SQLException e) {

		}

		try {
			w.setJenisKelamin(rs.getString("jk"));
		} catch (SQLException e) {

		}

		try {
			w.setNoHP(rs.getString("no_hp"));
		} catch (SQLException e) {

		}
		return w;
	}

}
