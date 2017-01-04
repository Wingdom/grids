COP 3330 - Programming Assignment #5

Objective 
1. To familiarize you with writing Java applets.
2. To improve your skills with Graphical User Interfaces.

Problem: Grids
The game of Grids is played by two players on an 8 by 8 grid of dots. Grids is a turn-based game, meaning that player one takes a turn, then player two takes a turn, then player one again, and so on, until the game ends. A turn consists of selecting a single dot from the grid. Once a dot is selected, it cannot become unselected, and it cannot be selected again. Initially, none of the dots in the grid are selected. The game ends when all dots have been selected. The goal of each player is to complete as many boxes as possible, and the player who completes the most boxes wins. A box is completed by selecting fourth dot surrounding a square on the board. With a well chosen move, a player can complete up to four boxes in one turn. The pictures below help to clarify the situation:

Filled circles represent dots that have been selected. Empty circles represent unselected dots.

	A move that completes one box				A move that completes three boxes

Your goal is to write a Java applet that allows two human players who are sitting at the same computer to play Grids with each other. Your applet must obey the following constraints, but you are otherwise free to design the applet as you please.
•	The rules of the game described above must be enforced (e.g. trying to select the same dot twice has no effect).
•	The players must be able to select dots by clicking them.
•	The players must be assigned separate colors to identify them, such as red and blue.
•	The game grid must be displayed in a clear and understandable way, with dots that have been selected by a player and the boxes completed by a player being identified in that player's color. Also, if two selected dots are horizontally or vertically adjacent, there should be a line connecting them.
•	The applet must clearly identify whose turn it is, either player one or player two.
•	The number of boxes completed by each player must be displayed numerically.
•	When the game ends, the winner must be clearly identified and the players must be allowed the option to play a new game. Since an applet does not terminate itself, a decision not to start a new game must be temporary. That is to say, if the user chooses not to play a new game immediately, they should still be able to start a new game later without restarting the applet.

Implementation Hints
You should write a class that extends JApplet. This will effectively be your main class, although you won't actually be writing a main method for this assignment.

You should override the paint method on your applet to draw the game state and display the score. That way, if the applet gets covered up by another window, it will still display the board when applet is revealed again.

Add a MouseListener of your own design to the applet using the addMouseListener method. Adding the MouseListener should probably be done inside the init method on your applet class. That way, the applet will able to respond to clicks. The mouseClicked method on a MouseListener gets a MouseEvent object that identifies the position of the click through its getX and getY methods. By the way, the MouseListener interface is in java.awt.event.

Deliverables
You must submit the source code (The .java files, not the .class files) for your program over WebCourses by 11:55 PM on Wednesday, April 22nd, 2009. Since an applet's display size is defined by the web page that it's on, you must also submit an .html file that can be used in conjunction with your applet once it is compiled. You must send your source files and html file as attachments using the "Add Attachments" button. Assignments that are typed into the submission box will not be accepted. 

Restrictions
Your program must compile using Java 6.0 or later. It’s okay to develop your program using the IDE of your choice, although Eclipse is recommended. Your program should include a header comment with the following information: your name, course number, section number, assignment title, and date.

Grading Details
Your programs will be graded upon the following criteria:
1) Correctness
2) Your programming style and use of white space. Even if you have a plan and your program works perfectly, if your programming style is poor or your use of white space is poor, you could get 10% or 15% deducted from your grade.
