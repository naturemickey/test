import java.util.Random;
import java.util.stream.Stream;

import calc.INode;
import calc.IOp;
import calc.OpNode;

public class RandomExpression {

	private static Random r = new Random();

	public static void main(String[] args) {
		Stream.generate(() -> 0).limit(20).map(a -> genExp()).forEach(System.out::println);
	}

	private static INode generate() {
		if (r.nextInt(2) > 0)
			return randomNum;
		return genExp();
	}

	private static INode randomNum = () -> r.nextInt(999);

	private static INode genExp() {
		switch (r.nextInt(4)) {
		case 0:
			return new OpNode(IOp.ADD, generate(), randomNum);
		case 1:
			return new OpNode(IOp.SUB, generate(), randomNum);
		case 2:
			return new OpNode(IOp.MUL, generate(), randomNum);
		case 3:
		}
		return new OpNode(IOp.DIV, generate(), randomNum);
	}

}
