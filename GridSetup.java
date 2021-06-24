package minesweeper;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridSetup extends JPanel implements MouseListener, KeyListener {

	final int BOX_SIZE=22;
	
	int gridWidth;
	int gridHeight;
	int noOfMines;
	int placedFlags;
	Integer adjacentMines;
	Random r;
	
	int x,y;
	
	JPanel gridPanel;
	JLabel winLabel;
	
	Box[][] boxArray;
	
	public GridSetup(int gridWidth, int gridHeight, int noOfMines) {
		this.gridWidth=gridWidth;
		this.gridHeight=gridHeight;
		this.noOfMines=noOfMines;
		
		adjacentMines=0;
		placedFlags=0;
		
		gridPanel=new JPanel();
		
		gridPanel.setPreferredSize(new Dimension(800,600));
		r=new Random();
		boxArray=new Box[gridWidth][gridHeight];
		setFocusable(true);
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void restart() {
		gameSetup(getGraphics());
	}
	
	public void win(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("You win!", 2, 10+gridHeight*BOX_SIZE);
	}
	
	public void lose(Graphics g) {
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("You lose", 2, 10+gridHeight*BOX_SIZE);
	}
	
	public void gameSetup(Graphics g) {
		//loop to draw grid
		for(int i=0;i<gridWidth;i++) {
			for(int j=0;j<gridHeight;j++) {
				boxArray[i][j]=new Box(i,j,false,false,false,false,0,BOX_SIZE);
				boxArray[i][j].draw(g, i, j);	//each box in the array calls its own draw method.
			}
		}
		
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
				//Case 2: Top left corner
				if(col==0&&row==0) {				
					if(boxArray[col][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;
				}
				//Case 7: Left edge
				else if(col==0&&row>0&&row<gridHeight-1) {
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;
					if(boxArray[col+1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col][row+1].isMine())
						adjacentMines++;					
				}
				//Case 3: Bottom left corner
				else if(col==0&&row==gridHeight-1) {				
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;
				}
				//Case 8: Top edge
				else if(row==0&&col>0&&col<gridWidth-1) {	
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
					if(boxArray[col-1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;				
				}
				//Case 1: 8 adjacent boxes
				else if(col>0&&col<gridWidth-1&&row>0&&row<gridHeight-1) {
					if(boxArray[col-1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
					if(boxArray[col-1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;
					if(boxArray[col+1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
				}
				//Case 9: Bottom edge
				else if(col>0&&col<gridWidth-1&&row==gridHeight-1) {	
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
					if(boxArray[col-1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col+1][row].isMine())
						adjacentMines++;	
				}
				//Case 4: Top right corner
				else if(col==gridWidth-1&&row==0) {	
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
					if(boxArray[col-1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col][row+1].isMine())
						adjacentMines++;		
				}
				//Case 6: Right edge
				else if(col==gridWidth-1&&row>0&&row<gridHeight-1) {
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row].isMine())
						adjacentMines++;
					if(boxArray[col-1][row+1].isMine())
						adjacentMines++;
					if(boxArray[col][row+1].isMine())
						adjacentMines++;				
				}
				//Case 5: Bottom right corner
				else if(col==gridWidth-1&&row==gridHeight-1) {	
					if(boxArray[col][row-1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row-1].isMine())
						adjacentMines++;
					if(boxArray[col-1][row].isMine())
						adjacentMines++;				
				}
					boxArray[col][row].setAdjacentMines(adjacentMines);
					//System.out.print(boxArray[col][row].getAdjacentMines());
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameSetup(g);
		g.setFont(new Font("Arial", Font.PLAIN, 14));
		g.drawString("Left click to reveal a box", 2, 30+gridHeight*BOX_SIZE);
		g.drawString("Right click to flag and unflag a box", 2, 44+gridHeight*BOX_SIZE);
		g.drawString("Correctly flagged mines will be marked orange", 2, 58+gridHeight*BOX_SIZE);
		g.drawString("Incorrectly flagged mines will be marked pink", 2, 72+gridHeight*BOX_SIZE);
		g.drawString("Press r to restart", 2, 86+gridHeight*BOX_SIZE);
	}
	
	public void mouseClicked(MouseEvent e) {
		x=e.getX();	
		y=e.getY();
		if(x>0&&x<BOX_SIZE*gridWidth&&y>0&&y<BOX_SIZE*gridHeight) {
			int col, row;
			col=(int)Math.floor((x)/BOX_SIZE);
			row=(int)Math.floor((y)/BOX_SIZE);
			if(e.getButton()==MouseEvent.BUTTON1) {	//left click
				if(boxArray[col][row].isFlagged()==false&&boxArray[col][row].isRevealed()==false&&boxArray[col][row].isMine()==false) {					
					revealBoxes(boxArray,col,row);					
				}
				
				//shows remaining mines on the board when you click a mine
				if(boxArray[col][row].isMine()&&boxArray[col][row].isFlagged()==false) {
					for(int i=0;i<boxArray.length;i++) {
						for(int j=0;j<boxArray[col].length;j++) {
							if(boxArray[i][j].isMine()) {
								boxArray[i][j].revealBox(getGraphics(), true, 0);
								if(boxArray[i][j].isFlagged())
									boxArray[i][j].markAsCorrectlyFlagged(getGraphics());
							}
							else if(boxArray[i][j].isFlagged())
								boxArray[i][j].markAsIncorrectlyFlagged(getGraphics());
						}
					}
				}
				
			}
			
			if(e.getButton()==MouseEvent.BUTTON3) { //right click (flag)
				if(boxArray[col][row].isRevealed()==false&&boxArray[col][row].isFlagged()) {
					boxArray[col][row].unflag(getGraphics());
					placedFlags--;
				}
				else if(boxArray[col][row].isRevealed()==false) {
					boxArray[col][row].flag(getGraphics());
					placedFlags++;
				}
				
				//checks for win - If and only if all the mines have been flagged and no other boxes.
				if(placedFlags==noOfMines) {
					int correctlyGuessedMines=0;
					for(int i=0;i<boxArray.length;i++) {
						for(int j=0;j<boxArray[col].length;j++) {
							if(boxArray[i][j].isMine()&&boxArray[i][j].isFlagged()) {
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
	}
	
	public void revealBoxes(Box[][] boxArray, int col, int row) {
		boxArray[col][row].revealBox(getGraphics(), false, boxArray[col][row].getAdjacentMines());
		//while there are no adjacent mines
		if(boxArray[col][row].getAdjacentMines()==0) {
			//Case 1: 8 adjacent boxes
			if(row>0&&row<gridHeight-1&&col>0&&col<gridWidth-1) {
				if(!boxArray[col-1][row-1].isRevealed())
					revealBoxes(boxArray, col-1, row-1);
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col, row-1);
				if(!boxArray[col+1][row-1].isRevealed())
					revealBoxes(boxArray, col+1, row-1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
				if(!boxArray[col+1][row+1].isRevealed())
					revealBoxes(boxArray, col+1, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
				if(!boxArray[col-1][row+1].isRevealed())
					revealBoxes(boxArray, col-1, row+1);
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
			}
			//Case 2: Top left corner
			else if(row==0&&col==0) {
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
				if(!boxArray[col+1][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
			}
			//Case 3: Bottom left corner
			else if(row==gridHeight-1&&col==0) {
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col, row-1);
				if(!boxArray[col+1][row-1].isRevealed())
					revealBoxes(boxArray, col+1, row-1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
			}
			//Case 4: Top right corner
			else if(row==0&&col==gridWidth-1) {
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
				if(!boxArray[col-1][row+1].isRevealed())
					revealBoxes(boxArray, col-1, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
			}
			//Case 5: Bottom right corner
			else if(row==gridHeight-1&&col==gridWidth-1) {
				if(!boxArray[col-1][row-1].isRevealed())
					revealBoxes(boxArray, col-1, row-1);
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col, row-1);
				if(!boxArray[col-1][row].isRevealed()) 
					revealBoxes(boxArray, col-1, row);
			}
			//Case 6: Left edge
			else if(col==0&&row>0&&row<gridHeight-1) {
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col,row-1);
				if(!boxArray[col+1][row-1].isRevealed())			
					revealBoxes(boxArray, col+1, row-1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
				if(!boxArray[col+1][row+1].isRevealed())
					revealBoxes(boxArray, col+1, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
			}
			//Case 7: Right edge
			else if(col==gridWidth-1&&row>0&&row<gridHeight-1) {
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col, row-1);
				if(!boxArray[col-1][row-1].isRevealed())
					revealBoxes(boxArray, col-1, row-1);
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
				if(!boxArray[col-1][row+1].isRevealed())
					revealBoxes(boxArray, col-1, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
			}
			//Case 8: Bottom edge
			else if(col>0&&col<gridWidth-1&&row==gridHeight-1) {
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
				if(!boxArray[col-1][row-1].isRevealed())
					revealBoxes(boxArray, col-1, row-1);
				if(!boxArray[col][row-1].isRevealed())
					revealBoxes(boxArray, col, row-1);
				if(!boxArray[col+1][row-1].isRevealed())
					revealBoxes(boxArray, col+1, row-1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
			}
			//Case 9: Top edge
			else if(col>0&&col<gridWidth-1&&row==0) {
				if(!boxArray[col-1][row].isRevealed())
					revealBoxes(boxArray, col-1, row);
				if(!boxArray[col-1][row+1].isRevealed())
					revealBoxes(boxArray, col+1, row+1);
				if(!boxArray[col][row+1].isRevealed())
					revealBoxes(boxArray, col, row+1);
				if(!boxArray[col+1][row+1].isRevealed())
					revealBoxes(boxArray, col+1, row+1);
				if(!boxArray[col+1][row].isRevealed())
					revealBoxes(boxArray, col+1, row);
			}
		}
	}

	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_R) {
			restart();
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
