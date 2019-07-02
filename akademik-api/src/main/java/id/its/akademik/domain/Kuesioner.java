package id.its.akademik.domain;

import java.util.List;

public class Kuesioner {

	private String id;
	private Integer tahunKurikulum;
	private Integer jenjang;
	private String pertanyaan;
	private List<Jawaban> jawaban;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTahunKurikulum() {
		return tahunKurikulum;
	}

	public void setTahunKurikulum(Integer tahunKurikulum) {
		this.tahunKurikulum = tahunKurikulum;
	}

	public Integer getJenjang() {
		return jenjang;
	}

	public void setJenjang(Integer jenjang) {
		this.jenjang = jenjang;
	}

	public String getPertanyaan() {
		return pertanyaan;
	}

	public void setPertanyaan(String pertanyaan) {
		this.pertanyaan = pertanyaan;
	}

	public List<Jawaban> getJawaban() {
		return jawaban;
	}

	public void setJawaban(List<Jawaban> jawaban) {
		this.jawaban = jawaban;
	}

}
