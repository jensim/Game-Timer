package se.jensim.warhammer.gametimer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends ContainerPanel implements ActionListener {
	private static final long serialVersionUID = 6099065145535084085L;

	private final int DELAY = 100;
	private final Timer timer = new Timer(DELAY, this);

	private final JLabel
			lblCapTime = new JLabel("Time remaining: "),
			lblCapTotal = new JLabel("Total remaining: "),
			lblTime = new JLabel("time"),
			lblTotal = new JLabel("total"),
			lblCaption = new JLabel();

	private final JLabel[] labels = new JLabel[]{
			lblCaption,
			lblCapTime,
			lblCapTotal,
			lblTime,
			lblTotal};

	public GamePanel(final String CAPTION, final long TIME, ActionListener actionListener) {
		super(CAPTION, actionListener);
		lblCaption.setText(CAPTION);
		timeRemain = TIME;
		timeStart = TIME;

		// TODO: DEBUG PRINT
		// System.out.println(String.format("GamePanel '%s' long parameter:%d, timeRemain:%d", CAPTION, TIME,
		// timeRemain));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel grid = new JPanel(new GridLayout(2, 2));

		for (JLabel lbl : labels) {
			lbl.setFont(fonts[0]);
		}

		add(lblCaption);
		add(grid);

		grid.add(lblCapTime);
		grid.add(lblTime);

		grid.add(lblCapTotal);
		grid.add(lblTotal);
	}

	public void actionPerformed(ActionEvent e) {
		long timeTmpRemain = (timeRemain - System.currentTimeMillis() + timeLastmeasure);
		long timeTmpTotal = (timeRemainTotal - System.currentTimeMillis() + timeLastmeasure);

		lblTime.setText(HelperTool.millsToTimeString(timeTmpRemain));
		lblTotal.setText(HelperTool.millsToTimeString(timeTmpTotal));
		
		if(timeTmpRemain <= 0 && listener != null){
			ActionEvent event = new ActionEvent(this, -1, caption);
			listener.actionPerformed(event);
		}
	}

	public final void setFont(int font) {
		if (font >= fonts.length || font < 0) {
			//WARNING
		} else {
			for (JLabel lbl : labels) {
				lbl.setFont(fonts[font]);
			}
		}
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
		timeRemainTotal -= timeSpan;
		timeLastmeasure = System.currentTimeMillis();
	}
}
