package calc;

@FunctionalInterface
public interface IOp {
	
	double calc(double a, double b);
	
	IOp ADD = (a, b) -> a + b;
	IOp SUB = (a, b) -> a - b;
	IOp MUL = (a, b) -> a * b;
	IOp DIV = (a, b) -> a / b;
}
