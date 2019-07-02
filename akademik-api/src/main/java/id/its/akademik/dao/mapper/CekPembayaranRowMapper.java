package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.CekPembayaran;

public class CekPembayaranRowMapper implements RowMapper<CekPembayaran> {

	@Override
	public CekPembayaran mapRow(ResultSet rs, int rowNum) throws SQLException {

		CekPembayaran p = new CekPembayaran();

		p.setId(rs.getString("bill_data_id"));
		p.setKey1(rs.getString("bill_key1"));
		p.setKey2(rs.getString("bill_key2"));
		p.setSemesterDikti(rs.getString("semester_dikti"));
		p.setFlag(rs.getString("bill_flag"));
		p.setJumlah(rs.getDouble("bill_amount"));

		return p;
	}

}
