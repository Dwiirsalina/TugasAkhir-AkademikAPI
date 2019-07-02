package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.PeringkatIPD;

public class PeringkatIPDRowMapper implements RowMapper<PeringkatIPD> {

	@Override
	public PeringkatIPD mapRow(ResultSet rs, int rowNum) throws SQLException {
		Double terisi = rs.getDouble("ke_terisi");
		Integer jumlahPengisi = rs.getInt("jumlah_pengisi");
		Integer respondenDosen = rs.getInt("responden_dosen");

		PeringkatIPD peringkat = new PeringkatIPD();
		peringkat.setIdMk(rs.getString("ke_kr_mk_id"));
		try {
			peringkat.setKodeMk(rs.getString("kode_mk"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		peringkat.setNamaMk(rs.getString("mk_mata_kuliah"));
		peringkat.setKelas(rs.getString("ke_kelas"));
		peringkat.setNamaDosen(rs.getString("pe_namalengkap"));
		peringkat.setTerisi(terisi);
		peringkat.setNip(rs.getString("pe_nip"));
		peringkat.setJumlahPengisiIpm(jumlahPengisi);
		peringkat.setJumlahRespondenIpd(respondenDosen);
		peringkat.setIpd(rs.getDouble("rata2ipd_kinerja_dosen"));
		peringkat.setIpm(rs.getDouble("rata2ipd_mk"));
		

		Double persentasePengisiIPM = jumlahPengisi / terisi * 100;
		peringkat.setPersentasePengisiIpm(persentasePengisiIPM);
		Double persentasePengisiIPD = respondenDosen / terisi * 100;
		peringkat.setPersentasePengisiIpd(persentasePengisiIPD);

		peringkat.setRataIPD(rs.getDouble("rata2ipd"));
		
		peringkat.setPermanen(rs.getBoolean("is_permanen_nilai"));

		return peringkat;
	}

}
