package id.its.akademik.util;

public class AkadUtil {

	public static String getKodeFakultasFromNrp(String nrp) {
		
		return nrp.substring(0, 1);
	}
	
	public static String getKodeFakultasFromKodeDepartemen(String kodeJurusan) {
		
		return kodeJurusan.substring(0, 1);
	}
	
	public static String getKodeDepartemenFromNrp(String nrp) {
		return nrp.substring(0, 2) + nrp.substring(4, 7);
	}
	
}
