package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Akademik;

public class AkademikRowMapper implements RowMapper<Akademik> {

	@Override
	public Akademik mapRow(ResultSet rs, int rowNum) throws SQLException {

		Akademik akad = new Akademik();

		akad.setId(rs.getString("id"));
		akad.setNrp(rs.getString("nrp"));
		akad.setNama(rs.getString("nama"));
		akad.setJalurTerima(rs.getString("jalur_terima"));
		akad.setTanggalMasuk(rs.getDate("tgl_masuk"));
		akad.setNomorUjianMasuk(rs.getString("no_ujian_masuk"));
		try {
			akad.setNilaiStk(rs.getDouble("nilai_stk_nem"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkFisika(rs.getDouble("nilai_stk_fisika"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkMatematika(rs.getDouble("nilai_stk_mat"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkInggris(rs.getDouble("nilai_stk_inggris"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkKimia(rs.getDouble("nilai_stk_kimia"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkBiologi(rs.getDouble("nilai_stk_biologi"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiStkIndonesia(rs.getDouble("nilai_stk_indo"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiToefl(rs.getDouble("nilai_toefl"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiToeflLulus(rs.getDouble("nilai_toefl_lulus"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiTpa(rs.getDouble("nilai_tpa"));
		} catch (SQLException e) {
		}
		try {
			akad.setNilaiPsikotes(rs.getDouble("nilai_psikotest"));
		} catch (SQLException e) {
		}

		akad.setAsalSekolah(rs.getString("asal_sekolah"));

		return akad;
	}

}
