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

import id.its.akademik.dao.KelembagaanDao;
import id.its.akademik.domain.Departemen;
import id.its.akademik.domain.Fakultas;
import id.its.akademik.domain.Kalender;
import id.its.akademik.domain.ProgramStudi;
import id.its.integra.security.Secured;

@Path("/fakultas")
@Secured(roles = { "Dosen", "Mahasiswa", "Wali" }, module = "AKAD")
public class FakultasEndpoint extends BaseEndpoint {

	private KelembagaanDao kelembagaanDao;

	public void setKelembagaanDao(KelembagaanDao kelembagaanDao) {
		this.kelembagaanDao = kelembagaanDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public Response getFakultas(@QueryParam("q") String query, @QueryParam("fields") String fields) {

		List<Fakultas> listFakultas = this.kelembagaanDao.getFakultas(null, query);

		return Response.ok(toJson(listFakultas, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kalender")
	public Response getKalender(@QueryParam("tahun") String tahun, @QueryParam("semester") String semester) {

		List<Kalender> listKalender = this.kelembagaanDao.getKalenderAkademik(tahun, semester);

		return Response.ok(toJson(listKalender)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getFakultasById(@PathParam("id") String id, @QueryParam("fields") String fields) {

		List<Fakultas> listFakultas = this.kelembagaanDao.getFakultas(id, null);

		return Response.ok(toJson(listFakultas, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/departemen")
	public Response getListDepartemen(@PathParam("id") String kodeFakultas, @QueryParam("q") String query,
			@DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("fields") String fields) {

		List<Departemen> listDept = this.kelembagaanDao.getDepartemen(null, kodeFakultas, query, page, perPage);

		return Response.ok(toJson(listDept, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id-fakultas}/departemen/{id-departemen}")
	public Response getDepartemen(@PathParam("id-fakultas") String idFakultas,
			@PathParam("id-departemen") String idDepartemen, @QueryParam("fields") String fields) {

		List<Departemen> listDept = this.kelembagaanDao.getDepartemen(idDepartemen, idFakultas, null, 0, 1);

		return Response.ok(toJson(listDept, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id-fakultas}/departemen/{id-departemen}/prodi")
	public Response getListProdi(@PathParam("id-fakultas") String idFakultas,
			@PathParam("id-departemen") String idDepartemen, @QueryParam("q") String query,
			@DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("fields") String fields) {

		List<ProgramStudi> listProdi = this.kelembagaanDao.getProgramStudi(null, idFakultas, idDepartemen, query, page,
				perPage);

		return Response.ok(toJson(listProdi, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id-fakultas}/departemen/{id-departemen}/prodi/{id-prodi}")
	public Response getProdi(@PathParam("id-fakultas") String idFakultas,
			@PathParam("id-departemen") String idDepartemen, @PathParam("id-prodi") String idProdi,
			@QueryParam("fields") String fields) {

		List<ProgramStudi> listProdi = this.kelembagaanDao.getProgramStudi(idProdi, idFakultas, idDepartemen, null, 0,
				1);

		return Response.ok(toJson(listProdi, fields)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/prodi")
	public Response getProdiByIdFakultas(@PathParam("id") String id, @QueryParam("q") String query,
			@DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("100") @QueryParam("per-page") Integer perPage, @QueryParam("fields") String fields) {

		List<ProgramStudi> listProdi = this.kelembagaanDao.getProgramStudi(null, id, null, query, page, perPage);

		return Response.ok(toJson(listProdi, fields)).build();
	}

}
