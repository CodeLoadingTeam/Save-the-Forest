import java.util.Random;

public class Objects {

	////////////////////////////////////////////////////////////////////////#1/
	public double x, y, ySpeed, y1;
	public int L;
	
	public static int xspeed = -100;
	public static int HOLESIZE = 94; //50 pixels
	public boolean counted = false;
	
	public Hitbox boxcima;
	public Hitbox boxbaixo;
	public Hitbox boxObj;
	
	public double gravity;
			
	public static Random gerador = new Random();
	
	public static int [][] p = { // positions of each obj
			{16, 15, 21, 65},
			{45, 8, 41, 70},
			{87, 33, 89, 47},
			{16, 90, 24, 72}, 
			{44, 109, 33, 54},
			{81, 91, 23, 72},
			{106, 125, 43, 39},
			{152, 118, 34, 45},
			{188, 104, 31, 59},
			{222, 112, 26, 51},
			{251, 103, 25, 60}	
	};
	
	////////////////////////////////////////////////////////////////////////#2/
	public Objects(double x, double y, int chooseObj, double gravity) {

		this.x = x;
		this.y = y;
		this.ySpeed = 0;
		this.L = chooseObj;
		
		this.gravity = gravity;
		this.boxObj = new Hitbox (x, y, x + p[L][2], y + p[L][3]);
	}
	
	////////////////////////////////////////////////////////////////////////#3/
	public void tique(double dt) {
//		x += xspeed * dt; (p/ caso obj cair fazendo curva)
		
//		boxcima.mover(xspeed*dt, 0);
//		boxbaixo.mover(xspeed*dt, 0);
		
		ySpeed += gravity * dt;
		y += ySpeed * dt;
		
		boxObj.mover(x, y, x + p[L][2], y + p[L][3]);
		
	}
	
	////////////////////////////////////////////////////////////////////////#4/
	public void drawItself(Tela t) {
		//  
		int valor = 0;
		
		if (L == 2 && x > 200 && x <= 245) { 
			valor =  48;
			}
		
		t.imagem("obj/trashObjects.png", p[L][0], p[L][1], p[L][2], p[L][3], 0, x-valor, y);
	}
}
