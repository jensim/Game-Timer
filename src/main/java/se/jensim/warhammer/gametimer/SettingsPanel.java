package se.jensim.warhammer.gametimer;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author jens
 */
public class SettingsPanel extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 2839991578674267943L;

	final JSpinner time[][] = new JSpinner[][]{
			{new JSpinner(new SpinnerNumberModel(4, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(0, 0, 55, 5))},
			{new JSpinner(new SpinnerNumberModel(0, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(20, 0, 55, 5))},
			{new JSpinner(new SpinnerNumberModel(0, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(30, 0, 55, 5))}};
	final String[] title = new String[]{"Total time", "Presentation", "Deployment", "Round time"};
	final JLabel lblRoundTime = new JLabel();

	final int COLS = 5, ROWS = title.length;

	public SettingsPanel() {
		setLayout(new GridLayout(ROWS, COLS));

		for (int i = 0; i < COLS * (ROWS - 1); ++i) {
			int row = i / COLS;
			int col = i % COLS;
			switch (col) {
				case 0 :
					add(new JLabel(title[row]));
					break;
				case 1 :
					add(time[row][0]);
					time[row][0].addChangeListener(this);
					break;

				case 2 :
					add(new JLabel("h"));
					break;

				case 3 :
					add(time[row][1]);
					time[row][1].addChangeListener(this);
					break;

				case 4 :
					add(new JLabel("m"));
					break;

				default :
					break;
			}
		}

		add(new JLabel(title[title.length - 1]));
		add(lblRoundTime);
	}

	public int getTotalTime() {
		int total = ((Integer) time[0][0].getValue()) * HelperTool.TIME_H;
		total += ((Integer) time[0][1].getValue()) * HelperTool.TIME_M;
		return total;
	}

	public int getPresentationTime() {
		int total = ((Integer) time[1][0].getValue()) * HelperTool.TIME_H;
		total += ((Integer) time[1][1].getValue()) * HelperTool.TIME_M;
		return total;
	}

	public final int getDeploymentTime() {
		int total = ((Integer) time[2][0].getValue()) * HelperTool.TIME_H;
		total += ((Integer) time[2][1].getValue()) * HelperTool.TIME_M;
		return total;
	}

	public void stateChanged(ChangeEvent e) {
		int timeM = ((Integer) time[0][1].getValue());
		int timeH = ((Integer) time[0][0].getValue());

		for (int row = 1; row < time.length; ++row) {
			JSpinner spinnH = time[row][0];
			timeH -= ((Integer) spinnH.getValue());

			JSpinner spinnM = time[row][1];
			timeM -= ((Integer) spinnM.getValue());
		}

		int timeTot = timeM + (timeH * 60);
		int rounds = MainWindow.DEFAULT_PLAYERS * MainWindow.DEFAULT_ROUNDS;
		timeTot /= rounds;
		timeH = timeTot / 60;
		timeM = timeTot % 60;

		lblRoundTime.setText(timeH + "h " + timeM + "m");
	}
}
