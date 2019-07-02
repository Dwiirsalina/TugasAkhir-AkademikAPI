package id.its.akademik.endpoint.test;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import id.its.akademik.endpoint.DosenEndpoint;
import id.its.akademik.endpoint.MahasiswaEndpoint;
import id.its.akademik.endpoint.ProdiEndpoint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:akademik-endpoint-beans.xml" })
public class FakultasEndpointTest {

	@Autowired
	DosenEndpoint fakultasEndpoint;

	@Test
	public void canGetFakultas() {
//		Response response = fakultasEndpoint.getListDosen("Rizky");
//		Response response = fakultasEndpoint.getDosen("051100122");
		Response response = fakultasEndpoint.getMahasiswaWaliByNIP("051100122","51100");
		Object obj = response.getEntity();

		System.out.println(response.getEntity());
	}
}
