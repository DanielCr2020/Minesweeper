package minesweeper;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.JFrame;

public class MinesweeperDriver extends JFrame implements KeyListener {
	
	public MinesweeperDriver(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }
		
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int gridHeight;
				int gridWidth;
				int noOfMines;
				int boxSize;
				
				Scanner s=new Scanner(System.in);
				
				System.out.print("Input height: ");
				gridHeight=s.nextInt();
				System.out.print("Input width: ");
				gridWidth=s.nextInt();
				System.out.println("Input number of mines: ");
				noOfMines=s.nextInt();
				
				boxSize=700/(gridWidth+gridHeight);
				while(noOfMines<0||noOfMines>gridHeight*gridWidth) {
					System.out.print("Invalid number of mines. Try again: ");
					noOfMines=s.nextInt();
				}
				
				GridSetup minesweeperGrid=new GridSetup(gridWidth, gridHeight, noOfMines, boxSize);
				
				JFrame frame=new JFrame("Minesweeper");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
				frame.setMinimumSize(new Dimension(700,600));
				frame.setContentPane(minesweeperGrid);
			}
		});
	}
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
