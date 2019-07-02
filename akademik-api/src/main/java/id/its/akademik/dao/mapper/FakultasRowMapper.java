package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import id.its.akademik.domain.Fakultas;

import org.springframework.jdbc.core.RowMapper;

public class FakultasRowMapper implements RowMapper<Fakultas> {

	@Override
	public Fakultas mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Fakultas f = new Fakultas();
		
		f.setId(rs.getString("id"));
		f.setNama(rs.getString("nama"));
		f.setNamaInggris(rs.getString("nama_inggris"));
		f.setSingkatan(rs.getString("singkatan"));
		
		return f;
	}

}
