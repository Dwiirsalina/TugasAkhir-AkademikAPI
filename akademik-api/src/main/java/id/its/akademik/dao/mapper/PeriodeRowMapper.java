package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Periode;

public class PeriodeRowMapper implements RowMapper<Periode> {

	@Override
	public Periode mapRow(ResultSet rs, int rowNum) throws SQLException {

		Periode p = new Periode();

		try {
			p.setProdi(rs.getString("kode_dept"));
		} catch (SQLException e) {

		}

		try {
			p.setKeterangan(rs.getString("keterangan"));
		} catch (SQLException e) {

		}
		p.setTahun(rs.getInt("tahun"));
		p.setSemester(rs.getInt("semester"));

		return p;
	}

}
