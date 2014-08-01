public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World");
		Ddfs ddfs = new Ddfs();
		String s = ddfs.getTags("localhost:8989");
		System.out.println("reply is: " + s);

		Tag tag = ddfs.getTag("localhost:8989", "jj72");
		System.out.println("urls are: " + tag.getUrls());
	}
}