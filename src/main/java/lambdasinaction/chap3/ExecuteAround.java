package lambdasinaction.chap3;

import java.io.*;
public class ExecuteAround {

	public static void main(String ...args) throws IOException{
		String path = "src/main/resources/lambdasinaction/chap3/data.txt";
		File f = new File(path);
		System.out.println(f.getAbsolutePath());

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);

        System.out.println("---");

		String oneLine = processFile((BufferedReader b) -> b.readLine());
		System.out.println(oneLine);

		String twoLines = processFile((BufferedReader b) -> b.readLine() + b.readLine());
		System.out.println(twoLines);

		System.out.println("-----------------------------");
		BufferedReaderProcessor processor = (BufferedReader b) -> b.readLine();
		String s = processor.process(new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt")));
		System.out.println(s);
	}

    public static String processFileLimited() throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt"))) {
            return br.readLine();
        }
    }


	public static String processFile(BufferedReaderProcessor p) throws IOException {
		try(BufferedReader br = new BufferedReader(new FileReader("lambdasinaction/chap3/data.txt"))){
			return p.process(br);
		}

	}

	@FunctionalInterface
	public interface BufferedReaderProcessor{
		public String process(BufferedReader b) throws IOException;

	}
}
