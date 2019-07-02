package id.its.akademik.dao.cache;

import java.util.List;

import id.its.akademik.domain.Kurikulum;
import id.its.akademik.domain.MataKuliah;

public interface KurikulumCache {
	Boolean checkKey(String key);
	void setMkKurikulum(String key,List<Kurikulum> list);
	List<Kurikulum> getMkKurikulum(String key);
	void setTahunKurikulum(String key,List<Kurikulum> list);
	List<Kurikulum> getTahunKurikulum(String key);
}
