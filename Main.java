
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World");
		Ddfs ddfs = new Ddfs();
		String s = ddfs.getTags("localhost:8989");
		System.out.println("reply is: " + s);
		

		String s1 = ddfs.getTag("localhost:8989", "jj71");
		System.out.println("reply is: " + s1);
	}
}