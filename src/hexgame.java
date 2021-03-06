import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

/**********************************
 GUI Version of a board game created at 
 Rutgers University
Board Initialization Written by: M.H.
Written by: Ryan Couch
Date: December 2015
 ***********************************/

public class hexgame
{
	private hexgame() {
		initGame();
		createAndShowGUI();
		generateBuildingBlocks();
		//initPlayers();
		//generatePlayerOrder();
		//playersTurn();
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new hexgame();
			}
		});
	}

	//constants and global variables
	final static Color COLOURBACK = Color.WHITE;
	final static Color COLOURCELL = Color.GRAY;
	final static Color COLOURGRID = Color.BLACK;
	final static Color COLOURONE = new Color(255,255,255,200);
	final static Color COLOURONETXT = Color.BLUE;
	final static Color COLOURTWO = new Color(0,0,0,200);
	final static Color COLOURTWOTXT = new Color(255,100,255);
	final static int EMPTY = 0;
	final static int BSIZE = 13; //board size.
	final static int HEXSIZE = 60; //hex size in pixels
	final static int BORDERS = 15;
	final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS*3; //screen size (vertical dimension).
	final static int numPlayers = 4;
	player[] players = new player[4];
	player currentPlayer = null;
	int[][] board = new int[BSIZE][BSIZE];
	
	void initGame(){

		hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.

		hexmech.setHeight(HEXSIZE); //Either setHeight or setSize must be run to initialize the hex
		hexmech.setBorders(BORDERS);

		for (int i=0;i<BSIZE;i++) {
			for (int j=0;j<BSIZE;j++) {
				board[i][j]=EMPTY;
			}
		}

	}

	void initPlayers(){
		//JOptionPane pane = new JOptionPane("What would you like to build?");
		for(int i=0; i<4; i++){
		JFrame frame = new JFrame("Game Setup");
		String name = JOptionPane.showInputDialog(frame, "Player "+(i+1)+": Enter your name");
		player p = new player(name);
		players[i] = p;
		}

	}
	
	void generatePlayerOrder(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		Collections.shuffle(list);
	//	System.out.println(list);
		
		player[] temp = new player[4];

		for(int i=0; i<4; i++){
			players[i].order = list.get(i);
			int x = list.get(i)-1;
			temp[x] = new player(players[i].name);
			temp[x].order = list.get(i);
		}
		
		players = temp;
		JFrame frame = new JFrame("Turn Order");
		JFrame.setDefaultLookAndFeelDecorated(true);
		Object[] columns = {"1st", "2nd", "3rd", "4th"};
		Object[] names = new Object[4];
		for(int i=0; i<4; i++){
			//JTextField name = new JTextField(players[i].name);
			names[i] = (Object)players[i].name;
			//name.setSize(50, 10);
			//frame.add(name);
		}
		Object[][] data = {columns, names};
		JTable table = new JTable(data, columns);
		frame.setSize(600,400);
		frame.add(table,BorderLayout.CENTER);
		frame.pack();
		//frame.setSize(200, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	void generateBuildingBlocks(){
		Graphics g = null;
		colorHex(1,2, g);
		
	}
	
	void colorHex(int x, int y, Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLUE);
		hexmech.fillHex(x,y,board[x][y],g2);
	}
	
	void playersTurn(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		//show whos turn it is
		displayPlayersTurn();
		
		//ask what player would like to do
		showMoveTypes();
		
		//carry out turn
		turn t = new turn(this.currentPlayer);
		
	}
	
	void showMoveTypes(){
		JFrame frame = new JFrame("What would you like to do?");
		JOptionPane j = new JOptionPane("Options");
		//add move, attack, upgrade, build
		
		Object[] types = {"Move", "Build", "Upgrade", "Attack"};
		int response = JOptionPane.showOptionDialog(frame, "Select turn option", "Build", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, types, types[0]);
		
		//move
		if(response==0){
			//Select builder
			//Select location (must be in bounds)
			//board[p.x][p.y] = (int)'M';
		}
		//build
		if(response==1){
			//Select location
			//Must be building block
			//board[p.x][p.y]= (int)'F'; 
		}  
		//upgrade
		if(response==2){
			//Select building (must exist)
			//Must have enough $
			//board[p.x][p.y]= (int)'L'; 
		}  
		//attack
		if(response==3){
			//Select barracks
			//Select what you want to attack
			//Odds and all that shit
			//board[p.x][p.y]= (int)'C'; 
		}  

		
		
		
		frame.add(j);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	
	void displayPlayersTurn(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Next Turn!");
		frame.setSize(300,70);
		
		//delete later
		currentPlayer = players[0];
		
		JTextField name = new JTextField("Player: "+currentPlayer.name);
		name.setEditable(false);
		frame.add(name);
		//frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void createAndShowGUI()
	{
		DrawingPanel panel = new DrawingPanel();


		//JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("How Was Your Day?");
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		Container content = frame.getContentPane();
		content.add(panel);
		//this.add(panel); -- cannot be done in a static context
		//for hexes in the FLAT orientation, the height of a 10x10 grid is 1.1764 * the width. (from h / (s+t))
		frame.setSize( (int)(SCRSIZE/1.23), SCRSIZE);
		frame.setResizable(false);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
	}


	class DrawingPanel extends JPanel
	{
		//mouse variables here
		//Point mPt = new Point(0,0);

		public DrawingPanel()
		{
			setBackground(COLOURBACK);

			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);
		}

		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			super.paintComponent(g2);
			//draw grid
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					hexmech.drawHex(i,j,g2);
				}
			}
			//fill in hexes
			for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
					//if (board[i][j] < 0) hexmech.fillHex(i,j,COLOURONE,-board[i][j],g2);
					//if (board[i][j] > 0) hexmech.fillHex(i,j,COLOURTWO, board[i][j],g2);
					
					//lock border pieces
					hexmech.fillHex(i,j,board[i][j],g2);
				}
			}

			//g.setColor(Color.RED);
			//g.drawLine(mPt.x,mPt.y, mPt.x,mPt.y);
		}

		class MyMouseListener extends MouseAdapter	{ //inner class inside DrawingPanel
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				//mPt.x = x;
				//mPt.y = y;
				Point p = new Point( hexmech.pxtoHex(e.getX(),e.getY()) );
				if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE) return;

				//DEBUG: colour in the hex which is supposedly the one clicked on
				//clear the whole screen first.
				/* for (int i=0;i<BSIZE;i++) {
				for (int j=0;j<BSIZE;j++) {
				board[i][j]=EMPTY;
					}
				} */
				JFrame frame = new JFrame("JOptionPane ex");
				//JOptionPane pane = new JOptionPane("What would you like to build?");
				Object[] poss = {"Military Barracks", "Factory", "Laboratory", "Church"};
				int response = JOptionPane.showOptionDialog(frame, "Select building type", "Build", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, poss, poss[0]);
		
				if(response==0){
					board[p.x][p.y] = (int)'M';
				}
				if(response==1){
					board[p.x][p.y]= (int)'F'; 
				}  
				if(response==2){
					board[p.x][p.y]= (int)'L'; 
				}  
				if(response==3){
					board[p.x][p.y]= (int)'C'; 
				}  
				
				//What do you want to do when a hexagon is clicked?
				repaint();
			}
		} //end of MyMouseListener class
	} // end of DrawingPanel class
} 