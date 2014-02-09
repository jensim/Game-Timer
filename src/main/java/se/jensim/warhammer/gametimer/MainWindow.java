package se.jensim.warhammer.gametimer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = -5821143729479243771L;

	public static final int DEFAULT_ROUNDS = 6, DEFAULT_PLAYERS = 2;

	private final SettingsPanel settingsPanel = new SettingsPanel();

	private final JButton
			btnStart = new JButton("Start"),
			btnStop = new JButton("Stop"),
			btnPause = new JButton("pause"),
			btnNext = new JButton("Next"),
			btnBack = new JButton("Back");

	private final JPanel buttonPanel = new JPanel();
	private final JPanel mainPanel = new JPanel();

	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menuFont = new JMenu("Font");
	private final JMenuItem[] menuFonts;

	private int page = -1;
	private AudioPlayer audioPlayer = new AudioPlayer();

	final ArrayList<ContainerPanel> panelList = new ArrayList<ContainerPanel>();

	public MainWindow() {

		setTitle("Game timer");
		setSize(450, 300);
		setLocationRelativeTo(null);
		setVisible(true);

		btnBack.addActionListener(this);
		btnNext.addActionListener(this);
		btnStart.addActionListener(this);
		btnStop.addActionListener(this);
		btnPause.addActionListener(this);

		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(btnStart);
		buttonPanel.add(btnStop);
		buttonPanel.add(btnPause);
		buttonPanel.add(btnNext);
		buttonPanel.add(btnBack);

		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		stop();

		addWindowListener(this);

		menuFonts = new JMenuItem[ContainerPanel.fonts.length];
		for (int i = 0; i < menuFonts.length; ++i) {
			menuFonts[i] = new JMenuItem("Font " + (i + 1));
			menuFont.add(menuFonts[i]);
			menuFonts[i].addActionListener(this);
		}
		menuBar.add(menuFont);

		setJMenuBar(menuBar);
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
					panelList.add(new GamePanel("Player:" + player + " Round:" + round, round_length));
				}
			}
			panelList.add(new StatisticsPanel("Time remaining of each turn.", 0));

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
				System.out.println("WARNING: NOT SUPPOSE TO BE ABLE TO...((Pause button, page " + page + ")).");
			}
		} else if (e.getSource() instanceof JMenuItem) {
			for (JMenuItem item : menuFonts) {
				if (e.getSource() == item) {
					String[] header = item.getText().split("Font ");
					Integer index = Integer.parseInt(header[1]) - 1;
					for (ContainerPanel pnl : panelList) {
						if (pnl instanceof GamePanel) {
							GamePanel gPnl = (GamePanel) pnl;
							gPnl.setFont(index);
						}
					}
					break;
				}
			}
		}
	}
	private void setupPage(final int from) {

		// DEBUG
		// System.out.println(String.format("#setupPage() page:%d from:%d", page, from));

		if (page == -1) { // Settings page (FIRST)
			mainPanel.removeAll();
			mainPanel.add(settingsPanel);
		} else if (page < panelList.size()) { // Statistics page (LAST)

			if (from >= 0 && from < panelList.size()) {
				panelList.get(from).stop();
			}

			ContainerPanel pnl = panelList.get(page);

			if (page == 0 && from == -1) {
				audioPlayer.playSound("PrepareForBattle.wav");
			} else {
				long timeRemainingTotal = panelList.get(from).getTotal();
				panelList.get(page).setTotal(timeRemainingTotal);
			}

			if (page >= 0 && page < panelList.size()) {
				panelList.get(page).start();
			}

			if (pnl instanceof StatisticsPanel) {

				ArrayList<String> statsList = new ArrayList<String>();
				for (Iterator<ContainerPanel> itr = panelList.iterator(); itr.hasNext();) {
					ContainerPanel pan = itr.next();
					if (pan instanceof GamePanel) { // NOT the last page..
						GamePanel gamePan = (GamePanel) pan;
						statsList.add(gamePan.caption + "   " + HelperTool.millsToTimeString2(gamePan.getPlayedTime()));
					}
				}
				String[] statsArray = new String[statsList.size()];
				statsArray = statsList.toArray(statsArray);

				StatisticsPanel stats = (StatisticsPanel) panelList.get(page);
				stats.setContent(statsArray);
				
				audioPlayer.playSound("GameOver.wav");
			}

			mainPanel.removeAll();
			mainPanel.add(panelList.get(page));
			panelList.get(page).repaint();

		} else {
			stop();
		}

		btnStart.setVisible(page == -1);
		btnBack.setVisible(page > 0 && page < panelList.size());
		btnNext.setVisible(page >= 0 && page < panelList.size() - 1);
		btnPause.setVisible(page >= 0 && page < panelList.size());
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

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		System.out.println("Window closing");
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {
		System.out.println("Window closed");
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}
}