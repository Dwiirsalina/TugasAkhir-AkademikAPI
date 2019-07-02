package id.its.akademik.dao.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import id.its.akademik.dao.AbstractDao;
import id.its.akademik.dao.SppDao;

public class HttpSppDao extends AbstractDao implements SppDao {

	private final String url = "http://10.199.13.45/cek/cekspp.php?gendull=";
	private final String USER_AGENT = "";

	@Override
	public Integer getStatusPembayaranSpp(String nrp, Integer tahun, Integer semester) {

		String responseGet = "";
		int responseCode = 0;

		StringBuffer urlPath = new StringBuffer(url);
		urlPath.append(nrp);
		urlPath.append(tahun.toString());
		urlPath.append(semester.toString());

		try {
			URL obj = new URL(urlPath.toString());
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "AkademikAPI/1.0");

			responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			responseGet = response.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int result = 0;

		if (responseGet == null || responseGet.isEmpty()) {
			result = 0;
		} else if (responseGet.equals("1") || responseGet.equals("2") || responseGet.equals("5")) {
			result = 1;
		}

		return result;
	}

}
