package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MataKuliah;

public class MataKuliahRowMapper implements RowMapper<MataKuliah> {

	@Override
	public MataKuliah mapRow(ResultSet rs, int rowNum) throws SQLException {

		MataKuliah k = new MataKuliah();

		k.setProdi(rs.getString("prodi"));
		k.setTahunKurikulum(rs.getInt("tahun"));
		try {
			k.setSemesterKurikulum(rs.getInt("semester"));
		} catch (Exception e) {
		}
		k.setId(rs.getString("id_mk"));
		k.setKode(rs.getString("kode_mk"));
		k.setNama(rs.getString("mk"));
		k.setNamaInggris(rs.getString("mk_inggris"));
		try {
			k.setSks(rs.getInt("sks"));
		} catch (Exception e) {
		}
		try {
			k.setJenis(rs.getString("jenis_mk"));
		} catch (Exception e) {
		}

		return k;
	}

}
