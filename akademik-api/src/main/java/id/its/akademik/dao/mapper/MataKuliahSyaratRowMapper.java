package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MataKuliahSyarat;

public class MataKuliahSyaratRowMapper implements RowMapper<MataKuliahSyarat> {

	@Override
	public MataKuliahSyarat mapRow(ResultSet rs, int rowNum) throws SQLException {

		MataKuliahSyarat k = new MataKuliahSyarat();

		k.setProdi(rs.getString("prodi"));
		k.setTahunKurikulum(rs.getInt("tahun"));
		try {
			k.setSemesterKurikulum(rs.getInt("semester"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		k.setId(rs.getString("id_mk"));
		k.setKode(rs.getString("kode_mk"));
		k.setNama(rs.getString("mk"));
		k.setNamaInggris(rs.getString("mk_inggris"));
		k.setSks(rs.getInt("sks"));
		k.setJenis(rs.getString("jenis_mk"));

		return k;
	}

}
