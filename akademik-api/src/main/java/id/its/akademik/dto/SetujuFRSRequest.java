package id.its.akademik.dto;

public class SetujuFRSRequest {

	private String nrp;
	private Integer tahun;
	private Integer semester;

	public SetujuFRSRequest() {

	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
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
}
