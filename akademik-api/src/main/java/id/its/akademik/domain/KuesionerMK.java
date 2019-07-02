package id.its.akademik.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class KuesionerMK {

	@JsonProperty("id_mk")
	private String idMk;
	@JsonProperty("id_jurusan")
	private String idJurusan;
	private String kelas;
	private String komentar;
	private List<TanyaJawab> jawaban;

	public String getIdMk() {
		return idMk;
	}

	public void setIdMk(String idMk) {
		this.idMk = idMk;
	}

	public String getIdJurusan() {
		return idJurusan;
	}

	public void setIdJurusan(String idJurusan) {
		this.idJurusan = idJurusan;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

	public List<TanyaJawab> getJawaban() {
		return jawaban;
	}

	public void setJawaban(List<TanyaJawab> jawaban) {
		this.jawaban = jawaban;
	}

}
