package minesweeper;

import javax.swing.*;
import java.awt.*;

public class Box extends JPanel{
	
	boolean isRevealed;
	boolean isMine;
	boolean flagged;
	boolean wasClicked;
	int inThisCol;
	int inThisRow;
	Integer adjacentMines;
	final int BOX_SIZE;

	public Box(int inThisCol, int inThisRow, boolean isRevealed, boolean isMine, boolean isFlagged, boolean wasClicked, int adjacentMines, int size) {
		this.inThisCol=inThisCol;
		this.inThisRow=inThisRow;
		this.isRevealed=isRevealed;
		this.isMine=isMine;
		flagged=isFlagged;
		this.adjacentMines=adjacentMines;
		this.wasClicked=wasClicked;
		this.BOX_SIZE=size;
	}
	
	public void draw(Graphics g, int inThisCol, int inThisRow) {
		super.paintComponent(g);
		g.drawRect(inThisCol*BOX_SIZE, inThisRow*BOX_SIZE, BOX_SIZE, BOX_SIZE);
		g.setColor(Color.darkGray);
		g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3);
	}

	public void revealBox(Graphics g, boolean wasClicked, Integer adjacentMines) {
		if(!flagged) {
			if(!isMine) {
				g.clearRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3); //x, y, width, height
				isRevealed=true;
				g.setColor(Color.black);
				g.setFont(new Font("Arial", Font.BOLD, (int)BOX_SIZE/2));
				if(adjacentMines!=0)
					g.drawString(adjacentMines.toString(), inThisCol*BOX_SIZE+BOX_SIZE/4, (inThisRow+1)*BOX_SIZE-BOX_SIZE/4);
			}
			
			if(isMine&&wasClicked) {
				//System.out.println("You lose");
				isRevealed=true;
				g.setColor(Color.red);
				g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3); //x, y, width, height
			}	
		}
	}
	
	public void flag(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3); //x, y, width, height
		flagged=true;
	}
	
	public void markAsCorrectlyFlagged(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3);
		//g.fillRect(inThisCol*BOX_SIZE+BOX_SIZE/4, inThisRow*BOX_SIZE+BOX_SIZE/4, (3*BOX_SIZE/4)-3, (3*BOX_SIZE/4)-3); //x, y, width, height
	}
	
	public void markAsIncorrectlyFlagged(Graphics g) {
		g.setColor(Color.pink);
		g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3);
		//g.fillRect(inThisCol*BOX_SIZE+BOX_SIZE/4, inThisRow*BOX_SIZE+BOX_SIZE/4, (3*BOX_SIZE/4)-3, (3*BOX_SIZE/4)-3); //x, y, width, height
	}
	
	public void unflag(Graphics g) {
		g.clearRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3);
		g.setColor(Color.darkGray);
		g.fillRect(2+inThisCol*BOX_SIZE, 2+inThisRow*BOX_SIZE, BOX_SIZE-3, BOX_SIZE-3);
		flagged=false;
	}
	
	public void setAdjacentMines(int adjacentMines2) {
		adjacentMines=adjacentMines2;
	}
	
	/*public void setClicked() {
		wasClicked=true;
	}*/
	
	public boolean isFlagged() {
		return flagged;
	}
	
	public boolean isRevealed() {
		return isRevealed;
	}
	
	public boolean isMine() {
		return isMine;
	}
	
	public void setAsMine() {
		isMine=true;
	}
	
	public Integer getAdjacentMines() {
		return adjacentMines;
	}
}
