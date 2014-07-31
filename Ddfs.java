import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Ddfs {

	public String getTags(String master) {
		return getUrl(master, "/ddfs/tags");
	}


	public String getTag(String master, String tag) {
		String reply =getUrl(master, "/ddfs/tag/" + tag);
		return reply;
	}
	
	private String getUrl(String master, String path) {
		String url = "http://" + master + path;
		 
		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpURLConnection con;
		try {
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} else {
				System.out.println("response code is: " + responseCode);
			}
			
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
