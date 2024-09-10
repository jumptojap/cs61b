public class Planet{
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static final double G = 6.67e-11;
    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }
    public Planet(Planet b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }
    public double calcDistance(Planet b){
        double dx = b.xxPos - this.xxPos;
        double dy = b.yyPos - this.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }
    public double calcForceExertedBy(Planet b){
        return G * b.mass * this.mass / calcDistance(b) / calcDistance(b);
    }
    public double calcForceExertedByX(Planet b){
        return calcForceExertedBy(b) * (b.xxPos - this.xxPos) / calcDistance(b);
    }
    public double calcForceExertedByY(Planet b){
        return calcForceExertedBy(b) * (b.yyPos - this.yyPos) / calcDistance(b);
    }
    public double calcNetForceExertedByX(Planet[] bodys){
        int len = bodys.length;
        double res = 0;
        for(int i = 0; i < len; i++){
            if(this.equals(bodys[i]))
                continue;
            res = res + calcForceExertedByX(bodys[i]);
        }
        return res;
    }
    public double calcNetForceExertedByY(Planet[] bodys){
        int len = bodys.length;
        double res = 0;
        for(int i = 0; i < len; i++){
            if(this.equals(bodys[i]))
                continue;
            res = res + calcForceExertedByY(bodys[i]);
        }
        return res;
    }
    public void update(double dt, double fx, double fy){
        double anetx = fx / this.mass;
        double anety = fy / this.mass;
        xxVel = xxVel + dt * anetx;
        yyVel = yyVel + dt * anety;
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }
    public void draw(){
        StdDraw.enableDoubleBuffering();
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }

}