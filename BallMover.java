package balls;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BallMover extends JPanel implements ActionListener, KeyListener {
	JFrame jf = new JFrame();
	JFrame jf2 = new JFrame();
	Timer tm = new Timer(1, this);
	Timer tm2 = new Timer(1000, this);
	private TextField tf;
	private Label score1;
	private int p1s = 0;
	private Label score2;
	private int p2s = 0;
	private static final int HEIGHT = 1080;
	private static final int WIDTH = 1600;
	private int y1 = 30;
	private int y2 = 200;
	private int velY1 = 2;
	private int velY2 = 2;
	private int ballx = WIDTH / 2;
	private int bally = HEIGHT / 2;
	private double velBall = 2;
	private int ballAngle;

	public BallMover() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		jf2.setSize(300, HEIGHT);
		Rectangle instructions = new Rectangle(300, 300);
		Rectangle score1Pos = new Rectangle(300, 300);
		score1Pos.setLocation(0, WIDTH/3);
		Rectangle score2Pos = new Rectangle(300, 300);
		score2Pos.setLocation(0, 2*WIDTH/3);
				
		tf = new TextField("Place Cursor Here To Control Panels", 1);
		tf.setBounds(instructions);
		tf.setEditable(false);
		jf2.add(tf);
		tf.addKeyListener(this);
		
		score1 = new Label("Player 1 Score: " + p1s);
		score1.setBounds(score1Pos);
		jf2.add(score1);
		
		score2 = new Label("Player 2 Score: " + p2s);
		score2.setBounds(score2Pos);
		jf2.add(score2);
		jf2.setVisible(true);

		jf.setSize(WIDTH, HEIGHT);
		jf.add(this);
		jf.setVisible(true);

		if (Math.random() > .5) {
			if (Math.random() > .5) {
				ballAngle = (int) (Math.random() * 45) + 315;
			} else {
				ballAngle = (int) (Math.random()) + 45;
			}
		} else {
			if (Math.random() > .5) {
				ballAngle = (int) (Math.random() * 45) + 135;
			} else {
				ballAngle = (int) (Math.random() * 45) + 180;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(30, y1, 30, 200);
		g.fillRect(WIDTH - 80, y2, 30, 200);
		g.fillOval(ballx, bally, 40, 40);
	}

	public static void main(String args[]) {
		new BallMover();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char input = e.getKeyChar();
		if (input == '0') {
			velBall = 2;
			ballx = WIDTH / 2;
			bally = HEIGHT / 2;
			tm.start();
		}
		if (input == 'q') {
			velY1 *= -1;
		}
		if (input == 'p') {
			velY2 *= -1;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (y1 + 210 > HEIGHT || y1 <= 0) {
			if (y1 <= 0) {
				y1 = 5;
			}
			velY1 *= -1;
		}
		if (y2 + 210 > HEIGHT || y2 <= 0) {
			if (y2 <= 0) {
				y2 = 5;
			}
			velY2 *= -1;
		}
		y1 = y1 + velY1;
		y2 = y2 + velY2;
		if (ballx < 70 && ballx > 40 && bally >= y1 && bally <= y1 + 200) // bounce off p1
		{
			if (Math.random() > .5) {
				ballAngle = (int) (Math.random() * 45) + 315;
			} else {
				ballAngle = (int) (Math.random()) + 45;
			}
			velBall *= 1.1;
		}

		if (ballx > WIDTH - 100 && ballx < WIDTH - 70 && bally >= y2 && bally <= y2 + 200) // bounce off p2
		{
			if (Math.random() > .5) {
				ballAngle = (int) (Math.random() * 45) + 135;
			} 
			else {
				ballAngle = (int) (Math.random() * 45) + 180;
			}
			velBall *= 1.1;
		}

		if (ballx < 0) // Player 2 Wins
		{
			p2s++;
			score1.setText("Player2 Score: " + p2s);
			tm.stop();
		}
		if (ballx > WIDTH) // Player 1 Wins
		{
			p1s++;
			score2.setText("Player 1 Score: " + p1s);
			tm.stop();
		}
		if (bally > HEIGHT - 50 || bally <= 0) // touches bottom or top wall
		{
			if (bally <= 0) {
				bally = 5;
			}
			ballAngle *= -1;
		}
		ballx += velBall * Math.cos(Math.toRadians(ballAngle));
		bally += velBall * Math.sin(Math.toRadians(ballAngle));
		repaint();
	}

}
