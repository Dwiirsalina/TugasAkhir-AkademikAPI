package id.its.akademik.domain;

import java.util.List;

public class KemajuanStudi {

	private String id;
	private Integer tahun;
	private String tahunAjaran;
	private Integer idSemester;
	private String semester;
	private String status;
	private Integer semesterAmbil;
	private Double ips;
	private Integer sks;
	private List<DaftarNilai> transkrip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}

	public String getTahunAjaran() {
		return tahunAjaran;
	}

	public void setTahunAjaran(String tahunAjaran) {
		this.tahunAjaran = tahunAjaran;
	}

	public Integer getIdSemester() {
		return idSemester;
	}

	public void setIdSemester(Integer idSemester) {
		this.idSemester = idSemester;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSemesterAmbil() {
		return semesterAmbil;
	}

	public void setSemesterAmbil(Integer semesterAmbil) {
		this.semesterAmbil = semesterAmbil;
	}

	public Double getIps() {
		return ips;
	}

	public void setIps(Double ips) {
		this.ips = ips;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public List<DaftarNilai> getTranskrip() {
		return transkrip;
	}

	public void setTranskrip(List<DaftarNilai> transkrip) {
		this.transkrip = transkrip;
	}

}
