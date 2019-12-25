package tetris;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	public static final int CELL_SIZE = 25;		//a cell size

	/* the color of all the cells  */
	public static final int COLOR_CYAN = 0;
	public static final int COLOR_BLUE = 1;
	public static final int COLOR_GREEN = 2;
	public static final int COLOR_YELLOW = 3;
	public static final int COLOR_ORANGE = 4;
	public static final int COLOR_RED = 5;
	public static final int COLOR_PINK = 6;

	private int color;	//color of the cell
	private int x;	//x-axis
	private int y;	//y-axis


	public Cell(int x, int y, int style) {
		/* choose the color with style */
		switch(style) {
			case 0: color = COLOR_CYAN; break;
			case 1: color = COLOR_BLUE; break;
			case 2: color = COLOR_GREEN; break;
			case 3: color = COLOR_YELLOW; break;
			case 4: color = COLOR_ORANGE; break;
			case 5: color = COLOR_RED; break;
			case 6: color = COLOR_PINK; break;
		}

		this.x = x;
		this.y = y;
	}

	// Set the new coordinate 
	public void setX(int newX) {
		x = newX;
	}

	// Set the y-axis of the Cell
	public void setY(int newY) {
		y = newY;
	}

	// Get the x-axis of the Cell
	public int getX() {
		return x;
	}

	// Get the y-axis of the Cell
	public int getY() {
		return y;
	}

	public void paintCell(Graphics g) {
		switch(color) {
			case COLOR_CYAN: g.setColor(Color.CYAN);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_BLUE: g.setColor(Color.BLUE);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_GREEN: g.setColor(Color.GREEN);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_YELLOW: g.setColor(Color.YELLOW);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_ORANGE: g.setColor(Color.ORANGE);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_RED: g.setColor(Color.RED);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_PINK: g.setColor(Color.PINK);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
		}
	}
}
