package se.jensim.warhammer.gametimer;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class ContainerPanel extends JPanel {
	private static final long serialVersionUID = -7221157797565823435L;

	protected final String caption;
	protected final ActionListener listener;

	protected static final int TIME_M = 60 * 1000,
			TIME_H = 60 * TIME_M;

	protected long
			timeStart = 0,
			timeRemain = 0,
			timeRemainTotal = 0,
			timeLastmeasure = System.currentTimeMillis();

	protected static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 14);

	public ContainerPanel(String cap, ActionListener listener) {
		caption = cap;
		this.listener = listener;
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
	
	public long getPlayedTime(){
		return timeStart - timeRemain;
	}

	public abstract void setMyFonts(Font font);
	public abstract void start();
	public abstract void stop();
	public abstract void toggleRunning();
}
