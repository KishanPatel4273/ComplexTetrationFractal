package ComplexTetrationFractal;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class Display extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1000;
	public static final int HEIGHT = 1000;
	public static final String TITLE = "Tetration fractal";
	
	
	private Thread thread;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private ComplexTetrationFractal fractal;
	
	public Display() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		
		fractal = new ComplexTetrationFractal(WIDTH, HEIGHT, 40);
		int[] p = fractal.getFractal(new Complex(-.188f,.235f), .02f);
		//int[] p = fractal.getFractal(new Complex(-1.9f,0), 3f);
		//int[] p = fractal.getFractal(new Complex(-1.3f,2.85f), .8f); // doesnt work +-bail
		//int[] p = fractal.getFractal(new Complex(2.15f,-.91f), .5f);
		for(int i = 0; i < p.length; i++) {
			pixels[i] = p[i];
		}
	}

	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;

		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			requestFocus();

			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					//System.out.println(frames + "fps");
					previousTime += 1000;
					frames = 0;
				}

			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
		}
	}
	
	
	private void tick() {
	
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(img, 0, 0, null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame(TITLE);
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);;
		game.start();
	}	

}