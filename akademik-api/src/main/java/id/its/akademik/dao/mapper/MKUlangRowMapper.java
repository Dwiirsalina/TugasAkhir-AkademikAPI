package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MKUlang;

public class MKUlangRowMapper implements RowMapper<MKUlang> {

	@Override
	public MKUlang mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MKUlang mk = new MKUlang();
		
		mk.setSemester(rs.getInt("semester"));
		mk.setId(rs.getString("id_mk"));
		mk.setNama(rs.getString("mk"));
		mk.setSks(rs.getInt("sks"));
		mk.setNilaiHuruf(rs.getString("nilai_huruf"));
		mk.setTahunAmbil(rs.getInt("tahun_ambil"));
		mk.setSemesterAmbil(rs.getInt("semester_ambil"));
		
		return mk;
	}
	

}
