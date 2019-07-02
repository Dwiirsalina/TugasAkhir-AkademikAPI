package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Foto;

public class FotoMahasiswaRowMapper implements RowMapper<Foto> {

	@Override
	public Foto mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Foto fm = new Foto();
		
		fm.setId(rs.getString("id"));
		fm.setNrp(rs.getString("nrp"));
		fm.setNama(rs.getString("nama"));
		
		try {
			fm.setFoto(rs.getBytes("foto"));
		} catch (SQLException e) {
			
		}
		
		return fm;
	}

}
