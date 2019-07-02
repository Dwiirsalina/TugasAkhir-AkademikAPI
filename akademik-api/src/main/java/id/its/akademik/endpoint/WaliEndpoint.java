package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.WaliDao;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.WaliBiodata;
import id.its.integra.security.Secured;

@Path("/wali")
public class WaliEndpoint extends BaseEndpoint {

	private WaliDao waliDao;
	private MahasiswaDao mahasiswaDao;

	public void setWaliDao(WaliDao waliDao) {
		this.waliDao = waliDao;
	}

	public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
		this.mahasiswaDao = mahasiswaDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	@Secured(roles = { "Wali" }, module = "AKAD")
	public Response getWali(@Context SecurityContext securityContext) {

		String id = securityContext.getUserPrincipal().getName();
		WaliBiodata wali = this.waliDao.getWali(id);

		return Response.ok(toJson(wali)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{no-hp}")
	public Response getWaliByPonsel(@PathParam("no-hp") String noHp) {
		WaliBiodata wali = this.waliDao.getWaliByPhone(noHp);

		return Response.ok(toJson(wali)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mahasiswa")
	@Secured(roles = { "Wali" }, module = "AKAD")
	public Response getMahasiswa(@Context SecurityContext securityContext) {

		String id = securityContext.getUserPrincipal().getName();
		List<MahasiswaFoto> mahasiswa = this.waliDao.getMahasiswa(id);

		for (MahasiswaFoto m : mahasiswa) {
			m.setSemesterKe(this.mahasiswaDao.getSemesterKe(m.getId()));
		}
		return Response.ok(toJson(mahasiswa)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mahasiswa/{nrp}")
	public Response getMahasiswaByNrp(@PathParam("nrp") String nrp, @QueryParam("tanggal-lahir") String tanggalLahir) {

		if (nrp == null || tanggalLahir == null) {
			return Response.status(400).build();
		}
		List<MahasiswaFoto> mahasiswa = this.waliDao.getMahasiswaByNRP(nrp, tanggalLahir);

		for (MahasiswaFoto m : mahasiswa) {
			m.setSemesterKe(this.mahasiswaDao.getSemesterKe(m.getId()));
		}
		return Response.ok(toJson(mahasiswa)).build();
	}
}
