package id.its.akademik.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("Mahasiswa")
public class MahasiswaFoto {

	private String id;
	private String nrp;
	private String kodeProdi;
	private String nama;
	private String namaLengkap;
	private String jenisKelamin;
	private String dosenWali;
	private Date tanggalLahir;
	private String tempatLahir;
	private String agama;
	private String statusKawin;
	private String alamatSurabaya;
	private String kodePos;
	private String noTelp;
	private String email;
	private String golonganDarah;
	private String kewarganegaraan;
	private String prodi;
	private Double ipk;
	private Integer sksLulus;
	private String tahunMasuk;
	private Integer semesterKe;
	private byte[] foto;

	public MahasiswaFoto() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNrp() {
		return nrp;
	}

	public void setNrp(String nrp) {
		this.nrp = nrp;
	}

	public String getKodeProdi() {
		return kodeProdi;
	}

	public void setKodeProdi(String kodeProdi) {
		this.kodeProdi = kodeProdi;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNamaLengkap() {
		return namaLengkap;
	}

	public void setNamaLengkap(String namaLengkap) {
		this.namaLengkap = namaLengkap;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public String getDosenWali() {
		return dosenWali;
	}

	public void setDosenWali(String dosenWali) {
		this.dosenWali = dosenWali;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public String getAgama() {
		return agama;
	}

	public void setAgama(String agama) {
		this.agama = agama;
	}

	public String getStatusKawin() {
		return statusKawin;
	}

	public void setStatusKawin(String statusKawin) {
		this.statusKawin = statusKawin;
	}

	public String getAlamatSurabaya() {
		return alamatSurabaya;
	}

	public void setAlamatSurabaya(String alamatSurabaya) {
		this.alamatSurabaya = alamatSurabaya;
	}

	public String getKodePos() {
		return kodePos;
	}

	public void setKodePos(String kodePos) {
		this.kodePos = kodePos;
	}

	public String getNoTelp() {
		return noTelp;
	}

	public void setNoTelp(String noTelp) {
		this.noTelp = noTelp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGolonganDarah() {
		return golonganDarah;
	}

	public void setGolonganDarah(String golonganDarah) {
		this.golonganDarah = golonganDarah;
	}

	public String getKewarganegaraan() {
		return kewarganegaraan;
	}

	public void setKewarganegaraan(String kewarganegaraan) {
		this.kewarganegaraan = kewarganegaraan;
	}

	public String getProdi() {
		return prodi;
	}

	public void setProdi(String prodi) {
		this.prodi = prodi;
	}

	public Double getIpk() {
		return ipk;
	}

	public void setIpk(Double ipk) {
		this.ipk = ipk;
	}

	public Integer getSksLulus() {
		return sksLulus;
	}

	public void setSksLulus(Integer sksLulus) {
		this.sksLulus = sksLulus;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public Integer getSemesterKe() {
		return semesterKe;
	}

	public void setSemesterKe(Integer semesterKe) {
		this.semesterKe = semesterKe;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
