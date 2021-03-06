package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.PegawaiDao;
import id.its.akademik.dao.cache.MahasiswaCache;
import id.its.akademik.dao.cache.PegawaiCache;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.FRSFoto;
import id.its.akademik.domain.Foto;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Komentar;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.PeringkatIPD;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.WaktuPermanenNilai;
import id.its.integra.security.Secured;

@Path("/dosen")
//@Secured(roles = { "Dosen" }, module = "AKAD")
public class DosenEndpoint extends BaseEndpoint {

	private MahasiswaDao mahasiswaDao;
	private PegawaiDao pegawaiDao;
	private AkademikDao akademikDao;
	private MahasiswaCache mahasiswaCache;
	private PegawaiCache pegawaiCache;

	public void setMahasiswaDao(MahasiswaDao mhsDao) {
		this.mahasiswaDao = mhsDao;
	}

	public void setPegawaiDao(PegawaiDao pegawaiDao) {
		this.pegawaiDao = pegawaiDao;
	}

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}
	
	public void setMahasiswaCache(MahasiswaCache mahasiswaCache) {
		this.mahasiswaCache = mahasiswaCache;
	}
	
	public void setPegawaiCache(PegawaiCache pegawaiCache) {
		this.pegawaiCache = pegawaiCache;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public Response getListDosen(@QueryParam("nama") String nama) {

		List<Pegawai> listPegawai = pegawaiDao.getListPegawai(nama);
		return Response.ok(listPegawai).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getDosen(@Context SecurityContext securityContext) {

		String nip = securityContext.getUserPrincipal().getName();
		
		Pegawai p =null;
		if(this.pegawaiCache.checkKey("Pegawai_"+nip)==true)
		{
			p=this.pegawaiCache.getDataPegawai("Pegawai_"+nip);
		}
		else 
		{
			 p = this.pegawaiDao.getPegawai(nip);
			 if (p == null) {
				 p = this.pegawaiDao.getPegawaiByNIPBaru(nip);
			}
			 
				 this.pegawaiCache.setDataPegawai("Pegawai_"+nip, p);
		}

		return Response.ok(toJson(p)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nip}")
	public Response getDosen(@PathParam("nip") String nip) {
		
		Pegawai p;
		if(this.pegawaiCache.checkKey("Pegawai_"+nip)==true)
		{
			p=this.pegawaiCache.getDataPegawai("Pegawai_"+nip);
		}
		else 
		{
			 p = this.pegawaiDao.getPegawai(nip);
			 if (p == null) {
					p = this.pegawaiDao.getPegawaiByNIPBaru(nip);
			}

			 
				 this.pegawaiCache.setDataPegawai("Pegawai_"+nip, p);
		}
		
		
		return Response.ok(p).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nip}/mhswali")
	public Response getMahasiswaWaliByNIP(@PathParam("nip") String nip, @QueryParam("departemen") String kodeJurusan) {

		List<Mahasiswa> listMhs=null;
		if(this.mahasiswaCache.checkKey("Mahasiswa_"+nip+"_"+kodeJurusan)==true)
		{
			listMhs=this.mahasiswaCache.getMahasiswaWali("Mahasiswa_"+nip+"_"+kodeJurusan);
		}
		else
		{
			listMhs = this.mahasiswaDao.getMahasiswaWaliByNip(nip);
			 if(listMhs!=null&&!listMhs.isEmpty())
			 {
				this.mahasiswaCache.setMahasiswaWali("Mahasiswa_"+nip+"_"+kodeJurusan, listMhs); 
			 }
		}

		return Response.ok(toJson(listMhs)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("mhswali")
	public Response getMahasiswaWali(@Context SecurityContext securityContext, @QueryParam("prodi") String prodi,
			@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester) {

		String nip = securityContext.getUserPrincipal().getName();

		List<FRSFoto> listFRS = this.mahasiswaDao.getMahasiswaWali(nip, prodi, tahun, semester);

		for (FRSFoto frs : listFRS) {
			String status = this.mahasiswaDao.getStatusMahasiswa(frs.getNrp());
			Boolean disetujui = this.akademikDao.getFRSDisetujui(frs.getNrp(), tahun, semester);
			frs.setStatusKeaktifan(status);
			frs.setDisetujui(disetujui);
		}

		return Response.ok(toJson(listFRS)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/frs")
	public Response getFRSMahasiswaWali(@Context SecurityContext securityContext, @QueryParam("prodi") String kodeProdi,
			@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester) {

		String nip = securityContext.getUserPrincipal().getName();

		if (nip == null || kodeProdi == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		List<FRS> listFRS = this.akademikDao.getListFRS(kodeProdi, nip, tahun, semester);

		return Response.ok(toJson(listFRS)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nip}/frs")
	public Response getFRSMahasiswaWaliByQuery(@QueryParam("q") String query, @PathParam("nip") String nip,
			@QueryParam("departemen") String kodeJurusan, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {

		List<FRS> listFRS = this.akademikDao.getListFRS(kodeJurusan, nip, tahun, semester);

		return Response.ok(toJson(listFRS)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nip}/ipd")
	public Response getIPD(@PathParam("nip") String nip, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("matkul") Integer kodemk) {

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nip}/waktu-permanen-nilai")
	public Response getWaktuPermanenNilai(@PathParam("nip") String nip, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {

		List<WaktuPermanenNilai> list = this.akademikDao.getWaktuPermanenNilai(nip, tahun, semester);

		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("matakuliah/{id-mk}/kelas/{id-kelas}/komentar-ipm")
	public Response getKomentarIPM(@Context SecurityContext securityContext, @PathParam("id-kelas") String idKelas,
			@PathParam("id-mk") String idMk, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {

		String nip = securityContext.getUserPrincipal().getName();
		List<Komentar> list = this.akademikDao.getKomentarIPM(nip, idMk, tahun, semester, idKelas);

		return Response.ok(toJson(list)).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/matakuliah/{id-mk}/kelas/{id-kelas}/komentar-ipd")
	public Response getKomentarIPD(@Context SecurityContext securityContext, @PathParam("id-kelas") String idKelas,
			@PathParam("id-mk") String idMk, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {

		String nip = securityContext.getUserPrincipal().getName();

		List<Komentar> list = this.akademikDao.getKomentarIPD(nip, idMk, tahun, semester, idKelas);

		return Response.ok(toJson(list)).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/foto")
	public Response getFoto(@Context SecurityContext securityContext) {
		String nip = securityContext.getUserPrincipal().getName();

		Foto fm = this.pegawaiDao.getFotoDosen(nip);

		if (fm != null && fm.getFoto() != null) {
			return Response.ok(toJson(fm)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jadwal")
	public Response getJadwal(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi,
			@QueryParam("lang") String bahasa) {
		String nip = securityContext.getUserPrincipal().getName();
		if (nip == null || nip.isEmpty() || tahun == null || semester == null || kodeProdi == null) {
			return Response.status(400).build();
		}
		
		List<JadwalKuliah> jadwal=null;
		if(this.pegawaiCache.checkKey("DosenJadwal")==true)
		{
			jadwal=this.pegawaiCache.getJadwalDosen("DosenJadwal");
		}
		else
		{
			jadwal = this.pegawaiDao.getJadwalAjar(nip, tahun, semester, kodeProdi, bahasa);
			if(jadwal!=null&&!jadwal.isEmpty())
			{
				this.pegawaiCache.setJadwalDosen("DosenJadwal", jadwal);
			}
		}
		 
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jadwal-selanjutnya")
	public Response getJadwalSelanjutnya(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("hari") Integer idHari,
			@QueryParam("prodi") String kodeProdi, @QueryParam("lang") String bahasa) {
		String nip = securityContext.getUserPrincipal().getName();
		if (nip == null || nip.isEmpty() || tahun == null || semester == null || idHari == null) {
			return Response.status(400).build();
		}

		List<JadwalKuliah> jadwal = this.pegawaiDao.getJadwalAjarSelanjutnya(nip, tahun, semester, idHari, kodeProdi,
				bahasa);
		if (jadwal.isEmpty())
			jadwal = this.pegawaiDao.getJadwalAjarSelanjutnya(nip, tahun, semester, 0, kodeProdi, bahasa);
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/periode")
	public Response getListPeriode(@Context SecurityContext securityContext, @QueryParam("prodi") String kodeProdi) {
		String nip = securityContext.getUserPrincipal().getName();
		if (nip == null || nip.isEmpty() || kodeProdi == null) {
			return Response.status(400).build();
		}

		List<PeriodeMahasiswa> periode = this.pegawaiDao.getPeriode(nip, kodeProdi);
		return Response.ok(toJson(periode)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/peringkat-ipd")
	public Response getPeringkatIPD(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi,
			@DefaultValue("") @QueryParam("order") String urutkan, @QueryParam("lang") String bahasa) {

		if (kodeProdi == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		List<PeringkatIPD> peringkat = this.pegawaiDao.getPeringkatIPD(kodeProdi, tahun, semester, urutkan, bahasa);

		return Response.ok(toJson(peringkat)).build();

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ipd")
	public Response getIPD(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi,
			@DefaultValue("") @QueryParam("order") String urutkan, @QueryParam("lang") String bahasa) {
		String nip = securityContext.getUserPrincipal().getName();

		if (nip == null || nip.isEmpty() || kodeProdi == null) {
			return Response.status(400).build();
		}

		List<PeringkatIPD> peringkat = this.pegawaiDao.getIPD(nip, kodeProdi, tahun, semester, urutkan, bahasa);

		return Response.ok(toJson(peringkat)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/matakuliah/{id}/kelas/{id-kelas}/ipd")
	public Response getIPDDetail(@Context SecurityContext securityContext, @PathParam("id") String idMk,
			@PathParam("id-kelas") String idKelas, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi) {
		String nip = securityContext.getUserPrincipal().getName();

		if (nip == null || nip.isEmpty() || kodeProdi == null) {
			return Response.status(400).build();
		}

		PeringkatIPD ipd = this.pegawaiDao.getIPDSpesifik(nip, kodeProdi, tahun, semester, idMk, idKelas);
		return Response.ok(toJson(ipd)).build();

	}
}
