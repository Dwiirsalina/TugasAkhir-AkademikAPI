package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Pegawai;

public class PegawaiRowMapper implements RowMapper<Pegawai> {

	@Override
	public Pegawai mapRow(ResultSet rs, int rowNum) throws SQLException {

		Pegawai p = new Pegawai();

		try {
			p.setNip(rs.getString("nip"));
		} catch (SQLException e) {

		}

		try {
			p.setNama(rs.getString("nama"));
		} catch (SQLException e) {

		}

		try {
			p.setNamaLengkap(rs.getString("nama_lengkap"));
		} catch (SQLException e) {

		}

		try {
			p.setEmail(rs.getString("email"));
		} catch (SQLException e) {

		}

		try {
			p.setNipBaru(rs.getString("nip_baru"));
		} catch (SQLException e) {

		}

		try {
			p.setJenis(rs.getString("jenis_pegawai"));
		} catch (SQLException e) {

		}

		try {
			p.setTanggalLahir(rs.getDate("tanggal_lahir"));
		} catch (SQLException e) {

		}

		try {
			p.setAlamat(rs.getString("alamat"));
		} catch (SQLException e) {

		}

		try {
			p.setNoTelp(rs.getString("telp"));
		} catch (SQLException e) {

		}

		try {
			p.setNoHP(rs.getString("no_hp"));
		} catch (SQLException e) {

		}
		return p;
	}

}
