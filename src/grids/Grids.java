package grids;
//Cameron Yeager
//c2279189
//Assignment 5
//4/22/1009
//for problems with repaint, please see notes written at end of paint() method

import java.util.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Grids extends JApplet implements MouseListener, Runnable, ActionListener
{

	private static final long serialVersionUID = 1L;
	
	public Graphics g;
	private Container container;
	private LayoutManager layout;
	private Canvas canvas;
	
	private JLabel player1;
	private JLabel player2;
	private JTextField Player1Score;
	private JTextField Player2Score;
	private JButton NewGame;
	
	int p1 = 0;
	int p2 = 0;
	public Dots[][] dots;
	public Vector<Lines> lines;
	boolean pTurn;
	
	public void init()
	{
		//set up the container, and its layout
		container = getContentPane();
		container.setSize(650, 600);
		container.setBackground(Color.LIGHT_GRAY);
		layout = new FlowLayout();
		container.setLayout(layout);
		
		//set up the canvas, and then add it
		//and everything else to the container
		canvas = new Canvas();
		canvas.setSize(550, 550);
		canvas.setBackground(Color.WHITE);
		canvas.addMouseListener(this);
		container.add(canvas);
		
		player1 = new JLabel("Player 1:");
		player1.setForeground(Color.RED);
		container.add(player1);
		
		Player1Score = new JTextField(3);
		Player1Score.setText(Integer.toString(p1));
		Player1Score.setEnabled(false);
		container.add(Player1Score);
		
		player2 = new JLabel("Player 2:");
		player2.setForeground(Color.BLACK);
		container.add(player2);
		
		Player2Score = new JTextField(3);
		Player2Score.setText(Integer.toString(p2));
		Player2Score.setEnabled(false);
		container.add(Player2Score);
		
		NewGame = new JButton("New Game");
		NewGame.addActionListener(this);
		container.add(NewGame);
		
		lines = new Vector<Lines>();
		pTurn = true;
		dots = new Dots[8][8];
		
		drawDots();
		
		container.setVisible(true);
		
		repaint();
	}
	
	public void paint(Graphics g)
	{
		g = canvas.getGraphics();
		
		int startX;
		int startY;
		int endX;
		int endY;
		
		//the vector of lines on the canvas
		for(Lines l : lines)
		{
			startX = l.getStartDot().getX() + 8;
			startY = l.getStartDot().getY() + 8;
			endX = l.getEndDot().getX() + 8;
			endY = l.getEndDot().getY() + 8;
			
			g.setColor(l.getDotColor());
			g.drawLine(startX, startY, endX, endY);
		}
		
		//the array of dots on the canvas
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				g.setColor(dots[x][y].color);
				g.fillOval(dots[x][y].getX(), dots[x][y].getY(), 16, 16);
			}//for x
		}//for y
		
		//repaint the canvas and container
		container.repaint();
		
		//if repaint() is added to paint(), the container disapears,
		//the canvas takes up the whole window, and clicking on dots might stop 
		//functioning. I have asked many people, and a TA about this issue
		//and none of them know what to do to fix it. Without repaint()
		//sometimes the dots on the canvas disapear, but when you click
		//on the canvas again, everything goes back to normal, with no interruption
		//in game play. repaint() might also have something to do with why the lines
		//dont disapear when a new game is started, until you move the window, then only
		//the current lines appear, and all the old ones go away
		//repaint();
	}
	
	public void drawDots() 
	{
		g = canvas.getGraphics();
		int xPos;
		int yPos;
		
		for(int y = 0; y < 8; y++)
		{
			for(int x = 0; x < 8; x++)
			{
				//the position of the current dot on the canvas
				xPos = x * (550/8) + 8;
				yPos = y * (550/8) + 8;
				
				dots[x][y] = new Dots(xPos, yPos);
				
				//sets the dot to its current color and draws it
				g.setColor(dots[x][y].color);
				g.fillOval(xPos, yPos, 16, 16);
			}//for x
		}//for y
		
		repaint();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		g = canvas.getGraphics();
		
		//if the mouse click was on the canvas
		if(e.getSource() == canvas) 
		{
			int xPos = e.getX() - 8, 
				yPos = e.getY() - 8;
			
			for(int y = 0; y < 8; y++) 
			{
				for(int x = 0; x < 8; x++) 
				{
					if(dots[x][y].clickInDot(xPos, yPos) && !(dots[x][y].isClicked())) 
					{

						// set the color to the players color
						if(pTurn)
							g.setColor(Color.RED);
						else
							g.setColor(Color.BLUE);
						
						//color for the dot
						dots[x][y].clickDot(g.getColor());
						
						//draw the filled in dot
						g.fillOval(dots[x][y].getX(), dots[x][y].getY(), 16, 16);
						
						connectTheDots(dots, x, y);
						
						// End this player's turn
						pTurn = !pTurn;
						
						//change the color of the player to indicate a changed turn
						if(pTurn)
						{
							player1.setForeground(Color.RED);
							player2.setForeground(Color.BLACK);
						}
						else
						{
							player2.setForeground(Color.BLUE);
							player1.setForeground(Color.BLACK);
						}//else
					}//if
				}//if x
			}//if y
		}//if canvas
		
		repaint();
	}//mouse click
	
	public void connectTheDots(Dots[][] Dots, int x, int y)
	{
		//this big mess is all the variables for the dots surround the
		//current dot in the dot[][] array
		int upX = x, upY = y - 1,
			downX = x, downY = y + 1,
			leftX = x - 1, leftY = y,
			rightX = x + 1, rightY = y,
			upRightX = x + 1, upRightY = y - 1,
			downRightX = x + 1, downRightY = y + 1,
			upLeftX = x - 1, upLeftY = y - 1,
			downLeftX = x - 1, downLeftY = y + 1;
			
		//all of these if statements check every dot around the current dot
		//and draws the line if one or more of them have been picked by the same player
		if(upY >= 0 && Dots[upX][upY].isClicked())
		{
			lines.add(new Lines(Dots[x][y], Dots[upX][upY], g.getColor()));
		}//if
		if(downY < 8 && Dots[downX][downY].isClicked()) 	
		{
			lines.add(new Lines(Dots[x][y], Dots[downX][downY], g.getColor()));
		}//if
		
		if(leftX >= 0 && Dots[leftX][leftY].isClicked())
		{
			lines.add(new Lines(Dots[x][y], Dots[leftX][leftY], g.getColor()));
		}//if
		
		if(rightX < 8 && Dots[rightX][rightY].isClicked())
		{
			lines.add(new Lines(Dots[x][y], Dots[rightX][rightY], g.getColor()));
		}//if
		
		
		if(upLeftX >= 0 && upLeftY >= 0 && upY >= 0 && leftX >= 0) 
		{
			if(Dots[upLeftX][upLeftY].isClicked() && Dots[upX][upY].isClicked() && Dots[leftX][leftY].isClicked()) 
			{
				lines.add(new Lines(Dots[upLeftX][upLeftY], Dots[x][y], g.getColor()));
				lines.add(new Lines(Dots[upX][upY], Dots[leftX][leftY], g.getColor()));
				
				scoreAndBox();
			}
		}//if
		
		if(upRightX < 8 && upRightY >= 0 && upY >= 0 && rightX < 8) 
		{
			if(Dots[upRightX][upRightY].isClicked() && Dots[upX][upY].isClicked() && Dots[rightX][rightY].isClicked()) 
			{
				lines.add(new Lines(Dots[upRightX][upRightY], Dots[x][y], g.getColor()));
				lines.add(new Lines(Dots[upX][upY], Dots[rightX][rightY], g.getColor()));
				
				scoreAndBox();
			}
		}//if
		
		if(downLeftX >= 0 && downLeftY < 8 && downY < 8 && leftX >= 0) 
		{
			if(Dots[downLeftX][downLeftY].isClicked() && Dots[downX][downY].isClicked() && Dots[leftX][leftY].isClicked()) 
			{
				lines.add(new Lines(Dots[downLeftX][downLeftY], Dots[x][y], g.getColor()));
				lines.add(new Lines(Dots[downX][downY], Dots[leftX][leftY], g.getColor()));
				
				scoreAndBox();
			}
		}//if
		
		if(downRightX < 8 && downRightY < 8 && downY < 8 && rightX < 8) 
		{
			if(Dots[downRightX][downRightY].isClicked() && Dots[downX][downY].isClicked() && Dots[rightX][rightY].isClicked()) 
			{
				lines.add(new Lines(Dots[downRightX][downRightY], Dots[x][y], g.getColor()));
				lines.add(new Lines(Dots[downX][downY], Dots[rightX][rightY], g.getColor()));
				
				scoreAndBox();
			}
		}//if
		
		repaint();
	}//connect the dots
	
	public void scoreAndBox()
	{
		//increases the score when someone fills in a box
		if(pTurn) 
		{
			p1++;
			Player1Score.setText(Integer.toString(p1));
		}
		else 
		{
			p2++;
			Player2Score.setText(Integer.toString(p2));
		}
		
		repaint();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		int answer;
		String[] resetOptions = {"New Game", "Continue Old Game"};
		
		//if the new game button is clicked
		if(e.getSource() == NewGame) 
		{
			answer = JOptionPane.showOptionDialog(null, "Are you ready to start another AWESOME new game?!?!", "New Game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, resetOptions, resetOptions[1]);
			
			if(answer == JOptionPane.YES_OPTION)
				newGame();
		}
		repaint();
	}
	
	public void newGame()
	{
		//reset all the player stats
		p1 = 0;
		p2 = 0;
		Player1Score.setText(Integer.toString(p1));
		Player2Score.setText(Integer.toString(p2));
		player1.setBackground(Color.RED);
		player2.setBackground(Color.BLACK);
		pTurn = true;

		//reset all the dots
		for(int y = 0; y < 8; y++)			
			for(int x = 0; x < 8; x++)
				dots[x][y].setDots();
		
		Dots.resetClickCount();
		
		//remove all lines from the vector
		//the lines dont disapear from the gameboard for some reason
		//fairly sure its a repaint issue
		lines.removeAllElements();
		
		//paint it black
		canvas.setBackground(Color.WHITE);
	}
	
	
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void run() {repaint();	}
}
