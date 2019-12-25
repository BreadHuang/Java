package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 俄罗斯方块游戏场景类
 * @author Leslie Leung
 */
public class TetrisPane extends JPanel {
	public static final int ROWS = 20;	//the rows of scene
	public static final int COLUMNS = 16;	//the columns of scene
	
	/* 表示7种不同的四格方块 */
	public static final int I_SHAPED = 0;
	public static final int S_SHAPED = 1;
	public static final int T_SHAPED = 2;
	public static final int Z_SHAPED = 3;
	public static final int L_SHAPED = 4;
	public static final int O_SHAPED = 5;
	public static final int J_SHAPED = 6;
	
	public static final int KIND = 7;
	public static final int INIT_SPEED = 1000;	//fall down's speed
	
	private static int randomNum = 0;	//the number of block that have been made
	
	private Random random;
	private Tetromino currentTetromino;	//the current block
	private Cell[][] wall;
	private Timer autoDrop;
	private KeyControl keyListener;
	
	public TetrisPane() {
		setPreferredSize(new Dimension(COLUMNS * Cell.CELL_SIZE, ROWS * Cell.CELL_SIZE));
		
		random = new Random();
		wall = new Cell[ROWS][COLUMNS];
		autoDrop = new Timer();
		keyListener = new KeyControl();
		
		randomOne();
		
		autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
	}
	
	public void randomOne() {
		Tetromino tetromino = null;
		
		// Product a tetris randomly
		switch(random.nextInt(KIND)) {
			case I_SHAPED: 
				tetromino = new IShaped();
				break;
			case S_SHAPED: 
				tetromino = new SShaped();
			   	break;
			case T_SHAPED: 
				tetromino = new TShaped();
			    break;
			case Z_SHAPED: 
				tetromino = new ZShaped();
			    break;
			case L_SHAPED: 
				tetromino = new LShaped();
			    break;
			case O_SHAPED: 
				tetromino = new OShaped();
			    break;
			case J_SHAPED: 
				tetromino = new JShaped();
			    break;
		}
		currentTetromino = tetromino;	//the current block is the generated block
		randomNum ++;
	}
	
	public boolean isGameOver() {
		int x, y;	//The current block's coordinate
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			if(isContain(x, y)) {//if there is already a block at the position just be generated and it exists.then you lost
				return true;
			}
		}
		
		return false;
	}
	
	//Each time a block is generated,the falling speed increased by changing the interval of the TimerTask
	public double interval() {
		return INIT_SPEED * Math.pow((double)39 / 38, 0 - randomNum);
	}
	
	public KeyControl getInnerInstanceOfKeyControl() {
		return keyListener;
	}
	
	//For automatic drop of Tetris
	private class DropExecution extends TimerTask {	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isGameOver()) {
				JOptionPane.showMessageDialog(null, "You are lost");
				autoDrop.cancel();
				removeKeyListener(keyListener);
				return;
			}
			
			if(!isReachBottomEdge()) {
				currentTetromino.softDrop();
			} else {
				landIntoWall();		//add the block to the wall
				removeRows();	//If the row is full,then delete the row
				randomOne();	//New a block
				
				autoDrop.cancel();
				autoDrop = new Timer();
				autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
			}
			
			repaint();
		}	
	}
	
	public void landIntoWall() {
		int x, y;	//The block's coordinate which can't move
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			wall[y][x] = getCurrentCells()[i];	//添加到墙上
		}
	}
	
	private class KeyControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT: //move left
					
					if(!isReachLeftEdge()) {
						currentTetromino.moveLeft();
						repaint();
					}					
					break;
					
				case KeyEvent.VK_RIGHT:	//move right
					
					if(!isReachRightEdge()) {
						currentTetromino.moveRight();
						repaint();
					}
					break;
				
				case KeyEvent.VK_DOWN:	//move down
					
					if(!isReachBottomEdge()) {
						currentTetromino.softDrop();
						repaint();
					}
					
					break;
					
				case KeyEvent.VK_SPACE:	//move quickly
					
					hardDrop();
					landIntoWall();
					removeRows();
					
					randomOne();
					autoDrop.cancel();
					autoDrop = new Timer();
					autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
					
					repaint();
					break;
					
				case KeyEvent.VK_D:
					
					if(!clockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {//俄罗斯方块没越界且其不为O形时，旋转
						currentTetromino.clockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
					
				case KeyEvent.VK_A:
					
					if(!anticlockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {//俄罗斯方块没越界且其不为O形时，旋转
						currentTetromino.anticlockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
			}
		}
	}
	
	private class IShaped extends Tetromino {

		public IShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_CYAN);
			cells[0] = new Cell(4, 0, Cell.COLOR_CYAN);
			cells[2] = new Cell(5, 0, Cell.COLOR_CYAN);
			cells[3] = new Cell(6, 0, Cell.COLOR_CYAN);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	private class SShaped extends Tetromino {
		
		public SShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Cell.COLOR_BLUE);
			cells[1] = new Cell(5, 0, Cell.COLOR_BLUE);
			cells[2] = new Cell(3, 1, Cell.COLOR_BLUE);
			cells[3] = new Cell(4, 1, Cell.COLOR_BLUE);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	private class TShaped extends Tetromino {

		public TShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_GREEN);
			cells[0] = new Cell(4, 0, Cell.COLOR_GREEN);
			cells[2] = new Cell(5, 0, Cell.COLOR_GREEN);
			cells[3] = new Cell(4, 1, Cell.COLOR_GREEN);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}

	private class ZShaped extends Tetromino {

		public ZShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_ORANGE);
			cells[2] = new Cell(4, 0, Cell.COLOR_ORANGE);
			cells[0] = new Cell(4, 1, Cell.COLOR_ORANGE);
			cells[3] = new Cell(5, 1, Cell.COLOR_ORANGE);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	private class LShaped extends Tetromino {
		
		public LShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_PINK);
			cells[0] = new Cell(4, 0, Cell.COLOR_PINK);
			cells[2] = new Cell(5, 0, Cell.COLOR_PINK);
			cells[3] = new Cell(3, 1, Cell.COLOR_PINK);

			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	private class OShaped extends Tetromino {

		public OShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Cell.COLOR_RED);
			cells[1] = new Cell(5, 0, Cell.COLOR_RED);
			cells[2] = new Cell(4, 1, Cell.COLOR_RED);
			cells[3] = new Cell(5, 1, Cell.COLOR_RED);

			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	private class JShaped extends Tetromino {

		public JShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_YELLOW);
			cells[0] = new Cell(4, 0, Cell.COLOR_YELLOW);
			cells[2] = new Cell(5, 0, Cell.COLOR_YELLOW);
			cells[3] = new Cell(5, 1, Cell.COLOR_YELLOW);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	public void removeRows() {
		for(int i = 0; i < getCurrentCells().length; i ++) {
			removeRow(getCurrentCells()[i].getY());
		}
	}
	
	public Cell getAxis() {
		return currentTetromino.getAxis();
	}

	public Cell[] getRotateCells() {
		return currentTetromino.getRotateCells();
	}

	public Cell[] getCurrentCells() {
		return currentTetromino.getCells();
	}

	public boolean isReachBottomEdge() {
		int oldY, newY, oldX;		//定义格子旧的纵坐标、新的纵坐标和旧的横坐标
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldY = getCurrentCells()[i].getY();
			newY = oldY + 1;
			oldX = getCurrentCells()[i].getX();
			
			if(oldY == ROWS - 1) {//到达面板底部
				return true;
			}
			
			if(isContain(oldX, newY)) {//下一位置有方块
				return true;
			}
		}
		return false;
	}

	public boolean isReachLeftEdge() {
		int oldX, newX, oldY;
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX - 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == 0 || isContain(newX, oldY)) {
				return true;
			}
			
			if(isContain(newX, oldY)) {
				return true;
			}
		}
		return false;
	}

	public boolean isReachRightEdge() {
		int oldX, newX, oldY;
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX + 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == COLUMNS - 1 || isContain(newX, oldY)) {
				return true;
			}
			
			if(isContain(newX, oldY)) {
				return true;
			}
		}
		return false;
	}

	public boolean clockwiseRotateIsOutOfBounds() {
		int oldX;
		int oldY;
		int newX;
		int newY;
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - oldY + getAxis().getY();
			newY = getAxis().getY() + oldX - getAxis().getX();
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {
				return true;
			}
			
			if(isContain(newX, newY)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean anticlockwiseRotateIsOutOfBounds() {
		int oldX;
		int oldY;
		int newX;
		int newY;
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - getAxis().getY() + oldY;
			newY = getAxis().getY() + getAxis().getX() - oldX;
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {
				return true;
			}
			
			if(isContain(newX, newY)) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isContain(int x, int y) {
		if(wall[y][x] == null) {
			return false;
		} else {
			return true;
		}
	}

	public void hardDrop() {
		while(!isReachBottomEdge()) {
			currentTetromino.softDrop();
		}
	}

	public void removeRow(int i) {
		int oldY, newY;	
		
		for(int j = 0; j < COLUMNS; j ++) {
			if(wall[i][j] == null) {
				return;
			}
		}
		
		for(int k = i; k >= 1; k --){
			System.arraycopy(wall[k - 1], 0, wall[k], 0, COLUMNS);
			
			for(int m = 0; m < COLUMNS; m ++) {
				if(wall[k][m] != null) {
					oldY = wall[k][m].getY();
					newY = oldY + 1;
					wall[k][m].setY(newY);				
				}
			}
			
		}
		Arrays.fill(wall[0], null);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getBounds().width, getBounds().height);
		
		for(int i = 0; i < ROWS; i ++) {
			for(int j = 0; j < COLUMNS; j ++) {
				if(wall[i][j] == null) {
					g.setColor(Color.WHITE);
					g.fillRect(j * Cell.CELL_SIZE + 1, i * Cell.CELL_SIZE + 1, Cell.CELL_SIZE - 2, Cell.CELL_SIZE - 2);
				} else {
					wall[i][j].paintCell(g);
				}
			}
		}
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			getCurrentCells()[i].paintCell(g);
		}
		
	}
}
