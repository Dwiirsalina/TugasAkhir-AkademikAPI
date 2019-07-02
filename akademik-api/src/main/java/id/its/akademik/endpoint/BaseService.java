package id.its.akademik.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import id.its.integra.security.Secured;

@Path("/")
public class BaseService {

	@GET
	@Path("/")
	public Response getBase(@Context HttpHeaders headers) {
		
		String jwtToken = headers.getRequestHeader("x-jwt-assertion").get(0);
		
		return Response.ok(jwtToken).build();
	}
}
