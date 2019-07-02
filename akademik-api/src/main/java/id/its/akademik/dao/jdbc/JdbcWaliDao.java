package id.its.akademik.dao.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.WaliDao;
import id.its.akademik.dao.mapper.MahasiswaFotoRowMapper;
import id.its.akademik.dao.mapper.WaliRowMapper;
import id.its.akademik.domain.MahasiswaFoto;
import id.its.akademik.domain.WaliBiodata;

public class JdbcWaliDao extends AbstractDao implements WaliDao {
	@Override
	public WaliBiodata getWali(String id) {
		String sql = "SELECT id_user_sso AS id, nama, email, nik, tempat_lahir, tanggal_lahir, tempat_lahir + ', ' + CONVERT(VARCHAR, tanggal_lahir, 106) AS ttl, jk, no_hp FROM Wali WHERE id_user_sso = :id";
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<WaliBiodata> listWali = jdbcTemplate.query(sql, params, new WaliRowMapper());

		if (!listWali.isEmpty()) {
			return listWali.get(0);
		} else {
			return null;
		}
	}

	@Override
	public WaliBiodata getWaliByPhone(String noHp) {
		String sql = "SELECT id_user_sso AS id, nama, email, nik, tempat_lahir, tanggal_lahir, tempat_lahir + ', ' + CONVERT(VARCHAR, tanggal_lahir, 106) AS ttl, jk, no_hp FROM Wali WHERE no_hp = :phone";
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", noHp);

		List<WaliBiodata> listWali = jdbcTemplate.query(sql, params, new WaliRowMapper());

		if (!listWali.isEmpty()) {
			return listWali.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<MahasiswaFoto> getMahasiswa(String idWali) {
		String sql = "SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) AS kode_prodi, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.ma_idsex AS jk, p.PE_NamaLengkap AS dosen_wali, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.ma_telpmhs AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, ps.PS_Nama_Baru AS prodi, CONVERT(DECIMAL(10,2), MA_IPK) AS ipk, MA_SksLulus AS sks_lulus, YEAR(MA_TglMasukITS) AS tahun_masuk, m.MA_Photo AS foto FROM mahasiswa m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id LEFT JOIN Pegawai p ON p.PE_Nip = m.MA_PE_NipWali JOIN ProgramStudi ps ON substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = ps.PS_Kode_Prodi_Lama JOIN Mahasiswa_Wali mw ON mw.ma_nrp = m.MA_Nrp JOIN Wali w ON mw.id_ortuwali = w.id WHERE w.id_user_sso = :id";
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", idWali);

		List<MahasiswaFoto> listMahasiswa = jdbcTemplate.query(sql, params, new MahasiswaFotoRowMapper());
		return listMahasiswa;
	}

	@Override
	public List<MahasiswaFoto> getMahasiswaByNRP(String nrp, String tanggalLahir) {
		String sql = "SELECT m.ma_nrp AS id, m.ma_nrp_baru AS nrp, substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) AS kode_prodi, m.ma_nama AS nama, m.ma_namalengkap AS nama_lengkap, m.ma_idsex AS jk, p.PE_NamaLengkap AS dosen_wali, m.ma_tgllahir AS tgl_lahir, m.ma_tmplahir AS tmpt_lahir, ag.ag_agama AS agama, m.ma_idstatusnikah AS status_kawin, m.ma_alamatsby AS alamat_sby, m.ma_kodepos AS kodepos, m.ma_telpmhs AS telp_mhs, m.ma_email AS email, m.ma_goldarah AS gol_darah, wn.Kewarganegaraan as kewarganegaraan, ps.PS_Nama_Baru AS prodi, CONVERT(DECIMAL(10,2), MA_IPK) AS ipk, MA_SksLulus AS sks_lulus, YEAR(MA_TglMasukITS) AS tahun_masuk, m.MA_Photo AS foto FROM mahasiswa m LEFT JOIN val_Kewarganegaraan wn ON wn.ID = m.ma_idkewarganegaraan LEFT JOIN agama ag ON ag.ag_id = m.ma_ag_id LEFT JOIN Pegawai p ON p.PE_Nip = m.MA_PE_NipWali JOIN ProgramStudi ps ON substring(m.ma_nrp, 1, 2)+substring(m.ma_nrp, 5, 3) = ps.PS_Kode_Prodi_Lama WHERE m.ma_nrp_baru = :nrp AND CONVERT(date, MA_TglLahir) = :tglLahir";
		NamedParameterJdbcTemplate jdbcTemplate = this.getNamedParameterJdbcTemplate();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nrp", nrp);
		params.put("tglLahir", tanggalLahir);

		List<MahasiswaFoto> listMahasiswa = jdbcTemplate.query(sql, params, new MahasiswaFotoRowMapper());
		return listMahasiswa;
	}
}
