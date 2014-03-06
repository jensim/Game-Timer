package se.jensim.warhammer.gametimer;

import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FontChooser extends JDialog{

	private static final long serialVersionUID = 6342232654392112076L;

	private final JLabel
			lblName = new JLabel("Font name: "),
			lblType = new JLabel("Font type: "),
			lblSize = new JLabel("Font size: ");

	private final JTextField fontName = new JTextField();
	private final JComboBox<FontType> fontType = new JComboBox<FontChooser.FontType>();
	private final JTextField fontSize = new JTextField();
	
	private final GridLayout glMain = new GridLayout(4, 2);

	private final Component[][] comps = new Component[][]{
			{lblName, fontName},
			{lblType, fontType},
			{lblSize, fontSize}
	};

	public FontChooser(Frame frame, Font f) {
		super(frame, "Close to set font");

		getContentPane().setLayout(glMain);

		for(int i = 0; i < comps.length; ++i){
			for(int j = 0; j < comps[i].length; ++j){
				getContentPane().add(comps[i][j].toString(), comps[i][j]);
			}
		}
		
		fontName.setText(f.getFontName());
		
		fontType.addItem(new FontType("Plain", Font.PLAIN));
		fontType.addItem(new FontType("Italic", Font.ITALIC));
		fontType.addItem(new FontType("Bold", Font.BOLD));
		
		fontSize.setText(String.valueOf(f.getSize()));

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		revalidate();
	}

	public Font getFont() {
		try {
			final String fontString = fontName.getText();
			final int type = ((FontType) fontType.getSelectedItem()).getType();
			final String sizeString = fontSize.getText();
			final int sizeInt = Integer.parseInt(sizeString);
			final Font f = new Font(fontString, type, sizeInt);
			return f;
		} catch (Exception e) {
		}
		return null;
	}

	public void showMe() {
		setVisible(true);
	}

	private class FontType {
		private final String name;
		private final int ref;

		public FontType(String name, int ref) {
			this.name = name;
			this.ref = ref;
		}

		public int getType() {
			return ref;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
