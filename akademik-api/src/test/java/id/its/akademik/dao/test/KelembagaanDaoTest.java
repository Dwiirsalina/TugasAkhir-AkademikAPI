package id.its.akademik.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import id.its.akademik.dao.KelembagaanDao;
import id.its.akademik.domain.Fakultas;
import id.its.akademik.domain.ProgramStudi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:akademik-data-beans.xml"})
public class KelembagaanDaoTest {

	@Autowired
	KelembagaanDao kelembagaanDao;
	
	@Test
	public void canRetrieveAllFakultas() {
		List<ProgramStudi> listFakultas = kelembagaanDao.getProgramStudi("1", "1", "100", "1", 100,0);
		
		assertEquals(0, listFakultas.size());
	}
	
}
