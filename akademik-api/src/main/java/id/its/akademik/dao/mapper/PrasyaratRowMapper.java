package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Prasyarat;

public class PrasyaratRowMapper implements RowMapper<Prasyarat> {

	@Override
	public Prasyarat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Prasyarat p = new Prasyarat();

		p.setMataKuliah(rs.getString("mk_mata_kuliah"));
		p.setSyaratTahunKurikulum(rs.getInt("sy_mk_thnkurikulumsyarat"));
		p.setSyaratKodeMatkul(rs.getString("sy_mk_idsyarat"));
		p.setStatusWajibAmbil(rs.getString("sy_wajibambil"));
		p.setSyaratKuNilaiHuruf(rs.getString("sy_ku_nilaihuruf"));
		p.setStatusLulus(rs.getString("sy_lulus"));

		return p;
	}

}
