package id.its.akademik.domain;

public class Prasyarat {

	private String mataKuliah;
	private Integer syaratTahunKurikulum;
	private String syaratKodeMatkul;
	private String statusWajibAmbil;
	private String syaratKuNilaiHuruf;
	private String statusLulus;

	public String getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(String mataKuliah) {
		this.mataKuliah = mataKuliah;
	}

	public Integer getSyaratTahunKurikulum() {
		return syaratTahunKurikulum;
	}

	public void setSyaratTahunKurikulum(Integer syaratTahunKurikulum) {
		this.syaratTahunKurikulum = syaratTahunKurikulum;
	}

	public String getSyaratKodeMatkul() {
		return syaratKodeMatkul;
	}

	public void setSyaratKodeMatkul(String syaratKodeMatkul) {
		this.syaratKodeMatkul = syaratKodeMatkul;
	}

	public String getStatusWajibAmbil() {
		return statusWajibAmbil;
	}

	public void setStatusWajibAmbil(String statusWajibAmbil) {
		this.statusWajibAmbil = statusWajibAmbil;
	}

	public String getSyaratKuNilaiHuruf() {
		return syaratKuNilaiHuruf;
	}

	public void setSyaratKuNilaiHuruf(String syaratKuNilaiHuruf) {
		this.syaratKuNilaiHuruf = syaratKuNilaiHuruf;
	}

	public String getStatusLulus() {
		return statusLulus;
	}

	public void setStatusLulus(String statusLulus) {
		this.statusLulus = statusLulus;
	}

}
