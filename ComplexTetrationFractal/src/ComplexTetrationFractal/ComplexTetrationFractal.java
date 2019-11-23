package ComplexTetrationFractal;

import java.awt.Point;
import java.util.ArrayList;

public class ComplexTetrationFractal {

	private int width, height, bail;
	private int[] pixels;
	private ArrayList<Point> points;
	
	public ComplexTetrationFractal(int width, int height, int bail) {
		this.width = width;
		this.height = height;
		this.bail = bail;
		pixels = new int[width * height];
		points = new ArrayList<Point>();
		
	}
	
	public int[] getFractal(Complex center, float radius) {
		generateFractal(center.getReal() - radius, center.getReal() + radius, center.getImaginary() - radius, center.getImaginary() + radius);
		return getPixels();
	}
	
	
	public Complex tetrate(Complex z) {
		Complex w = z;
		for(int i = 1; i < bail; i++) {
			w = z.raiseTo(z, w);
		}
		return w;
	}
	
	public void generateFractal(float minX,float maxX, float minY, float maxY) {
		points.clear();
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
		float[] range ={Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE};//min max X , min max Y
		float distanceX = (float)Math.abs((maxX - minX));
		float distanceY = (float)Math.abs((maxY - minY));
		for (float x = minX; x <= maxX; x += (float)(distanceX/width)) {
			for (float y = minY; y <= maxY; y += (float)(distanceY/height)) {
				float magnitude = tetrate(new Complex(x,y)).getMagnitude();	
				
				float a1 = ((float)x * (width/distanceX)) + (float)width/(distanceX);
				float a2 = ((float)y * (height/distanceY)) + (float)height/(distanceY);
				range[0] = Math.min(range[0], a1);
				range[2] = Math.min(range[2], a2);
				if(magnitude < 10000){
					points.add(new Point((int)a1-1, (int)a2-1));	
				}
			}
		}
		for(int i = 0; i < points.size(); i++){
			drawPoint((int) (points.get(i).x - range[0]), (int) (points.get(i).y - range[2]), 15402033);//12435678/100);
		}
	}
	
	public void drawPoint(int x, int y, int color) {
		if((x + y * width) > -1 && (x + y * width) <= pixels.length) {
			pixels[x + y * width] = color; 	
		}
	}
	
	private int[] getPixels() {
		return pixels;
	}
}
