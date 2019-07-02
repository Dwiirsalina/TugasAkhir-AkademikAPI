package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.ProdiAjar;

public class ProdiAjarRowMapper implements RowMapper<ProdiAjar> {

	@Override
	public ProdiAjar mapRow(ResultSet rs, int rowNum) throws SQLException {

		ProdiAjar pa = new ProdiAjar();

		pa.setKodeJur(rs.getString("kodejur"));
		pa.setKodeProdi(rs.getString("kodeprodi"));
		pa.setProdiBaru(rs.getString("prodi_baru"));
		pa.setFakultasBaru(rs.getString("fakultas_baru"));
		pa.setFakultasId(rs.getString("fakultas_id"));

		return pa;
	}

}
