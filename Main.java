import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import net.razorvine.pickle.PickleException;
import net.razorvine.pickle.Unpickler;

public class Main {
	public static void main(String[] args) {
		Ddfs ddfs = new Ddfs("localhost", "8989");
		Tag tag = ddfs.getTag("h");
		String thisBlob = tag.getUrls().get(0).get(0);
		InputStream is = ddfs.getBlob(thisBlob);
		;

		Unpickler un = new Unpickler();
		try {
			int hunkSize = UnChunker.unchunk(is);

			while (hunkSize != -1) {
				byte[] compressedHunk = new byte[hunkSize];
				byte[] uncompressedHunk = new byte[hunkSize];

				is.read(compressedHunk);
				Inflater decompressor = new Inflater();
				decompressor.setInput(compressedHunk);
				decompressor.inflate(uncompressedHunk);
				System.out.println(uncompressedHunk);
				InputStream hunkInputStream = new ByteArrayInputStream(
						uncompressedHunk);
				while (true) {
					try {
						Object obj = un.load(hunkInputStream);
						System.out.println(obj);
					} catch (PickleException e) {
						break;
					}
				}
				hunkSize = UnChunker.unchunk(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
	}
}