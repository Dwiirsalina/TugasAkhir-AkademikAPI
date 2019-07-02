package id.its.akademik.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Kelas {

	private Integer tahun;
	private Integer semester;

	@JsonIgnore
	private String idMk;
	private MataKuliah mataKuliah;

	private Integer sks;
	private String kelas;
	private Integer terisi;
	private Integer dayaTampung;
	private String nip_dosen;
	private String dosen;
	private String nip_baru;
	private String keterangan;
	private String idProdi;
	private List<JadwalKelas> jadwal;

	public Kelas() {
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

	public MataKuliah getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(MataKuliah mataKuliah) {
		this.mataKuliah = mataKuliah;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String nama) {
		this.kelas = nama;
	}

	public Integer getTerisi() {
		return terisi;
	}

	public void setTerisi(Integer terisi) {
		this.terisi = terisi;
	}

	public Integer getDayaTampung() {
		return dayaTampung;
	}

	public void setDayaTampung(Integer dayaTampung) {
		this.dayaTampung = dayaTampung;
	}

	public String getNip_dosen() {
		return nip_dosen;
	}

	public void setNip_dosen(String nip_dosen) {
		this.nip_dosen = nip_dosen;
	}

	public String getDosen() {
		return dosen;
	}

	public void setDosen(String dosen) {
		this.dosen = dosen;
	}

	public String getNip_baru() {
		return nip_baru;
	}

	public void setNip_baru(String nip_baru) {
		this.nip_baru = nip_baru;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getIdProdi() {
		return idProdi;
	}

	public void setIdProdi(String idProdi) {
		this.idProdi = idProdi;
	}

	public List<JadwalKelas> getJadwal() {
		return jadwal;
	}

	public void setJadwal(List<JadwalKelas> jadwal) {
		this.jadwal = jadwal;
	}

	public String getIdMk() {
		return idMk;
	}

	public void setIdMk(String idMk) {
		this.idMk = idMk;
	}

	public static class MataKuliah {

		private String id;
		private String kode;
		private String nama;
		private String namaInggris;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getKode() {
			return kode;
		}

		public void setKode(String kode) {
			this.kode = kode;
		}

		public String getNama() {
			return nama;
		}

		public void setNama(String nama) {
			this.nama = nama;
		}

		public String getNamaInggris() {
			return namaInggris;
		}

		public void setNamaInggris(String namaInggris) {
			this.namaInggris = namaInggris;
		}

	}

}
