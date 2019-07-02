package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Kuesioner;

public class KuesionerRowMapper implements RowMapper<Kuesioner> {

	@Override
	public Kuesioner mapRow(ResultSet rs, int rowNum) throws SQLException {
		Kuesioner k = new Kuesioner();

		k.setId(rs.getString("id"));
		k.setTahunKurikulum(rs.getInt("tahunKurikulum"));
		k.setJenjang(rs.getInt("jenjang"));
		String pertanyaan = rs.getString("pertanyaan");
		if (pertanyaan.contains(" - "))
			if (rs.getString("bahasa").equalsIgnoreCase("en")) {
				k.setPertanyaan(pertanyaan.substring(pertanyaan.indexOf(" - ") + 3, pertanyaan.length()));
			} else {
				k.setPertanyaan(pertanyaan.substring(0, pertanyaan.indexOf(" - ")));
			}
		else if (pertanyaan.contains(" -\r\n"))
			if (rs.getString("bahasa").equalsIgnoreCase("en")) {
				k.setPertanyaan(pertanyaan.substring(pertanyaan.indexOf(" -\r\n") + 4, pertanyaan.length()));
			} else {
				k.setPertanyaan(pertanyaan.substring(0, pertanyaan.indexOf(" -\r\n")));
			}
		else {
			k.setPertanyaan(pertanyaan);
		}
		return k;
	}
}
