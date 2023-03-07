import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.TreeSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Canvas;

/**
 * Motor do jogo, gerencia a parte gráfica e os eventos
 */
public class Motor{
	
	///////////////////////////////////////////////////////////////////////////////////////////////#1/
    public Jogo jogo;
    public BufferStrategy strategy;
    public TreeSet<String> keySet = new TreeSet<String>();
    
    ///////////////////////////////////////////////////////////////////////////////////////////////#2/
    public Motor(Jogo j) {
    	
        jogo = j;
        Canvas canvas = new Canvas();
        JFrame container = new JFrame(jogo.getTitulo());
        JPanel panel = (JPanel) container.getContentPane();
        
        panel.setPreferredSize( new Dimension( jogo.getLargura(), jogo.getAltura() ) );
        panel.setLayout(null);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        Rectangle bounds = gs[gs.length-1].getDefaultConfiguration().getBounds();
        
        container.setResizable(false);
        container.setBounds(bounds.x + (bounds.width - jogo.getLargura())/2,
                            bounds.y + (bounds.height - jogo.getAltura())/2,
                            jogo.getLargura(), jogo.getAltura());
        
        canvas.setBounds(0, 0, jogo.getLargura(), jogo.getAltura());
        panel.add(canvas);        
        canvas.setIgnoreRepaint(true);
        container.pack();
        container.setVisible(true);
        
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent evt) {
                keySet.add(keyString(evt));
            }
            @Override
            public void keyReleased(KeyEvent evt) {
                keySet.remove(keyString(evt));
            }
            @Override
            public void keyTyped(KeyEvent evt) {
                jogo.tecla(keyString(evt));
            }
        });
        
        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        mainLoop();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////#3/
    private void mainLoop() {
    	
        Timer t = new Timer(5, new ActionListener() {
            public long t0;
            
            public void actionPerformed(ActionEvent evt) {
                long t1 = System.currentTimeMillis();
                if(t0 == 0) t0 = t1;
                
                if(t1 > t0) {
                	
                    double dt = (t1 - t0) / 1000.0;
                    t0 = t1;
                    jogo.tique(keySet, dt);     
                    Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
                    g.setColor(Color.black);
                    g.fillRect(0, 0, jogo.getLargura(), jogo.getAltura());
                    jogo.desenhar(new Tela(g));
                    strategy.show();
                }
            }
        });
            
        t.start();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////#4/
    private static String keyString(KeyEvent evt) {
    	
        if(evt.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
            return String.valueOf(evt.getKeyChar()).toLowerCase();
            
        } else {
            switch (evt.getKeyCode()) {
            case KeyEvent.VK_ALT: return "alt";
            case KeyEvent.VK_CONTROL: return "control";
            case KeyEvent.VK_SHIFT: return "shift";
            case KeyEvent.VK_LEFT: return "left";
            case KeyEvent.VK_RIGHT: return "right";
            case KeyEvent.VK_UP: return "up";
            case KeyEvent.VK_DOWN: return "down";
            case KeyEvent.VK_ENTER: return "enter";
            case KeyEvent.VK_DELETE: return "delete";
            case KeyEvent.VK_TAB: return "tab";
            case KeyEvent.VK_WINDOWS: return "windows";
            case KeyEvent.VK_BACK_SPACE: return "backspace";
            case KeyEvent.VK_ALT_GRAPH: return "altgr";
            case KeyEvent.VK_F1: return "F1";
            case KeyEvent.VK_F2: return "F2";
            case KeyEvent.VK_F3: return "F3";
            case KeyEvent.VK_F4: return "F4";
            case KeyEvent.VK_F5: return "F5";
            case KeyEvent.VK_F6: return "F6";
            case KeyEvent.VK_F7: return "F7";
            case KeyEvent.VK_F8: return "F8";
            case KeyEvent.VK_F9: return "F9";
            case KeyEvent.VK_F10: return "F10";
            case KeyEvent.VK_F11: return "F11";
            case KeyEvent.VK_F12: return "F12";
            default: return "";
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////#5/
    public static void tocar(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
        } catch(Exception e) {
        }
    }

}
