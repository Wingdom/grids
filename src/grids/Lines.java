package grids;
//Cameron Yeager
//c2279189
//Assignment 5
//4/22/1009

import java.awt.*;

public class Lines 
{
	private Dots startDot;
	private Dots endDot;
	private Color c;
	
	//defines everything about the line objects
	public Lines(Dots startDot, Dots endDot, Color c)
	{
		this.startDot = startDot;
		this.endDot = endDot;
		this.c = c;
	}//Lines
	
	public Dots getStartDot()
	{
		return startDot;
	}//getStartDot
	
	public Dots getEndDot()
	{
		return endDot;
	}//getEndDot
	
	public Color getDotColor()
	{
		return c;
	}//getDotCOlor
}
