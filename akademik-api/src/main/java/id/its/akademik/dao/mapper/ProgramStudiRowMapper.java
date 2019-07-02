package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.ProgramStudi;

public class ProgramStudiRowMapper implements RowMapper<ProgramStudi> {

	@Override
	public ProgramStudi mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ProgramStudi ps = new ProgramStudi();
		
		ps.setId(rs.getString("id"));
		ps.setKode(rs.getString("kode_prodi"));
		ps.setNama(rs.getString("nama"));
		
		return ps;
	}

}
