package se.jensim.warhammer.gametimer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = -5821143729479243771L;

	private static final int DEFAULT_ROUNDS = 6, DEFAULT_PLAYERS = 2;

	private final SettingsPanel settingsPanel = new SettingsPanel();

	private final JButton
			btnStart = new JButton("Start"),
			btnStop = new JButton("Stop"),
			btnPause = new JButton("pause"),
			btnNext = new JButton("Next"),
			btnBack = new JButton("Back");

	private final JPanel buttonPanel = new JPanel();
	private final JPanel mainPanel = new JPanel();

	private int page = -1;

	final ArrayList<GamePanel> panelList = new ArrayList<GamePanel>();

	public MainWindow() {

		setTitle("Simple example");
		setSize(300, 200);
		setLocationRelativeTo(null);
		setVisible(true);

		btnBack.addActionListener(this);
		btnNext.addActionListener(this);
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnPause.addActionListener(this);

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(btnStart);
		buttonPanel.add(btnStop);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnNext);
		buttonPanel.add(btnBack);

		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		stop();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			panelList.clear();

			int timeTotal = settingsPanel.getTotalTime();
			int timePresent = settingsPanel.getPresentationTime();
			int timeDeploy = settingsPanel.getDeploymentTime();
			int rounds = DEFAULT_ROUNDS;
			int players = DEFAULT_PLAYERS;

			GamePanel presentation = new GamePanel("Presentation", timePresent);
			presentation.setTotal(timeTotal);
			panelList.add(presentation);

			panelList.add(new GamePanel("Deployment", timeDeploy));

			int round_length = (timeTotal - timePresent - timeDeploy) / (rounds * players);
			for (int round = 1; round <= DEFAULT_ROUNDS; ++round) {
				for (int player = 1; player <= DEFAULT_PLAYERS; ++player) {
					panelList.add(new GamePanel("P:" + player + " R:" + round, round_length));
				}
			}
			// TODO: add(statistics page);

			page = 0;
			setupPage(-1);
		} else if (e.getSource() == btnStop) {
			stop();
		} else if (e.getSource() == btnNext) {
			setupPage(page++);
		} else if (e.getSource() == btnBack) {
			setupPage(page--);
		} else if (e.getSource() == btnPause) {
			if (page >= 0 && page < panelList.size()) {
				panelList.get(page).toggleRunning();
			} else {
				System.out.println("WARNING: NOT SUPPOSE TO BE ABLE TO...((Pause button, page "
						+ page + ")).");
			}
		}
	}

	private void setupPage(final int from) {
		
		if (page == -1) { // Settings page (FIRST)
			mainPanel.removeAll();
			mainPanel.add(settingsPanel);
		} else if (page >= panelList.size()) { // Statistics page (LAST)
			// TODO add(<Statistic page>);
		} else { // Game pages
			if (from >= 0 && from < panelList.size()) {
				panelList.get(from).stop();
			}
			if (page == 0 && from == -1) {
				//DO NOTHING
			} else {
				// FIXME
				int timeRemainingTotal = panelList.get(from).getTotal();
				panelList.get(page).setTotal(timeRemainingTotal);
			}

			
			if (page >= 0 && page < panelList.size()) {
				panelList.get(page).start();
			}

			mainPanel.removeAll();
			mainPanel.add(panelList.get(page));
			panelList.get(page).repaint();
		}

		boolean isGame = page < panelList.size() && page >= 0;

		btnStart.setVisible(page < 0);
		btnBack.setVisible(page > 0);
		btnNext.setVisible(isGame);
		btnPause.setVisible(isGame);
		btnStop.setVisible(page >= 0);
		
		buttonPanel.repaint();

		repaint();
	}

	public void stop() {
		if (page >= 0 && page < panelList.size()) {
			panelList.get(page).stop();
		}
		page = -1;
		setupPage(-1);
		settingsPanel.repaint();
	}
}