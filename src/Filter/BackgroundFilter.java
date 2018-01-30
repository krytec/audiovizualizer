package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * @author Florian Ortmann , Lea Haugrund
 * Visualisiert Musik mithilfe von Vierecken im Hintergrund
 */
public class BackgroundFilter extends Filter {
    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;

    /**
     * Constructor für einen BackgroundFilter
     * @param name Name des Filters
     * @param playerFassade Zur Ansteuerung des Mp3Players via Minim
     * @param gc Graphiccontext des Canvas
     */
    public BackgroundFilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }

    /**
     * Zeichnet den Filter
     */
    @Override
    public void drawFilter() {
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);


        double midx = gc.getCanvas().getWidth()/2;
        double midy = gc.getCanvas().getHeight()/2;
        double buffer =0;
        double lowspec,midspec,highspec;
        for (int i = 0;i<fft.specSize();i++){
            buffer += fft.getFreq(i);
        }
        lowspec=buffer*0.01;
        midspec=buffer*0.02;
        highspec=buffer*0.03;

        //top right
        gc.strokeRect(midx-lowspec,midy,lowspec,lowspec);
        gc.strokeRect(midx-midspec,midy,midspec,midspec);
        gc.strokeRect(midx-highspec,midy,highspec,highspec);

        //down left
        gc.strokeRect(midx,midy-lowspec,lowspec,lowspec);
        gc.strokeRect(midx,midy-midspec,midspec,midspec);
        gc.strokeRect(midx,midy-highspec,highspec,highspec);
        //down right
        gc.strokeRect(midx,midy,lowspec,lowspec);
        gc.strokeRect(midx,midy,midspec,midspec);
        gc.strokeRect(midx,midy,highspec,highspec);
        //top left
        gc.strokeRect(midx-lowspec,midy-lowspec,lowspec,lowspec);
        gc.strokeRect(midx-midspec,midy-midspec,midspec,midspec);
        gc.strokeRect(midx-highspec,midy-highspec,highspec,highspec);

        gc.strokeLine(midx,midy,midx+highspec,midy+highspec);
        gc.strokeLine(midx,midy,midx-highspec,midy-highspec);
        gc.strokeLine(midx,midy,midx-highspec,midy+highspec);
        gc.strokeLine(midx,midy,midx+highspec,midy-highspec);

    }
}
