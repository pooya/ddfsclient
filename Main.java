import java.util.ArrayList;
import java.util.Iterator;

import org.discoproject.ddfs.Ddfs;
import org.discoproject.ddfs.Tag;
import org.discoproject.ddfs.DdfsBlobReader;


public class Main {
	public static void main(String[] args) {
		Ddfs ddfs = new Ddfs("localhost", "8989");
		Tag tag = ddfs.getTag("h5");

		ArrayList<ArrayList<String>> urls = tag.getUrls();
		for (ArrayList<String> replica : urls) {
			String thisBlob = replica.get(0);
			Iterator<Object> objs = new DdfsBlobReader(ddfs, thisBlob);
			while (objs.hasNext()) {
				System.out.println(objs.next());
			}
		}

	}
}