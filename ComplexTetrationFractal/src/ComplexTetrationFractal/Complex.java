package ComplexTetrationFractal;
public class Complex {
	
	private float real, imaginary;
	
	public Complex() {
		this.real = 0;
		this.imaginary = 0;
	}
	
	public Complex(float real, float imaginary){
		this.real = real;
		this.imaginary = imaginary;
	}
	
	public void add(Complex a){
		real += a.getReal();
		imaginary += a.getImaginary();
	}
	
	public void sub(Complex a){
		real -= a.getReal();
		imaginary -= a.getImaginary();
	}
	
	public void multiply(Complex a){
		float r = real * a.getReal()  - imaginary * a.getImaginary();
		float i = real * a.getImaginary() + imaginary * a.real;
		real = r;
		imaginary = i;
	}
	
	public float getMagnitude(){
		return (float) Math.sqrt(real * real + imaginary * imaginary);
	}
	
	public float getReal(){
		return real;
	}
	
	public float getArg() {//range (-pi,pi]
		float angle = (float) Math.abs(Math.atan(imaginary/real));
		if(real > 0 && imaginary > 0) {//q1
			return angle;
		}
		if(real < 0 && imaginary > 0) {//q2
			return (float) (Math.PI - angle);
		}
		if(real < 0 && imaginary < 0) {//q3
			return (float) (-Math.PI + angle);
		}
		if(real > 0 && imaginary < 0) {//q4
			return -angle;
		}
		if(real == 0 && imaginary > 0) {
			return (float) (Math.PI/2.0f);
		}
		if(real == 0 && imaginary < 0) {
			return (float) (-Math.PI/2.0f);
		}
		if(real > 0 && imaginary == 0) {
			return (float) (0.0f);
		}
		if(real < 0 && imaginary == 0) {
			return (float) (Math.PI);
		}
		return (float) 0;
	}
	
	public void raiseTo(Complex z) {
		float r = (float) ((power(getMagnitude(), z.getReal())) * (exp(-1 * z.getImaginary() * z.getArg())) * ((Math.cos(z.getReal() * getArg() + z.getImaginary() * ln(getMagnitude())))));
		float i = (float) ((power(getMagnitude(), z.getReal())) * (exp(-1 * z.getImaginary() * z.getArg())) * ((Math.sin(z.getReal() * getArg() + z.getImaginary() * ln(getMagnitude())))));
		real = r;
		imaginary = i;
	}
	
	public Complex raiseTo(Complex w, Complex z) {
		float a = w.getReal();
		float b = w.getImaginary();
		float c = z.getReal();
		float d = z.getImaginary();
		float radius = (float) Math.sqrt(a*a + b*b);
		float angle = w.getArg();
		float r =  (float) (Math.pow(radius, c) * Math.pow(Math.E, -d* angle) * Math.cos(c*angle + d * ln(radius)));
		float i =  (float) (Math.pow(radius, c) * Math.pow(Math.E, -d* angle) * Math.sin(c*angle + d * ln(radius)));
		return new Complex(r, i);
	}
	
	private float power(float x, float n) {
		return (float) (Math.pow(x, n));
	}
	
	private float exp(float x) {
		return (float) Math.exp(x);
	}
	
	private float ln(float x) {
		return (float) (Math.log(x)/Math.log(Math.E));
	}
	
	private float log(float x) {
		return (float) (Math.log(x));
	}
	
	public float getImaginary(){
		return imaginary;
	}
	
	public Complex getValue() {
		return new Complex(real, imaginary);
	}
	
	public String toString(){
		return real + " + " + imaginary + "i ";
	}
}