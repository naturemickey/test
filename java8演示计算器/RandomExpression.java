import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomExpression {

	private static Random r = new Random();
	private static final char[] ops = { '+', '-', '*', '/', };

	public static void main(String[] args) throws Exception {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("exps"));) {
			for (int i = 0; i < 100; ++i) {
				bw.write(genExp());
				bw.write("\n");
			}
		}
	}

	private static String genExp() {
		int len = 2 + r.nextInt(5);
		return Stream.generate(() -> r.nextInt(100)).limit(len).map(i -> "" + ops[r.nextInt(4)] + i)
				.collect(Collectors.joining()).substring(1);
	}

}
