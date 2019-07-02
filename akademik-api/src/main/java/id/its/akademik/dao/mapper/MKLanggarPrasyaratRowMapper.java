package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MKLanggarPrasyarat;

public class MKLanggarPrasyaratRowMapper implements RowMapper<MKLanggarPrasyarat> {

	@Override
	public MKLanggarPrasyarat mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		MKLanggarPrasyarat mk = new MKLanggarPrasyarat();
		
		mk.setId(rs.getString("id_mk"));
		mk.setNama(rs.getString("mk"));
		mk.setIdPrasyarat(rs.getString("id_mk_prasyarat"));
		mk.setNamaPrasyarat(rs.getString("mk_prasyarat"));
		mk.setKeterangan(rs.getString("keterangan"));
		
		return mk;
	}

}
