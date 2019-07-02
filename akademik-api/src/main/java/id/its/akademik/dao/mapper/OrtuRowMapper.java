package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.OrangTua;

public class OrtuRowMapper implements RowMapper<OrangTua> {

	@Override
	public OrangTua mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		OrangTua ot = new OrangTua();
		
		ot.setNrp(rs.getString("nrp"));
		ot.setNama(rs.getString("nama"));
		ot.setNamaAyah(rs.getString("nama_ayah"));
		ot.setNamaIbu(rs.getString("nama_ibu"));
		ot.setAlamat(rs.getString("alamat"));
		ot.setKota(rs.getString("kota"));
		ot.setProvinsi(rs.getString("provinsi"));
		ot.setKodePos(rs.getString("kodepos"));
		ot.setPekerjaanAyah(rs.getString("pekerjaan_ayah"));
		ot.setPekerjaanIbu(rs.getString("pekerjaan_ibu"));
		ot.setPendapatan(rs.getString("pendapatan"));
		ot.setTelp(rs.getString("telp"));
		
		return ot;
	}
	
}
