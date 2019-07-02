package id.its.akademik.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "status_bayar_spp")
@XmlType(name = "status_bayar_spp")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusBayarSpp {

	private String nrp;
	private Integer tahun;
	private Integer semester;
	private Integer statusBayar;
	
	public StatusBayarSpp() {
		
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
	
	public Integer getStatusBayar() {
		return statusBayar;
	}
	
	public void setStatusBayar(Integer statusBayar) {
		this.statusBayar = statusBayar;
	}
	
}
