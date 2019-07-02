package id.its.akademik.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import id.its.akademik.domain.Jawaban;

public class JawabanRowMapper implements RowMapper<Jawaban> {
	@Override
	public Jawaban mapRow(ResultSet rs, int rowNum) throws SQLException {
		Jawaban jawaban = new Jawaban();
		jawaban.setId(rs.getString("id"));
		String jawab = rs.getString("jawaban");
		if (jawab.contains(" -")) {
			if (rs.getString("bahasa").equalsIgnoreCase("en")) {
				jawaban.setJawaban(jawab.substring(jawab.indexOf(" -") + 2, jawab.length()));
			} else {
				jawaban.setJawaban(jawab.substring(0, jawab.indexOf(" -")));
			}
		} else {
			jawaban.setJawaban(jawab);
		}
		return jawaban;
	}
}
