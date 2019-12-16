import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.BadLocationException;

public class WordProcessor extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	GraphicPanel graphicPanel = new GraphicPanel();
	DocumentPane mainDocument = new DocumentPane();

	static JComboBox<String> fontName = new JComboBox<String>();
	static JComboBox<String> fontSize = new JComboBox<String>(
			new String[] { "", "8", "9", "10", "12", "14", "16", "18", "20", "24", "28", "32", "36", "40", "48" });;

	public WordProcessor() {
		setLayout(new BorderLayout());
		add(mainDocument, BorderLayout.CENTER);

		JPanel menuBar = new JPanel(new BorderLayout());
		JPanel upperMenuBar = new JPanel();
		JPanel lowerMenuBar = new JPanel();
		menuBar.add(upperMenuBar, BorderLayout.NORTH);
		menuBar.add(lowerMenuBar, BorderLayout.SOUTH);

		JButton[] upperMenuButtons = { new JButton("Save"), new JButton("Open"), new JButton("Copy"),
				new JButton("Cut"), new JButton("Paste"), new JButton("Word Count"), new JButton("Character Count") };
		for (JButton menuButton : upperMenuButtons) {
			menuButton.addActionListener(this);
			upperMenuBar.add(menuButton);
		}
		
		fontName.addItem("");
		for (String font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames())
			fontName.addItem(font);
		fontName.setActionCommand("font name");
		fontName.addActionListener(this);
		lowerMenuBar.add(fontName);

		fontSize.setActionCommand("font size");
		fontSize.addActionListener(this);
		lowerMenuBar.add(fontSize);

		JButton[] lowerMenuButtons = { new JButton("Change Case"), new JButton("Find and Replace") };
		for (JButton menuButton : lowerMenuButtons) {
			menuButton.addActionListener(this);
			lowerMenuBar.add(menuButton);
		}

		add(menuBar, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "Save":
			mainDocument.save();
			break;
		case "Open":
			mainDocument.open();
			break;
		case "Copy":
			mainDocument.copy();
			break;
		case "Cut":
			mainDocument.cut();
			break;
		case "Paste":
			mainDocument.paste();
			break;
		case "Change Case":
			try {
				mainDocument.changeCase();
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			break;
		case "font name":
			mainDocument.setFontName((String) fontName.getSelectedItem());
			break;
		case "font size":
			mainDocument.setFontSize((String) fontSize.getSelectedItem());
			break;
		}
	}
	
	public static void comboBoxUpdate(String fontNameString, String fontSizeString) {
		fontName.setSelectedItem(fontNameString);
		fontSize.setSelectedItem(fontSizeString);
	}


	public static void main(String[] args) {
		WordProcessor wp = new WordProcessor();
		wp.setVisible(true);
		wp.setSize(700, 500);
		wp.setMinimumSize(new Dimension(300, 100));
		wp.setDefaultCloseOperation(EXIT_ON_CLOSE);
		fontName.setSelectedItem("Nimbus Sans");
		/*JFrame myFrame = new JFrame();
		myFrame.add(new GraphicPanel());
		myFrame.setVisible(true);
		myFrame.setSize(400, 400);
		myFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);*/
	}

}
