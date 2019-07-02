package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.WaktuFrs;

public class WaktuFRSRowMapper implements RowMapper<WaktuFrs> {

	@Override
	public WaktuFrs mapRow(ResultSet rs, int rowNum) throws SQLException {
		WaktuFrs frs = new WaktuFrs();
		frs.setTahun(rs.getInt("ThnAjaran"));
		frs.setSemester(rs.getInt("IDSemester"));
		frs.setMulaiAmbil(rs.getTimestamp("tglmulaifrs"));
		frs.setSelesaiAmbil(rs.getTimestamp("tglakhirfrs"));
		frs.setMulaiUbah(rs.getTimestamp("tglmulaiubah"));
		frs.setSelesaiUbah(rs.getTimestamp("tglakhirubah"));
		frs.setMulaiDrop(rs.getTimestamp("tglmulaidrop"));
		frs.setSelesaiDrop(rs.getTimestamp("tglakhirdrop"));
		frs.setSelesaiKuliah(rs.getTimestamp("tglakhirkuliah"));
		return frs;
	}

}
