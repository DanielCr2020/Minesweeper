# Minesweeper

Daniel Craig

My Minesweeper that I programmed for fun over the summer to challenge myself and learn some more programming techniques.

This is my first GitHub repository so I apologize if it does not look like I know what I am doing.

Hi!

I decided to have some fun over the summer and make minesweeper. 
Play like any normal minesweeper game. 

The number in each box represents how many mines are in the 8 boxes touching that box.

Left click to reveal a box, right click to flag (yellow).
Correctly flagged mines will be colored orange.
Incorrectly flagged mines will be colored pink.
You win when you flag all of the mines and reveal all other boxes.

When the game starts, it asks for a row count, column count, and number of mines.
Then, a jframe window opens up.

The code creates a 2D array of boxes. 
Each box is an object of the Box class. It contains parameters including (but not limited to) nunber of adjacent mines,
where it is in the 2D array, is it a mine, and is it flagged. 
To reveal boxes, I used recursion. I reveal the called box. Then, I check that there are zero adjacent mines. 
If there are, I run cases for each box position (middle, edge, corner), and then call the method again.

I encourage viewers to look at this code and try to learn from it.

