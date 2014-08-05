package org.discoproject.ddfs;

import java.util.ArrayList;
import java.util.Iterator;

public class DdfsTagReader implements Iterator<Object> {
	private Iterator<ArrayList<String>> urls;
	private DdfsBlobReader blobReader;
	private Ddfs ddfs;

	public DdfsTagReader(String tagname) {
		ddfs = new Ddfs("localhost", "8989");
		Tag tag = ddfs.getTag(tagname);
		urls = tag.getUrls().iterator();
	}

	@Override
	public boolean hasNext() {
		if (blobReader != null && blobReader.hasNext()) {
			return true;
		} else if (urls.hasNext()) {
			ArrayList<String> replica = urls.next();
			String blobName = replica.get(0);
			blobReader = new DdfsBlobReader(ddfs, blobName);
			return blobReader.hasNext();
		} else {
			return false;
		}
	}

	@Override
	public Object next() {
		return blobReader.next();
	}

	@Override
	public void remove() {
		throw new RuntimeException("remove is not supported.");
	}

}
