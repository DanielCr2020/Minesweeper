# Minesweeper
My Minesweeper that I programmed for fun that is the perfect example of me overthinking things

I've never made a readme before so I have no idea what I'm doing.

Hi!

I decided to have some fun over the summer and make minesweeper. 
Play like any normal minesweeper game. 

The number in each box represents how many mines are in the 8 boxes touching that box.

Left click to reveal a box, right click to flag (yellow).
Correctly flagged mines will be colored orange.
Incorrectly flagged mines will be colored pink.
You win when you flag all of the mines and reveal all other boxes.

To reveal boxes, I used recursion. I reveal the called box. Then, I check that there are zero adjacent mines. 
If there are, I run cases for each box position (middle, edge, corner), and then call the method again.

I encourage you to look at this code and try to learn from it.

