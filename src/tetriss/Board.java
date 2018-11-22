package tetriss;



import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JFrame {
	Tetris game ;


	public static int score=0;
  
	public  static boolean victory=true;
	public Board() {
		super("TETRIS");
	}

	public void start() {
		setIconImage(new ImageIcon("TETR.png").getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(12*26+10, 26*23+25); 
		setVisible(true);
		setResizable(false);
		setLocation(450,100);
		game=new Tetris();
		add(game);
		
	 addKeyListener(new KeyListener() {
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.turn(-1);
				break;
			case KeyEvent.VK_DOWN:
				game.turn(+1);
				break;
			case KeyEvent.VK_LEFT:
				game.move(-1);
				break;
			case KeyEvent.VK_RIGHT:
				game.move(+1);
				break;
			case KeyEvent.VK_SPACE:
				game.dropDown();
				break;
			} 
		}
		
		public void keyReleased(KeyEvent e) {
		}
	});
	
	 
	new Thread() {
		@Override 
		public void run() {
			while (true) {
				try {
					Thread.sleep(500);
					if(victory) {
					game.dropDown();
					}else {
						break;
					}
				} catch ( Exception e ) {
					
				}
			}
			Object[] options = { "OK", "NO" };
			int n=JOptionPane.showOptionDialog(null, "Score: "+score+". Save results?", "Game Over",
			JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
			null, options, options[0]);
			    System.exit(0);
	}
		}.start();
 
	}
}


