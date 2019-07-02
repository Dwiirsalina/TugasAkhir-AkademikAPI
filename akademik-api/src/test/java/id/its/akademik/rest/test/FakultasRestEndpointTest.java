package id.its.akademik.rest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class FakultasRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	// @Test
	public void canGetFakultas() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/akademik/fakultas",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	//
	// @Test
	// public void canGetFakultasByID() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/fakultas/1",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFakultasByQuery() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/fakultas?q=infor",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFakultasFilteredFields() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/fakultas?fields=id,nama",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetDepartemen() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/fakultas/5/departemen", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	//
	// @Test
	// public void canGetDepartemenByID() {
	//
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/fakultas/5/departemen/51",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetDepartemenByQuery() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/fakultas/5/departemen?q=tik",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetProdi() {

		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/fakultas/5/departemen/51/prodi", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
	//
	// @Test
	// public void canGetProdiByID() {
	//
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/fakultas/5/departemen/51/prodi/51100",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetProdiByQuery() {
	//
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/fakultas/5/departemen/51/prodi?q=tik",
	// String.class);
	//
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetProdiByIdFakultas() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/fakultas/4/prodi?q=Tek",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

}
