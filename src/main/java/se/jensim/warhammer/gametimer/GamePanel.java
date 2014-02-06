package se.jensim.warhammer.gametimer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 6099065145535084085L;

	private static final int TIME_M = 60 * 1000;
	private static final int TIME_H = 60 * TIME_M;

	private final int
			DELAY = 100,
			TIME_START;

	private int
			timeRemain,
			timeRemainTotal;

	private Timer timer = new Timer(DELAY, this);

	private final JLabel lblTime = new JLabel("time"),
			lblTimeTotal = new JLabel("total"),
			lblCaption;

	public GamePanel(final String CAPTION, final int TIME) {
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

		int tmp_remain = timeRemain;
		int timeH = timeRemain / TIME_H;
		tmp_remain -= timeH * TIME_H;
		int timeM = tmp_remain / TIME_M;
		tmp_remain -= timeM * TIME_M;
		int timeS = tmp_remain / 1000;

		int tmp_tot = timeRemainTotal;
		int totH = timeRemainTotal / TIME_H;
		tmp_tot -= totH * TIME_H;
		int totM = tmp_tot / TIME_M;
		tmp_tot -= totM * TIME_M;
		int totS = tmp_tot / 1000;

		lblTime.setText(String.format("Remaining: %02d:%02d:%02d", timeH, timeM, timeS));
		lblTimeTotal.setText(String.format("Total remaining: %02d:%02d:%02d", totH, totM, totS));
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

	public final void setTotal(final int total) {
		this.timeRemainTotal = total;
	}

	public final int getTotal() {
		return timeRemainTotal;
	}
}
