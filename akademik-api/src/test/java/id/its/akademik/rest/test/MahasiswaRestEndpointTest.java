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
public class MahasiswaRestEndpointTest {

	@Autowired
	private RestTemplate restTemplate;

	public HttpHeaders httpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-jwt-assertion",
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6InFNczJiOFJ4WFktdWc4SnNvOVRMM1NwT2ZLSSJ9.eyJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9hcHBsaWNhdGlvbnRpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9zdWJzY3JpYmVyIjoiYWRtaW4iLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC9rZXl0eXBlIjoiUFJPRFVDVElPTiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3RpZXIiOiJVbmxpbWl0ZWQiLCJodHRwOlwvXC93c28yLm9yZ1wvY2xhaW1zXC92ZXJzaW9uIjoiMS4yIiwiaXNzIjoid3NvMi5vcmdcL3Byb2R1Y3RzXC9hbSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9ubmFtZSI6Im15SVRTIE1haGFzaXN3YSIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2VuZHVzZXIiOiJJTlRFR1JBLklUUy5BQy5JRFwvMTk5MzIwMTcyMjQyOUBjYXJib24uc3VwZXIiLCJleHAiOjE1NTQ4NjUxODUsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwcGxpY2F0aW9uaWQiOiIxNCIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL3VzZXJ0eXBlIjoiQVBQTElDQVRJT05fVVNFUiIsImh0dHA6XC9cL3dzbzIub3JnXC9jbGFpbXNcL2FwaWNvbnRleHQiOiJcL2FrYWRlbWlrXC8xLjIifQ==.Iw9T9FZstxMfgktavPZmEgctJgIcJ7t8K229v+lEuiQio8TnKNCN4ElPI8TBaPgKXmkzw9FWjUIZGazHRDjdbWYbqG6WGEX4ySGtka+5hSKMxsv091AAp+behpCY9JI5316xpCCsDn4/WP2AauhCj6omsZ4ocAjzs+hWMZ8LoOZWC6ZIeaP/wq5SWkrbwUiv5zWN5NvnmVglzk01YknQ/7kDkBjq32vNAmN/RgiOdq5nikrzM2KSZRFtebGq+Tf2ZrjK7DwI1ayQO9hTJVRDW3H0fHqWikLAFGwNkZr+aTpypQJEIQhhun4wvitHs1JdJRmsnQKHMseGjKqMN1efyQ==");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

		return headers;
	}
	//
	// @Test
	// public void canPostKuesioner() {
	// HttpHeaders headers = httpHeaders();
	// final String uri =
	// "http://localhost:8080/akademik/mahasiswa/kuesioner/mata-kuliah";
	// KuesionerPost kuesioner = new KuesionerPost();
	// List<TanyaJawab> detail = new ArrayList<TanyaJawab>();
	// TanyaJawab tanya1 = new TanyaJawab();
	// TanyaJawab tanya2 = new TanyaJawab();
	// tanya1.setIdJawaban("1");
	// tanya1.setIdPertanyaan("564");
	// tanya2.setIdJawaban("4");
	// tanya2.setIdPertanyaan("1234");
	// detail.add(tanya1);
	// detail.add(tanya2);
	// kuesioner.setJawaban(detail);
	// kuesioner.setIdJurusan("45678");
	// kuesioner.setIdMk("56789");
	// kuesioner.setIdSemester(2);
	// kuesioner.setKelas("A");
	// kuesioner.setKomentar("baik");
	// kuesioner.setTahun(1993);
	// kuesioner.setTahunKurikulum(5678);
	//
	// HttpEntity<Object> entity = new HttpEntity<Object>(kuesioner, headers);
	//
	// ResponseEntity<String> responseEntity = restTemplate.exchange(uri,
	// HttpMethod.POST, entity, String.class);
	// assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	// public void canPostKuesionerMK() {
	// // HttpHeaders headers = httpHeaders();
	// final String uri =
	// "http://localhost:8080/akademik/mahasiswa/kuesioner/mata-kuliah";
	// KuesionerMK kuesioner = new KuesionerMK();
	// List<TanyaJawab> detail = new ArrayList<TanyaJawab>();
	// TanyaJawab tanya1 = new TanyaJawab();
	// TanyaJawab tanya2 = new TanyaJawab();
	// tanya1.setIdJawaban("1");
	// tanya1.setIdPertanyaan("564");
	// tanya2.setIdJawaban("4");
	// tanya2.setIdPertanyaan("1234");
	// detail.add(tanya1);
	// detail.add(tanya2);
	// kuesioner.setJawaban(detail);
	// kuesioner.setIdJurusan("45678");
	// kuesioner.setIdMk("56789");
	// kuesioner.setIdSemester(2);
	// kuesioner.setKelas("A");
	// kuesioner.setKomentar("baik");
	// kuesioner.setTahun(1995);
	// kuesioner.setTahunKurikulum(5678);
	//
	// ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri,
	// kuesioner, String.class);
	// assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	// public void canPostKuesionerDosen() {
	// // HttpHeaders headers = httpHeaders();
	// final String uri =
	// "http://localhost:8080/akademik/mahasiswa/kuesioner/dosen";
	// KuesionerDosen kuesioner = new KuesionerDosen();
	// List<TanyaJawab> detail = new ArrayList<TanyaJawab>();
	// TanyaJawab tanya1 = new TanyaJawab();
	// TanyaJawab tanya2 = new TanyaJawab();
	// tanya1.setIdJawaban("1");
	// tanya1.setIdPertanyaan("564");
	// tanya2.setIdJawaban("4");
	// tanya2.setIdPertanyaan("1234");
	// detail.add(tanya1);
	// detail.add(tanya2);
	// kuesioner.setJawaban(detail);
	// kuesioner.setIdJurusan("45678");
	// kuesioner.setIdMk("56789");
	// kuesioner.setIdSemester(2);
	// kuesioner.setKelas("A");
	// kuesioner.setKomentar("baik");
	// kuesioner.setTahun(1993);
	// kuesioner.setNip("123456");
	// kuesioner.setTahunKurikulum(5678);
	//
	// ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri,
	// kuesioner, String.class);
	// assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	public void canGetBiodata() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8080/akademik/mahasiswa",
				HttpMethod.GET, new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetFRS() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange(
				"http://localhost:8080/akademik/mahasiswa/frs?tahun=2018&semester=2", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	//
	// @Test
	// public void canGetMahasiswa() {
	// ResponseEntity<String> entity =
	// restTemplate.getForEntity("http://localhost:8080/akademik/mahasiswa/pekerjaan",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetBiodataMahasiswa() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/akademik/mahasiswa/1118201002",
				String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetOrtuMahasiswa() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/mahasiswa/5114100096/ortu", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetWaliMahasiswa() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/mahasiswa/05211750012012/wali", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	//
	// @Test
	// public void canGetPekerjaanMahasiswa() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/5114100096/pekerjaan",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetAkademikMahasiswa() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/5217201212/akademik",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetKeaktifanMahasiswa() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/5114100096/keaktifan",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetTranskripMahasiswa() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange(
				"http://localhost:8080/akademik/mahasiswa/05211750012012/transkrip", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetFotoMahasiswa() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/mahasiswa/5114100096/foto", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetListMahasiswa() {
		ResponseEntity<String> entity = restTemplate
				.getForEntity("http://localhost:8080/akademik/mahasiswa/list?prodi=51100", String.class);

		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
	//
	// @Test
	// public void canGetNilaiKuliahMahasiswa() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/1217201008/nilai",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetNilaiKuliahMahasiswa2() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1217201008/nilai?tahun=2017&semester=1",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetMKUlang() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/5213100088/frs/mk-diulang",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetMKWajibAmbil() {
	// ResponseEntity<String> entity = restTemplate
	// .getForEntity("http://localhost:8080/akademik/mahasiswa/1105100055/frs/mk-wajib-ambil",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetMKLanggarPrasyarat() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1113100009/frs/mk-langgar-prasyarat?semester=1&tahun=2017",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFRSMahasiswa() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1317030008/frs?semester=1&tahun=2017",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFRSKuliahMahasiswa() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1314100008/frs/kuliah?tahun=2016&semester=2",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetStatusPembayaranSpp() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1314100008/status-bayar-spp?tahun=2016&semester=2",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetEvaluasi() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/9109205412/evaluasi?tahun=2016&semester=2",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetEWS() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1111100020/ews?tahun=2016&semester=2",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetFRSDisetujui() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/1111100020/frs-disetujui?tahun=2016&semester=2",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }

	// @Test
	// public void canGetPrasyarat() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/matakuliah/TF1355/prasyarat?tahun-kurikulum=2014",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetPersiapan() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/matakuliah/IG1101/persiapan?tahun-kurikulum=2014",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	// @Test
	// public void canGetFRSBoleh() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/prasyarat-frs?tahun=2017&semester=2&tahun-kurikulum=2014",
	// String.class);
	//
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	// public void canGetKuesioner() {
	// ResponseEntity<String> entity = restTemplate.getForEntity(
	// "http://localhost:8080/akademik/mahasiswa/kuesioner/mata-kuliah?tahun=2017&semester=1",
	// String.class);
	// assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	// }
	//
	// @Test
	public void canGetJawaban() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/mahasiswa/kuesioner/dosen/pertanyaan?tahun-kurikulum=2014&id-bahasa=en",
				String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetJadwal() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/mahasiswa/jadwal?tahun=2018&semester=1&prodi=51100", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetSekarang() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/akademik/mahasiswa/sekarang",
				String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetPembayaran() {
		HttpHeaders headers = httpHeaders();
		ResponseEntity<String> entity = restTemplate.exchange(
				"http://localhost:8080/akademik/mahasiswa/05211840000048/pembayaran", HttpMethod.GET,
				new HttpEntity<Object>(headers), String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetAjukan() {
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/akademik/mahasiswa/frs/ajukan?tahun=2017&semester=2", String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}

	// @Test
	public void canGetListFRS() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:8080/akademik/mahasiswa/list-frs",
				String.class);
		assertThat(entity.getStatusCode(), equalTo(HttpStatus.OK));
	}
}
