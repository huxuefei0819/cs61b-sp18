public class NBody{

	private static String bkgImg = "images/starfield.jpg";

	public static double readRadius(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		in.close();
		return radiusOfUniverse;
	}

	public static Body[] readBodies(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		Body[] bodyArray = new Body[numOfPlanets];
		for(int i=0;i<numOfPlanets;i++){
			 double xxPos = in.readDouble();
			 double yyPos = in.readDouble();
			 double xxVel = in.readDouble();
			 double yyVel = in.readDouble();
			 double mass = in.readDouble();
			 String imgFileName = in.readString();
			 bodyArray[i] = new Body(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
		}
		in.close();
		return bodyArray;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]); 
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = NBody.readRadius(filename);
		Body[] bodies = NBody.readBodies(filename); 

		StdDraw.enableDoubleBuffering();
		for(int i=0;i<T;i+=dt){

			double[] xForces = new double[bodies.length];
			double[] yForces = new double[bodies.length];
			for(int j=0;j<bodies.length;j++){
				xForces[j] = bodies[j].calcNetForceExertedByX(bodies);
				yForces[j] = bodies[j].calcNetForceExertedByY(bodies);
		    }
		    for(int j=0;j<bodies.length;j++){
		    	bodies[j].update(dt, xForces[j], yForces[j]);
		    }
		    StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, bkgImg);
			for(Body b:bodies){
				b.draw();
			}
			StdDraw.show();
		    StdDraw.pause(1000);	
		 
		}

		StdOut.printf("%d\n", bodies.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}