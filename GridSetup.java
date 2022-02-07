package minesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JPanel;

public class GridSetup extends JPanel implements MouseListener, KeyListener {

	//final int boxSize=20;
	
	int gridWidth;
	int gridHeight;
	int noOfMines;
	int placedFlags;
	int revealedBoxes;
	int boxSize;
	Integer adjacentMines;
	Random r;
	
	int x,y;
	
	JPanel gridPanel;
	
	Box[][] boxArray;
	
	public GridSetup(int gridWidth, int gridHeight, int noOfMines, int boxSize) {
		this.gridWidth=gridWidth;
		this.gridHeight=gridHeight;
		this.noOfMines=noOfMines;
		this.boxSize=boxSize;
		
		
		adjacentMines=0;
		placedFlags=0;
		revealedBoxes=0;
		
		gridPanel=new JPanel();
		
		gridPanel.setPreferredSize(new Dimension(800,600));
		r=new Random();
		boxArray=new Box[gridWidth][gridHeight];
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void win(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.green);
		g.drawString("You win!", 2, 10+gridHeight*boxSize);
	}
	
	public void lose(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.setColor(Color.red);
		g.drawString("You lose", 2, 10+gridHeight*boxSize);
	}
	
	public void gameSetup(Graphics g) {
		
		placedFlags=0;
		revealedBoxes=0;
		
		//loop to draw grid
		for(int i=0;i<gridWidth;i++) {
			for(int j=0;j<gridHeight;j++) {
				boxArray[i][j]=new Box(i,j,false,false,false,false,0,boxSize);
				boxArray[i][j].draw(g, i, j);	//each box in the array calls its own draw method.
			}
		}
		g.setColor(Color.black);
		g.fillRect(2, gridHeight*boxSize, 60, 17);
		
		//adding mines
		int noOfAddedMines=0;
		while(noOfAddedMines<noOfMines) {
			int col=r.nextInt(gridWidth);
			int row=r.nextInt(gridHeight);
			if(boxArray[col][row].isMine()==false) {
				boxArray[col][row].setAsMine();
				noOfAddedMines++;
			}	
		}
		
		//setting number of adjacent mines for each box.
		for(int col=0;col<gridWidth;col++) {
			for(int row=0;row<gridHeight;row++) {
				adjacentMines=0;
				
				if(col>0) {
					if(row>0&&boxArray[col-1][row-1].isMine())
						adjacentMines++;
					if(row<gridHeight-1&&boxArray[col-1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
				}
				
				if(col<gridWidth-1) {
					if(row>0&&boxArray[col+1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;
					if(row<gridHeight-1&&boxArray[col+1][row+1].isMine())
						adjacentMines++;
				}
				
				if(row>0&&boxArray[col][row-1].isMine())
					adjacentMines++;
				if(row<gridHeight-1&&boxArray[col][row+1].isMine())
					adjacentMines++;
				
				boxArray[col][row].setAdjacentMines(adjacentMines);
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameSetup(g);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("Left click to reveal a box", 2, 30+gridHeight*boxSize);
		g.drawString("Right click to flag and unflag a box", 2, 44+gridHeight*boxSize);
		g.drawString("Correctly flagged mines will be marked orange", 2, 58+gridHeight*boxSize);
		g.drawString("Incorrectly flagged mines will be marked pink", 2, 72+gridHeight*boxSize);
		g.drawString("Press r to restart", 2, 86+gridHeight*boxSize);
	}
	
	public void mousePressed(MouseEvent e) {
		x=e.getX();	
		y=e.getY();
		if(x>0&&x<boxSize*gridWidth&&y>0&&y<boxSize*gridHeight) {
			int col, row;
			col=(int)Math.floor((x)/boxSize);
			row=(int)Math.floor((y)/boxSize);
			if(e.getButton()==MouseEvent.BUTTON1) {	//left click
				if(boxArray[col][row].isFlagged()==false&&boxArray[col][row].isRevealed()==false&&boxArray[col][row].isMine()==false) {					
					revealBoxes(boxArray,col,row);					
				}
				
				//shows remaining mines on the board when you click a mine
				if(boxArray[col][row].isMine()&&boxArray[col][row].isFlagged()==false) {
					lose(getGraphics());
					for(int i=0;i<boxArray.length;i++) {
						for(int j=0;j<boxArray[col].length;j++) {
							if(boxArray[i][j].isMine()) {
								boxArray[i][j].revealBox(getGraphics(), true, 0);
								if(boxArray[i][j].isFlagged())
									boxArray[i][j].markAsCorrectlyFlagged(getGraphics(),true);
							}
							else if(boxArray[i][j].isFlagged())
								boxArray[i][j].markAsCorrectlyFlagged(getGraphics(),false);
						}
					}
				}
			}
			if(e.getButton()==MouseEvent.BUTTON3) { //right click (flag)
				if(boxArray[col][row].isRevealed()==false&&boxArray[col][row].isFlagged()) {
					boxArray[col][row].flag(getGraphics(), false);
					placedFlags--;
				}
				else if(boxArray[col][row].isRevealed()==false) {
					boxArray[col][row].flag(getGraphics(), true);
					placedFlags++;
				}
			}	
			
			//checks for win - If and only if all the mines have been flagged and no other boxes.
			if(placedFlags==noOfMines) {
				int correctlyGuessedMines=0;
				for(int i=0;i<boxArray.length;i++) {
					for(int j=0;j<boxArray[col].length;j++) {
						//System.out.println(gridWidth*gridHeight-placedFlags==revealedBoxes);
						if( (boxArray[i][j].isMine()&&boxArray[i][j].isFlagged()&&gridWidth*gridHeight-placedFlags==revealedBoxes) ) {
							correctlyGuessedMines++;
							if(correctlyGuessedMines==noOfMines) {
								win(getGraphics());
							}
						}
					}
				}
			}
		}
	}
	

	public void revealBoxes(Box[][] boxArray, int col, int row) {
		boxArray[col][row].revealBox(getGraphics(), false, boxArray[col][row].getAdjacentMines());
		revealedBoxes++;
		if(boxArray[col][row].getAdjacentMines()==0&&!boxArray[col][row].isFlagged()) {
			if(col>0) {
				if(row>0&&!boxArray[col-1][row-1].isRevealed())
					revealBoxes(boxArray, col-1, row-1);
				if(row<gridHeight-1&&!boxArray[col-1][row+1].isRevealed())
					revealBoxes(boxArray, col-1, row+1);
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
			}
			if(col<gridWidth-1) {
				if(row>0&&!boxArray[col+1][row-1].isRevealed())
					revealBoxes(boxArray, col+1, row-1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
				if(row<gridHeight-1&&!boxArray[col+1][row+1].isRevealed())
					revealBoxes(boxArray, col+1, row+1);
			}
			if(row>0&&!boxArray[col][row-1].isRevealed())
				revealBoxes(boxArray, col, row-1);
			else if(row<gridHeight-1&&!boxArray[col][row+1].isRevealed())
				revealBoxes(boxArray, col, row+1);
		}
	}
	


	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_R) {
			gameSetup(getGraphics());
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
