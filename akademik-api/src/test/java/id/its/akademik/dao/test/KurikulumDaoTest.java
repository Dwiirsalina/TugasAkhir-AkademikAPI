package id.its.akademik.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import id.its.akademik.dao.KurikulumDao;
import id.its.akademik.domain.Kurikulum;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:akademik-data-beans.xml" })
public class KurikulumDaoTest {

	@Autowired
	KurikulumDao kurikulumDao;

	@Test
	public void canRetrieveMahasiswa() {
		List<Kurikulum> list = kurikulumDao.getTahunKurikulum("21030");

		assertEquals(7, list.size());
	}

}
