package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.NilaiKuliah;

public class NilaiKuliahRowMapper implements RowMapper<NilaiKuliah> {

	@Override
	public NilaiKuliah mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		NilaiKuliah nk = new NilaiKuliah();
		
		try {
			nk.setTahun(rs.getInt("tahun"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setSemester(rs.getInt("semester"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setKode(rs.getString("kode"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setMataKuliah(rs.getString("mk"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setSks(rs.getInt("sks"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setNilaiAngka(rs.getDouble("nilai_angka"));
		} catch(SQLException e) {
			
		}
		
		try {
			nk.setNilaiHuruf(rs.getString("nilai_huruf"));
		} catch(SQLException e) {
			
		}
		
		return nk;
	}

}
