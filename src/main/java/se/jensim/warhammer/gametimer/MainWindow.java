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

		setTitle("Game timer - by Jens - but please blame Daniel");
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
			int round_length = settingsPanel.getRoundTime(DEFAULT_PLAYERS, DEFAULT_ROUNDS);

			if (round_length < 0) {
				return;
			}
			if (timePresent > 0) {
				GamePanel presentation = new GamePanel("Presentation", timePresent, this);
				presentation.setTotal(timeTotal);
				panelList.add(presentation);
			}
			if (timeDeploy > 0) {
				GamePanel deploy = new GamePanel("Deployment", timeDeploy, this);
				panelList.add(deploy);
				deploy.setTotal(timeTotal);
			}

			for (int round = 1; round <= DEFAULT_ROUNDS; ++round) {
				for (int player = 1; player <= DEFAULT_PLAYERS; ++player) {
					GamePanel gp = new GamePanel("Player:" + player + " Round:" + round, round_length, this);
					panelList.add(gp);
					gp.setTotal(timeTotal);
				}
			}
			panelList.add(new StatisticsPanel("Time remaining of each turn.", 0, this));

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
		} else if (e.getSource() instanceof ContainerPanel) {
			setupPage(page++);
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
				int timeTotal = settingsPanel.getTotalTime();
				int timePresent = settingsPanel.getPresentationTime();
				int timeDeploy = settingsPanel.getDeploymentTime();
				int round_length = settingsPanel.getRoundTime(DEFAULT_PLAYERS, DEFAULT_ROUNDS);

				final ArrayList<String> statsList = new ArrayList<String>();
				if (timeTotal > 0) {
					statsList.add("Total time: " + HelperTool.millsToTimeString2(timeTotal));
				}
				if (timePresent > 0) {
					statsList.add("Presentation time: " + HelperTool.millsToTimeString2(timePresent));
				}
				if (timeDeploy > 0) {
					statsList.add("Deployment time: " + HelperTool.millsToTimeString2(timeDeploy));
				}
				if (round_length > 0) {
					statsList.add("Round time (each): " + HelperTool.millsToTimeString2(round_length));
				}
				statsList.add("Players: " + DEFAULT_PLAYERS);
				statsList.add("Rounds: " + DEFAULT_ROUNDS);

				String[] statsArray = new String[statsList.size()];
				statsArray = statsList.toArray(statsArray);

				StatisticsPanel stats = (StatisticsPanel) panelList.get(page);
				stats.setContent(statsArray);

				audioPlayer.playSound("GameOver.wav");
			} else {
				if (from < page && page > 0) {
					audioPlayer.playSound("fog-horn.wav");
				}
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
		btnPause.setVisible(page >= 0 && page < panelList.size() - 1);
		btnStop.setVisible(page >= 0 && page < panelList.size() - 1);

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