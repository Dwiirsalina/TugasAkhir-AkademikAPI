package id.its.akademik.domain;

import java.util.List;

public class Persiapan {
	private Integer totalSks;
	private Double ipTahap;
	private String keterangan;
	List<DaftarNilai> daftarNilai;

	public Integer getTotalSks() {
		return totalSks;
	}

	public void setTotalSks(Integer totalSks) {
		this.totalSks = totalSks;
	}

	public Double getIpTahap() {
		return ipTahap;
	}

	public void setIpTahap(Double ipTahap) {
		this.ipTahap = Double.isNaN(ipTahap) ? 0.0 : ipTahap;
	}

	public List<DaftarNilai> getDaftarNilai() {
		return daftarNilai;
	}

	public void setDaftarNilai(List<DaftarNilai> daftarNilai) {
		this.daftarNilai = daftarNilai;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

}
