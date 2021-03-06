package id.its.akademik.domain;

public class Fakultas {

	private String id;
	private String nama;
	private String namaInggris;
	private String singkatan;
	
	public Fakultas() { }

	public Fakultas(String id, String nama) {
		this.id = id;
		this.nama = nama;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNamaInggris() {
		return namaInggris;
	}

	public void setNamaInggris(String namaInggris) {
		this.namaInggris = namaInggris;
	}

	public String getSingkatan() {
		return singkatan;
	}

	public void setSingkatan(String singkatan) {
		this.singkatan = singkatan;
	}
	
}
