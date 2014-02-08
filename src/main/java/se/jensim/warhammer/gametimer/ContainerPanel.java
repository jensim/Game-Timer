package se.jensim.warhammer.gametimer;

import javax.swing.JPanel;

public abstract class ContainerPanel extends JPanel {
	private static final long serialVersionUID = -7221157797565823435L;
	
	protected final String caption;

	protected static final int TIME_M = 60 * 1000,
			TIME_H = 60 * TIME_M;

	protected int
			timeRemain,
			timeRemainTotal;
	
	public ContainerPanel(String cap) {
		caption = cap;
	}
	
	public final String getCaption(){
		return this.caption;
	}

	public void setTotal(final int total) {
		this.timeRemainTotal = total;
	}

	public int getTotal() {
		return timeRemainTotal;
	}
	
	public abstract void start() ;
	public abstract void stop();
	public abstract void toggleRunning();
}
