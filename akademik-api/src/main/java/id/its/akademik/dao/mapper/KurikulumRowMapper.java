package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Kurikulum;

public class KurikulumRowMapper implements RowMapper<Kurikulum> {

	@Override
	public Kurikulum mapRow(ResultSet rs, int rowNum) throws SQLException {
		Kurikulum k = new Kurikulum();
		try {
			k.setProdi(rs.getString("prodi"));
		} catch (SQLException e) {
		}
		try {
			k.setTahunKurikulum(rs.getInt("tahun"));
		} catch (SQLException e) {
		}
		try {
			k.setSemesterKurikulum(rs.getInt("semester"));
		} catch (SQLException e) {
		}
		try {
			k.setSks(rs.getInt("sks"));
		} catch (SQLException e) {
		}
		return k;
	}

}
