package id.its.akademik.rest.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public class ProdiRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	// @Test
	// public void canGetListProdi() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/prodi",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	@Test
	public void canGetProdi() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/akademik/prodi/51100",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetKelasProdi() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/prodi/__tpb/kelas?tahun=2017&semester=1&order=kelas", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	//
	// @Test
	// public void canGetKelasProdiTPB() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/prodi/__TPB/kelas?tahun=2017&semester=1",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetKelasProdiTPBPaging() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/prodi/__TPB/kelas?tahun=2017&semester=1&page=0&per-page=10&order=kelas&lang=en",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetPesertaKelasProdi() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/prodi/__TPB/matakuliah/SM1203/kelas/21/peserta?tahun=2017&semester=2",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
	//
	// @Test
	// public void canGetKapasitasKelasProdi() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/prodi/51100/matakuliah/KI1406/kelas/_/kapasitas?tahun=2016&semester=1",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// // @Test
	// // public void canGetDayaTampung() {
	// // ResponseEntity<String> entity = restTemplate.getForEntity(
	// //
	// "http://localhost:8080/akademik/prodi/__TPB/matakuliah/IG1109/kelas/—/daya-tampung?tahun=2017&semester=2&tahun-kurikulum=2014&nrp=2813100001",
	// // String.class);
	// //
	// // assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// // }
	//
	// @Test
	// public void canGetPeriodeProdi() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/prodi/51100/periode",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetKurikulumProdi() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/prodi/51100/kurikulum?tahun=2014&semester=8",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	@Test
	public void canGetIPD() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/prodi/34100/ipd?tahun=2016&semester=2", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetTahunKurikulum() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/prodi/tahun-kurikulum", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void canGetSyaratMataKuliah() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/prodi/51100/kurikulum/matakuliah/KI141502/syarat?semester=2&tahun-kurikulum=2014",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

}
