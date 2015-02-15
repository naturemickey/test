import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.JFrame;

public class Test extends JFrame {

	private static final long serialVersionUID = -759745851194255793L;

	public static interface Fun {
		double f(double d);
	}

	private static final double dx = 0.05; // 如果太小影响绘图的速度，太大就不精确。
	// 求任意函数的微分函数
	private static final Function<Fun, Fun> fdx = fun -> x -> (fun.f(x + dx) - fun.f(x)) / dx;
	private static final Fun f1 = x -> Math.sin(x * x / 4);
	private static final Fun f2 = fdx.apply(f1);

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		super.setBounds(10, 50, winWidth, winHeight);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setVisible(true);

		final Container cp = super.getContentPane();
		final int leftLimit = (winWidth - cp.getWidth()) / 2;
		final int rightLimit = winWidth - leftLimit - 1;
		final int topLimit = winHeight - cp.getHeight() - leftLimit;
		final int bottomLimit = winHeight - leftLimit - 1;

		MouseAdapter ma = new MouseAdapter() {
			private int x;
			private int y;

			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}

			public void mouseDragged(MouseEvent e) {
				xoffset += (e.getX() - x);
				yoffset += (e.getY() - y);

				xoffset = (xoffset < leftLimit) ? leftLimit : ((xoffset > rightLimit) ? rightLimit : xoffset);
				yoffset = (yoffset < topLimit) ? topLimit : ((yoffset > bottomLimit) ? bottomLimit : yoffset);
				x = e.getX();
				y = e.getY();
				repaint();
			}
		};
		super.addMouseListener(ma);
		super.addMouseMotionListener(ma);
	}

	private final int winWidth = 1000;
	private final int winHeight = (int) (winWidth * (Math.sqrt(5) - 1) / 2);
	private final int bs = 100;
	private int xoffset = winWidth - winHeight;
	private int yoffset = winHeight / 2;

	@Override
	public void paint(Graphics g) {
		BufferedImage bf = new BufferedImage(winWidth, winHeight, BufferedImage.TYPE_INT_RGB);
		Graphics bg = bf.createGraphics();
		bg.fillRect(0, 0, winWidth, winHeight);

		bg.setColor(Color.LIGHT_GRAY);
		int step = 4;
		Stream.iterate(0, i -> i + step).limit(winWidth / step)
				.forEach(i -> bg.drawLine(i, yoffset, i + step / 2, yoffset));
		Stream.iterate(0, i -> i + step).limit(winHeight / step)
				.forEach(i -> bg.drawLine(xoffset, i, xoffset, i + step / 2));

		for (double x = 0 - xoffset; x < winWidth; x += dx) {
			paintStep(bg, Color.RED, f1, x);
			paintStep(bg, Color.BLUE, f2, x);
		}

		g.drawImage(bf, 0, 0, null);
	}

	private void paintStep(Graphics g, Color color, Fun f, double x) {
		g.setColor(color);
		double y = f.f(x);
		if (Double.isNaN(y) || Double.isInfinite(y))
			return;
		int x1 = (int) (x * bs);
		int y1 = (int) (yoffset + (y * bs) * -1);
		x += dx;
		y = f.f(x);
		if (Double.isNaN(y) || Double.isInfinite(y))
			return;
		int x2 = (int) (x * bs);
		int y2 = (int) (yoffset + (y * bs) * -1);
		if ((y1 < 0 && y2 < 0) || (winHeight < y1 && winHeight < y2))
			return;
		g.drawLine(xoffset + x1, y1, xoffset + x2, y2);
	}
}
