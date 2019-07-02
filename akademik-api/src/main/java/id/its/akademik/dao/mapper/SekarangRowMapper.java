package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Sekarang;

public class SekarangRowMapper implements RowMapper<Sekarang> {

	@Override
	public Sekarang mapRow(ResultSet rs, int rowNum) throws SQLException {

		Sekarang s = new Sekarang();

		s.setSemester(rs.getString("semester"));
		s.setTahun(rs.getString("tahun"));
		s.setTahunAjaran(rs.getString("tahun_ajaran"));
		s.setTahunKurikulum(rs.getString("tahun_kurikulum"));
		return s;
	}

}
