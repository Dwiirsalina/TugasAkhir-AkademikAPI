package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.KemajuanStudi;

public class KemajuanStudiRowMapper implements RowMapper<KemajuanStudi> {

	@Override
	public KemajuanStudi mapRow(ResultSet rs, int rowNum) throws SQLException {

		KemajuanStudi d = new KemajuanStudi();

		d.setId(rs.getString("id"));
		d.setTahun(rs.getInt("tahun"));
		d.setTahunAjaran(rs.getString("tahun_ajaran"));
		d.setIdSemester(rs.getInt("idsemester"));
		d.setSemester(rs.getString("semester"));
		d.setStatus(rs.getString("status"));
		d.setSemesterAmbil(rs.getInt("semester_ambil"));
		d.setIps(rs.getDouble("ips"));
		d.setSks(rs.getInt("sks"));

		return d;
	}

}
