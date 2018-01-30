package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Florian Ortmann, Lea Haugrund
 * Visualisiert Musik in Form von Linien
 */
public class LineFilter extends Filter {

    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;

    /**
     * Constructor für einen LineFilter
     * @param name name des Filters
     * @param playerFassade Zur Ansteuerung des Mp3Players via Minim
     * @param gc Graphiccontext des Canvas
     */
    public LineFilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }

    /**
     * drawFilter Methode zum zeichnen des Filters
     */
    @Override
    public void drawFilter(){
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.window(FFT.LANCZOS);
        fft.forward(playerFassade.getAudio().mix);
        double points = fft.specSize();
        double width = gc.getCanvas().getWidth()/2;
        // damit die Linie sich an das Fenster anpasst
        points = points> width? width-10:points;
        // damit das Programm nicht abstürtzt wenns zu klein wird mit der Map
        width = width>81.5?width:81.5;

        double x = gc.getCanvas().getWidth()/8;
        double y = gc.getCanvas().getHeight()/2-gc.getCanvas().getHeight()/10;

        //Spreaded die Linien um einen kleinen Wert
        float spread = map(200, 0,  width<points? (float)width:(float)points, 1, (float) x/8);




        for (int i = 0; i < points; i+=spread) {

            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            double buffer = Math.log( 2 * fft.getBand(i)/fft.timeSize() );
            buffer=map((float) buffer,0,-150,0,(float) gc.getCanvas().getHeight());
            gc.strokeLine(i+midx-x,midy+y,i+midx-x,midy+y-Math.abs(buffer));



        }



    }

}
