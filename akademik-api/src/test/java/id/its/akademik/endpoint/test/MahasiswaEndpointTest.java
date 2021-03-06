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
public class MahasiswaEndpointTest {

	@Autowired
	MahasiswaEndpoint mahasiswaEndpoint;

	@Test
	public void canGetMahasiswa() {
//		Response response = mahasiswaEndpoint.getAkademikMahasiswa("5115100004");
//		Response response = mahasiswaEndpoint.getJadwal("5115100132", 2017, 2, "51100", "Indonesia");
//		Response response = mahasiswaEndpoint.getListSemester("5115100004");
//		Response response = mahasiswaEndpoint.getKemajuanStudiMahasiswa(null, "5115100004", "Indonesia");
//		Response response = mahasiswaEndpoint.getTranskripMahasiswa("5115100004", "Indonesia");
//		Response response = mahasiswaEndpoint.getAkademikMahasiswa("5115100004");
//		Get FRS Mahasiswa
//		Response response = mahasiswaEndpoint.getFRSMahasiswa("5115100004", 2015, 1, "Indonesia");
//		getMahasiswa Ortu 
//		Response response = mahasiswaEndpoint.getPekerjaanMahasiswa("5115100004");
//		getkuesionerDosen
//		Response response = mahasiswaEndpoint.getKuesionerDosen("2015", 1, );
//		jadwal selanjutnya 
//		Response response = mahasiswaEndpoint.getJadwalSelanjutnya("5115100004", 2015, 1, 3, "51100", "Indonesia");
//		Response response = mahasiswaEndpoint.getListFRSMahasiswaByNrp("5115100004");
		Response response = mahasiswaEndpoint.getBiodataMahasiswa("5115100004");
//		Get Kuesioner Dosen 
//		Response response = mahasiswaEndpoint.getKuesionerDosen(2015, 1, );
//		get Pertanyaan Kuesioner
//		Response response = mahasiswaEndpoint.getPertanyaanKuesionerDosen("Indonesia", dwiirsalina);
//		Response response = mahasiswaEndpoint.getOrtuMahasiswa("5115100004");
//	Response response = mahasiswaEndpoint.getM("5115100132", 2017, 2, "51100", "Indonesia");
//		Response response = mahasiswaEndpoint.getKuesionerDosen("2018", semester, securityContext);
//		Response response = mahasiswaEndpoint.getJadwalKuliah("5115100087", 2018, 2, "51100", "Indonesia");
		Object obj = response.getEntity();

		System.out.println(response.getEntity());
	}
}
