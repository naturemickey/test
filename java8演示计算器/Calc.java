import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Calc {
	private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

	public static void main(String[] args) throws Exception {
		List<String> lines = Files.readAllLines(Paths.get("exps"));

		lines.stream().map(Calc::eval).forEach(System.out::println);
	}

	private static String eval(String exp) {
		try {
			return exp + " = " + engine.eval(exp);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
