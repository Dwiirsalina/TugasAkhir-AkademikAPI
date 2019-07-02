package id.its.akademik.domain;

public class CekPembayaran {

	private String id;
	private String key1;
	private String key2;
	private String semesterDikti;
	private String flag;
	private Double jumlah;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public String getSemesterDikti() {
		return semesterDikti;
	}

	public void setSemesterDikti(String semesterDikti) {
		this.semesterDikti = semesterDikti;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getJumlah() {
		return jumlah;
	}

	public void setJumlah(Double jumlah) {
		this.jumlah = jumlah;
	}

}
