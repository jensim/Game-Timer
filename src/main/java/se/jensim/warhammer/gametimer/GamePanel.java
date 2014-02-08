package se.jensim.warhammer.gametimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

public class GamePanel extends ContainerPanel implements ActionListener {
	private static final long serialVersionUID = 6099065145535084085L;

	private final int
			DELAY = 100,
			TIME_START;

	private final Timer timer = new Timer(DELAY, this);

	private final JLabel lblTime = new JLabel("time"),
			lblTimeTotal = new JLabel("total"),
			lblCaption;

	public GamePanel(final String CAPTION, final int TIME) {
		super(CAPTION);
		lblCaption = new JLabel(CAPTION);
		TIME_START = TIME;
		timeRemain = TIME_START;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(lblCaption);
		add(lblTimeTotal);
		add(lblTime);
	}

	public void actionPerformed(ActionEvent e) {
		timeRemain -= DELAY;
		timeRemainTotal -= DELAY;

		lblTime.setText("Remaining: " + HelperTool.millsToTimeString(timeRemain));
		lblTimeTotal.setText("Total remaining: " + HelperTool.millsToTimeString(timeRemainTotal));
	}

	public final void start() {
		if (!timer.isRunning()) {
			timer.start();
		}
	}

	public final void stop() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}

	public final void toggleRunning() {
		if (timer.isRunning()) {
			timer.stop();
		} else {
			timer.start();
		}
	}
}
