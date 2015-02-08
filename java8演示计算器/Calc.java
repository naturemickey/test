import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Calc {

	public static void main(String[] args) throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

		List<String> lines = Files.readAllLines(Paths.get("exps"));

		List<String> res = lines.stream().map(exp -> {
			try {
				return exp + " = " + engine.eval(exp);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}).collect(Collectors.toList());

		Files.write(Paths.get("express"), res);
	}
}
