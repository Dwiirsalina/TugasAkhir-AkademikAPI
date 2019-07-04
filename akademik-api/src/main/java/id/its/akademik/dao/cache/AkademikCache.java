package id.its.akademik.dao.cache;

import java.util.List;

import id.its.akademik.domain.FRS;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.MataKuliah;
import id.its.akademik.domain.MataKuliahSyarat;
import id.its.akademik.domain.Sekarang;

public interface AkademikCache {
	Boolean checkKey(String key);
	void setMatakuliah(String key,List<MataKuliah> list);
	List<MataKuliah> getMataKuliah(String key);
	void setPesertaKelas(String key,List<MahasiswaFoto> list);
	List<MahasiswaFoto> getPesertaKelas(String key);
	void setMataKuliahSyarat(String key,List<MataKuliahSyarat> list);
	List<MataKuliahSyarat> getMataKuliahSyarat(String key);
	void setListFrs(String key,List<FRS> list);
	List<FRS> getListFrs(String key);
	void setBasicSekarang(String key,List<Sekarang> list);
	List<Sekarang> getBasicSekarang(String key);
	void setDataFrs(String key, FRS frs);
	FRS getDataFrs(String key);
}
