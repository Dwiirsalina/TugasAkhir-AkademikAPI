package id.its.akademik.dao;

import java.util.List;

import id.its.akademik.domain.Departemen;
import id.its.akademik.domain.Fakultas;
import id.its.akademik.domain.Kalender;
import id.its.akademik.domain.ProdiAjar;
import id.its.akademik.domain.ProgramStudi;

public interface KelembagaanDao {

	List<Fakultas> getFakultas(String id, String query);

	List<Departemen> getDepartemen(String id, String idFakultas, String query, int page, int perPage);

	List<ProgramStudi> getProgramStudi(String id, String idFakultas, String idDepartemen, String query, int page,
			int perPage);

	List<ProdiAjar> getProdiAjar();

	List<Kalender> getKalenderAkademik(String tahun, String semester);

}
