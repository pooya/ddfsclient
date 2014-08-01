import java.util.ArrayList;

import org.json.*;

public class Tag {
	private String id;
	private int version;
	private String date;
	private ArrayList<ArrayList<String>> urls;

	public String getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public String getDate() {
		return date;
	}

	public ArrayList<ArrayList<String>> getUrls() {
		return urls;
	}

	private Tag() {
	}

	public static Tag getTag(String s) {
		Tag tag = new Tag();
		JSONObject obj = new JSONObject(s);
		tag.version = obj.getInt("version");
		tag.id = obj.getString("id");
		tag.date = obj.getString("last-modified");

		JSONArray urllist = obj.getJSONArray("urls");

		tag.urls = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < urllist.length(); i++) {
			JSONArray blobs = urllist.getJSONArray(i);
			ArrayList<String> theseBlobs = new ArrayList<String>();
			for (int j = 0; j < blobs.length(); j++) {
				theseBlobs.add(blobs.getString(j));
			}
			tag.urls.add(theseBlobs);
		}
		return tag;
	}
}
