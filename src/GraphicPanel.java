import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class GraphicPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private static final double HANDLE_SIZE = 8;
	private static final double RECTANGLE_ROUNDNESS = 25;
	boolean setVisible = false;
	private int selectedHandle = -1;
	private Rectangle2D.Double[] sizingHandles;
	private boolean sizingHandleVisible = false;
	private Point2D oldLocation;
	private Shape shape;
	private String shapeID; 

	public GraphicPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);

		JButton[] selectButtons = { new JButton("Square"), new JButton("Rectangle"), new JButton("Round Rectangle"),
				new JButton("Ellipse"), new JButton("Line") };
		for (JButton button : selectButtons) {
			button.addActionListener(this);
			add(button);
		}
	}

	private void setShape() {
		if (shape == null) {
			switch (shapeID) {
			case "Square":
				sizingHandles = new Rectangle2D.Double[] { new Rectangle2D.Double(50, 50, HANDLE_SIZE, HANDLE_SIZE), new Rectangle2D.Double(100, 100, HANDLE_SIZE, HANDLE_SIZE) };
				shape = new Rectangle2D.Double(
						Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
						Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
						Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
						Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
			case "Rectangle":
				sizingHandles = new Rectangle2D.Double[] { new Rectangle2D.Double(50, 50, HANDLE_SIZE, HANDLE_SIZE),
						new Rectangle2D.Double(150, 100, HANDLE_SIZE, HANDLE_SIZE) };
				shape = new Rectangle2D.Double(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
						Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
						Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
						Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
				break;
			case "Ellipse":
				sizingHandles = new Rectangle2D.Double[] { new Rectangle2D.Double(50, 50, HANDLE_SIZE, HANDLE_SIZE),
						new Rectangle2D.Double(150, 100, HANDLE_SIZE, HANDLE_SIZE) };
				shape = new Ellipse2D.Double(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
						Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
						Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
						Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
				break;
			case "Round Rectangle":
				sizingHandles = new Rectangle2D.Double[] { new Rectangle2D.Double(50, 50, HANDLE_SIZE, HANDLE_SIZE),
						new Rectangle2D.Double(100, 100, HANDLE_SIZE, HANDLE_SIZE) };
				shape = new RoundRectangle2D.Double(
						Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
						Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
						Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
						Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()), RECTANGLE_ROUNDNESS,
						RECTANGLE_ROUNDNESS);
				break;
			case "Line":
				sizingHandles = new Rectangle2D.Double[] { new Rectangle2D.Double(50, 50, HANDLE_SIZE, HANDLE_SIZE),
						new Rectangle2D.Double(50, 100, HANDLE_SIZE, HANDLE_SIZE) };
				shape = new Line2D.Double(sizingHandles[0].getCenterX(), sizingHandles[0].getCenterY(),
						sizingHandles[1].getCenterX(), sizingHandles[1].getCenterY());
				break;
			case "Curve":

				break;
			}
		}
		switch (shapeID) {
		case "Square":
			double xDist = sizingHandles[0].getX() - sizingHandles[1].getX();
			double yDist = sizingHandles[0].getY() - sizingHandles[1].getY();
			if(selectedHandle == 0) {
				if(Math.abs(xDist) > Math.abs(yDist))
					sizingHandles[0].setRect(sizingHandles[1].getX() + xDist, sizingHandles[1].getY() + xDist, HANDLE_SIZE, HANDLE_SIZE);
				else
					sizingHandles[0].setRect(sizingHandles[1].getX() + yDist, sizingHandles[1].getY() + yDist, HANDLE_SIZE, HANDLE_SIZE);
			}
			else {
				if(Math.abs(xDist) > Math.abs(yDist)) 
					sizingHandles[1].setRect(sizingHandles[0].getX() - xDist, sizingHandles[0].getY() - xDist, HANDLE_SIZE, HANDLE_SIZE);
				else
					sizingHandles[1].setRect(sizingHandles[0].getX() - yDist, sizingHandles[0].getY() - yDist, HANDLE_SIZE, HANDLE_SIZE);
			}
			((Rectangle2D) shape).setRect(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
					Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
					Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
					Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
		case "Rectangle":
			((Rectangle2D) shape).setRect(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
					Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
					Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
					Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
			break;
		case "Ellipse":
			((Ellipse2D) shape).setFrame(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
					Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
					Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
					Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
			break;
		case "Round Rectangle":
			((RoundRectangle2D) shape).setFrame(Math.min(sizingHandles[0].getCenterX(), sizingHandles[1].getCenterX()),
					Math.min(sizingHandles[0].getCenterY(), sizingHandles[1].getCenterY()),
					Math.abs(sizingHandles[0].getCenterX() - sizingHandles[1].getCenterX()),
					Math.abs(sizingHandles[0].getCenterY() - sizingHandles[1].getCenterY()));
			break;
		case "Line":
			((Line2D) shape).setLine(sizingHandles[0].getCenterX(), sizingHandles[0].getCenterY(),
					sizingHandles[1].getCenterX(), sizingHandles[1].getCenterY());
			break;
		case "Curve":
			break;
		}
	}

	private boolean doesNotIntersectSizingHandles(MouseEvent e) {
		boolean flag = true;
		for (Rectangle2D sizingHandle : sizingHandles) {
			if (sizingHandle.contains(e.getPoint()))
				flag = false;
		}
		return flag;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		if (sizingHandleVisible) {
			for (Rectangle2D sizingHandle : sizingHandles) {
				g2.fill(sizingHandle);
			}
		}

		if (setVisible)
			g2.draw(shape);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (sizingHandleVisible) {
			for (int i = 0; i < sizingHandles.length; i++) {
				if (sizingHandles[i].contains(e.getPoint())) {
					selectedHandle = i;
				}
			}
			if (shape.intersects(e.getX() - 3, e.getY() - 3, 6, 6) && doesNotIntersectSizingHandles(e)) {
				oldLocation = e.getPoint();
				selectedHandle = -2;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectedHandle = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedHandle == -1)
			return;

		if (selectedHandle == -2) {
			for (Rectangle2D sizingHandle : sizingHandles)
				sizingHandle.setRect(Math.max(sizingHandle.getX() + (e.getX() - oldLocation.getX()), 0),
						Math.max(sizingHandle.getY() + (e.getY() - oldLocation.getY()), 0), HANDLE_SIZE, HANDLE_SIZE);
			oldLocation = e.getPoint();
		} else {
			sizingHandles[selectedHandle].setRect(Math.max(e.getX(), 0), Math.max(e.getY(), 0), HANDLE_SIZE,
					HANDLE_SIZE);
		}
		setShape();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (shape.intersects(e.getX() - 3, e.getY() - 3, 6, 6))
			sizingHandleVisible = !sizingHandleVisible;
		else
			sizingHandleVisible = false;
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		shapeID = e.getActionCommand();
		shape = null;
		setShape();
		setVisible = true;
		sizingHandleVisible = true;
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
