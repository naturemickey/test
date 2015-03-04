import static java.lang.Math.PI;
import static java.lang.Math.sin;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import javax.swing.JFrame;

public class Test extends JFrame {

	private static final long serialVersionUID = -759745851194255793L;

	public static interface Fun {
		double __(double d);
	}

	private static final double dx = 0.01;
	private static final Fun f = t -> {
		double y = 0;
		int 频域 = 100;
		while (--频域 > 0) {
			double w = 频域 % 2 == 0 ? 0 : 频域;
			double a = 4 / PI / 频域;
			double theta = 0;
			y += a * sin(w * t + theta);
		}
		return y;
	};

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
			doublePair dp = new doublePair();
			for (double x = 0 - winWidth; x < bfWidth; x += dx) {
				dp = paintStep(bg, Color.RED, f, x, dp);
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

	private static class doublePair {
		double _x = Double.NaN;
		double _y = Double.NaN;
	}

	private doublePair paintStep(Graphics2D g, Color color, Fun f, double x, doublePair lastDp) {
		g.setColor(color);

		double thisX = x;
		double thisY = f.__(x);
		double lastX = lastDp._x;
		double lastY = lastDp._y;

		doublePair dp = new doublePair();
		dp._y = thisY;
		dp._x = thisX;

		if (Double.isNaN(thisY) || Double.isInfinite(thisY))
			return dp;

		if (Double.isNaN(lastY) || Double.isInfinite(lastY))
			return dp;

		int x2 = (int) (thisX * bs);
		int y2 = (int) (winHeight + (thisY * bs) * -1);

		int x1 = (int) (lastX * bs);
		int y1 = (int) (winHeight + (lastY * bs) * -1);

		if ((y1 < -winWidth && y2 < -winWidth) || (winHeight * 2 < y1 && winHeight * 2 < y2))
			return dp;

		g.drawLine(winWidth + x1, y1, winWidth + x2, y2);

		return dp;
	}
}
