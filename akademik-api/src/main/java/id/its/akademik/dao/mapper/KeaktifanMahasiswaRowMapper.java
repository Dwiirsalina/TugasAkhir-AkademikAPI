package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.KeaktifanMahasiswa;

public class KeaktifanMahasiswaRowMapper implements RowMapper<KeaktifanMahasiswa> {

	@Override
	public KeaktifanMahasiswa mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		KeaktifanMahasiswa km = new KeaktifanMahasiswa();
		
		km.setId(rs.getString("id"));
		km.setNrp(rs.getString("nrp"));
		km.setTahun(rs.getInt("tahun"));
		km.setSemester(rs.getInt("semester"));
		km.setTanggal(rs.getDate("tanggal"));
		km.setNoSurat(rs.getString("no_surat"));
		km.setKeterangan(rs.getString("keterangan"));
		km.setStatus(rs.getString("status_aktif"));
		km.setIps(rs.getDouble("ips"));
		
		return km;
	}

}
