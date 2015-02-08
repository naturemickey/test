import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomExpression {

	private static Random r = new Random();
	private static final char[] ops = { '+', '-', '*', '/', };

	public static void main(String[] args) throws Exception {
		List<String> exps = Stream.generate(() -> 1).limit(100).map(i -> genExp()).collect(Collectors.toList());
		Files.write(Paths.get("exps"), exps);
	}

	private static String genExp() {
		int len = 2 + r.nextInt(5);
		return Stream.generate(() -> r.nextInt(100)).limit(len).map(i -> "" + ops[r.nextInt(4)] + i)
				.collect(Collectors.joining()).substring(1);
	}

}
