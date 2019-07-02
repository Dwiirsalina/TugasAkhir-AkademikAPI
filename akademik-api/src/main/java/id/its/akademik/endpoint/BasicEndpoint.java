package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.domain.Sekarang;
import id.its.integra.security.Secured;

@Path("/basic")
public class BasicEndpoint extends BaseEndpoint {

	private AkademikDao akademikDao;

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sekarang")
	@Secured(roles = { "Mahasiswa", "Dosen", "Wali" }, module = "AKAD", type = "APPLICATION_USER")
	public Response getStatusSekarang(@Context SecurityContext securityContext) {
		List<Sekarang> sekarang = this.akademikDao.getSekarang();
		return Response.ok(toJson(sekarang)).build();
	}
}
