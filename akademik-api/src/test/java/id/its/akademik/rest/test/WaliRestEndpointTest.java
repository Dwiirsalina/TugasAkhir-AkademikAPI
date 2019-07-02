package id.its.akademik.rest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class WaliRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-jwt-assertion",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFNczJiOFJ4WFktdWc4SnNvOVRMM1NwT2ZLSSJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9zdWJzY3JpYmVyIjoiYWRtaW4iLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9rZXl0eXBlIjoiU0FOREJPWCIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3RpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC92ZXJzaW9uIjoiMS4yIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6Im15SVRTIFdhbGkiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9lbmR1c2VyIjoiTVkuSVRTLkFDLklEXC8wODUyOTQxOTUxNThAY2FyYm9uLnN1cGVyIiwiZXhwIjoxNTUwNjI3ODA0LCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbmlkIjoiMjciLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC91c2VydHlwZSI6IkFQUExJQ0FUSU9OX1VTRVIiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcGljb250ZXh0IjoiXC9ha2FkZW1pa1wvMS4yIn0=.YZ17OQMYw09Wh0DOOXEJ2KJwJyZKVBcHhDLYjbgrptlfTfVIXqIOBsyEpzPjamZec2bWxE38yijK1wB9pbS3uJtRHYD7n4o3Xd9pNEFCGovckJwsLubKVlIyJr2tsWBg3D1naJayQfSsRiUDfEa9yNHn1W+U1Pegc48M58P1Td+gNDjdZTtHKBKt3zA/8zwFMwHRmBFUrqJHrbawyCIkvBEIJop/etUjbDMcW2nj2UkyeO5CwJoZst5g1MoDtJhWdgtZ0KPjmrM2qSYYEadE4KWm4JVlHt6IQexse8FssLBcZ7acEB0JaFlE0bnYdx24kaSDwl2jscE+zjLaflCsrQ==");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		return headers;
	}

	// @Test
	public void canGetWali() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/akademik/wali", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetMahasiswa() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/akademik/wali/mahasiswa",
				HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void canGetWaliByPhone() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/akademik/wali/08155008265",
				HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void canGetMahasiswaByNRP() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange(
				"http://localhost:8080/akademik/wali/mahasiswa/06111750010007?tanggal-lahir=1990-11-25", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

}
