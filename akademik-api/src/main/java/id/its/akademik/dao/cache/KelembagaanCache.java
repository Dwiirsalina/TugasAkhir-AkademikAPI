package id.its.akademik.dao.cache;

import java.util.List;

import org.springframework.data.redis.core.ListOperations;

import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;

public interface KelembagaanCache {
	Boolean checkKey(String key);	
	void setProdiAjar(String key,List<ProdiAjar> list);
	List<ProdiAjar> getProdiAjar(String key);
	void setProdi(String key,List<ProgramStudi> list);
	List<ProgramStudi> getProdi(String key);
}
