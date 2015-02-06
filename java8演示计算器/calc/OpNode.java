package calc;

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

}
