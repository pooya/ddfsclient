import org.discoproject.ddfs.DdfsTagReader;

public class Main {
	public static void main(String[] args) {
		DdfsTagReader reader = new DdfsTagReader("h2");
		while (reader.hasNext()) {
			System.out.println(reader.next());
		}
	}
}