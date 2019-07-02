package id.its.akademik.dao.cache;

import java.util.List;

import org.springframework.data.redis.core.ListOperations;

import id.its.akademik.domain.ProdiAjar;

public interface KelembagaanCache {
	Boolean checkKey(String key);
//	void setListOperations(String key,List<ProdiAjar> prodiAjar);
//	List<ProdiAjar> getListOperations(String key);
	void setProdiAjar(String key,List<ProdiAjar> list);
	List<ProdiAjar> getProdiAjar(String key);
}
