package org.discoproject.ddfs;

import java.io.IOException;
import java.io.InputStream;

public class UnChunker {
	private static final int HEADER_SIZE = 14;

	public static int unchunk(InputStream is) {
		byte[] chunkHeader = new byte[HEADER_SIZE];
		try {
			long nRead = is.read(chunkHeader);
			if (nRead != HEADER_SIZE) {
				return -1;
			}
//			for (int i =0; i < HEADER_SIZE; i++) {
//				System.out.printf("%x\t", chunkHeader[i] & 0xff);
//			}
			int version = 0xff & chunkHeader[0];
			long isCompressed = (0xff & chunkHeader[1]);
			/*
			 * TODO add checksum verification: long checkSum = 0;
			 * 
			 * for (int i = 2, offset = 0; i < 6; i++) { checkSum += (0xff &
			 * chunkHeader[i]) << offset; offset += 8; }
			 */

			int hunkSize = 0;

			for (int i = 6, offset = 0; i < 14; i++) {
				hunkSize += (0xff & chunkHeader[i]) << offset;
				offset += 8;
			}
			if (version != 129) {
				System.out.println("version:" + version +
								   " isCompressed : " + isCompressed +
								   " hunkSize: " + hunkSize);
				throw new RuntimeException("version " + version + " not implemented yet.");
			}
			if (isCompressed != 1) {
				throw new RuntimeException("not implemented yet" + isCompressed);
			}
			return hunkSize;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
