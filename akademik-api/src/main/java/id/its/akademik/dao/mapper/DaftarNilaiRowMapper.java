package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.DaftarNilai;

public class DaftarNilaiRowMapper implements RowMapper<DaftarNilai> {

	@Override
	public DaftarNilai mapRow(ResultSet rs, int rowNum) throws SQLException {

		DaftarNilai t = new DaftarNilai();

		t.setNrp(rs.getString("nrp"));
		try {
			t.setTahap(rs.getString("tahap"));
		} catch (Exception e) {
		}
		t.setSemesterAmbil(rs.getInt("semester_ambil"));
		t.setTahunKurikulum(rs.getInt("tahun_kurikulum"));
		t.setIdMataKuliah(rs.getString("id_mk"));
		t.setMataKuliah(rs.getString("mk"));
		try {
			t.setMataKuliahInggris(rs.getString("mk_inggris"));
		} catch (Exception e) {
		}
		t.setSks(rs.getInt("sks"));
		t.setTahun(rs.getInt("tahun"));
		t.setSemester(rs.getInt("semester"));
		try {
			t.setSingkatanSemester(rs.getString("singkatan_semester"));
		} catch (Exception e) {
		}
		t.setNilaiHuruf(rs.getString("nilai_huruf"));
		t.setNilaiAkhir(rs.getString("nilai_akhir"));
		t.setNilaiAngka(rs.getDouble("nilai_angka"));
		try {
			t.setUrutan(rs.getInt("urutan"));
		} catch (Exception e) {
		}
		return t;
	}

}
