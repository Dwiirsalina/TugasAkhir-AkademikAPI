package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Transkrip;

public class TranskripRowMapper implements RowMapper<Transkrip> {

	@Override
	public Transkrip mapRow(ResultSet rs, int rowNum) throws SQLException {

		Transkrip t = new Transkrip();
		try {
			t.setNrp(rs.getString("nrp"));
		} catch (Exception e) {
		}
		try {
			t.setJudulTA(rs.getString("judulTA"));
		} catch (Exception e) {
		}
		try {
			t.setJudulTaInggris(rs.getString("judulTAInggris"));
		} catch (Exception e) {
		}
		try {
			t.setPembimbing1(rs.getString("pembimbing1"));
		} catch (Exception e) {
		}
		try {
			t.setPembimbing2(rs.getString("pembimbing2"));
		} catch (Exception e) {
		}
		try {
			t.setPembimbing3(rs.getString("pembimbing3"));
		} catch (Exception e) {
		}

		return t;
	}

}
