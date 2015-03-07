import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.swing.JFrame;

public class Test extends JFrame {

	private static final long serialVersionUID = -8424996879509102365L;

	public static final int winWidth = 1000;
	public static final int winHeight = (int) (winWidth * (Math.sqrt(5) - 1) / 2);
	public static final ExecutorService es = Executors.newCachedThreadPool();
	public static final int count = 100;

	public static void main(String[] args) {
		// System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		new Test();
	}

	public Test() {
		super.setBounds(10, 50, winWidth, winHeight);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.setVisible(true);
		Graphics g = super.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, winWidth, winHeight);
		List<Bee> bees = new ArrayList<>(count);
		for (int i = 0; i < count; ++i) {
			bees.add(new Bee(winWidth / 2, winHeight / 2, g, bees, 2 + i % 2));
		}
		bees.forEach(es::execute);
	}

	@Override
	public void finalize() {
		es.shutdown();
	}

}

class pairHolder {
	double ds;
	Bee bee;

	public pairHolder(double ds, Bee bee) {
		super();
		this.ds = ds;
		this.bee = bee;
	}
}

class Bee implements Runnable {
	private int x, y;
	private Graphics g;
	private Random r = new Random();
	private List<Bee> group = new ArrayList<>();
	private int neighborsCount;

	public Bee(int x, int y, Graphics g, List<Bee> group, int neighborsCount) {
		this.x = x;
		this.y = y;
		this.g = g;
		this.group = group;
		this.neighborsCount = neighborsCount;
	}

	public void move() {
		int x1 = x + randomInt();
		int y1 = y + randomInt();
		if (x == x1 && y == y1)
			return;
		List<pairHolder> phl = this.group.stream().filter(bee -> bee != this)
				.map(bee -> new pairHolder(distance(x, y, bee.x, bee.y), bee)).collect(Collectors.toList());
		phl.sort((a, b) -> a.ds > b.ds ? 1 : a.ds < b.ds ? -1 : 0);

		for (Bee bee : phl.subList(0, neighborsCount).stream().map(p -> p.bee).collect(Collectors.toList())) {
			double d1 = distance(x, y, bee.x, bee.y);
			double d2 = distance(x1, y1, bee.x, bee.y);
			if (d2 < 15) { // too close
				if (d2 < d1)
					return;
			} else if (d2 > 25) { // to far
				if (d2 > d1)
					return;
			}
		}
		if (x1 > 0 && x1 < Test.winWidth && y1 > 0 && y1 < Test.winHeight) {
			draw(g, x, y, x1, y1);
			x = x1;
			y = y1;
		}
	}

	private static synchronized void draw(Graphics g, int x, int y, int x1, int y1) {
		int d = 4;
		g.setColor(Color.WHITE);
		g.fillRect(x, y, d, d);
		g.setColor(Color.BLACK);
		g.fillRect(x1, y1, d, d);
	}

	private double distance(int x1, int y1, int x2, int y2) {
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		return Math.sqrt(dx * dx + dy * dy);
	}

	private int randomInt() {
		return r.nextInt(5) - 2;
	}

	@Override
	public void run() {
		while (true) {
			this.move();
			try {
				TimeUnit.MICROSECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}