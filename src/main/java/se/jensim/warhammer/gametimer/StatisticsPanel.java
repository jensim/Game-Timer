package se.jensim.warhammer.gametimer;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class StatisticsPanel extends ContainerPanel {
	private static final long serialVersionUID = 6070064091425673875L;
	
	private final ArrayList<JLabel> labelList = new ArrayList<JLabel>();

	public StatisticsPanel(String CAPTION, int TIME, ActionListener actionListener) {
		super(CAPTION, actionListener);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	public final void setContent(String... strings) {
		removeAll();
		labelList.clear();
		for (String s : strings) {
			JLabel e = new JLabel(s);
			add(e);
			labelList.add(e);
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

	@Override
	public void setMyFonts(Font font) {
		for(JLabel l : labelList ){
			l.setFont(font);
		}
	}
}
