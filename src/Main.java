import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Main implements Jogo{
	
	///////////////////////////////////////////////////////////////////////////////////////////////#1/
	public Person person;
	
	public Random gerador = new Random();
	public ArrayList<Objects> obj = new ArrayList<Objects>();
	public int chooseObj, lastObj, xRandom;
	
	public Timer objTimer; 
	public Timer background;
	public Hitbox groundbox; 
	
	 
	public int record = 0;
	public int frequencia = 3;
	public double gravity = 25;
	public ScoreNumber scorenumber;
	
	public Timer auxtimer;
		
	public double scenario_offset = 0;
	public int game_state = 0; //[0->Start Screen] [1->Get Ready] [2->Game] [3->Game Over]
	
	
	public int falha = 0; // ao deixar obj cair no chão
	public int fase = 0;
	public int getImg = 0;
	public int vida = 0;
			   				   
	public String [][] images = {

							{"found_1/Background 1.1.png", // to change images
					   		 "found_1/Background 2.1.png",
					   		 "found_1/Background 3.1.png"},		
							
							{"found_2/Background 1.2.png", // to change images
						   	 "found_2/Background 2.2.png",
						     "found_2/Background 3.2.png"},	
							
							{"found_3/Background 1.3.png", // to change images
							 "found_3/Background 2.3.png",
							 "found_3/Background 3.3.png"},	
	};
	
	///////////////////////////////////////////////////////////////////////////////////////////////#2/
	public Main() { // constructor method
		person = new Person(50, getAltura() - 182);
		scorenumber = new ScoreNumber(0);
		
		groundbox = new Hitbox(0, getAltura()-119, getLargura(), getAltura());
		
		background = new Timer (0.1, true, mudaFundo());
		objTimer = new Timer(2.75, true, addObject());
		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#3/	
	public String getTitulo() {
		return "Save the Forest";
	}
	
	public String getAuthor() {
		return "Equipe | (APS)";
	}
	
	public int getLargura() {
		return 288;
	}
	
	public int getAltura() {
		return 512;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#4/
	private Acao addObject(){
		return new Acao(){
			public void executa(){
				
				while (chooseObj == lastObj) {
					chooseObj = gerador.nextInt(10);
					xRandom = gerador.nextInt(245);
				}
				
				obj.add( new Objects( xRandom + 1, 0, chooseObj, gravity) );
				lastObj = chooseObj;				
			}		
		};
	}
	
	private Acao proxCena() {
		return new Acao(){
			public void executa(){
				game_state += 1;
				game_state = game_state % 4;
			}
		};
	}
	
	public Acao mudaFundo() {
		return new Acao() {
			public void executa() {
				getImg++;
				getImg %= 3;	
			}
		};
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#5/
	public void gameOver() {
		obj = new ArrayList<Objects>();
		person = new Person(50, getAltura() / 4);
		person = new Person(50, getAltura() - 182);
		proxCena().executa();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#6/
	public void tecla(String c) { // == click
	
		switch (game_state){
			case 0:
				
				if(c.equals(" ")){
					auxtimer = new Timer(1.6, false, proxCena());
					proxCena().executa();
				}
				
				break;
				
			case 1:	
				break;
				
			case 2: // mexer aqui
				
				if(c.equals("d")) {

					person.movimento(1); // direita (1)
				} 
				
				if (c.equals("a")) {
					
					person.movimento(0); // esquerda (0)
				}
				
				break;
				
			case 3:
				
				if(c.equals(" ")){
					scorenumber.setScore(0);
					proxCena().executa();
				}
				
				break;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#7/	
	public void tique(Set<String> keys, double dt) { // == frames
		
		background.tique(dt / 2);
		
		switch (game_state) { 
			case 0: //Main Screen
				fase = 0;
				break;
				
			case 1: //Get Ready
				auxtimer.tique(dt);
				person.updateSprite(dt);
				break;
			
			case 2: //Game Screen
				objTimer.tique(dt); // criar outra variavel para determinar velocidade dessa variável
				person.update(dt);
				person.updateSprite(dt);			
				
				
				for (Objects ob: obj) { // mexer aqui 
					ob.tique(dt);
					
				}
				
				if (obj.size() > 0 && obj.get(0).boxObj.intersecao(person.box) != 0) {
			
				    scorenumber.modifyScore(1);
				    obj.remove( 0 );
				}
							
				if (obj.size() > 0 && obj.get(0).boxObj.intersecao(groundbox) != 0) { // quando obj encostar no chão
					obj.remove(0);
					falha++;
					
					if (falha == 3) { // p/ perder uma vida
						fase++;
						vida++;
						falha = 0;			
					}
				}
						
				if (fase > 2) {
					fase = 2;
					
					if (scorenumber.getScore() > ScoreNumber.record) {
						ScoreNumber.record = scorenumber.getScore();
					}
					
					gameOver();
					return;
				}
				
				if (scorenumber.getScore() == frequencia) { // para velocidade do jogo
					frequencia += 3;
					gravity += 50;
				}
				
				break;
				
			case 3: //Game Over Screen
				break;
			}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#8/
	public void desenhar(Tela t) {
		//Draw background no matter what
		
		t.imagem(images[fase][getImg], 0, 0, 288, 512, 0,(int) 0, 0);	

		for ( Objects obj : obj) {
			obj.drawItself(t);
		}
		
		switch (game_state) {
			case 0:
				t.texto("Pressione espaço", getLargura()/10, getAltura()/2 - 16, 28, Cor.BRANCO);
				break;
				
			case 1:
				person.drawItself(t);
				t.imagem("hud.png",292,442,174,44, 0, getLargura()/5, getAltura()/3);
				scorenumber.drawScore(t, 5, 5);
				break;
				
			case 2:
				scorenumber.drawScore(t, 5, 5);
				if (vida == 0) {
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.32, 3);
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.20, 3);
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.10, 3);
				}
					
				if (vida == 1)	{
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.20, 3);
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.10, 3);
				}
				
				if (vida == 2) {
					t.imagem("obj/heart.png",0,0,18,15, 0, getLargura()/1.10, 3);
				}
				person.drawItself(t);
				break;
				
			case 3:
				t.imagem("hud.png", 292, 398, 188, 38, 0, getLargura()/2 - 188/2, 100);
				t.imagem("hud.png", 292, 116, 226, 116, 0, getLargura()/2 - 226/2, getAltura()/2 - 116/2);
				vida=0;
				scorenumber.drawScore(t, getLargura()/2 + 50, getAltura()/2 - 41);
				scorenumber.drawRecord(t, getLargura()/2 + 50, getAltura()/2);
				break;
		}		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////#9/
	public static void main(String[] args) {
		roda();
	}
	
	private static void roda() {
		new Motor(new Main());
	}

}
