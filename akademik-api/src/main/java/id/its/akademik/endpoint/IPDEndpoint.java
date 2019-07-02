package id.its.akademik.endpoint;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.domain.Kuesioner;

@Path("/ipd")
public class IPDEndpoint extends BaseEndpoint {

	private AkademikDao akademikDao;

	public AkademikDao getAkademikDao() {
		return akademikDao;
	}

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner-ipm")
	public Response getListKuesionerIPM(@QueryParam("tahun-kurikulum") Integer tahunKurikulum) {

		List<Kuesioner> listKuesioner = this.akademikDao.getKuesionerIPM(tahunKurikulum);

		return Response.ok(toJson(listKuesioner)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuesioner-ipd")
	public Response getListKuesionerIPD(@QueryParam("tahun-kurikulum") Integer tahunKurikulum) {

		List<Kuesioner> listKuesioner = this.akademikDao.getKuesionerIPD(tahunKurikulum);

		return Response.ok(toJson(listKuesioner)).build();
	}
}