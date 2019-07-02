package id.its.akademik.domain;

import java.util.List;

public class KelasAmbil {

	private String id;
	private String kode;
	private String mataKuliah;
	private String kodeProdi;
	private Integer sks;
	private String kelas;
	private String dosen;
	private String nilaiHuruf;
	private Integer semester;
	private String statusAmbil;
	private boolean canDelete = true;
	private List<JadwalKuliah> jadwal;

	public KelasAmbil() {

	}

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

	public String getMataKuliah() {
		return mataKuliah;
	}

	public void setMataKuliah(String mataKuliah) {
		this.mataKuliah = mataKuliah;
	}

	public String getKodeProdi() {
		return kodeProdi;
	}

	public void setKodeProdi(String kodeProdi) {
		this.kodeProdi = kodeProdi;
	}

	public Integer getSks() {
		return sks;
	}

	public void setSks(Integer sks) {
		this.sks = sks;
	}

	public String getDosen() {
		return dosen;
	}

	public void setDosen(String dosen) {
		this.dosen = dosen;
	}

	public String getKelas() {
		return kelas;
	}

	public void setKelas(String kelas) {
		this.kelas = kelas;
	}

	public String getNilaiHuruf() {
		return nilaiHuruf;
	}

	public void setNilaiHuruf(String nilaiHuruf) {
		this.nilaiHuruf = nilaiHuruf;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public String getStatusAmbil() {
		return statusAmbil;
	}

	public void setStatusAmbil(String statusAmbil) {
		this.statusAmbil = statusAmbil;
	}

	public List<JadwalKuliah> getJadwal() {
		return jadwal;
	}

	public void setJadwal(List<JadwalKuliah> jadwal) {
		this.jadwal = jadwal;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

}
