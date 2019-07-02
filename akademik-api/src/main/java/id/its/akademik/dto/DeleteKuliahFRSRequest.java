package id.its.akademik.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class DeleteKuliahFRSRequest {

	@JsonProperty("kode_prodi")
	private String kodeProdi;
	@JsonProperty("id_mk")
	private String idMataKuliah;
	private String kelas;

	public DeleteKuliahFRSRequest() {
	}

	public String getKodeProdi() {
		return kodeProdi;
	}

	public void setKodeProdi(String kodeProdi) {
		this.kodeProdi = kodeProdi;
	}

	public String getIdMataKuliah() {
		return idMataKuliah;
	}

	public void setIdMataKuliah(String idMataKuliah) {
		this.idMataKuliah = idMataKuliah;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

}
