package id.its.akademik.domain;

import java.util.Date;
import java.util.List;

public class Pembayaran {

	private String id;
	private String semesterDikti;
	private Boolean flag;
	private Double jumlah;
	private Date tanggal;
	private String keterangan;
	private List<DetailPembayaran> detailPembayaran;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSemesterDikti() {
		return semesterDikti;
	}

	public void setSemesterDikti(String semesterDikti) {
		this.semesterDikti = semesterDikti;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Double getJumlah() {
		return jumlah;
	}

	public void setJumlah(Double jumlah) {
		this.jumlah = jumlah;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public List<DetailPembayaran> getDetailPembayaran() {
		return detailPembayaran;
	}

	public void setDetailPembayaran(List<DetailPembayaran> detailPembayaran) {
		this.detailPembayaran = detailPembayaran;
	}

}
