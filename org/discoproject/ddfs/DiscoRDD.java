package org.discoproject.ddfs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import net.razorvine.pickle.PickleException;
import net.razorvine.pickle.Unpickler;

public class DiscoRDD implements Iterator<Object> {
	private InputStream inputStream;
	private ArrayList<Object> objects;
	private int hunkSize;
	Unpickler unpickler;

	public DiscoRDD(InputStream inputStream) {
		this.inputStream = inputStream;
		objects = new ArrayList<Object>();
		unpickler = new Unpickler();
	}

	private void readObjectsFromHunk() {
		byte[] compressedHunk = new byte[hunkSize];
		byte[] uncompressedHunk = new byte[hunkSize * 10];

		try {
			inputStream.read(compressedHunk);
			Inflater decompressor = new Inflater();
			decompressor.setInput(compressedHunk);
			decompressor.inflate(uncompressedHunk);
			InputStream hunkInputStream = new ByteArrayInputStream(
					uncompressedHunk);
			while (true) {
				try {
					Object obj = unpickler.load(hunkInputStream);
					objects.add(obj);
				} catch (PickleException e) {
					break;
				}
			}
		} catch (IOException e1) {
		} catch (DataFormatException e1) {
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
