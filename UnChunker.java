import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import net.razorvine.pickle.PickleException;
import net.razorvine.pickle.Unpickler;

public class UnChunker {
	private static final int HEADER_SIZE = 14;

	public static int unchunk(InputStream is) {
		byte[] chunkHeader = new byte[HEADER_SIZE];
		try {
			long nRead = is.read(chunkHeader);
			if (nRead != HEADER_SIZE) {
//				throw new IOException("bad header size: " + nRead);
				return -1;
			}
			int version = 0xff & chunkHeader[0];
			long isCompressed = (0xff & chunkHeader[1]);
			long checkSum = 0;

			for (int i = 2, offset = 0; i < 6; i++) {
				checkSum += (0xff & chunkHeader[i]) << offset;
				offset += 4;
			}

			int hunkSize = 0;

			for (int i = 6, offset = 0; i < 14; i++) {
				hunkSize += (0xff & chunkHeader[i]) << offset;
				offset += 4;
			}
//			System.out.println(version + " : " + isCompressed + " : "
//					+ checkSum + " : " + hunkSize);
			return hunkSize;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static Iterator<String> getElementsOfBlob(InputStream is) {
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
			return null;
		} catch (DataFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
}
