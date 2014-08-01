import java.io.IOException;
import java.io.InputStream;

public class UnChunker {
	private static final int HEADER_SIZE = 14;

	public static int unchunk(InputStream is) {
		byte[] chunkHeader = new byte[HEADER_SIZE];
		try {
			long nRead = is.read(chunkHeader);
			if (nRead != HEADER_SIZE) {
				throw new IOException("bad header");
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
			System.out.println(version + " : " + isCompressed + " : "
					+ checkSum + " : " + hunkSize);
			return hunkSize;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
