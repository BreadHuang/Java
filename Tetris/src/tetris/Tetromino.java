package tetris;

public class Tetromino {
	protected Cell[] cells;
	protected Cell axis;
	protected Cell[] rotateCells;
	
	protected void anticlockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;
		int oldY;
		int newX;
		int newY;
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - axis.getY() + oldY;
			newY = axis.getY() + axis.getX() - oldX;
			
			rotateCells[i].setX(newX);
			rotateCells[i].setY(newY);
		}
	}
	
	protected void clockwiseRotate(Cell axis, Cell[] rotateCells) {
		int oldX;
		int oldY;
		int newX;
		int newY;
		
		for(int i = 0; i < 3; i ++) {
			oldX = rotateCells[i].getX();
			oldY = rotateCells[i].getY();
			
			newX = axis.getX() - oldY + axis.getY();
			newY = axis.getY() + oldX - axis.getX();
			
			rotateCells[i].setX(newX);
			rotateCells[i].setY(newY);
		}
	}
	
	/**
	 * 实现四格方块的自动下落
	 */
	protected void softDrop() {
		int oldY;
		int newY;
		
		for(int i = 0; i < cells.length; i ++) {
			oldY = cells[i].getY();
			newY = oldY + 1;
			
			cells[i].setY(newY);
		}
	}
	
	protected void moveLeft() {
		int oldX;
		int newX;
		
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX - 1;
			
			cells[i].setX(newX);
		}
	}
	
	protected void moveRight() {
		int oldX;
		int newX;
		
		for(int i = 0; i < cells.length; i ++) {
			oldX = cells[i].getX();
			newX = oldX + 1;
			
			cells[i].setX(newX);
		}
	}
	
	protected Cell[] getCells() {
		return cells;
	}
	
	protected Cell getAxis() {
		return axis;
	}
	
	protected Cell[] getRotateCells() {
		return rotateCells;
	}
	
	protected void setAxis() {
		axis = cells[0];
	}
	
	protected void setRotateCells() {
		rotateCells = new Cell[]{cells[1], cells[2], cells[3]};
	}
}
