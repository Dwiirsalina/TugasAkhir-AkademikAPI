package id.its.akademik.domain;

public class JadwalKuliah {
	private Integer tahun;
	private Integer semester;
	private String idMk;
	private String namaMk;
	private String kelas;
	private String hari;
	private String ruangan;
	private String jamMulai;
	private String jamUsai;
	private String namaDosen;

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

	public String getIdMk() {
		return idMk;
	}

	public void setIdMk(String idMk) {
		this.idMk = idMk;
	}

	public String getNamaMk() {
		return namaMk;
	}

	public void setNamaMk(String namaMk) {
		this.namaMk = namaMk;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public String getHari() {
		return hari;
	}

	public void setHari(String hari) {
		this.hari = hari;
	}

	public String getRuangan() {
		return ruangan;
	}

	public void setRuangan(String ruangan) {
		this.ruangan = ruangan;
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

	public String getNamaDosen() {
		return namaDosen;
	}

	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}

}
