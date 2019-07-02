package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.ListKuesioner;

public class ListKuesionerRowMapper implements RowMapper<ListKuesioner> {

	@Override
	public ListKuesioner mapRow(ResultSet rs, int rowNum) throws SQLException {
		ListKuesioner l = new ListKuesioner();
		l.setIdMataKuliah(rs.getString("id_mk"));
		l.setKode(rs.getString("kode"));
		l.setNamaMataKuliah(rs.getString("mata_kuliah"));
		l.setNipPengajar(rs.getString("nip"));
		l.setNamaPengajar(rs.getString("nama_pengajar"));
		l.setKodeJurusan(rs.getString("kode_jurusan"));
		l.setKelas(rs.getString("kelas"));
		l.setStatus(rs.getBoolean("status"));
		return l;
	}

}
