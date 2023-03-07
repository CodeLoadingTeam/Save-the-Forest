public class Person {
	
	///////////////////////////////////////////////////////////////////#1/
	
	public double x, y, ySpeed; // x, y = coordenada/posição, vy = velocidade no y
	public static double G = 0; // standard (to flappy bird) is: 1200
	
	public Hitbox box;
	public Timer sprite_timer;
	
	public int sprite_state = 0;
	
	public int [] sprite_states = {0, 1, 2, 3, 4, 1}; 
	
	public int sentido = 1;
	public int[] sprites_x = {26, 118, 206, 298, 390}; // one: 26 / two: 118 / three: 206 / four: 298/ five: 390 
	public int sprites_y = 100;
	public int[] leftSprites_x = {20, 107, 205, 290, 385};
    public int leftSprites_y = 11;
		
	///////////////////////////////////////////////////////////////////#2/
	
	public Person(double x, double y){
		this.x = x;
		this.y = y;
		this.ySpeed = 0;
		this.box = new Hitbox(x, y, x+38, y+68);
		sprite_timer = new Timer(0.1, true, mudasprite());
	}
	
	///////////////////////////////////////////////////////////////////#3/
	
	private Acao mudasprite() {
		return new Acao() {
			public void executa() {
				sprite_state++;
				sprite_state %= sprite_states.length;
			}
		};
	}
	
	public void movimento(int sentido) {
		
		this.sentido = sentido;
		
		switch (sentido) {
		case 0: // Esquerda
			
			if (x >= 0) {
				x -= 10.5;
			}
			break;
			
		case 1: // Direita
			
			if (x <= 288 - 38.51) {
				x += 10.5;
			}
			break;
		}
	}
	
	public void update(double dt) {
//		ySpeed += G*dt;
//		y += ySpeed*dt;
		this.box.mover(x, y, x+38, y+68);
	}
	
	public void updateSprite(double dt) {
		sprite_timer.tique(dt);
	}
	
	///////////////////////////////////////////////////////////////////#4/
	
	public void drawItself(Tela t) {
//		System.out.println(sprite_states[sprite_state]);
		
		if (sentido == 1) {
			t.imagem("obj/person.png", sprites_x[sprite_states[sprite_state]], sprites_y, 38, 68, Math.atan(ySpeed/200), x, y);
		} else {
			t.imagem("obj/person.png", leftSprites_x[sprite_states[sprite_state]], leftSprites_y, 38, 68, Math.atan(ySpeed/200), x, y);
		}
		
		
	}
	
}
