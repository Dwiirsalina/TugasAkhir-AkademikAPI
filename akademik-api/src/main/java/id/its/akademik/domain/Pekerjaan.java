package id.its.akademik.domain;

public class Pekerjaan {

	private String id;
	private String nrp;
	private String nama;
	private String pekerjaan;
	private String instansi;
	private String alamatInstansi;
	private String telpInstansi;
	private String faxInstansi;
	private String jabatan;
	private String pendapatan;
	
	public Pekerjaan() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getPekerjaan() {
		return pekerjaan;
	}

	public void setPekerjaan(String pekerjaan) {
		this.pekerjaan = pekerjaan;
	}

	public String getInstansi() {
		return instansi;
	}

	public void setInstansi(String instansi) {
		this.instansi = instansi;
	}

	public String getAlamatInstansi() {
		return alamatInstansi;
	}

	public void setAlamatInstansi(String alamatInstansi) {
		this.alamatInstansi = alamatInstansi;
	}

	public String getTelpInstansi() {
		return telpInstansi;
	}

	public void setTelpInstansi(String telpInstansi) {
		this.telpInstansi = telpInstansi;
	}

	public String getFaxInstansi() {
		return faxInstansi;
	}

	public void setFaxInstansi(String faxInstansi) {
		this.faxInstansi = faxInstansi;
	}

	public String getJabatan() {
		return jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	public String getPendapatan() {
		return pendapatan;
	}

	public void setPendapatan(String pendapatan) {
		this.pendapatan = pendapatan;
	}
	
}
