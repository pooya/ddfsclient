package org.discoproject.ddfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.InflaterInputStream;

import net.razorvine.pickle.PickleException;
import net.razorvine.pickle.Unpickler;
import de.undercouch.bson4jackson.io.BoundedInputStream;

public class DdfsBlobReader implements Iterator<Object> {
	private InputStream inputStream;
	private ArrayList<Object> objects;
	private int hunkSize;
	Unpickler unpickler;

	public DdfsBlobReader(Ddfs ddfs, String blobName) {
		InputStream inputStream = ddfs.getBlob(blobName);
		this.inputStream = inputStream;
		objects = new ArrayList<Object>();
		unpickler = new Unpickler();
		hunkSize = -1;
	}

	private void readObjectsFromHunk() {
		try {
			InputStream is = new InflaterInputStream(new BoundedInputStream(
					inputStream, hunkSize));
			while (true) {
				try {
					Object obj = unpickler.load(is);
					objects.add(obj);
				} catch (PickleException e) {
					break;
				}
			}
		} catch (IOException e1) {
			if (e1.getMessage() != "premature end of file") {
				e1.printStackTrace();
			}
		}
		hunkSize = -1;
	}

	private void readNextHunk() {
		hunkSize = UnChunker.unchunk(inputStream);
	}

	@Override
	public boolean hasNext() {
		if (!objects.isEmpty())
			return true;
		if (hunkSize != -1) {
			readObjectsFromHunk();
			if (!objects.isEmpty())
				return true;
		}
		readNextHunk();
		if (hunkSize == -1) {
			return false;
		} else {
			readObjectsFromHunk();
			return !objects.isEmpty();
		}
	}

	@Override
	public Object next() {
		return objects.remove(0);
	}

	@Override
	public void remove() {
		throw new RuntimeException("remove not supported yet.");
	}
}
