package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.IPD;

public class IPDRowMapper implements RowMapper<IPD> {

	@Override
	public IPD mapRow(ResultSet rs, int rowNum) throws SQLException {
		IPD ipd = new IPD();

		ipd.setTahun(rs.getInt("tahun"));
		ipd.setSemester(rs.getInt("semester"));
		ipd.setKodeProdi(rs.getString("kodeProdi"));
		ipd.setRataRata(rs.getDouble("rata"));

		return ipd;
	}
}
