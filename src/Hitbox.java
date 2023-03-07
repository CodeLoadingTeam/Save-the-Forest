public class Hitbox {
    // Posições
    public static int TOPO     = 1;
    public static int ESQUERDO = 2;
    public static int FUNDO    = 4;
    public static int DIREITO  = 8;
    
    // Canto superior esquerdo e
    // inferior direito
    private double x0, y0, x1, y1;

    public Hitbox(double x0, double y0, double x1, double y1) {
        if(x0 < x1) {
            this.x0 = x0;
            this.x1 = x1;
        } else {
            this.x0 = x1;
            this.x1 = x0;
        }
        if(y0 < y1) {
            this.y0 = y0;
            this.y1 = y1;
        } else {
            this.y0 = y1;
            this.y1 = y0;
        }
    }
    
    public void mover(double x0, double y0, double x1, double y1) {
        if(x0 < x1) {
            this.x0 = x0;
            this.x1 = x1;
        } else {
            this.x0 = x1;
            this.x1 = x0;
        }
        if(y0 < y1) {
            this.y0 = y0;
            this.y1 = y1;
        } else {
            this.y0 = y1;
            this.y1 = y0;
        }
    }
    
    public void mover(double dx, double dy) {
        x0 += dx;
        x1 += dx;
        y0 += dy;
        y1 += dy;
    }
    
    // Esse ret�ngulo colidiu com hb, e onde em hb?
    public int intersecao(Hitbox hb) {
        double w = ((x1-x0) + (hb.x1 - hb.x0)) / 2;
        double h = ((y1-y0) + (hb.y1 - hb.y0)) / 2;
        double dx = ((x1 + x0) - (hb.x1 + hb.x0)) / 2;
        double dy = ((y1 + y0) - (hb.y1 + hb.y0)) / 2;
        if (Math.abs(dx) <= w && Math.abs(dy) <= h) {
            double wy = w * dy; double hx = h * dx;
            if (wy > hx) {
                if (wy > -hx) return FUNDO;
                else return ESQUERDO;
            } else {
                if (wy > -hx) return DIREITO;
                else return TOPO;
            }
        }
        return 0; 
    }

}