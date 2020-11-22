public class Body{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private final static double G = 6.67e-11;

	public Body(double xP, double yP, double xV, double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Body(Body b){
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b){
		/** Returns the distance r=sqrt((x1-x2)^2 +(y1-y2)^2) */
		return Math.sqrt(Math.pow(this.xxPos-b.xxPos, 2)+Math.pow(this.yyPos-b.yyPos, 2));
	}

	public double calcForceExertedBy(Body b){
		/** Returns the force F=G*m1*m2/r^2 */
		return G*this.mass*b.mass/Math.pow(this.calcDistance(b),2);
	}

	public double calcForceExertedByX(Body b){
		/** Returns the force Fx=F*dx/r */
		return this.calcForceExertedBy(b)*(b.xxPos-this.xxPos)/this.calcDistance(b);

	}

	public double calcForceExertedByY(Body b){
		/** Returns the force Fy=F*dy/r */
		return this.calcForceExertedBy(b)*(b.yyPos-this.yyPos)/this.calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] ab){
		/** Returns the sum of X force */
		double netX = 0;
		for(Body b:ab){
			if(this.equals(b)){
				continue;
			}else{
				netX += this.calcForceExertedByX(b);
			}
		}
		return netX;
	}

	public double calcNetForceExertedByY(Body[] ab){
		/** Returns the sum of Y force */
		double netY = 0;
		for(Body b:ab){
			if(this.equals(b)){
				continue;
			}else{
				netY += this.calcForceExertedByY(b);
			}
		}
		return netY;
	}

	public void update(double time, double xforce, double yforce){
		double ax = xforce/this.mass;
		double ay = yforce/this.mass;
		double vx = this.xxVel + time*ax;
		double vy = this.yyVel + time*ay;
		double px = this.xxPos + time*vx;
		double py = this.yyPos + time*vy;

		this.xxVel = vx;
		this.yyVel = vy;
		this.xxPos = px;
		this.yyPos = py;
	}

	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
	}
}