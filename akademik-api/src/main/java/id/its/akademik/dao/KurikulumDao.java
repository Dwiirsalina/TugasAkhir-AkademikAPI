package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MataKuliah;

public interface KurikulumDao {

	List<Kurikulum> getKurikulum(String idProdi, Integer tahun);

	List<MataKuliah> getMatakuliahKurikulum(String idProdi, Integer tahun, Integer semester, String bahasa);
	
//	List<MataKuliah> getMatakuliahKurikulum();

	List<Kurikulum> getTahunKurikulum(String idProdi);

}
