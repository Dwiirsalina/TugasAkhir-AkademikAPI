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
public class ProdiEndpointTest {

	@Autowired
	ProdiEndpoint prodiEndpoint;

	@Test
	public void canGetFakultas() {
//		Response response = fakultasEndpoint.getListDosen("Rizky");
//		Response response = prodiEndpoint.getMataKuliah("51100", 2017, 2);
//		Response response = prodiEndpoint.getMataKuliah("21030", 2018, 2);
//		Response response = prodiEndpoint.getKelasDitawarkan("51100", 2017, 2, 1, 100, "listKelas", "nama", "Indonesia", true);
//		Response response = prodiEndpoint.getMatakuliahKurikulum("51100", 2018, "Indonesia");
//		--Pesertakelas:----
//		Response response = prodiEndpoint.getPesertaKelas("51100", "KI1301", "C", 2011, 1);
//		--ProgramStudi
//		Response response = prodiEndpoint.getListProdi("q", 0, 100, "fields");
//		Response response = prodiEndpoint.getMataKuliah("51100", 2017, 2);
//		GetProdiAjar 		
		Response response = prodiEndpoint.getProdiAjar();
//		Get Kurikulum 
//		Response response = prodiEndpoint.getMatakuliahKurikulum("51100", 2018, "Indonesia");
//		get tahun kurikulum 
//		Response response = prodiEndpoint.getTahunKurikulum("51100");
		Object obj = response.getEntity();

		System.out.println(response.getEntity());
	}
}
