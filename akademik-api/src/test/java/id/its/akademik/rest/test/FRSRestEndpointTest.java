package id.its.akademik.rest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })

public class FRSRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-jwt-assertion",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFNczJiOFJ4WFktdWc4SnNvOVRMM1NwT2ZLSSJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9zdWJzY3JpYmVyIjoiYWRtaW4iLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9rZXl0eXBlIjoiU0FOREJPWCIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3RpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC92ZXJzaW9uIjoiMS4yIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6Im15SVRTIE1haGFzaXN3YSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2VuZHVzZXIiOiJJTlRFR1JBLklUUy5BQy5JRFwvMDUyMTE3NTAwMTIwMTJAY2FyYm9uLnN1cGVyIiwiZXhwIjoxNTQ4MjA2Mzg5LCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbmlkIjoiMTQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VydHlwZSI6IkFQUExJQ0FUSU9OX1VTRVIiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcGljb250ZXh0IjoiXC9ha2FkZW1pa1wvMS4yIn0=.jgQV0av96dCHJ8RvQuTCp3ZM7Gk4NSSoRUF5zxu8Ehq4cAYkarEnk4F3q4M5YyGlB/kyqRKcQyK/F0uMaA2V3mNfc4GHYRX2C2IUOkEdQSMbYAdLV/o/9JOephFE0N65Q/c3A7FhRVH6nQeNy4phbg6ah8DCEUnTMv0Uz7wDdVIcuChMnFzK+bFoWEXre3Aj67u4Vvfs+BVsZZUBMSY5/XHdl5Tt5UKBVOk6ZDgLUEHN16164ZD/T/5Op48N0JpWEmfrVwVFLJnCf+aHxZQcaIpBU/1vmADP6mySwf3Qjp41WQHgjfgn/lgctQZ7MG1NHxi++6u83Qur5WIkLFJ3TA==");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		return headers;
	}

	// @Test
	public void canGetJadwalFRS() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/frs/jadwal?tahun=2016&semester=2", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	// public void canPostFRS() {
	// final String uri =
	// "http://localhost:8080/akademik/frs/kuliah?tahun=2018&semester=1&is-pengayaan=true";
	// AddKuliah add = new AddKuliah();
	// List<AddKuliahFRSRequest> list = new ArrayList<>();
	// AddKuliahFRSRequest frsreq = new AddKuliahFRSRequest();
	// frsreq.setKodeProdi("15100");
	// // frsreq.setKodeProdi("52100");
	// frsreq.setIdMataKuliah("SB4733");
	// frsreq.setKelas("A");
	// list.add(frsreq);
	// add.setFrsRequest(list);
	// // list.add(frsreq);
	// ResponseEntity<String> entity = restTemplate.postForEntity(uri, add,
	// String.class);
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	public void canGetFRSSyarat() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange(
				"http://localhost:8080/akademik/frs/cek-auth?tahun=2018&semester=1", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
}
