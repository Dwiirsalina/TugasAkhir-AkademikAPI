package id.its.akademik.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

public class JadwalKelas {

	@JsonIgnore
	private String kodeMk;
	@JsonIgnore
	private String kelas;
	private String hari;
	private String jamMulai;
	private String jamUsai;
	private String ruangan;
	private String idProdi;

	public JadwalKelas() {

	}

	public String getHari() {
		return hari;
	}

	public void setHari(String hari) {
		this.hari = hari;
	}

	public String getJamMulai() {
		return jamMulai;
	}

	public void setJamMulai(String jamMulai) {
		this.jamMulai = jamMulai;
	}

	public String getJamUsai() {
		return jamUsai;
	}

	public void setJamUsai(String jamUsai) {
		this.jamUsai = jamUsai;
	}

	public String getRuangan() {
		return ruangan;
	}

	public void setRuangan(String ruangan) {
		this.ruangan = ruangan;
	}

	public String getKodeMk() {
		return kodeMk;
	}

	public void setKodeMk(String kodeMk) {
		this.kodeMk = kodeMk;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public String getIdProdi() {
		return idProdi;
	}

	public void setIdProdi(String idProdi) {
		this.idProdi = idProdi;
	}

}
