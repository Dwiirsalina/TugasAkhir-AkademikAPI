package id.its.akademik.domain;

import java.util.List;

public class Transkrip {

	private String nrp;
	private Integer sks;
	private Integer sksTempuh;
	private Integer sksLulus;
	private Double ipk;
	private String judulTA;
	private String judulTaInggris;
	private String pembimbing1;
	private String pembimbing2;
	private String pembimbing3;
	List<Persiapan> tahap;

	public Transkrip() {

	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
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

	public Double getIpk() {
		return ipk;
	}

	public void setIpk(Double ipk) {
		this.ipk = Double.isNaN(ipk) ? 0.0 : ipk;
	}

	public String getJudulTA() {
		return judulTA;
	}

	public void setJudulTA(String judulTA) {
		this.judulTA = judulTA;
	}

	public String getJudulTaInggris() {
		return judulTaInggris;
	}

	public void setJudulTaInggris(String judulTaInggris) {
		this.judulTaInggris = judulTaInggris;
	}

	public String getPembimbing1() {
		return pembimbing1;
	}

	public void setPembimbing1(String pembimbing1) {
		this.pembimbing1 = pembimbing1;
	}

	public String getPembimbing2() {
		return pembimbing2;
	}

	public void setPembimbing2(String pembimbing2) {
		this.pembimbing2 = pembimbing2;
	}

	public String getPembimbing3() {
		return pembimbing3;
	}

	public void setPembimbing3(String pembimbing3) {
		this.pembimbing3 = pembimbing3;
	}

	public List<Persiapan> getTahap() {
		return tahap;
	}

	public void setTahap(List<Persiapan> tahap) {
		this.tahap = tahap;
	}

}
