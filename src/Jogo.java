public interface Jogo {
	
    String getTitulo();
    int getLargura();
    int getAltura();
    void tique(java.util.Set<String> teclas, double dt);
    void tecla(String tecla);
//    void keyPressed(KeyEvent tecla);
//    void keyRelease(KeyEvent tecla);
    void desenhar(Tela tela);
    
}