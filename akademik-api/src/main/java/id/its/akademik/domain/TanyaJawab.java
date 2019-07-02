package id.its.akademik.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class TanyaJawab {

	@JsonProperty("id_pertanyaan")
	private String idPertanyaan;
	@JsonProperty("id_jawaban")
	private String idJawaban;

	public String getIdPertanyaan() {
		return idPertanyaan;
	}

	public void setIdPertanyaan(String idPertanyaan) {
		this.idPertanyaan = idPertanyaan;
	}

	public String getIdJawaban() {
		return idJawaban;
	}

	public void setIdJawaban(String idJawaban) {
		this.idJawaban = idJawaban;
	}

}
