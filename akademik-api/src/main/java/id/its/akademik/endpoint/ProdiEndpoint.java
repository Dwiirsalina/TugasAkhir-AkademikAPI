package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import id.its.akademik.dao.KelembagaanDao;
import id.its.akademik.dao.KurikulumDao;
import id.its.akademik.dao.cache.AkademikCache;
import id.its.akademik.dao.cache.KelembagaanCache;
import id.its.akademik.dao.cache.KurikulumCache;
//import id.its.akademik.dao.cache.KurikulumCache;
import id.its.akademik.domain.ErrorMessage;
import id.its.akademik.domain.IPD;
import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.Periode;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;
import id.its.akademik.service.AkademikService;
import id.its.integra.security.Secured;

@Path("/prodi")
public class ProdiEndpoint extends BaseEndpoint {

	private KelembagaanDao kelembagaanDao;
	private KelembagaanCache kelembagaanCache;
	private KurikulumDao kurikulumDao;
	private KurikulumCache kurikulumCache;
	private AkademikService akademikService;
	private AkademikCache akademikCache;
	

	public void setAkademikService(AkademikService akademikService) {
		this.akademikService = akademikService;
	}

	public void setKurikulumDao(KurikulumDao kurikulumDao) {
		this.kurikulumDao = kurikulumDao;
	}

	public void setKelembagaanDao(KelembagaanDao kelembagaanDao) {
		this.kelembagaanDao = kelembagaanDao;
	}

	public void setKelembagaanCache(KelembagaanCache kelembagaanCache)
	{
		this.kelembagaanCache=kelembagaanCache;
	}

	public void setKurikulumCache(KurikulumCache kurikulumCache)
	{
		this.kurikulumCache=kurikulumCache;
	}
	
	public void setAkademikCache(AkademikCache akademikCache)
	{
		this.akademikCache=akademikCache;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getListProdi(@QueryParam("q") String query, @DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("fields") String fields) {
		
		List<ProgramStudi> listProdi=null;
		if(this.kelembagaanCache.checkKey("ProgramStudi")==true)
		{
			listProdi=this.kelembagaanCache.getProdi("ProgramStudi");
		}
		else
		{
			listProdi = this.kelembagaanDao.getProgramStudi(null, null, null, query, page, perPage);
			if(listProdi!=null&&!listProdi.isEmpty())
			{
				this.kelembagaanCache.setProdi("ProgramStudi", listProdi);
			}
		}
		return Response.ok(toJson(listProdi, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getProdi(@PathParam("id") String idProdi, @QueryParam("fields") String fields) {
		List<ProgramStudi> listProdi=null;
		if(this.kelembagaanCache.checkKey("ProgramStudi")==true)
		{
			listProdi=this.kelembagaanCache.getProdi("ProgramStudi");
		}
		else
		{
			listProdi = this.kelembagaanDao.getProgramStudi(idProdi, null, null, null, 0, 100);
			if(listProdi!=null&&!listProdi.isEmpty())
			{
				this.kelembagaanCache.setProdi("ProgramStudi", listProdi);
			}
		}
		return Response.ok(toJson(listProdi, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ajar")
	public Response getProdiAjar() {
		List<ProdiAjar> listProdi=null;
		if(this.kelembagaanCache.checkKey("ProdiAjar")==true)
		{
			listProdi=this.kelembagaanCache.getProdiAjar("ProdiAjar");
		}
		else
		{
			listProdi = this.kelembagaanDao.getProdiAjar();
			if(listProdi!=null&&!listProdi.isEmpty())
			{
				this.kelembagaanCache.setProdiAjar("ProdiAjar", listProdi);
			}
		}
		
		return Response.ok(toJson(listProdi)).build();
	}

	// prodi/51100/kelas?isPengayaan = true -> pengayaan
	// prodi/51100/kelas?isPengayaan = false -> dep
	// prodi/tpb/kelas?isPengayaan = false -> mku
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/kelas")
	public Response getKelasDitawarkan(@PathParam("id") String idProdi, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("fields") String fields,
			@DefaultValue("nama") @QueryParam("order") String urutkan, @QueryParam("lang") String bahasa,
			@DefaultValue("0") @QueryParam("is-pengayaan") Boolean isPengayaan) {
		if (idProdi == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		List<Kelas> listKelas = this.akademikService.getKelasDitawarkan(idProdi, tahun, semester, page, perPage,
				urutkan, bahasa, isPengayaan);
		Integer tahunKurikulum = this.akademikService.getTahunKurikulum(tahun);

		for (Kelas ke : listKelas) {
			List<String> ket = this.akademikService.getKeteranganKodeJurusanBoleh(ke.getIdMk(), ke.getTahun(), semester,
					tahunKurikulum, ke.getKelas());
			StringBuilder s = new StringBuilder();
			for (String keterangan : ket) {
				s.append(keterangan);
				s.append(" ");
			}
			ke.setKeterangan(s.toString());
		}

		return Response.ok(toJson(listKelas)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/matakuliah")
	public Response getMataKuliah(@PathParam("id") String idProdi, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {
		if (idProdi == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}
		
		List<MataKuliah> listMk=null;
		if(this.akademikCache.checkKey("MataKuliah_"+idProdi+"_"+tahun+"_"+semester)==true)
		{
			listMk=this.akademikCache.getMataKuliah("MataKuliah_"+idProdi+"_"+tahun+"_"+semester);
		}
		else
		{
			listMk = this.akademikService.getMataKuliah(idProdi, tahun, semester);
			 if(listMk!=null&&!listMk.isEmpty())
			 {
				this.akademikCache.setMatakuliah("MataKuliah_"+idProdi+"_"+tahun+"_"+semester, listMk); 
			 }
		}
		return Response.ok(toJson(listMk)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/kurikulum/matakuliah/{id-mk}/syarat")
	public Response getMataKuliahSyarat(@PathParam("id") String idProdi, @PathParam("id-mk") String idMk,
			@QueryParam("semester") Integer semester, @QueryParam("tahun-kurikulum") String tahunKurikulum,
			@QueryParam("lang") String bahasa) {
		if (idProdi == null) {
			return Response.status(400).build();
		}
		
		List<MataKuliahSyarat> listMk=null;
		if(this.akademikCache.checkKey("SyaratMataKuliah_"+idProdi+"_"+idMk+"_"+semester+"_"+tahunKurikulum)==true)
		{
			listMk=this.akademikCache.getMataKuliahSyarat("SyaratMataKuliah_"+idProdi+"_"+idMk+"_"+semester+"_"+tahunKurikulum);
		}
		else
		{
			listMk = this.akademikService.getSpesifikMataKuliah(idProdi, idMk, semester, bahasa);
			 if(listMk!=null&&!listMk.isEmpty())
			 {
				this.akademikCache.setMataKuliahSyarat("SyaratMataKuliah_"+idProdi+"_"+idMk+"_"+semester+"_"+tahunKurikulum, listMk); 
			 }
		}

		for (MataKuliahSyarat mk : listMk) {
			List<MataKuliah> list = this.akademikService.getSyaratMataKuliah(tahunKurikulum, mk.getProdi(),
					mk.getKode(), bahasa);
			mk.setSyarat(list);
		}

		return Response.ok(toJson(listMk)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/matakuliah/{id-mk}/kelas/{kode-kelas}/peserta")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getPesertaKelas(@PathParam("id") String idProdi, @PathParam("id-mk") String idMK,
			@PathParam("kode-kelas") String kodeKelas, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {
		if (idProdi == null || idMK == null || kodeKelas == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}
		
		List<MahasiswaFoto> listMhs=null;
		if(this.akademikCache.checkKey("MahasiswaFoto_"+idProdi+"_"+idMK+"_"+tahun+"_"+semester)==true)
		{
			listMhs=this.akademikCache.getPesertaKelas("MahasiswaFoto_"+idProdi+"_"+idMK+"_"+tahun+"_"+semester);
		}
		else
		{
			listMhs = this.akademikService.getPesertaKelas(idProdi, idMK, kodeKelas, tahun, semester);
			 if(listMhs!=null&&!listMhs.isEmpty())
			 {
				this.akademikCache.setPesertaKelas("MahasiswaFoto_"+idProdi+"_"+idMK+"_"+tahun+"_"+semester, listMhs); 
			 }
		}
		
		return Response.ok(toJson(listMhs, "id,nrp,nama")).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/matakuliah/{id-mk}/kelas/{kode-kelas}/kapasitas")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getKapasitasKelas(@PathParam("id") String idProdi, @PathParam("id-mk") String idMK,
			@PathParam("kode-kelas") String kodeKelas, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {
		if (idProdi == null || idMK == null || kodeKelas == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		Integer kapasitas = this.akademikService.getKapasitasKelas(idProdi, idMK, kodeKelas, tahun, semester, tahun);

		return Response.ok(toJson(kapasitas)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/matakuliah/{id-mk}/kelas/{kode-kelas}/daya-tampung")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getDayaTampung(@PathParam("id") String idProdi, @PathParam("id-mk") String idMK,
			@PathParam("kode-kelas") String kodeKelas, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("tahun-kurikulum") Integer tahunKurikulum,
			@QueryParam("nrp") String nrp) {
		if (idProdi == null || idMK == null || kodeKelas == null || tahun == null || semester == null
				|| tahunKurikulum == null || nrp == null) {
			return Response.status(400).build();
		}
		String kodeJurusanBoleh = nrp.substring(0, 2) + nrp.substring(4, 7);

		List<Kelas> listKelas = this.akademikService.getDayaTampung(idProdi, idMK, tahun, semester, tahunKurikulum,
				kodeJurusanBoleh);
		Kelas kelas = null;
		if (!listKelas.isEmpty()) {
			kelas = listKelas.get(0);
		} else {
			ErrorMessage err = new ErrorMessage("Anda tidak diijinkan memilih kelas ini, silakan coba kelas lain");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}

		Integer jumlah = this.akademikService.getJumlahKuliah(idProdi, idMK, kodeKelas, tahun, semester, tahunKurikulum,
				kodeJurusanBoleh);
		if (jumlah >= kelas.getDayaTampung()) {
			ErrorMessage err = new ErrorMessage(
					"Kelas yang anda pilih telah melebihi kuota, silakan pilih kelas lain yang tersedia");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/periode")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getPeriode(@PathParam("id") String idProdi) {

		List<Periode> listPeriode = this.akademikService.getPeriodeByDepartemen(idProdi);

		return Response.ok(toJson(listPeriode)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/kurikulum")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getMatakuliahKurikulum(@PathParam("id") String idProdi, @QueryParam("tahun") Integer tahun,
			@QueryParam("lang") String bahasa) {
		if (idProdi == null || tahun == null) {
			return Response.status(400).build();
		}
		
		List<Kurikulum> list=null;
		if(this.kurikulumCache.checkKey("Kurikulum_"+idProdi+"_"+tahun)==true)
		{
			list=this.kurikulumCache.getMkKurikulum("Kurikulum_"+idProdi+"_"+tahun);
		}
		else
		{
			list = this.kurikulumDao.getKurikulum(idProdi, tahun);
			
			for (Kurikulum k : list) {
				List<MataKuliah> listMk = this.kurikulumDao.getMatakuliahKurikulum(k.getProdi(), k.getTahunKurikulum(),
						k.getSemesterKurikulum(), bahasa);
				k.setMataKuliah(listMk);
			
			}
			if(list!=null&&!list.isEmpty())
			{
				this.kurikulumCache.setMkKurikulum("Kurikulum_"+idProdi+"_"+tahun, list);
			}
		}
		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/tahun-kurikulum")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getTahunKurikulum(@PathParam("id") String idProdi) {
		List<Kurikulum> list= null;
		if(this.kurikulumCache.checkKey("Kurikulum_"+idProdi)==true)
		{
			list=this.kurikulumCache.getTahunKurikulum("Kurikulum_"+idProdi);
		}
		else
		{
			list = this.kurikulumDao.getTahunKurikulum(idProdi);
			if(list!=null&&!list.isEmpty())
			{
				this.kurikulumCache.setTahunKurikulum("Kurikulum_"+idProdi, list);
			}
		}
		return Response.ok(toJson(list)).build();
//		List<Kurikulum> list = this.kurikulumDao.getTahunKurikulum(idProdi);
//		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/ipd")
	// @Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getIPD(@PathParam("id") String idProdi, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {
		if (idProdi == null || tahun == null) {
			return Response.status(400).build();
		}

		List<IPD> listIPD = this.akademikService.getIPD(idProdi, tahun, semester);

		return Response.ok(toJson(listIPD)).build();
	}
}
