package id.its.akademik.domain;

import java.util.Date;

public class WaktuFrs {

	private Integer tahun;
	private Integer semester;
	private Date mulaiAmbil;
	private Date selesaiAmbil;
	private Date mulaiUbah;
	private Date selesaiUbah;
	private Date mulaiDrop;
	private Date selesaiDrop;
	private Date selesaiKuliah;
	
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
	
	public Date getMulaiAmbil() {
		return mulaiAmbil;
	}
	
	public void setMulaiAmbil(Date mulaiAmbil) {
		this.mulaiAmbil = mulaiAmbil;
	}
	
	public Date getSelesaiAmbil() {
		return selesaiAmbil;
	}
	
	public void setSelesaiAmbil(Date selesaiAmbil) {
		this.selesaiAmbil = selesaiAmbil;
	}
	
	public Date getMulaiUbah() {
		return mulaiUbah;
	}
	
	public void setMulaiUbah(Date mulaiUbah) {
		this.mulaiUbah = mulaiUbah;
	}
	
	public Date getSelesaiUbah() {
		return selesaiUbah;
	}
	
	public void setSelesaiUbah(Date selesaiUbah) {
		this.selesaiUbah = selesaiUbah;
	}
	
	public Date getMulaiDrop() {
		return mulaiDrop;
	}
	
	public void setMulaiDrop(Date mulaiDrop) {
		this.mulaiDrop = mulaiDrop;
	}
	
	public Date getSelesaiDrop() {
		return selesaiDrop;
	}
	
	public void setSelesaiDrop(Date selesaiDrop) {
		this.selesaiDrop = selesaiDrop;
	}
	
	public Date getSelesaiKuliah() {
		return selesaiKuliah;
	}
	
	public void setSelesaiKuliah(Date selesaiKuliah) {
		this.selesaiKuliah = selesaiKuliah;
	}
	
}
