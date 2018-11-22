package tetriss;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Tetris extends JPanel implements KeyListener {

	private Point pieceOrigin;
	private int currentPiece;
	

	

	private int position;
	private ArrayList<Integer> nextPieces = new ArrayList<Integer>();
	
	private Color[][] frame;
	private Shapes shapes;
	private JOptionPane optionPane;

	Tetris() {
		shapes = new Shapes();
		init();

	}

	private void init() {
		frame = new Color[12][24];
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				if (i == 0 || i == 11 || j == 22) {
					frame[i][j] = Color.GRAY;
				} else if (j == 0) {
					frame[i][j] = Color.BLACK;
				} else {
					frame[i][j] = Color.WHITE;
				}
			}
		}
		newPiece();
	}

	public void newPiece() {

		pieceOrigin = new Point(5, 2);
		position = 0;
		if (nextPieces.isEmpty()) {
			Collections.addAll(nextPieces, 0, 1, 2, 3, 4, 5, 6);
			Collections.shuffle(nextPieces);
		}
		currentPiece = nextPieces.get(0);
		nextPieces.remove(0);

	}

	public boolean IscollidesAt(int x, int y, int position) {

		for (Point point : new Shapes().getShapes()[currentPiece][position]) {
			if (frame[point.x + x][point.y + y] != Color.WHITE) {
				return true;
			}
		}
		return false;
	}
	public boolean IscollidesAtt(int x, int y, int position) {

		for (Point point : new Shapes().getShapes()[currentPiece][position]) {
			if (frame[point.x + x][point.y + y] == Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	public void turn(int i) {
		int newRotation = (position + i) % 4;
		if (newRotation < 0) {
			newRotation = 3;
		}
		if (!IscollidesAt(pieceOrigin.x, pieceOrigin.y, newRotation)) {
			position = newRotation;
		}
		repaint();
	}

	public void move(int i) {
		if (!IscollidesAt(pieceOrigin.x + i, pieceOrigin.y, position)) {
			pieceOrigin.x += i;
		}
		repaint();
	}

	public void dropDown() {
		if (!IscollidesAt(pieceOrigin.x, pieceOrigin.y + 1, position)) {
			pieceOrigin.y += 1;
		} else {
           if(IscollidesAtt(pieceOrigin.x, pieceOrigin.y -2, position)){
        	   Board.victory=false;
           }
		
		fixToFrame();
		
		}
		repaint();
	}

	public void fixToFrame() {
		for (Point point : shapes.getShapes()[currentPiece][position]) {
			frame[pieceOrigin.x + point.x][pieceOrigin.y + point.y] = shapes.getShapesColors()[currentPiece];
		}
		clearRows();
		newPiece();
	}

	public void removeRow(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 11; i++) {
				frame[i][j + 1] = frame[i][j];
			}
		}
	}

	public void clearRows() {
		

				boolean gap;
				int numClears = 0;

				for (int j = 21; j > 0; j--) {
					gap = false;
					for (int k = 1; k < 11; k++) {
						if (frame[k][j] == Color.WHITE) {
							gap = true;
							break;
						}
					}
					if (!gap) {
						removeRow(j);
						j += 1;
						numClears += 1;
					}
				}

				switch (numClears) {
				case 1:
					Board.score += 100;
					break;
				case 2:
					Board.score += 300;
					break;
				case 3:
					Board.score += 500;
					break;
				case 4:
					Board.score += 800;
					break;
				}
			}
		

	

	private void drawPiece(Graphics g) {
		g.setColor(shapes.getShapesColors()[currentPiece]);
		for (Point point : shapes.getShapes()[currentPiece][position]) {
			g.fillRect((point.x + pieceOrigin.x) * 26, (point.y + pieceOrigin.y) * 26, 25, 25);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.fillRect(0, 0, 26 * 12, 26 * 23);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(frame[i][j]);
				g.fillRect(26 * i, 26 * j, 25, 25);
			}
		}

		g.setColor(Color.WHITE);
		g.drawString("" + Board.score, 19 * 12, 25);

		drawPiece(g);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	



}
