package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MKWajibAmbil;

public class MKWajibAmbilRowMapper implements RowMapper<MKWajibAmbil> {

	@Override
	public MKWajibAmbil mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MKWajibAmbil mk = new MKWajibAmbil();
		
		mk.setSemester(rs.getInt("semester"));
		mk.setId(rs.getString("id_mk"));
		mk.setNama(rs.getString("mk"));
		mk.setJenis(rs.getString("jenis"));
		
		return mk;
	}

}
