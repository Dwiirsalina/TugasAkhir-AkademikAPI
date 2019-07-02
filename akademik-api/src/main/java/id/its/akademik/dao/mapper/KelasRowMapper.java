package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.Kelas.MataKuliah;

public class KelasRowMapper implements RowMapper<Kelas> {

	@Override
	public Kelas mapRow(ResultSet rs, int rowNum) throws SQLException {

		Kelas k = new Kelas();

		try {
			k.setTahun(rs.getInt("tahun"));
		} catch (SQLException e) {

		}

		try {
			k.setSemester(rs.getInt("semester"));
		} catch (SQLException e) {

		}

		try {
			k.setIdMk(rs.getString("id_mk"));
		} catch (SQLException e) {

		}

		try {

			MataKuliah mk = new MataKuliah();
			mk.setId(rs.getString("id_mk"));
			mk.setKode(rs.getString("kode_mk"));
			mk.setNama(rs.getString("nama_mk"));
			mk.setNamaInggris(rs.getString("nama_mk_inggris"));

			k.setMataKuliah(mk);
		} catch (SQLException e) {

		}

		try {
			k.setSks(rs.getInt("sks"));
		} catch (SQLException e) {

		}

		try {
			k.setKelas(rs.getString("kelas"));
		} catch (SQLException e) {

		}

		try {
			k.setTerisi(rs.getInt("terisi"));
		} catch (SQLException e) {

		}

		try {
			k.setDayaTampung(rs.getInt("daya_tampung"));
		} catch (SQLException e) {

		}

		try {
			k.setNip_dosen(rs.getString("nip_dosen"));
		} catch (SQLException e) {

		}
		try {
			k.setDosen(rs.getString("dosen"));
		} catch (SQLException e) {

		}

		try {
			k.setNip_baru(rs.getString("nip_baru"));
		} catch (SQLException e) {

		}

		try {
			k.setIdProdi(rs.getString("kodejur"));
		} catch (Exception e) {
		}
		return k;
	}

}
