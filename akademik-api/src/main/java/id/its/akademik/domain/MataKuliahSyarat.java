package id.its.akademik.domain;

import java.util.List;

public class MataKuliahSyarat {

	private String prodi;
	private Integer tahunKurikulum;
	private Integer semesterKurikulum;
	private String id;
	private String kode;
	private String nama;
	private String namaInggris;
	private Integer sks;
	private String jenis;
	private List<MataKuliah> syarat;

	public String getProdi() {
		return prodi;
	}

	public void setProdi(String prodi) {
		this.prodi = prodi;
	}

	public Integer getTahunKurikulum() {
		return tahunKurikulum;
	}

	public void setTahunKurikulum(Integer tahunKurikulum) {
		this.tahunKurikulum = tahunKurikulum;
	}

	public Integer getSemesterKurikulum() {
		return semesterKurikulum;
	}

	public void setSemesterKurikulum(Integer semesterKurikulum) {
		this.semesterKurikulum = semesterKurikulum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
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

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public String getJenis() {
		return jenis;
	}

	public void setJenis(String jenis) {
		this.jenis = jenis;
	}

	public List<MataKuliah> getSyarat() {
		return syarat;
	}

	public void setSyarat(List<MataKuliah> syarat) {
		this.syarat = syarat;
	}

}
