package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.Controller;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Florian Ortmann, Lea Haugrund
 * Farbfilter der Anhand der Musik berechnet wird
 */
public class ColorFilter extends Filter {
    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;
    private float rgb = 0;

    /**
     * Constructor für einen ColorFilter
     * @param name Name des Filters
     * @param playerFassade Zur Ansteuerung des Mp3Players via Minim
     * @param gc Graphiccontext des Canvas
     */
    public ColorFilter(String name,PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade=playerFassade;
        this.gc=gc;
    }

    /**
     * "Zeichnet den Filter" , legt die Farbe für Stroke und Fill fest damit die anderen Filter Farbig dargestellt werden
     */
    @Override
    public void drawFilter() {
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);



        for (int i = 0;i<fft.specSize();i++){
            rgb =( map(fft.getFreq(i), 0, 256, 0, 360) * 2);
            gc.setFill(Color.hsb(rgb,1,1,1));
            gc.setStroke(Color.hsb(rgb,1,1,1));
        }
    }


}
