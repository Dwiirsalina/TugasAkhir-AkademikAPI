package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Pekerjaan;

public class PekerjaanRowMapper implements RowMapper<Pekerjaan> {

	@Override
	public Pekerjaan mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Pekerjaan p = new Pekerjaan();
		
		p.setId(rs.getString("id"));
		p.setNrp(rs.getString("nrp"));
		p.setNama(rs.getString("nama"));
		p.setPekerjaan(rs.getString("pekerjaan"));
		p.setInstansi(rs.getString("instansi"));
		p.setAlamatInstansi(rs.getString("alamat_instansi"));
		p.setTelpInstansi(rs.getString("telp_instansi"));
		p.setFaxInstansi(rs.getString("fax_instansi"));
		p.setJabatan(rs.getString("jabatan"));
		p.setPendapatan(rs.getString("pendapatan"));
		
		return p;
	}

}
