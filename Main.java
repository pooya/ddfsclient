import java.io.InputStream;
import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		Ddfs ddfs = new Ddfs("localhost", "8989");
		Tag tag = ddfs.getTag("h");
		String thisBlob = tag.getUrls().get(0).get(0);

		InputStream is = ddfs.getBlob(thisBlob);
		Iterator<Object> objs = new DiscoRDD(is);
		while (objs.hasNext()) {
			System.out.println(objs.next());
		}
	}
}