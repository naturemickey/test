import java.awt.Color;
import java.awt.Graphics;
import java.util.function.Function;

import javax.swing.JFrame;

public class Test extends JFrame {

	private static final long serialVersionUID = -759745851194255793L;

	public static interface DD {
		double f(double d);
	}

	private static final double dx = 0.01;
	private Function<DD, DD> fdx = (DD dd) -> x -> (dd.f(x + dx) - dd.f(x)) / dx;
	private DD f1 = Math::sin;
	private DD f2 = fdx.apply(f1);

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		super.setSize(1000, 600);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		double x = 0;
		g.setColor(Color.RED);
		for (int i = 0; i < 1000; ++i) {
			int x1 = (int) (x * 100);
			int y1 = (int) (200 + (f1.f(x) * 100));
			x += dx;
			int x2 = (int) (x * 100);
			int y2 = (int) (200 + (f1.f(x) * 100));
			g.drawLine(x1, y1, x2, y2);
		}
		g.setColor(Color.green);
		x = 0;
		for (int i = 0; i < 1000; ++i) {
			int x1 = (int) (x * 100);
			int y1 = (int) (200 + (f2.f(x) * 100));
			x += dx;
			int x2 = (int) (x * 100);
			int y2 = (int) (200 + (f2.f(x) * 100));
			g.drawLine(x1, y1, x2, y2);
		}
	}

}
