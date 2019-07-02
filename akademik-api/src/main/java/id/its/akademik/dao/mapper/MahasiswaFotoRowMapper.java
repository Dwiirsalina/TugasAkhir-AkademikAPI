package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.MahasiswaFoto;

public class MahasiswaFotoRowMapper implements RowMapper<MahasiswaFoto> {

	@Override
	public MahasiswaFoto mapRow(ResultSet rs, int rowNum) throws SQLException {

		MahasiswaFoto m = new MahasiswaFoto();

		try {
			m.setId(rs.getString("id"));
		} catch (SQLException e) {

		}

		try {
			m.setNrp(rs.getString("nrp"));
		} catch (SQLException e) {

		}

		try {
			m.setKodeProdi(rs.getString("kode_prodi"));
		} catch (SQLException e) {

		}
		try {
			m.setNama(rs.getString("nama"));
		} catch (SQLException e) {

		}

		try {
			m.setNamaLengkap(rs.getString("nama_lengkap"));
		} catch (SQLException e) {

		}

		try {
			m.setJenisKelamin(rs.getString("jk"));
		} catch (SQLException e) {

		}
		try {
			m.setDosenWali(rs.getString("dosen_wali"));
		} catch (SQLException e) {

		}

		try {
			m.setTanggalLahir((rs.getDate("tgl_lahir")));
		} catch (SQLException e) {

		}

		try {
			m.setTempatLahir(rs.getString("tmpt_lahir"));
		} catch (SQLException e) {

		}

		try {
			m.setAgama((rs.getString("agama") != null ? rs.getString("agama").trim() : null));
		} catch (SQLException e) {

		}

		try {
			m.setStatusKawin(rs.getString("stat_kawin"));
		} catch (SQLException e) {

		}

		try {
			m.setAlamatSurabaya(rs.getString("alamat_sby"));
		} catch (SQLException e) {

		}

		try {
			m.setKodePos(rs.getString("kodepos"));
		} catch (SQLException e) {

		}

		try {
			m.setNoTelp((rs.getString("telp_mhs") != null ? rs.getString("telp_mhs").trim() : null));
		} catch (SQLException e) {

		}

		try {
			m.setEmail(rs.getString("email"));
		} catch (SQLException e) {

		}

		try {
			m.setGolonganDarah((rs.getString("gol_darah") != null ? rs.getString("gol_darah").trim() : null));
		} catch (SQLException e) {

		}

		try {
			m.setKewarganegaraan(rs.getString("kewarganegaraan"));
		} catch (SQLException e) {

		}

		try {
			m.setProdi(rs.getString("prodi"));
		} catch (SQLException e) {

		}
		try {
			m.setIpk(rs.getDouble("ipk"));
		} catch (SQLException e) {

		}
		try {
			m.setSksLulus(rs.getInt("sks_lulus"));
		} catch (SQLException e) {

		}
		try {
			m.setTahunMasuk(rs.getString("tahun_masuk"));
		} catch (SQLException e) {

		}
		try {
			m.setFoto(rs.getBytes("foto"));
		} catch (SQLException e) {

		}
		return m;
	}

}
