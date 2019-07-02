package id.its.akademik.domain;

import java.util.List;

public class FRS {

	private Integer tahun;
	private Integer semester;
	private String nrp;
	private String nrpBaru;
	private String nama;
	private String dosenWali;
	private String statusKeaktifan;
	private Boolean disetujui;
	private Double ipsLalu;
	private Double ipk;
	private Integer sksTempuh;
	private Integer sksLulus;
	private Integer sksAmbil;
	private Integer batasSks;
	private Integer sisaSks;
	private List<KelasAmbil> kelasAmbil;

	public FRS() {

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

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getNrpBaru() {
		return nrpBaru;
	}

	public void setNrpBaru(String nrpBaru) {
		this.nrpBaru = nrpBaru;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDosenWali() {
		return dosenWali;
	}

	public void setDosenWali(String dosenWali) {
		this.dosenWali = dosenWali;
	}

	public String getStatusKeaktifan() {
		return statusKeaktifan;
	}

	public void setStatusKeaktifan(String statusKeaktifan) {
		this.statusKeaktifan = statusKeaktifan;
	}

	public Boolean isDisetujui() {
		return disetujui;
	}

	public void setDisetujui(Boolean disetujui) {
		this.disetujui = disetujui;
	}

	public Double getIpsLalu() {
		return ipsLalu;
	}

	public void setIpsLalu(Double ipsLalu) {
		this.ipsLalu = ipsLalu;
	}

	public Double getIpk() {
		return ipk;
	}

	public void setIpk(Double ipk) {
		this.ipk = ipk;
	}

	public Integer getSksAmbil() {
		return sksAmbil;
	}

	public void setSksAmbil(Integer sksAmbil) {
		this.sksAmbil = sksAmbil;
	}

	public Integer getSksTempuh() {
		return sksTempuh;
	}

	public void setSksTempuh(Integer sksTempuh) {
		this.sksTempuh = sksTempuh;
	}

	public Integer getSksLulus() {
		return sksLulus;
	}

	public void setSksLulus(Integer sksLulus) {
		this.sksLulus = sksLulus;
	}

	public Integer getBatasSks() {
		return batasSks;
	}

	public void setBatasSks(Integer batasSks) {
		this.batasSks = batasSks;
	}

	public Integer getSisaSks() {
		return sisaSks;
	}

	public void setSisaSks(Integer sisa) {
		this.sisaSks = sisa;
	}

	public List<KelasAmbil> getKelasAmbil() {
		return kelasAmbil;
	}

	public void setKelasAmbil(List<KelasAmbil> kelasAmbil) {
		this.kelasAmbil = kelasAmbil;
	}

	public int getLimitSKS(double ips) {
		return 0;
	}

}
