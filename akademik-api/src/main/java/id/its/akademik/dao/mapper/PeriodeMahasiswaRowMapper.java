package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.PeriodeMahasiswa;

public class PeriodeMahasiswaRowMapper implements RowMapper<PeriodeMahasiswa> {

	@Override
	public PeriodeMahasiswa mapRow(ResultSet rs, int rowNum) throws SQLException {

		PeriodeMahasiswa s = new PeriodeMahasiswa();
		s.setTahun(rs.getString("tahun"));
		s.setSemester(rs.getString("semester"));
		s.setKeterangan(rs.getString("keterangan"));
		return s;
	}

}
