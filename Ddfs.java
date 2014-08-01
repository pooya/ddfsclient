import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Ddfs {
	private String master;
	private String port;

	public Ddfs(String master, String port) {
		this.master = master;
		this.port = port;
	}

	public String getTags() {
		return getUrl("/ddfs/tags");
	}

	public Tag getTag(String tag) {
		String reply = getUrl("/ddfs/tag/" + tag);
		return Tag.getTag(reply);
	}

	public String getBlobHost(String blobUrl) {
		int idx = blobUrl.indexOf(':') + 3;
		String urlAndPath = blobUrl.substring(idx);
		String res[] = urlAndPath.split("/", 2);
		return res[0];
	}

	public InputStream getBlob(String blobUrl) {
		int idx = blobUrl.indexOf(':') + 3;
		String urlAndPath = blobUrl.substring(idx);
		String res[] = urlAndPath.split("/", 2);
		return getInputStream(res[0] + ":" + this.port, "/" + res[1]);
	}

	public String getBlobAsString(String blobUrl) {
		int idx = blobUrl.indexOf(':') + 3;
		String urlAndPath = blobUrl.substring(idx);
		String res[] = urlAndPath.split("/", 2);
		return getUrl(res[0] + ":" + this.port, "/" + res[1]);
	}

	private String getUrl(String path) {
		return getUrl(this.master + ":" + this.port, path);
	}

	private String getUrl(String host, String path) {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				getInputStream(host, path)));
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.toString();
	}

	private InputStream getInputStream(String host, String path) {
		String url = "http://" + host + path;
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
				return con.getInputStream();
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
