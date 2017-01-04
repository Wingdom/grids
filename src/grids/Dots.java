package grids;
//Cameron Yeager
//c2279189
//Assignment 5
//4/22/1009

import java.awt.*;

public class Dots
{
	
	private static int dotsClicked; //total number of dots that have been clicked
	
	int xPos;
	int yPos;
	int topX;
	int bottomX;
	int topY;
	int bottomY;
	
	public Color color;
	
	boolean clicked;
	
	//defines everything about the dot objects
	public Dots(int x, int y)
	{
		clicked = false;
		xPos = x;
		yPos = y;
		topX = x+8;//10 is the radius of the dot
		bottomX = x-8;
		topY = y+8;
		bottomY = y-8;
		color = Color.BLACK;
		
	}//dots
	
	public static int getDotsClicked()
	{
		return dotsClicked;
	}//getDotsClicked
	
	public void clickDot(Color c)
	{
		clicked = true;
		color = c;
		dotsClicked++;
	}//clickDot
	
	public boolean isClicked()
	{
		return clicked;
	}//isClicked
	
	public int getX()
	{
		return xPos;
	}//getX
	
	public int getY()
	{
		return yPos;
	}//gety
	
	public void setDots()
	{
		clicked = false;
		color = Color.BLACK;
	}//setDots
	
	public boolean clickInDot(int x, int y)
	{
		if(x >= bottomX && x <= topX && y >= bottomY && y <= topY)
			return true;
		else
			return false;
	}//clickDot
	
	public static void resetClickCount()
	{
		dotsClicked = 0;
	}//reset
}
