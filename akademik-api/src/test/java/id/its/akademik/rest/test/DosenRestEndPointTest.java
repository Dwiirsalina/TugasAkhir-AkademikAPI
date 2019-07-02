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
public class DosenRestEndPointTest {

	@Autowired
	private RestTemplate restTemplate;

	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-jwt-assertion",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFNczJiOFJ4WFktdWc4SnNvOVRMM1NwT2ZLSSJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9zdWJzY3JpYmVyIjoiYWRtaW4iLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3RpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC92ZXJzaW9uIjoiMS4wIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6Im15SVRTIERvc2VuIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvZW5kdXNlciI6IklOVEVHUkEuSVRTLkFDLklEXC8wNTExMDAxMjJAY2FyYm9uLnN1cGVyIiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvYXBwbGljYXRpb25pZCI6IjU1IiwiaHR0cDpcL1wvd3NvMi5vcmdcL2NsYWltc1wvdXNlcnR5cGUiOiJBUFBMSUNBVElPTl9VU0VSIiwiZXhwIjoxNTMzMjAxMzQ1LCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcGljb250ZXh0IjoiXC9zaW1wZWdcLzEuMCJ9.pJQ2x+NtRK2zd59dKWM/2PaxZaqMFwSCPUuyAUaOdWnqu2t+DwvoLPcwKmkoHnHZGKcM7uS8iwRW3Qq8W7FiQzF2PTmxrbGeE1x2gj+zSPs/k5e1kkhbus267u2OIClEXyuyHp4hz/S5Yy267kRb6rfV1rYhnObplbc601o/6XdiILJP0RT9DZuprTsE0odyK4Bc/rX+kt6FfOtuzxsqaB3zfEi17V8BYfktT8F/gZR89336aqBXEF4Krw47q0l9K5+waKiUZgcNG9omE6Y+suI2u0Tz9MW9lQmLQ34Mu8vz3y2RHgrWL05Lc7vDK+NWUnuuXc4U2V+8Hai5F+oQQA==");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		return headers;
	}

	// @Test
	public void canGetBiodata() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/akademik/dosen", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);
		// ResponseEntity<String> entity = restTemplate
		// .postForEntity("http://localhost:8080/ekelas-api/glossary/8/entries",
		// request, String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	// public void canGetListDosen() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/dosen?nama=januar",
	//
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetDosen() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/dosen/194806191973011001",
	//
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetMahasiswaWali() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/mhswali?prodi=51100&tahun=2018&semester=1", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
	//
	// @Test
	// public void canGetFRSMahasiswaWali() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/dosen/131687915/frs?departemen=22100&tahun=2017&semester=1",
	//
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFRSMahasiswaWaliByQuery() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/dosen/131687915/frs?departemen=22100&tahun=2017&semester=1",
	//
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	// @Test
	// public void canGetWaktuPermanenNilai() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/dosen/999999999/waktu-permanen-nilai",
	//
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	public void canGetIPD() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/ipd?prodi=23100&tahun=2018&semester=1", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetKomentarIPM() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/matakuliah/TK4701/kelas/B/komentar-ipm?tahun=2018&semester=1",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetKomentarIPD() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/matakuliah/TK4701/kelas/B/komentar-ipm?tahun=2018&semester=1",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetPeringkatIPD() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/peringkat-ipd?tahun=2017&semester=1&prodi=51100&tahun-kurikulum=2014",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetIPDDetail() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/matakuliah/TK4701/kelas/B/ipd?tahun=2018&semester=1&prodi=23100",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetJadwal() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/dosen/jadwal?tahun=2018&semester=1&prodi=51100", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
}
