package id.its.akademik.endpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import id.its.akademik.dao.AkademikDao;
import id.its.akademik.dao.MahasiswaDao;
import id.its.akademik.dao.SppDao;
import id.its.akademik.domain.ErrorMessage;
import id.its.akademik.domain.Kelas;
import id.its.akademik.domain.Message;
import id.its.akademik.domain.Prasyarat;
import id.its.akademik.domain.Sekarang;
import id.its.akademik.domain.WaktuFrs;
import id.its.akademik.dto.AddKuliah;
import id.its.akademik.dto.AddKuliahFRSRequest;
import id.its.akademik.dto.SetujuFRSRequest;
import id.its.integra.security.Secured;

@Path("/frs")
public class FRSEndpoint extends BaseEndpoint {

	private AkademikDao akademikDao;
	private MahasiswaDao mahasiswaDao;
	private SppDao sppDao;

	public void setSppDao(SppDao sppDao) {
		this.sppDao = sppDao;
	}

	public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
		this.mahasiswaDao = mahasiswaDao;
	}

	public void setAkademikDao(AkademikDao akademikDao) {
		this.akademikDao = akademikDao;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cek-auth")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD")
	public Response getPrasyaratFRS(@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester,
			@Context SecurityContext securityContext) {
		String id = securityContext.getUserPrincipal().getName();
		if (tahun == null || semester == null) {
			return Response.status(400).build();
		}
		String nrp = this.mahasiswaDao.getNrpLama(id);

		String rNoEdit = null;
		List<Sekarang> sekarang = this.akademikDao.getSekarang();
		Sekarang s = sekarang.get(0);
		String periode = s.getTahun() + s.getSemester();
		String session = String.valueOf(tahun) + semester;

		Integer isVerifiedBiodata = this.mahasiswaDao.checkVerified(nrp);
		if (isVerifiedBiodata == null || isVerifiedBiodata == 0) {
			rNoEdit = "111";
			ErrorMessage err = new ErrorMessage(
					"Untuk dapat melakukan pengisian FRS, mohon untuk melengkapi biodata di aplikasi web SIAKAD terlebih dahulu");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}
		if (!periode.equalsIgnoreCase(session)) {
			rNoEdit = "111";
			ErrorMessage err = new ErrorMessage("Periode FRS ini sudah terlewati.");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}

		int isBayarspp = 0;
		if (Integer.valueOf((String.valueOf(tahun) + semester)) < 20082) {
			isBayarspp = this.akademikDao.getStatusPembayaranSpp(nrp, tahun, semester);
		}
		isBayarspp = this.sppDao.getStatusPembayaranSpp(nrp, tahun, semester);
		if (isBayarspp != 1) {
			String nrpBaru = this.mahasiswaDao.getNrpBaru(nrp);
			isBayarspp = this.sppDao.getStatusPembayaranSpp(nrpBaru, tahun, semester);
		}

		if (isBayarspp != 1) {
			rNoEdit = "111";
			if (isBayarspp == 0) {
				ErrorMessage err = new ErrorMessage(
						"Mahasiswa BELUM membayar SPP/SPI semester saat ini, sehingga tidak diperkenankan untuk mengisi FRS.");
				return Response.status(Status.BAD_REQUEST).entity(err).build();
			} else {
				ErrorMessage err = new ErrorMessage(
						"Tidak dapat membaca data pembayaran atau data pembayaran tidak ditemukan, mohon dicoba beberapa saat lagi.");
				return Response.status(Status.BAD_REQUEST).entity(err).build();
			}
		}

		String kodeJurusan = nrp.substring(0, 2) + nrp.substring(4, 7);
		if (Integer.valueOf(kodeJurusan.substring(2, 3)) <= 1 && tahun >= 2018) {

			String[] prodiTidakBukaPengayaan = new String[] { "16100", "39100", "53100" };
			List<String> list = Arrays.asList(prodiTidakBukaPengayaan);

			int isSudahBukaPengayaan = this.akademikDao.isBukaPengayaan(kodeJurusan, tahun, semester);

			if (isBayarspp == 1 && !list.contains(kodeJurusan) && isSudahBukaPengayaan != 1) {
				ErrorMessage err = new ErrorMessage(
						"Prodi Anda belum membuka kelas pengayaan sehingga Anda tidak bisa mengambil kelas pengayaan di prodi lain.");
				return Response.status(Status.BAD_REQUEST).entity(err).build();

			}
		}

		Integer evaluasi = this.akademikDao.getEvaluasi(nrp, tahun, semester);

		if (evaluasi == 1) {
			rNoEdit = "111";
			ErrorMessage err = new ErrorMessage("Mahasiswa TERKENA Evaluasi BATAS WAKTU, harap menghubungi BAAK.");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}

		int kenaEws = this.akademikDao.getEWS(nrp, tahun, semester);

		if (kenaEws == 1) {
			ErrorMessage err = new ErrorMessage("Semester ini anda terkena early warning");
			return Response.status(Status.BAD_REQUEST).entity(err).build();
		}

		if (rNoEdit == null) {
			if (!this.akademikDao.tahapFRS(tahun, semester)) {
				rNoEdit = "111";
			}
			if (nrp.substring(0, 1).equalsIgnoreCase("9")) {
				rNoEdit = "110";
			}
			if (!this.akademikDao.tahapFRS(tahun, semester) && !this.akademikDao.tahapUbahFRS(tahun, semester)) {
				if (!this.akademikDao.tahapDrop(tahun, semester)) {
					rNoEdit = "110";
				} else {
					rNoEdit = "100";
				}

			}
		}
		Boolean disetujui;
		if (rNoEdit != null) {
			if (!rNoEdit.equalsIgnoreCase("111")) {
				disetujui = this.akademikDao.getFRSDisetujui(nrp, tahun, semester);

				if (disetujui) {
					rNoEdit = "11" + rNoEdit.substring(2, 3);
				}
			}
		} else {
			rNoEdit = "000";
			disetujui = this.akademikDao.getFRSDisetujui(nrp, tahun, semester);
			if (disetujui)
				rNoEdit = "11" + rNoEdit.substring(2, 3);
		}

		return Response.ok(toJson(rNoEdit)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/cek-auth-dosen")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response getPrasyaratFRSDosen(@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester,
			@Context SecurityContext securityContext) {
		if (tahun == null || semester == null) {
			return Response.status(400).build();
		}
		String rNoEdit = "000";

		List<Sekarang> sekarang = this.akademikDao.getSekarang();
		Sekarang s = sekarang.get(0);
		String periode = s.getTahun() + s.getSemester();
		String session = String.valueOf(tahun) + semester;
		if (!periode.equalsIgnoreCase(session)) {
			rNoEdit = "111";
			ErrorMessage err = new ErrorMessage("Periode FRS ini sudah terlewati.", rNoEdit);
			return Response.ok().entity(err).build();
		}
		if (rNoEdit.equalsIgnoreCase("000")) {
			if (!this.akademikDao.tahapFRS(tahun, semester) && !this.akademikDao.tahapUbahFRS(tahun, semester)) {
				if (!this.akademikDao.tahapDrop(tahun, semester)) {
					rNoEdit = "110";
				} else {
					rNoEdit = "100";
				}

			}
		}

		ErrorMessage err = new ErrorMessage(null, rNoEdit);
		return Response.ok().entity(err).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/kuliah")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD")
	public Response tambahKuliah(AddKuliah requests, @Context SecurityContext securityContext,
			@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester,
			@QueryParam("is-pengayaan") Boolean isPengayaanParam) {
		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		return addKuliah(nrp, tahun, semester, isPengayaanParam, true, requests);

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/kuliah")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response tambahKuliahDosen(AddKuliah requests, @Context SecurityContext securityContext,
			@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester,
			@PathParam("nrp") String pathNrp, @QueryParam("is-pengayaan") Boolean isPengayaanParam) {

		if (pathNrp == null || pathNrp.isEmpty() || tahun == null || semester == null) {
			return Response.status(400).build();
		}

		return addKuliah(pathNrp, tahun, semester, isPengayaanParam, false, requests);

	}

	private Response addKuliah(String pathNrp, Integer tahun, Integer semester, Boolean isPengayaanParam,
			Boolean cekbatassks, AddKuliah requests) {

		List<Message> msg = new ArrayList<>();
		for (AddKuliahFRSRequest request : requests.getFrsRequest()) {
			String id = this.mahasiswaDao.getNrpLama(pathNrp);
			Integer tahunKurikulum = this.akademikDao.getTahunKurikulum(tahun);
			String kodeJurusan = request.getKodeProdi();
			String kodeJurusanMahasiswa = id.substring(0, 2) + id.substring(4, 7);
			String idKelas = request.getKelas();
			Integer sksTempuh = this.akademikDao.getSksTempuhPengayaan(id);
			if (kodeJurusan.equalsIgnoreCase("__TPB")) {
				idKelas = this.akademikDao.getKodeKelasUPMB(tahun, semester, kodeJurusan, request.getIdMataKuliah(),
						request.getKelas());
			}

			if (isPengayaanParam == null) {
				msg.add(new Message(
						"Mohon maaf versi ini belum support untuk pengayaan, silahkan update ke versi terbaru atau menggunakan web untuk frs"));
				return Response.ok(msg).build();
			}

			if (isPengayaanParam) {
				int minSksTempuh = minSksTempuh(id);
				Boolean isBolehAmbilPengayaan = false;
				if (id.substring(4, 6).equalsIgnoreCase("03") && sksTempuh >= minSksTempuh)
					isBolehAmbilPengayaan = true;

				if (id.substring(4, 6).equalsIgnoreCase("04") && sksTempuh >= minSksTempuh)
					isBolehAmbilPengayaan = true;

				if (id.substring(4, 6).equalsIgnoreCase("10") && sksTempuh >= minSksTempuh)
					isBolehAmbilPengayaan = true;

				if (!isBolehAmbilPengayaan) {
					msg.add(new Message("Belum bisa mengambil kelas pengayaan (SKS Tempuh: " + sksTempuh + "/"
							+ minSksTempuh + ")"));
					continue;
				}

			}
			// jika ada aksi, berhubungan dengan frs

			// menghitung sks ambil
			Integer pSksAmbil = this.akademikDao.getSKSAmbil(id, tahun, semester);
			if (pSksAmbil == null)
				pSksAmbil = 0;
			Integer p_sksmk = this.akademikDao.getSKS(request.getIdMataKuliah(), tahunKurikulum);

			// tentukan apakah perlu pengecekan sks dan prasyarat
			boolean cekkapasitas = true;
			boolean ceksksmax = true;
			boolean cektidakbolehambilpersiapan = true;

			String kodeProdi = kodeJurusan.substring(2, 5);

			if (kodeProdi.equalsIgnoreCase("105") || kodeProdi.equalsIgnoreCase("106")
					|| kodeProdi.equalsIgnoreCase("040")) {
				// khusus anak LJ boleh ngambil MK di semester berapapun
				// (2016-08-25, Budi, Req. BAKP)
				cektidakbolehambilpersiapan = false;
			}
			boolean mhss3 = false;

			if (id.substring(4, 5).equalsIgnoreCase("3"))
				mhss3 = true;
			if (mhss3)
				cekbatassks = false;

			// cek apakah matakuliah telah terprogram
			String p_kelasambil = this.akademikDao.getKelas(id, tahunKurikulum, kodeJurusan, tahun, semester,
					request.getIdMataKuliah());
			if (p_kelasambil != null) {
				String namaMk = this.akademikDao.getNamaMKKurikulum(request.getIdMataKuliah(), tahunKurikulum);
				msg.add(new Message("- Matakuliah " + namaMk + " telah diambil pada FRS ini"));
				continue;
			}

			// cek kapasitas kelas
			if (cekkapasitas) {
				Integer p_kapasitaskelas = this.akademikDao.getKapasitasKelas(kodeJurusan, request.getIdMataKuliah(),
						idKelas, tahun, semester, tahunKurikulum);
				if (p_kapasitaskelas != null && p_kapasitaskelas != 0) {
					String namaMk = this.akademikDao.getNamaMKKurikulum(request.getIdMataKuliah(), tahunKurikulum);

					String kelas = request.getKelas();
					msg.add(new Message("- Kapasitas kelas " + namaMk + " (" + kelas + ") telah penuh."));
					continue;
				}
			}

			// cek ada tidaknya jadwal ruang dari kelas yang dipilih
			Boolean cekJadwalRuang = this.akademikDao.cekJadwalRuang(tahun, semester, kodeJurusan,
					request.getIdMataKuliah(), idKelas, isPengayaanParam);
			if (!cekJadwalRuang) {
				String kelas = request.getKelas();
				msg.add(new Message("- Mata kuliah (" + request.getIdMataKuliah() + ") di kelas " + kelas
						+ " belum bisa dipilih karena belum memiliki jadwal."));
				continue;
			}

			// cek daya tampung yang telah ditentukan (UPMB SOSHUM)
			if (kodeJurusan.equalsIgnoreCase("__TPB") || kodeJurusan.equalsIgnoreCase("__MKU")) {
				String kodeJurusanBoleh = id.substring(0, 2) + id.substring(4, 7);

				List<Kelas> listKelas = this.akademikDao.getDayaTampung(kodeJurusan, request.getIdMataKuliah(), tahun,
						semester, tahunKurikulum, kodeJurusanBoleh);
				if (!listKelas.isEmpty()) {
					List<String> k = new ArrayList<>();
					for (Kelas ke : listKelas) {
						k.add(ke.getKelas());
					}
					if (!k.contains(request.getKelas())) {
						String kelas = request.getKelas();
						msg.add(new Message("- Anda memilih kelas " + kelas
								+ ". Anda hanya boleh memilih mata kuliah dari kelas yang telah ditentukan " + k));
						continue;
					} else {
						Boolean exists = this.akademikDao.getKelasTampung(request.getIdMataKuliah(), kodeJurusan, tahun,
								semester, tahunKurikulum, kodeJurusanBoleh, idKelas);
						if (!exists) {
							msg.add(new Message("- Anda tidak diijinkan memilih kelas ini, silakan coba kelas lain."));
							continue;
						}
					}

					Integer jumlah = this.akademikDao.getJumlahKuliah(kodeJurusan, request.getIdMataKuliah(), idKelas,
							tahun, semester, tahunKurikulum, kodeJurusanBoleh);
					Integer dayaTampung = 0;
					for (Kelas kelas : listKelas) {
						if (kelas.getKelas().equalsIgnoreCase(request.getKelas())) {
							dayaTampung = kelas.getDayaTampung();
						}
					}
					if (jumlah >= dayaTampung) {

						String kelas = request.getKelas();
						msg.add(new Message(
								"Kelas yang anda pilih telah melebihi kuota, silakan pilih kelas lain yang tersedia "
										+ kelas));
						continue;
					}

				}
			}

			String jenjang = id.substring(4, 5);
			// untuk S1 dan D3 hanya boleh mengambil 24 sks
			if (ceksksmax) {
				if (jenjang.equalsIgnoreCase("0") || jenjang.equalsIgnoreCase("1")) {
					if ((pSksAmbil + p_sksmk) > 24) {
						msg.add(new Message("- Maksimum pengambilan SKS Mahasiswa S1 dan D3 adalah 24 sks"));
						continue;
					}
				}
			}

			// untuk S2 hanya boleh mengambil 15 sks
			if (ceksksmax) {
				if (jenjang.equalsIgnoreCase("2") && !id.substring(6, 7).equalsIgnoreCase("0")) {
					if ((pSksAmbil + p_sksmk) > 15) {
						msg.add(new Message("- Maksimum pengambilan SKS mahasiswa S2 adalah 15 sks."));
						continue;
					}
				}
			}

			// cek batas sks
			if (cekbatassks) {
				Double ipsLalu = this.akademikDao.getIpsLalu(id, tahun, semester);
				Integer batasSks = this.akademikDao.getMaxSks(ipsLalu, id, tahun, semester, tahunKurikulum);
				Integer batasAmbil = pSksAmbil + p_sksmk;
				if (batasAmbil > batasSks) {
					msg.add(new Message("- Total SKS kelas yang akan diambil (" + batasAmbil + ") melebihi batas SKS ("
							+ batasSks + ")"));
					continue;
				}
			}
			// cek prasyarat
			List<Prasyarat> listPrasyarat = this.akademikDao.getPrasyarat(id, request.getIdMataKuliah(),
					tahunKurikulum);

			if (!listPrasyarat.isEmpty()) {
				for (Prasyarat prasyarat : listPrasyarat) {
					if ("BelumLulus".equalsIgnoreCase(prasyarat.getStatusLulus())) {
						msg.add(new Message("Harus LULUS matakuliah: " + prasyarat.getSyaratKodeMatkul() + " ("
								+ prasyarat.getMataKuliah() + ") terlebih dahulu, nilai saat ini: "
								+ prasyarat.getSyaratKuNilaiHuruf() + "!!"));
						continue;
					} else if ("BelumAmbil".equalsIgnoreCase(prasyarat.getStatusLulus())) {
						msg.add(new Message("Harus PERNAH MENGAMBIL matakuliah: " + prasyarat.getSyaratKodeMatkul()
								+ " (" + prasyarat.getMataKuliah() + ") terlebih dahulu !!"));
						continue;
					}
				}
			}
			if (cektidakbolehambilpersiapan) {
				Boolean checkMhs = this.akademikDao.getVLstudi(id);
				if (checkMhs) {
					Boolean strCheckMKSmt = this.akademikDao.getPersiapan(id, request.getIdMataKuliah(),
							tahunKurikulum);
					if (strCheckMKSmt) {
						msg.add(new Message(
								"Anda tidak diperkenankan mengambil mata kuliah dari semester 1 dan atau 2"));
						continue;
					}
				}
			}

			if (kodeJurusan.equalsIgnoreCase(kodeJurusanMahasiswa) || kodeJurusan.equalsIgnoreCase("__TPB")
					|| kodeJurusan.equalsIgnoreCase("__MKU")) {
				// cek no AMBIL (tidak boleh ambil selain yang AMBIL waktu ekiv)
				// ADMIN PUN tidak bisa menembus ini
				int checkBE = this.akademikDao.getCheckBE(id, tahunKurikulum, request.getIdMataKuliah());
				if (checkBE == 0) {
					msg.add(new Message(
							"- Mata kuliah ini sudah ditutup pada waktu ekivalensi, dan tidak diperbolehkan untuk mengambil ("
									+ request.getIdMataKuliah() + ")"));
					continue;
				}
				// cek no BEBAS (tidak boleh ambil yang sudah BEBAS waktu ekiv)
				// ADMIN PUN tidak bisa menembus ini
				int checkEQ = this.akademikDao.getCheckEqTidakBolehDiulang(id, tahunKurikulum,
						request.getIdMataKuliah());
				if (checkEQ == 1) {
					msg.add(new Message(
							"- Matakuliah ini sudah ditutup pada waktu ekivalensi, dan tidak diperbolehkan untuk mengulang ("
									+ request.getIdMataKuliah() + ")"));
					continue;
				}
			}

			try {
				Integer sukses = this.akademikDao.addMataKuliahFRS(id, tahun, semester, kodeJurusan, tahunKurikulum,
						request.getIdMataKuliah(), idKelas);
				if (sukses == 1)
					msg.add(new Message("Berhasil untuk id mata kuliah " + request.getIdMataKuliah()));
				else
					msg.add(new Message("Gagal untuk id mata kuliah " + request.getIdMataKuliah()));
			} catch (Exception e) {
				msg.add(new Message("Terjadi kesalahan untuk id mata kuliah " + request.getIdMataKuliah()));
			}
		}

		return Response.ok(msg).build();
	}

	private int minSksTempuh(String nrp) {

		// dict
		// D3 skstempuhmin 72
		// D4 S1 skstempuh 90
		// 1112100090
		// 2412041011
		// 1313030001

		if (nrp.substring(4, 6).equalsIgnoreCase("03"))
			return 72;

		if (nrp.substring(4, 6).equalsIgnoreCase("04"))
			return 90;

		if (nrp.substring(4, 6).equalsIgnoreCase("10"))
			return 90;
		return 0;
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/prodi/{id-prodi}/matakuliah/{id-mk}/kelas/{id-kelas}/kuliah")
	@Secured(roles = { "Mahasiswa" }, module = "AKAD")
	public Response dropKuliah(@PathParam("id-prodi") String kodeProdi, @PathParam("id-mk") String kodeMk,
			@PathParam("id-kelas") String idKelas, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester, @Context SecurityContext securityContext) {

		String nrp = securityContext.getUserPrincipal().getName();
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || kodeProdi == null || kodeMk == null
				|| idKelas == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		String kelas = this.akademikDao.getKodeKelasUPMB(tahun, semester, kodeProdi, kodeMk, idKelas);
		Integer tahunKurikulum = this.akademikDao.getTahunKurikulum(tahun);
		Integer sukses = this.akademikDao.deleteMataKuliahFRS(nrpLama, tahun, semester, kodeProdi, kodeMk, kelas,
				tahunKurikulum);

		if (sukses == 1) {
			return Response.status(200).build();
		} else {
			return Response.status(400).build();
		}

	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/prodi/{id-prodi}/matakuliah/{id-mk}/kelas/{id-kelas}/kuliah")
	@Secured(roles = { "Dosen" }, module = "AKAD")
	public Response dropKuliahDosen(@PathParam("nrp") String nrp, @PathParam("id-prodi") String kodeProdi,
			@PathParam("id-mk") String kodeMk, @PathParam("id-kelas") String idKelas,
			@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester) {
		if (nrp == null || nrp.isEmpty() || tahun == null || semester == null || kodeProdi == null || kodeMk == null) {
			return Response.status(400).build();
		}
		String nrpLama = this.mahasiswaDao.getNrpLama(nrp);
		String kelas = this.akademikDao.getKodeKelasUPMB(tahun, semester, kodeProdi, kodeMk, idKelas);
		Integer tahunKurikulum = this.akademikDao.getTahunKurikulum(tahun);
		Integer sukses = this.akademikDao.deleteMataKuliahFRS(nrpLama, tahun, semester, kodeProdi, kodeMk, kelas,
				tahunKurikulum);

		if (sukses == 1) {
			return Response.status(200).build();
		} else {
			return Response.status(400).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/persetujuan")
	@Secured(roles = { "Dosen" })
	public Response setujuFRS(SetujuFRSRequest request, @Context SecurityContext securityContext) {
		String nip = securityContext.getUserPrincipal().getName();
		String nrp = this.mahasiswaDao.getNrpLama(request.getNrp());
		this.akademikDao.setujuFRS(nrp, request.getTahun(), request.getSemester(), nip);

		return Response.ok(200).build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{nrp}/persetujuan")
	@Secured(roles = { "Dosen" })
	public Response batalFRS(@PathParam("nrp") String nrp, @QueryParam("tahun") Integer tahun,
			@QueryParam("semester") Integer semester) {

		if (nrp == null || tahun == null || semester == null) {
			return Response.status(400).build();
		}
		this.akademikDao.batalFRS(nrp, tahun, semester);

		return Response.ok(200).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/jadwal")
	@Secured(roles = { "Dosen", "Mahasiswa" }, module = "AKAD")
	public Response getJadwalFRS(@QueryParam("tahun") Integer tahun, @QueryParam("semester") Integer semester) {
		if (tahun == null || semester == null) {
			return Response.status(400).build();
		}

		List<WaktuFrs> listWaktuFRS = this.akademikDao.getJadwalFRS(tahun, semester);

		return Response.ok(toJson(listWaktuFRS)).build();
	}

}
