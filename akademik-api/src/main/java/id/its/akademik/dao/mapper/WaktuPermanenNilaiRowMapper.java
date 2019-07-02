package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.WaktuPermanenNilai;

public class WaktuPermanenNilaiRowMapper implements RowMapper<WaktuPermanenNilai> {

	@Override
	public WaktuPermanenNilai mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		WaktuPermanenNilai wpn = new WaktuPermanenNilai();
		
		wpn.setTahunAjaran(rs.getInt("thn_ajaran"));
		wpn.setSemester(rs.getInt("smt"));
		wpn.setKodeDepartemen(rs.getString("kode_dept"));
		wpn.setKodeMk(rs.getString("kode_mk"));
		wpn.setNamaMk(rs.getString("nama_mk"));
		wpn.setSksMk(rs.getInt("sks_mk"));
		wpn.setKelas(rs.getString("kelas"));
		wpn.setNip(rs.getString("nip"));
		wpn.setNamaDosen(rs.getString("nama_dosen"));
		wpn.setWaktuPermanen(rs.getTimestamp("waktu_permanen"));
		wpn.setBatasWaktu(rs.getTimestamp("batas_waktu"));
		wpn.setSelisihWaktu(rs.getInt("selisih_waktu"));
		
		return wpn;
	}

}
