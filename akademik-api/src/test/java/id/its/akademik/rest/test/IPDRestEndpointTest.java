package id.its.akademik.rest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class IPDRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	// @Test
	public void canGetKuesionerIPD() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/ipd/kuesioner-ipd?tahun-kurikulum=2014", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetKuesionerIPM() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/ipd/kuesioner-ipm?tahun-kurikulum=2014", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

}
