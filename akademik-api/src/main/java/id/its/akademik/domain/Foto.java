package id.its.akademik.domain;

public class Foto {

	private String id;
	private String nrp;
	private String nama;
	private byte[] foto;
	
	public Foto() { }
	
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
	
	public String getNama() {
		return nama;
	}
	
	public void setNama(String nama) {
		this.nama = nama;
	}
	
	public byte[] getFoto() {
		return foto;
	}
	
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	
}
