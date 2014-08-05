import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		Ddfs ddfs = new Ddfs("localhost", "8989");
		Tag tag = ddfs.getTag("h2");

		ArrayList<ArrayList<String>> urls = tag.getUrls();
		for (ArrayList<String> replica : urls) {
			String thisBlob = replica.get(0);
			InputStream is = ddfs.getBlob(thisBlob);
			Iterator<Object> objs = new DiscoRDD(is);
			while (objs.hasNext()) {
				System.out.println(objs.next());
			}
		}

	}
}