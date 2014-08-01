public class Main {
	public static void main(String[] args) {
		Ddfs ddfs = new Ddfs("localhost", "8989");
		String s = ddfs.getTags();
		System.out.println("reply is: " + s);

		Tag tag = ddfs.getTag("jj72");
		System.out.println("urls are: " + tag.getUrls());
		String blob = ddfs.getBlob(tag.getUrls().get(0).get(0));
		System.out.println(blob);
		System.out.println(ddfs.getBlobHost(tag.getUrls().get(0).get(0)));
	}
}