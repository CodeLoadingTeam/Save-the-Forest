public class Timer {
    double tempo;
    double limite;
    Acao acao;
    boolean repete;
    boolean fim;
    
    public Timer(double limite, boolean repete, Acao acao) {
        this.limite = limite;
        this.acao = acao;
        this.repete = repete;
    }

    public void tique(double dt) {
    	
        if(fim) return;
        
        tempo += dt;
        
        if(tempo > limite) {
            acao.executa();
            
            if(repete) {
                tempo -= limite;
                
            } else {
                fim = true;
            }
        }
    }
}