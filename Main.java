import org.discoproject.ddfs.DdfsTagReader;

public class Main {
	public static void main(String[] args) {
		DdfsTagReader reader = new DdfsTagReader("devdisco03", "8989", "chek");
//		DdfsTagReader reader = new DdfsTagReader("localhost", "8989", "h5");

		while (reader.hasNext()) {
			System.out.print(reader.next());
		}
	}
}