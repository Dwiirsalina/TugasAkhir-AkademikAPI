package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import id.its.akademik.domain.Departemen;

import org.springframework.jdbc.core.RowMapper;

public class DepartemenRowMapper implements RowMapper<Departemen> {

	@Override
	public Departemen mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Departemen d = new Departemen();
		
		d.setId(rs.getString("id"));
		d.setNama(rs.getString("nama"));
		d.setNamaInggris(rs.getString("nama_inggris"));
		
		return d;
	}

}
