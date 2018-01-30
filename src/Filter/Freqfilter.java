package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Florian Ortmann , Lea Haugrund
 * Visualisiert die Musik anhand ihrer Frequenz mit Quadraten
 */
public class Freqfilter extends Filter {

    private PlayerFassade playerFassade;
    private FFT fft;
    private GraphicsContext gc;
    private double r;

    /**
     * Constructor für den FrequenzFilter
     * @param name Name des Filters
     * @param playerFassade Zur Ansteuerung des Mp3Players via Minim
     * @param gc Graphiccontext des Canvas
     */
    public Freqfilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }


    /**
     * drawFilter Methode zum Zeichnen des Filters
     */
    @Override
    public void drawFilter() {
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);
        double points = fft.specSize();

        double slice = 2*Math.PI/points;

        //Spreaded die Quadrate
        float spread = map(450, 0, (float) points, 1, 21.5f);

        for (int i = 0; i < points; i += spread) {

            //Radius wird dynamisch berechnet, skaliert von 0-1 in Range der Höhe des Fensters/4
            r = map(fft.getFreq(i), 0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);

            double angle = slice * i;
            double buffer = 0;
            for (int j = 0; j < fft.specSize(); j++) {
                buffer += fft.getFreq(i) * 20;
            }
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            buffer = buffer / fft.specSize();
            double x = midx + r * Math.cos(angle);
            double y = midy + r * Math.sin(angle);
            double newX = x + (buffer/5 * Math.cos(angle));
            double newY = y + (buffer/5 * Math.sin(angle));
            gc.fill();
            gc.fillRect(newX, newY, (buffer > 5) ? buffer /5 : buffer, (buffer > 5) ? buffer /5 : buffer);




        }
    }


}
