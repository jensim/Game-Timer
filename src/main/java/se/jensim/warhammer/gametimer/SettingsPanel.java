package se.jensim.warhammer.gametimer;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * 
 * @author jens
 */
public class SettingsPanel extends JPanel {
	private static final long serialVersionUID = 2839991578674267943L;

	final JSpinner time[][] = new JSpinner[][]{
			{new JSpinner(new SpinnerNumberModel(4, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(0, 0, 55, 5))},
			{new JSpinner(new SpinnerNumberModel(1, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(0, 0, 55, 5))},
			{new JSpinner(new SpinnerNumberModel(1, 0, 24, 1)),
					new JSpinner(new SpinnerNumberModel(0, 0, 55, 5))}};
	final String[] title = new String[]{"Total time", "Presentation", "Deployment"};

	final int COLS = 5, ROWS = time.length;

	public SettingsPanel() {
		setLayout(new GridLayout(ROWS, COLS));

		for (int i = 0; i < COLS * ROWS; ++i) {
			int row = i / COLS;
			int col = i % COLS;
			switch (col) {
				case 0 :
					add(new JLabel(title[row]));
					break;
				case 1 :
					add(time[row][0]);
					break;

				case 2 :
					add(new JLabel("h"));
					break;

				case 3 :
					add(time[row][1]);
					break;

				case 4 :
					add(new JLabel("m"));
					break;

				default :
					break;
			}
		}
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
}
