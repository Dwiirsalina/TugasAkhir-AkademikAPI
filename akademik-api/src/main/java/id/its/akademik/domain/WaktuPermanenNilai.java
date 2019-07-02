package id.its.akademik.domain;

import java.util.Date;

public class WaktuPermanenNilai {

	private Integer tahunAjaran;
	private Integer semester;
	private String kodeDepartemen;
	private String kodeMk;
	private String namaMk;
	private Integer sksMk;
	private String kelas;
	private String nip;
	private String namaDosen;
	private Date waktuPermanen;
	private Date batasWaktu;
	private Integer selisihWaktu;
	
	public WaktuPermanenNilai() { }
	
	public Integer getTahunAjaran() {
		return tahunAjaran;
	}
	
	public void setTahunAjaran(Integer tahunAjaran) {
		this.tahunAjaran = tahunAjaran;
	}
	
	public Integer getSemester() {
		return semester;
	}
	
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	
	public String getKodeDepartemen() {
		return kodeDepartemen;
	}
	
	public void setKodeDepartemen(String kodeDepartemen) {
		this.kodeDepartemen = kodeDepartemen;
	}
	
	public String getKodeMk() {
		return kodeMk;
	}
	
	public void setKodeMk(String kodeMk) {
		this.kodeMk = kodeMk;
	}
	
	public String getNamaMk() {
		return namaMk;
	}
	
	public void setNamaMk(String namaMk) {
		this.namaMk = namaMk;
	}
	
	public Integer getSksMk() {
		return sksMk;
	}
	
	public void setSksMk(Integer sksMk) {
		this.sksMk = sksMk;
	}
	
	public String getKelas() {
		return kelas;
	}
	
	public void setKelas(String kelas) {
		this.kelas = kelas;
	}
	
	public String getNip() {
		return nip;
	}
	
	public void setNip(String nip) {
		this.nip = nip;
	}
	
	public String getNamaDosen() {
		return namaDosen;
	}
	
	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}
	
	public Date getWaktuPermanen() {
		return waktuPermanen;
	}
	
	public void setWaktuPermanen(Date waktuPermanen) {
		this.waktuPermanen = waktuPermanen;
	}
	
	public Date getBatasWaktu() {
		return batasWaktu;
	}
	
	public void setBatasWaktu(Date batasWaktu) {
		this.batasWaktu = batasWaktu;
	}
	
	public Integer getSelisihWaktu() {
		return selisihWaktu;
	}
	
	public void setSelisihWaktu(Integer selisihWaktu) {
		this.selisihWaktu = selisihWaktu;
	}
	
}
