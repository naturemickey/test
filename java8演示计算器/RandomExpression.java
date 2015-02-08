import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomExpression {

	private static Random r = new Random();

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
		return Stream.generate(() -> r.nextInt(100)).limit(len).map(i -> "" + genop() + i)
				.collect(Collectors.joining()).substring(1);
	}

	private static final char[] ops = { '+', '-', '*', '/', };

	private static char genop() {
		return ops[r.nextInt(4)];
	}
}
