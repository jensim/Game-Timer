package se.jensim.warhammer.gametimer;

import java.awt.Font;

import javax.swing.JPanel;

public abstract class ContainerPanel extends JPanel {
	private static final long serialVersionUID = -7221157797565823435L;

	protected final String caption;

	protected static final int TIME_M = 60 * 1000,
			TIME_H = 60 * TIME_M;

	protected long
			timeRemain,
			timeRemainTotal,
			timeLastmeasure = System.currentTimeMillis();

	protected static final Font[] fonts = new Font[]{
			new Font("Arial", Font.PLAIN, 14),
			new Font("Arial", Font.PLAIN, 18),
			new Font("Arial", Font.PLAIN, 24),
			new Font("Arial", Font.PLAIN, 30),
			new Font("Arial", Font.PLAIN, 36),
			new Font("Arial", Font.PLAIN, 42),
			new Font("Arial", Font.PLAIN, 48),
			new Font("Arial", Font.PLAIN, 54),
			new Font("Arial", Font.PLAIN, 60),
			new Font("Arial", Font.PLAIN, 66),
			new Font("Arial", Font.PLAIN, 72),
			new Font("Arial", Font.PLAIN, 78),
			new Font("Arial", Font.PLAIN, 84),
			new Font("Arial", Font.PLAIN, 90),
			new Font("Arial", Font.PLAIN, 96),
			new Font("Arial", Font.PLAIN, 102),
			new Font("Arial", Font.PLAIN, 108)};

	public ContainerPanel(String cap) {
		caption = cap;
	}

	public final String getCaption() {
		return this.caption;
	}

	public void setTotal(final long total) {
		this.timeRemainTotal = total;
	}

	public long getTotal() {
		return timeRemainTotal;
	}

	public abstract void start();
	public abstract void stop();
	public abstract void toggleRunning();
}
