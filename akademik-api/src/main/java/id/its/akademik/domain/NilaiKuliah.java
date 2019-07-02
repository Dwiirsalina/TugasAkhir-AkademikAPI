package id.its.akademik.domain;

public class NilaiKuliah {

	private Integer tahun;
	private Integer semester;
	private String kode;
	private String mataKuliah;
	private String kelas;
	private Integer sks;
	private String nilaiHuruf;
	private Double nilaiAngka;
	
	public NilaiKuliah() {
		
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

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(String mataKuliah) {
		this.mataKuliah = mataKuliah;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public String getNilaiHuruf() {
		return nilaiHuruf;
	}

	public void setNilaiHuruf(String nilaiHuruf) {
		this.nilaiHuruf = nilaiHuruf;
	}

	public Double getNilaiAngka() {
		return nilaiAngka;
	}

	public void setNilaiAngka(Double nilaiAngka) {
		this.nilaiAngka = nilaiAngka;
	}

	
}
