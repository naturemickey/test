package calc;

import java.util.stream.Stream;

public class OpNode implements INode {

	final private IOp op;
	final private INode left;
	final private INode right;

	public OpNode(IOp op, INode left, INode right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public double getResult() {
		return op.calc(left.getResult(), right.getResult());
	}

	@Override
	public String toString() {
		return Stream.of(left, op, right).map(x -> {
			if (x instanceof OpNode)
				return x.toString();
			if (x instanceof INode)
				return String.valueOf((int) ((INode) x).getResult());
			if (x == IOp.ADD)
				return "+";
			if (x == IOp.SUB)
				return "-";
			if (x == IOp.MUL)
				return "*";
			return "/";
		}).reduce("", (a, b) -> a + b);
	}
}
