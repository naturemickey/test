import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.JFrame;

public class Test extends JFrame {

	private static final long serialVersionUID = -759745851194255793L;

	public static interface Fun {
		double __(double d);
	}

	private static final double dx = 0.005;
	// 求任意函数的微分函数
	private static final Function<Fun, Fun> fdx = f -> x -> (f.__(x + dx) - f.__(x)) / dx;
	private static final Fun f1 = x -> Math.sin(x * x / 4);
	private static final Fun f2 = fdx.apply(f1);

	public static void main(String[] args) {
		new Test();
	}

	public Test() {
		super.setBounds(10, 50, winWidth, winHeight);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		{
			// init bf
			int bfWidth = winWidth * 2;
			int bfHeight = winHeight * 2;
			bf = new BufferedImage(bfWidth, bfHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D bg = (Graphics2D) bf.createGraphics();
			bg.fillRect(0, 0, bfWidth, bfHeight);

			bg.setColor(Color.LIGHT_GRAY);
			int step = 9;
			Stream.iterate(0, i -> i + step).limit(bfWidth / step)
					.forEach(i -> bg.drawLine(i, winHeight, i + step / 2, winHeight));
			Stream.iterate(0, i -> i + step).limit(bfHeight / step)
					.forEach(i -> bg.drawLine(winWidth, i, winWidth, i + step / 2));

			bg.setStroke(new BasicStroke(2f));
			for (double x1 = 0 - winWidth; x1 < bfWidth; x1 += dx) {
				paintStep(bg, Color.RED, f1, x1);
				paintStep(bg, Color.BLUE, f2, x1);
			}
		}
		super.setVisible(true);

		final Container cp = super.getContentPane();
		final int leftLimit = -cp.getWidth() - (winWidth - cp.getWidth()) / 2;
		final int rightLimit = -(winWidth - cp.getWidth()) / 2 - 1;
		final int topLimit = -cp.getHeight() - (winWidth - cp.getWidth()) / 2;
		final int bottomLimit = rightLimit;

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
	private int xoffset = -winHeight;
	private int yoffset = -winHeight / 2;

	private final BufferedImage bf;

	@Override
	public void paint(Graphics g) {
		g.drawImage(bf, xoffset, yoffset, null);
	}

	private void paintStep(Graphics2D g, Color color, Fun f, double x) {
		g.setColor(color);
		double y = f.__(x);
		if (Double.isNaN(y) || Double.isInfinite(y))
			return;
		int x1 = (int) (x * bs);
		int y1 = (int) (winHeight + (y * bs) * -1);
		x += dx;
		y = f.__(x);
		if (Double.isNaN(y) || Double.isInfinite(y))
			return;
		int x2 = (int) (x * bs);
		int y2 = (int) (winHeight + (y * bs) * -1);
		if ((y1 < -winWidth && y2 < -winWidth) || (winHeight * 2 < y1 && winHeight * 2 < y2))
			return;
		g.drawLine(winWidth + x1, y1, winWidth + x2, y2);
	}
}
