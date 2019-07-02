package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.DetailPembayaran;

public class DetailPembayaranRowMapper implements RowMapper<DetailPembayaran> {

	@Override
	public DetailPembayaran mapRow(ResultSet rs, int rowNum) throws SQLException {

		DetailPembayaran d = new DetailPembayaran();

		d.setId(rs.getString("bill_id"));
		d.setIdData(rs.getString("bill_data_id"));
		d.setKode(rs.getString("bill_code"));
		d.setJumlah(rs.getString("bill_amount"));
		d.setNama(rs.getString("bill_name"));

		return d;
	}

}
