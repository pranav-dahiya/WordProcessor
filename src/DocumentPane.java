import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.rtf.RTFEditorKit;

public class DocumentPane extends JEditorPane implements CaretListener {
	private static final long serialVersionUID = 1L;
	final JFileChooser fileChooser = new JFileChooser();
	private RTFEditorKit editorKit = new RTFEditorKit();
	private DefaultStyledDocument document;
	private Style font;
	private Style fontNameStyle;
	private Style fontSizeStyle;

	public DocumentPane() {
		setEditorKit(editorKit);
		document = (DefaultStyledDocument) getDocument();
		font = document.addStyle("font", null);
		fontNameStyle = document.addStyle("font name", font);
		fontSizeStyle = document.addStyle("font size", font);
		addCaretListener(this);
	}

	public void save() {
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				editorKit.write(new FileOutputStream(fileChooser.getSelectedFile()), document, 0, document.getLength());
			} catch (IOException | BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public void open() {
		int returnVal = fileChooser.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				editorKit.read(new FileReader(fileChooser.getSelectedFile()), document, 0);
				} catch (IOException | BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public void setFontName(String fontName) {
		if(!fontName.isEmpty())
			StyleConstants.setFontFamily(fontNameStyle, fontName);
		document.setCharacterAttributes(this.getSelectionStart(), this.getSelectionEnd(), fontNameStyle, false);
	}
	
	public void setFontSize(String fontSize) {
		if(!fontSize.isEmpty())
			StyleConstants.setFontSize(fontSizeStyle, Integer.parseInt(fontSize));
		document.setCharacterAttributes(this.getSelectionStart(), this.getSelectionEnd(), fontSizeStyle, false);
	}

	public void changeCase() throws BadLocationException {
		int offset = getSelectionStart();
		int length = getSelectionEnd() - offset;
		String text = document.getText(offset, length);
		int index = 0;
		while (true) {
			if (text.charAt(index) >= 'a' && text.charAt(index) <= 'z') {
				text = text.toUpperCase();
				break;
			} else if (text.charAt(index) >= 'A' && text.charAt(index) <= 'Z') {
				text = text.toLowerCase();
				break;
			} else
				index++;
		}
		document.replace(offset, length, text, null);
	}

	@Override
	public void caretUpdate(CaretEvent arg0) {
		int start = getSelectionStart();
		int end = getSelectionEnd();
		Element element = document.getCharacterElement(start);
		AttributeSet elementAttributes = element.getAttributes();
		String fontName = StyleConstants.getFontFamily(elementAttributes);
		String fontSize = Integer.toString(StyleConstants.getFontSize(elementAttributes));

		for (int index = start + 1; index <= end; index++) {
			element = document.getCharacterElement(index);
			elementAttributes = element.getAttributes();
			if (!fontName.equals(StyleConstants.getFontFamily(elementAttributes)))
				fontName = "";
			if (!fontSize.equals(Integer.toString(StyleConstants.getFontSize(elementAttributes))))
				fontSize = "";
			if(fontName.isEmpty() && fontSize.isEmpty())
				break;
		}
		
		
	}
}
