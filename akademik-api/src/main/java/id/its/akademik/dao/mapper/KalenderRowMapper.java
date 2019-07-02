package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Kalender;

public class KalenderRowMapper implements RowMapper<Kalender> {

	@Override
	public Kalender mapRow(ResultSet rs, int rowNum) throws SQLException {
		Kalender k = new Kalender();

		switch (rs.getString("Col")) {
		case "TglMulaiFRS":
			k.setKolom("Mulai Pengisian Mata Kuliah");
			break;
		case "TglAkhirFRS":
			k.setKolom("Batas Akhir Pengisian Mata Kuliah");
			break;
		case "TglMulaiUbah":
			k.setKolom("Mulai Perubahan Mata Kuliah");
			break;
		case "TglAkhirUbah":
			k.setKolom("Batas Akhir Perubahan Mata Kuliah");
			break;
		case "TglMulaiDrop":
			k.setKolom("Mulai Pembatalan Mata Kuliah");
			break;
		case "TglAkhirDrop":
			k.setKolom("Batas Akhir Pembatalan Mata Kuliah");
			break;
		case "TglAkhirKuliah":
			k.setKolom("Akhir Masa Perkuliahan");
			break;
		}
		k.setTanggal(rs.getDate("Value"));

		return k;
	}

}
