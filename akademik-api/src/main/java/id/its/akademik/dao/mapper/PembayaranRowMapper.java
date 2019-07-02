package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Pembayaran;

public class PembayaranRowMapper implements RowMapper<Pembayaran> {

	@Override
	public Pembayaran mapRow(ResultSet rs, int rowNum) throws SQLException {

		Pembayaran p = new Pembayaran();
		p.setId(rs.getString("bill_data_id"));
		p.setSemesterDikti(rs.getString("semester_dikti"));
		p.setFlag(rs.getBoolean("bill_flag"));
		p.setTanggal(rs.getDate("paid_date"));
		p.setJumlah(rs.getDouble("bill_amount"));
		p.setKeterangan(rs.getString("keterangan"));

		return p;
	}

}
