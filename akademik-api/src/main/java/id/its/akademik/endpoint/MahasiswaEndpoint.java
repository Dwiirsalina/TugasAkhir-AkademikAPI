package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.cache.AkademikCache;
import id.its.akademik.dao.cache.MahasiswaCache;
import id.its.akademik.domain.Akademik;
import id.its.akademik.domain.DaftarNilai;
import id.its.akademik.domain.ErrorMessage;
import id.its.akademik.domain.FRS;
import id.its.akademik.domain.Foto;
import id.its.akademik.domain.JadwalKuliah;
import id.its.akademik.domain.Jawaban;
import id.its.akademik.domain.KeaktifanMahasiswa;
import id.its.akademik.domain.KemajuanStudi;
import id.its.akademik.domain.Kuesioner;
import id.its.akademik.domain.KuesionerDosen;
import id.its.akademik.domain.KuesionerMK;
import id.its.akademik.domain.ListKuesioner;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pegawai;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.Pembayaran;
import id.its.akademik.domain.PeriodeMahasiswa;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;
import id.its.akademik.domain.Sekarang;
import id.its.akademik.domain.Transkrip;
import id.its.akademik.domain.WaliMahasiswa;
import id.its.akademik.service.AkademikService;
import id.its.integra.security.Secured;

@Path("/mahasiswa")
public class MahasiswaEndpoint extends BaseEndpoint {

	private MahasiswaDao mahasiswaDao;
	private AkademikDao akademikDao;
	private AkademikService akademikService;
	private MahasiswaCache mahasiswaCache;
	private AkademikCache akademikCache;
	private Logger log = LoggerFactory.getLogger(MahasiswaEndpoint.class);

	public void setMahasiswaDao(MahasiswaDao mhsDao) {
		this.mahasiswaDao = mhsDao;
	}

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}

	public void setAkademikService(AkademikService akademikService) {
		this.akademikService = akademikService;
	}
	
	public void setMahasiswaCache(MahasiswaCache mahasiswaCache) {
		this.mahasiswaCache = mahasiswaCache;
	}
	
	public void setAkademikCache(AkademikCache akademikCache) {
		this.akademikCache = akademikCache;
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response getListMahasiswa(@QueryParam("q") String query, @QueryParam("status") String status,
			@QueryParam("prodi") String kodeProdi, @QueryParam("angkatan") Integer angkatan,
			@DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("order") String order) {

		List<MahasiswaFoto> listMhs = null;
		if (status != null && !status.isEmpty()) {
			if (status.equalsIgnoreCase("aktif")) {
				listMhs = this.mahasiswaDao.getListMahasiswa(null, null, query, kodeProdi, angkatan, page, perPage,
						order);
			} else if (status.equalsIgnoreCase("nonaktif")) {
				listMhs = this.mahasiswaDao.getListMahasiswaNonAktif(null, null, query, kodeProdi, angkatan, page,
						perPage, order);
			}
		} else {
			listMhs = this.mahasiswaDao.getListMahasiswa(null, null, query, kodeProdi, angkatan, page, perPage, order);
		}

		for (MahasiswaFoto m : listMhs) {
			m.setSemesterKe(this.mahasiswaDao.getSemesterKe(m.getId()));
		}

		return Response.ok(toJson(listMhs)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD")
	public Response getMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<Mahasiswa> mhs=null;
		if(this.mahasiswaCache.checkKey("Mahasiswa_"+nrp)==true)
		{
			mhs=this.mahasiswaCache.getMahasiswaData("Mahasiswa_"+nrp);
		}
		else
		{
			mhs = this.mahasiswaDao.getMahasiswa(nrpLama);
//			if (!mhs.isEmpty())
//				mhs.get(0).setSemesterKe(this.mahasiswaDao.getSemesterKe(nrpLama));
			if(mhs!=null&&!mhs.isEmpty())
			{
				mhs.get(0).setSemesterKe(this.mahasiswaDao.getSemesterKe(nrpLama));
				this.mahasiswaCache.setMahasiswaData("Mahasiswa_"+nrp, mhs);
			}
		}
		return Response.ok(toJson(mhs)).build();
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD")
	public Response getBiodataMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<MahasiswaFoto> mhs = this.mahasiswaDao.getMahasiswaFoto(nrpLama);
		if (!mhs.isEmpty()) {
			MahasiswaFoto mahasiswa = mhs.get(0);
			mahasiswa.setSemesterKe(this.mahasiswaDao.getSemesterKe(nrpLama));
		}
		return Response.ok(toJson(mhs)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/pekerjaan")
	@Secured(roles = { "Dosen", "Wali" })
	public Response getPekerjaanMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
//		List<Pekerjaan> kerja = this.mahasiswaDao.getPekerjaanMahasiswa(nrpLama);
		List<Pekerjaan> kerja=null;
		if(this.mahasiswaCache.checkKey("Pekerjaan_"+nrp)==true)
		{
			kerja=this.mahasiswaCache.getPekerjaan("Pekerjaan_"+nrp);
		}
		else
		{
			kerja = this.mahasiswaDao.getPekerjaanMahasiswa(nrpLama);
			if(kerja!=null&&!kerja.isEmpty())
			{
				this.mahasiswaCache.setPekerjaan("Pekerjaan_"+nrp, kerja);
			}
		} 

		if (kerja == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(toJson(kerja)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pekerjaan")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getPekerjaanMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<Pekerjaan> kerja=null;
		if(this.mahasiswaCache.checkKey("Pekerjaan_"+nrp)==true)
		{
			kerja=this.mahasiswaCache.getPekerjaan("Pekerjaan_"+nrp);
		}
		else
		{
			kerja = this.mahasiswaDao.getPekerjaanMahasiswa(nrpLama);
			if(kerja!=null&&!kerja.isEmpty())
			{
				this.mahasiswaCache.setPekerjaan("Pekerjaan_"+nrp, kerja);
			}
		} 

		if (kerja == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(toJson(kerja)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/ortu")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response getOrtuMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<OrangTua> ortu=null;
		if(this.mahasiswaCache.checkKey("OrangTua_"+nrp)==true)
		{
			ortu=this.mahasiswaCache.getOrtuMhs("OrangTua_"+nrp);
		}
		else
		{
			ortu = this.mahasiswaDao.getOrtuMahasiswa(nrpLama);
			if(ortu!=null&&!ortu.isEmpty())
			{
				this.mahasiswaCache.setOrtuMhs("OrangTua_"+nrp, ortu);
			}
		}
		if (ortu == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(toJson(ortu)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ortu")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getOrtuMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
//		List<OrangTua> ortu = this.mahasiswaDao.getOrtuMahasiswa(nrpLama);
		List<OrangTua> ortu=null;
		if(this.mahasiswaCache.checkKey("OrangTua_"+nrp)==true)
		{
			ortu=this.mahasiswaCache.getOrtuMhs("OrangTua_"+nrp);
		}
		else
		{
			ortu = this.mahasiswaDao.getOrtuMahasiswa(nrpLama);
			if(ortu!=null&&!ortu.isEmpty())
			{
				this.mahasiswaCache.setOrtuMhs("OrangTua_"+nrp, ortu);
			}
		}

		if (ortu == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(toJson(ortu)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/akademik")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD")
	public Response getAkademikMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<Akademik> akad=null;
		if(this.mahasiswaCache.checkKey("Akademik_"+nrp)==true)
		{
			akad=this.mahasiswaCache.getAkademikMhs("Akademik_"+nrp);
		}
		else
		{
			akad = this.mahasiswaDao.getAkademikMahasiswa(nrpLama);
			if(akad!=null&&!akad.isEmpty())
			{
				this.mahasiswaCache.setAkademikMhs("Akademik_"+nrp, akad);
			}
		}
		
		if (akad == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(toJson(akad)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/wali")
	@Secured(roles = { "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getWaliMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		WaliMahasiswa wali = this.mahasiswaDao.getWaliMahasiswa(nrpLama);

		if (wali == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(toJson(wali)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/akademik")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getAkademikMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		List<Akademik> akad=null;
		if(this.mahasiswaCache.checkKey("Akademik_"+nrp)==true)
		{
			akad=this.mahasiswaCache.getAkademikMhs("Akademik_"+nrp);
		}
		else
		{
			akad = this.mahasiswaDao.getAkademikMahasiswa(nrpLama);
			if(akad!=null&&!akad.isEmpty())
			{
				this.mahasiswaCache.setAkademikMhs("Akademik_"+nrp, akad);
			}
		}

		if (akad == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(toJson(akad)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/keaktifan")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD")
	public Response getKeaktifanMahasiswa(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		List<KeaktifanMahasiswa> listAktif = this.mahasiswaDao.getKeaktifanMahasiswa(nrpLama);

		return Response.ok(toJson(listAktif)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/keaktifan")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getKeaktifanMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		List<KeaktifanMahasiswa> listAktif = this.mahasiswaDao.getKeaktifanMahasiswa(nrpLama);

		return Response.ok(toJson(listAktif)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/transkrip")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD")
	public Response getTranskripMahasiswa(@PathParam("nrp") String nrp, @QueryParam("lang") String bahasa) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		Transkrip transkrip;
		if(this.mahasiswaCache.checkKey("Transkrip_"+nrp)==true)
		{
			 transkrip=this.mahasiswaCache.getTranskripMhs("Transkrip_"+nrp);
		}
		else 
		{
			 transkrip = this.mahasiswaDao.getTranskrip(nrpLama);
			 if (transkrip == null) {
					return Response.status(Status.NOT_FOUND).build();
				}
				transkrip.setNrp(nrpLama);
				akademikService.setTranskrip(nrpLama, bahasa, transkrip);

				transkrip.setSksLulus(this.mahasiswaDao.getSksLulus(nrpLama));
				transkrip.setSksTempuh(this.mahasiswaDao.getSksTempuh(nrpLama));
			 if (transkrip!=null)
			 {
				 this.mahasiswaCache.setTranskripMhs("Transkrip_"+nrp, transkrip);
			 }
		}

		
//		Transkrip transkrip = this.mahasiswaDao.getTranskrip(nrpLama);
//		if (transkrip == null) {
//			return Response.status(Status.NOT_FOUND).build();
//		}
//		transkrip.setNrp(nrpLama);
//		akademikService.setTranskrip(nrpLama, bahasa, transkrip);
//
//		transkrip.setSksLulus(this.mahasiswaDao.getSksLulus(nrpLama));
//		transkrip.setSksTempuh(this.mahasiswaDao.getSksTempuh(nrpLama));

		return Response.ok(toJson(transkrip)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transkrip")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getTranskripMahasiswa(@Context SecurityContext securityContext, @QueryParam("lang") String bahasa) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		Transkrip transkrip;
		if(this.mahasiswaCache.checkKey("Transkrip_"+nrp)==true)
		{
			transkrip=this.mahasiswaCache.getTranskripMhs("Transkrip_"+nrp);
		}
		else 
		{
			transkrip = this.mahasiswaDao.getTranskrip(nrpLama);
			if (transkrip == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			transkrip.setNrp(nrpLama);
			akademikService.setTranskrip(nrpLama, bahasa, transkrip);

			transkrip.setSksLulus(this.mahasiswaDao.getSksLulus(nrpLama));
			transkrip.setSksTempuh(this.mahasiswaDao.getSksTempuh(nrpLama));
			 
			this.mahasiswaCache.setTranskripMhs("Transkrip_"+nrp, transkrip);
		}
		return Response.ok(toJson(transkrip)).build();
	}

	@GET
	@Produces("image/jpeg")
	@Path("/{nrp}/foto")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD")
	public Response getFotoMahasiswa(@PathParam("nrp") String nrp) {

		if (nrp == null) {
			return Response.status(400).build();
		}

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		Foto fm = this.mahasiswaDao.getFotoMahasiswa(nrpLama);

		if (fm != null && fm.getFoto() != null) {
			return Response.ok(toJson(fm.getFoto())).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/foto")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getFotoMahasiswa(@Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		Foto fm = this.mahasiswaDao.getFotoMahasiswa(nrpLama);

		if (fm != null && fm.getFoto() != null) {
			return Response.ok(toJson(fm)).build();
		} else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/list-frs")
	@Secured(roles = { "Dosen" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getListFRSMahasiswaByNrp(@PathParam("nrp") String nrp) {

		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<FRS> list=null;
		if(this.akademikCache.checkKey("FRS_"+nrp)==true)
		{
			list=this.akademikCache.getListFrs("FRS_"+nrp);
		}
		else
		{
			list = this.akademikDao.getListFRS(nrpLama);
			
			for (FRS frs : list) {
				Integer sks = this.akademikDao.getSKSAmbil(nrpLama, frs.getTahun(), frs.getSemester());
				Boolean disetujui = this.akademikDao.getFRSDisetujui(nrpLama, frs.getTahun(), frs.getSemester());
				frs.setDisetujui(disetujui);
				frs.setSksAmbil(sks);
			}
			
			if(list!=null&&!list.isEmpty())
			{
				this.akademikCache.setListFrs("FRS_"+nrp, list);
			}
		}

		if (list == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		

		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list-frs")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getListFRSMahasiswa(@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<FRS> list=null;
		if(this.akademikCache.checkKey("FRS_"+nrp)==true)
		{
			list=this.akademikCache.getListFrs("FRS_"+nrp);
		}
		else
		{
			list = this.akademikDao.getListFRS(nrpLama);
			
			for (FRS frs : list) {
				Integer sks = this.akademikDao.getSKSAmbil(nrpLama, frs.getTahun(), frs.getSemester());
				Boolean disetujui = this.akademikDao.getFRSDisetujui(nrpLama, frs.getTahun(), frs.getSemester());
				frs.setDisetujui(disetujui);
				frs.setSksAmbil(sks);
			}
			
			if(list!=null&&!list.isEmpty())
			{
				this.akademikCache.setListFrs("FRS_"+nrp, list);
			}
		}
		
		if (list == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/frs")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response getFRSMahasiswa(@PathParam("nrp") String id, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("lang") String bahasa) {

		if (id == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}
		String nrp = this.mahasiswaDao.getNrpLama(id);
		
		FRS frs;
		if(this.akademikCache.checkKey("Frs_"+nrp+"_"+tahun+"_"+semester)==true)
		{
			 frs=this.akademikCache.getDataFrs("Frs_"+nrp+"_"+tahun+"_"+semester);
		}
		else
		{
			frs = this.akademikDao.getFRS(nrp, tahun, semester);

			if (frs == null) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				akademikService.setFRS(nrp, tahun, semester, bahasa, frs);
				this.akademikCache.setDataFrs("Frs_"+nrp+"_"+tahun+"_"+semester, frs);
			}
				
				
		}
		return Response.ok(toJson(frs)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/frs")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getFRSMahasiswa(@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester,
			@Context SecurityContext securityContext, @QueryParam("lang") String bahasa) {

		if (tahun == null || semester == null) {
			return Response.status(400).build();
		}

		String id = securityContext.getUserPrincipal().getName();
		String nrp = this.mahasiswaDao.getNrpLama(id);

		FRS frs;
		if(this.akademikCache.checkKey("Frs_"+nrp+"_"+tahun+"_"+semester)==true)
		{
			 frs=this.akademikCache.getDataFrs("Frs_"+nrp+"_"+tahun+"_"+semester);
		}
		else
		{
			frs = this.akademikDao.getFRS(nrp, tahun, semester);

			if (frs == null) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				akademikService.setFRS(nrp, tahun, semester, bahasa, frs);
				this.akademikCache.setDataFrs("Frs_"+nrp+"_"+tahun+"_"+semester, frs);
			}
				
				
		}

		return Response.ok(toJson(frs)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/frs/ajukan")
	public Response getFRSAjukan(@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester) {

		if (tahun == null || semester == null) {
			return Response.status(400).build();
		}

		Boolean ajukan = this.akademikDao.tahapFRS(tahun, semester);

		return Response.ok(toJson(ajukan)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/cek-auth")
	public Response getJadwalKuesioner() {
		Boolean jadwal = this.akademikDao.cekJadwalKuesioner();
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/dosen")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getKuesionerDosen(@QueryParam("tahun") String tahun, @QueryParam("semester") Integer semester,
			@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (tahun == null || semester == null || nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<ListKuesioner> listKuesioner = this.mahasiswaDao.getListKuesionerDosen(nrpLama, tahun, semester);
		return Response.ok(toJson(listKuesioner)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/mata-kuliah")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getKuesionerMataKuliah(@QueryParam("tahun") String tahun, @QueryParam("semester") Integer semester,
			@QueryParam("lang") String bahasa, @Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (tahun == null || semester == null || nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<ListKuesioner> listKuesioner = this.mahasiswaDao.getListKuesionerMK(nrpLama, tahun, semester, bahasa);
		return Response.ok(toJson(listKuesioner)).build();

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/mata-kuliah")
	@Transactional
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response postKuesionerMataKuliah(KuesionerMK kuesioner, @Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();

		try {
			Boolean status = true;
			List<Sekarang> sekarang = this.akademikDao.getSekarang();
			Sekarang s = sekarang.get(0);
			String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
			status = status & this.akademikDao.postKuesionerMk(nrpLama, kuesioner, s.getTahun(), s.getSemester(),
					s.getTahunKurikulum());
			if (status) {
				return Response.status(Status.CREATED).build();
			} else {
				ErrorMessage err = new ErrorMessage("Terjadi kesalahan");
				return Response.status(Status.BAD_REQUEST).entity(err).build();
			}
		} catch (DataAccessException e) {
			log.error("Data access error in {}, cause {} ", e.getStackTrace()[0].getMethodName(), e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/dosen")
	@Transactional
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response postKuesionerDosen(KuesionerDosen kuesioner, @Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();

		try {
			Boolean status = true;
			List<Sekarang> sekarang = this.akademikDao.getSekarang();
			Sekarang s = sekarang.get(0);
			String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
			status = status & this.akademikDao.postKuesionerDosen(nrpLama, kuesioner, s.getTahun(), s.getSemester(),
					s.getTahunKurikulum());
			if (status) {
				return Response.status(Status.CREATED).build();
			} else {
				ErrorMessage err = new ErrorMessage("Terjadi kesalahan");
				return Response.status(Status.BAD_REQUEST).entity(err).build();
			}
		} catch (DataAccessException e) {
			log.error("Data access error in {}, cause {} ", e.getStackTrace()[0].getMethodName(), e.getCause());
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/dosen/pertanyaan")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getPertanyaanKuesionerDosen(@DefaultValue("in") @QueryParam("id-bahasa") String bahasa,
			@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		String jenjang = nrpLama.substring(4, 6);
		// PERKECUALIAN JENJANG # JIKA 11 MAKA MASUKKAN SBG JENJANG S2
		if (jenjang == "11")
			jenjang = "20";

		List<Sekarang> sekarang = this.akademikDao.getSekarang();
		Sekarang s = sekarang.get(0);
		
		List<Kuesioner> listPertanyaan=null;
		if(this.mahasiswaCache.checkKey("KuesionerDosen_"+jenjang)==true)
		{
			listPertanyaan=this.mahasiswaCache.getPertanyaanDosen("KuesionerDosen_"+jenjang);
		}
		else
		{
			listPertanyaan = this.mahasiswaDao.getPertanyaanDosen(jenjang, s.getTahunKurikulum(), bahasa);
			if(listPertanyaan!=null&&!listPertanyaan.isEmpty())
			{
				this.mahasiswaCache.setPertanyaanDosen("KuesionerDosen_"+jenjang, listPertanyaan);
			}
		}
		return Response.ok(toJson(listPertanyaan)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner/mata-kuliah/pertanyaan")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getPertanyaanKuesionerMataKuliah(@DefaultValue("in") @QueryParam("id-bahasa") String bahasa,
			@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();

		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		String jenjang = nrpLama.substring(4, 6);
		// PERKECUALIAN JENJANG # JIKA 11 MAKA MASUKKAN SBG JENJANG S2
		if (jenjang == "11")
			jenjang = "20";
		List<Sekarang> sekarang = this.akademikDao.getSekarang();
		Sekarang s = sekarang.get(0);

		String tahunKurikulum = s.getTahunKurikulum();
		
		List<Kuesioner> listPertanyaan=null;
		if(this.mahasiswaCache.checkKey("KuesionerMK"+jenjang)==true)
		{
			listPertanyaan=this.mahasiswaCache.getPertanyaanDosen("KuesionerMK"+jenjang);
		}
		else
		{
			listPertanyaan = this.mahasiswaDao.getPertanyaanMK(jenjang, tahunKurikulum, bahasa);
			if(listPertanyaan!=null&&!listPertanyaan.isEmpty())
			{
				this.mahasiswaCache.setPertanyaanDosen("KuesionerMK"+jenjang, listPertanyaan);
			}
		}

		for (Kuesioner kuesioner : listPertanyaan) {
			List<Jawaban> listJawaban = this.mahasiswaDao.getJawaban(jenjang, kuesioner.getId(), tahunKurikulum,
					bahasa);
			kuesioner.setJawaban(listJawaban);
		}

		return Response.ok(toJson(listPertanyaan)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kemajuan-studi")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getKemajuanStudi(@Context SecurityContext securityContext, @QueryParam("lang") String bahasa) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		
		List<KemajuanStudi> listKemajuan=null;
		if(this.mahasiswaCache.checkKey("KemajuanStudi_"+nrp)==true)
		{
			listKemajuan=this.mahasiswaCache.getKemajuanMhs("KemajuanStudi_"+nrp);
		}
		else
		{
			listKemajuan = this.mahasiswaDao.getKemajuanStudi(nrpLama);
			for (KemajuanStudi k : listKemajuan) {
				List<DaftarNilai> listTranskrip = this.mahasiswaDao.getTranskripMahasiswa(k.getTahun(), k.getIdSemester(),
						k.getSemesterAmbil(), k.getId(), bahasa);
				k.setTranskrip(listTranskrip);
			}
			if(listKemajuan!=null&&!listKemajuan.isEmpty())
			{
				this.mahasiswaCache.setKemajuanMhs("KemajuanStudi_"+nrp,listKemajuan);
			}
		}
		return Response.ok(toJson(listKemajuan)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/kemajuan-studi")
	@Secured(roles = { "Dosen", "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getKemajuanStudiMahasiswa(@Context SecurityContext securityContext, @PathParam("nrp") String nrp,
			@QueryParam("lang") String bahasa) {
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<KemajuanStudi> listKemajuan=null;
		if(this.mahasiswaCache.checkKey("KemajuanMahasiswa_"+nrp)==true)
		{
			listKemajuan=this.mahasiswaCache.getKemajuanMhs("KemajuanMahasiswa_"+nrp);
		}
		else
		{
			listKemajuan = this.mahasiswaDao.getKemajuanStudi(nrpLama);
			for (KemajuanStudi k : listKemajuan) {
				List<DaftarNilai> listTranskrip = this.mahasiswaDao.getTranskripMahasiswa(k.getTahun(), k.getIdSemester(),
						k.getSemesterAmbil(), k.getId(), bahasa);
				k.setTranskrip(listTranskrip);
			}
			if(listKemajuan!=null&&!listKemajuan.isEmpty())
			{
				this.mahasiswaCache.setKemajuanMhs("KemajuanMahasiswa_"+nrp,listKemajuan);
			}
		}

		return Response.ok(toJson(listKemajuan)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/periode")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getListSemester(@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<PeriodeMahasiswa> periode=null;
		if(this.mahasiswaCache.checkKey("PeriodeMahasiswa_"+nrp)==true)
		{
			periode=this.mahasiswaCache.getPeriodeMhs("PeriodeMahasiswa_"+nrp);
		}
		else
		{
			periode = this.mahasiswaDao.getPeriode(nrpLama);
			if(periode!=null&&!periode.isEmpty())
			{
				this.mahasiswaCache.setPeriodeMhs("PeriodeMahasiswa_"+nrp,periode);
			}
		}
		return Response.ok(toJson(periode)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/periode")
	@Secured(roles = { "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getListSemester(@PathParam("nrp") String nrp) {
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<PeriodeMahasiswa> periode=null;
		if(this.mahasiswaCache.checkKey("PeriodeMahasiswa_"+nrp)==true)
		{
			periode=this.mahasiswaCache.getPeriodeMhs("PeriodeMahasiswa_"+nrp);
		}
		else
		{
			periode = this.mahasiswaDao.getPeriode(nrpLama);
			if(periode!=null&&!periode.isEmpty())
			{
				this.mahasiswaCache.setPeriodeMhs("PeriodeMahasiswa_"+nrp,periode);
			}
		}
		return Response.ok(toJson(periode)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jadwal")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getJadwal(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi,
			@QueryParam("lang") String bahasa) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || kodeProdi == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		
		List<JadwalKuliah> jadwal=null;
		if(this.mahasiswaCache.checkKey("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester)==true)
		{
			jadwal=this.mahasiswaCache.getJadwalKuliah("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester);
		}
		else
		{
			jadwal = this.mahasiswaDao.getJadwalKuliah(nrpLama, tahun, semester, kodeProdi, bahasa);
			if(jadwal!=null&&!jadwal.isEmpty())
			{
				this.mahasiswaCache.setJadwalKuliah("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester, jadwal);
			}
		}		 
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/jadwal")
	@Secured(roles = { "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getJadwal(@PathParam("nrp") String nrp, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("prodi") String kodeProdi,
			@QueryParam("lang") String bahasa) {
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || kodeProdi == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<JadwalKuliah> jadwal=null;
		if(this.mahasiswaCache.checkKey("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester)==true)
		{
			jadwal=this.mahasiswaCache.getJadwalKuliah("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester);
		}
		else
		{
			jadwal = this.mahasiswaDao.getJadwalKuliah(nrpLama, tahun, semester, kodeProdi, bahasa);
			if(jadwal!=null&&!jadwal.isEmpty())
			{
				this.mahasiswaCache.setJadwalKuliah("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester, jadwal);
			}
		}
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jadwal-selanjutnya")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getJadwalSelanjutnya(@Context SecurityContext securityContext, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("hari") Integer idHari,
			@QueryParam("prodi") String kodeProdi, @QueryParam("lang") String bahasa) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || idHari == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		
		List<JadwalKuliah> jadwal=null;
		if(this.mahasiswaCache.checkKey("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari)==true)
		{
			jadwal=this.mahasiswaCache.getJadwalSelanjutnya("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari);
		}
		else
		{
			jadwal = this.mahasiswaDao.getJadwalKuliahSelanjutnya(nrpLama, tahun, semester, idHari,
					kodeProdi, bahasa);
			if(jadwal!=null&&!jadwal.isEmpty())
			{
				this.mahasiswaCache.setJadwalSelanjutnya("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari,jadwal);
			}
		}
		
		if (jadwal.isEmpty())
			jadwal = this.mahasiswaDao.getJadwalKuliahSelanjutnya(nrpLama, tahun, semester, 0, kodeProdi, bahasa);
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/jadwal-selanjutnya")
	@Secured(roles = { "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getJadwalSelanjutnya(@PathParam("nrp") String nrp, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @QueryParam("hari") Integer idHari,
			@QueryParam("prodi") String kodeProdi, @QueryParam("lang") String bahasa) {
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || idHari == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);

		List<JadwalKuliah> jadwal=null;
		if(this.mahasiswaCache.checkKey("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari)==true)
		{
			jadwal=this.mahasiswaCache.getJadwalSelanjutnya("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari);
		}
		else
		{
			jadwal = this.mahasiswaDao.getJadwalKuliahSelanjutnya(nrpLama, tahun, semester, idHari,
					kodeProdi, bahasa);
			if(jadwal!=null&&!jadwal.isEmpty())
			{
				this.mahasiswaCache.setJadwalSelanjutnya("JadwalKuliah_"+nrp+"_"+tahun+"_"+semester+"_"+idHari,jadwal);
			}
		}
		if (jadwal.isEmpty())
			jadwal = this.mahasiswaDao.getJadwalKuliahSelanjutnya(nrpLama, tahun, semester, 0, kodeProdi, bahasa);
		return Response.ok(toJson(jadwal)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/pembayaran")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getPembayaran(@Context SecurityContext securityContext) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		List<Pembayaran> list = akademikService.getPembayaran(nrp);
		return Response.ok(toJson(list)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/pembayaran")
	@Secured(roles = { "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getPembayaranMahasiswa(@PathParam("nrp") String nrp) {
		if (nrp == null || nrp.isEmpty()) {
			return Response.status(400).build();
		}
		List<Pembayaran> list = akademikService.getPembayaran(nrp);
		return Response.ok(toJson(list)).build();
	}
}
