package se.jensim.warhammer.gametimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Timer;

public class GamePanel extends ContainerPanel implements ActionListener {
	private static final long serialVersionUID = 6099065145535084085L;

	private final int DELAY = 100;
	private final Timer timer = new Timer(DELAY, this);

	private final JLabel lblTime = new JLabel("time"),
			lblTimeTotal = new JLabel("total"),
			lblCaption;

	public GamePanel(final String CAPTION, final long TIME) {
		super(CAPTION);
		lblCaption = new JLabel(CAPTION);
		timeRemain = TIME;

		//TODO: DEBUG PRINT
		//System.out.println(String.format("GamePanel '%s' long parameter:%d, timeRemain:%d", CAPTION, TIME, timeRemain));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(lblCaption);
		add(lblTimeTotal);
		add(lblTime);
	}

	public void actionPerformed(ActionEvent e) {
		long timeTmpRemain = (timeRemain - System.currentTimeMillis() + timeLastmeasure);
		long timeTmpTotal = (timeRemainTotal - System.currentTimeMillis() + timeLastmeasure);

		lblTime.setText("Remaining: " + HelperTool.millsToTimeString(timeTmpRemain));
		lblTimeTotal.setText("Total remaining: " + HelperTool.millsToTimeString(timeTmpTotal));
	}

	public final void start() {
		if (!timer.isRunning()) {
			timer.start();
		}
		timeLastmeasure = System.currentTimeMillis();
	}

	public final void stop() {
		if (timer.isRunning()) {
			timer.stop();
		}
		recordTimeSpan();
	}

	public final void toggleRunning() {
		if (timer.isRunning()) {
			stop();
		} else {
			start();
		}
	}

	private final void recordTimeSpan() {
		long timeSpan = System.currentTimeMillis() - timeLastmeasure;
		timeRemain -= timeSpan;
		timeRemainTotal -=timeSpan;
		timeLastmeasure = System.currentTimeMillis();
	}
}
