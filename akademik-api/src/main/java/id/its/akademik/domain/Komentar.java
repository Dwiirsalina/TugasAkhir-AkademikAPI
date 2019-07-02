package id.its.akademik.domain;

public class Komentar {
	private String idProdi;
	private String idMk;
	private String kelas;
	private Integer tahun;
	private Integer semester;
	private String komentar;

	public String getIdProdi() {
		return idProdi;
	}

	public void setIdProdi(String idProdi) {
		this.idProdi = idProdi;
	}

	public String getIdMk() {
		return idMk;
	}

	public void setIdMk(String idMk) {
		this.idMk = idMk;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public String getKomentar() {
		return komentar;
	}

	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}

}
