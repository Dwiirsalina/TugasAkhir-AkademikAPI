package id.its.akademik.domain;

import java.util.List;

public class Kurikulum {

	private String prodi;
	private Integer tahunKurikulum;
	private Integer semesterKurikulum;
	private Integer sks;
	private List<MataKuliah> mataKuliah;

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

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public List<MataKuliah> getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(List<MataKuliah> mataKuliah) {
		this.mataKuliah = mataKuliah;
	}

}
