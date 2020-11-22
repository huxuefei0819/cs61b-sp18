public class NBody{

	private static String bkgImg = "images/starfield.jpg";

	public static double readRadius(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		in.close();
		return radiusOfUniverse;
	}

	public static Planet[] readBodies(String filename){
		In in = new In(filename);
		int numOfPlanets = in.readInt();
		double radiusOfUniverse = in.readDouble();
		Planet[] bodyArray = new Planet[numOfPlanets];
		for(int i=0;i<numOfPlanets;i++){
			 double xxPos = in.readDouble();
			 double yyPos = in.readDouble();
			 double xxVel = in.readDouble();
			 double yyVel = in.readDouble();
			 double mass = in.readDouble();
			 String imgFileName = in.readString();
			 bodyArray[i] = new Planet(xxPos,yyPos,xxVel,yyVel,mass,imgFileName);
		}
		in.close();
		return bodyArray;
	}

	public static void main(String[] args){
		double T = Double.parseDouble(args[0]); 
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = NBody.readRadius(filename);
		Planet[] planets = NBody.readBodies(filename); 

		StdDraw.enableDoubleBuffering();
		for(int i=0;i<T;i+=dt){

			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int j=0;j<planets.length;j++){
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);
		    }
		    for(int j=0;j<planets.length;j++){
		    	planets[j].update(dt, xForces[j], yForces[j]);
		    }
		    StdDraw.setScale(-radius, radius);
			StdDraw.picture(0, 0, bkgImg);
			for(Planet b:planets){
				b.draw();
			}
			StdDraw.show();
		    StdDraw.pause(1000);	
		 
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}