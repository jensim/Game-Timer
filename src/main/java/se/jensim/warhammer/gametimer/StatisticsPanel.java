package se.jensim.warhammer.gametimer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class StatisticsPanel extends ContainerPanel {
	private static final long serialVersionUID = 6070064091425673875L;

	public StatisticsPanel(String CAPTION, int TIME) {
		super(CAPTION);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public final void setContent(String... strings) {
		removeAll();
		for (String s : strings) {
			add(new JLabel(s));
		}
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void toggleRunning() {
	}
}
