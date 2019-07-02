package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Komentar;

public class KomentarRowMapper implements RowMapper<Komentar> {

	@Override
	public Komentar mapRow(ResultSet rs, int rowNum) throws SQLException {
		Komentar k = new Komentar();

		k.setIdProdi(rs.getString("kode_prodi"));
		k.setIdMk(rs.getString("kode_mk"));
		k.setKelas(rs.getString("kelas"));
		k.setSemester(rs.getInt("semester"));
		k.setTahun(rs.getInt("tahun"));
		k.setKomentar(rs.getString("komentar"));
		return k;
	}
}
