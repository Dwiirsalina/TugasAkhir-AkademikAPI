package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.WaliBiodata;

public interface WaliDao {

	WaliBiodata getWali(String id);

	WaliBiodata getWaliByPhone(String noHp);

	List<MahasiswaFoto> getMahasiswa(String idWali);

	List<MahasiswaFoto> getMahasiswaByNRP(String nrp, String tanggalLahir);
}
