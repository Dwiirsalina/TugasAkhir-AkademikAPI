package id.its.akademik.domain;

public class ListKuesioner {

	private String idMataKuliah;
	private String kode;
	private String namaMataKuliah;
	private String nipPengajar;
	private String namaPengajar;
	private String kodeJurusan;
	private String kelas;
	private Boolean status;

	public String getIdMataKuliah() {
		return idMataKuliah;
	}

	public void setIdMataKuliah(String idMataKuliah) {
		this.idMataKuliah = idMataKuliah;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getNamaMataKuliah() {
		return namaMataKuliah;
	}

	public void setNamaMataKuliah(String namaMataKuliah) {
		this.namaMataKuliah = namaMataKuliah;
	}

	public String getNipPengajar() {
		return nipPengajar;
	}

	public void setNipPengajar(String nipPengajar) {
		this.nipPengajar = nipPengajar;
	}

	public String getNamaPengajar() {
		return namaPengajar;
	}

	public void setNamaPengajar(String namaPengajar) {
		this.namaPengajar = namaPengajar;
	}

	public String getKodeJurusan() {
		return kodeJurusan;
	}

	public void setKodeJurusan(String kodeJurusan) {
		this.kodeJurusan = kodeJurusan;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
