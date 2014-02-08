package se.jensim.warhammer.gametimer;

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
	
	public ContainerPanel(String cap) {
		caption = cap;
	}
	
	public final String getCaption(){
		return this.caption;
	}

	public void setTotal(final long total) {
		this.timeRemainTotal = total;
	}

	public long getTotal() {
		return timeRemainTotal;
	}
	
	public abstract void start() ;
	public abstract void stop();
	public abstract void toggleRunning();
}
