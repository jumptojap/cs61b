public class NBody{
    public static double readRadius(String path){
        In in = new In(path);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        return secondItemInFile;
    }
    public static Planet[] readPlanets(String path){
        In in = new In(path);
        int firstItemInFile = in.readInt();
        double secondItemInFile = in.readDouble();
        Planet[] res = new Planet[firstItemInFile];
        for(int i = 0; i < firstItemInFile; i++){
            res[i] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
        }
        return res;
    }
    public static void main(String[] args) {
        double t = 0;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] bodys = readPlanets(filename);
        while(t < T) {
            double[] xForces = new double[bodys.length];
            double[] yForces = new double[bodys.length];
            for (int i = 0; i < bodys.length; i++) {
                xForces[i] = bodys[i].calcNetForceExertedByX(bodys);
                yForces[i] = bodys[i].calcNetForceExertedByY(bodys);
                bodys[i].update(dt, xForces[i], yForces[i]);
            }
            String imageToDraw = "images/starfield.jpg";
            StdDraw.enableDoubleBuffering();
            StdDraw.setScale(-radius, radius);
            StdDraw.clear();
            StdDraw.picture(0, 0, imageToDraw);
            for (int i = 0; i < bodys.length; i++) {
                bodys[i].draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t = t + dt;
        }
        System.out.printf("%d\n", bodys.length);
        System.out.printf("%.2e\n", radius);
        for (int i = 0; i < bodys.length; i++) {
            System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodys[i].xxPos, bodys[i].yyPos, bodys[i].xxVel,
                    bodys[i].yyVel, bodys[i].mass, bodys[i].imgFileName);
        }


    }
}