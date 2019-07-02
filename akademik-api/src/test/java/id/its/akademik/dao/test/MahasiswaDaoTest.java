package id.its.akademik.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.domain.Mahasiswa;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.OrangTua;
import id.its.akademik.domain.Pekerjaan;
import id.its.akademik.domain.PeriodeMahasiswa;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:akademik-data-beans.xml" })
public class MahasiswaDaoTest {

	@Autowired
	MahasiswaDao mahasiswaDao;

	@Test
	public void canRetrieveMahasiswa() {
		List<MahasiswaFoto> list = mahasiswaDao.getMahasiswaFoto("5115100004");

		assertEquals(5, list.size());
	}

}
