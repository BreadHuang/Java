package tetris;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TetrisFrame extends JFrame {
	private TetrisPane tp;
	private JLabel mention;
	
	/**
	 * 构造方法
	 */
	public TetrisFrame() {
		setSize(550, 600);	//set the window's size
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);	//set the window's location in the middle of screen
		setTitle("Tetris");		//set the title
		setResizable(false);		//Don't allow to resize the window
		setLayout(new FlowLayout());	//typesetting
		
		tp = new TetrisPane();	//new pane object
		mention = new JLabel("Press 'A' to do Counterclockwise rotation\n" + 
							 "Press 'D' to do Counterclockwise rotation\n" + 
				             "The arrow keys can move the cell\n" +
							 "Press the space can let the cell fall quickly");
		
		add(mention);	//add the label to the main frame
		add(tp);		//add the game 
		
		//keyboard event
		addKeyListener(tp.getInnerInstanceOfKeyControl());
		tp.addKeyListener(tp.getInnerInstanceOfKeyControl());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new TetrisFrame();
	}
}
