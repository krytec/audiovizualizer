package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class BackgroundFilter extends Filter {
    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;
    private BeatDetect beatDetect;

    public BackgroundFilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }

    @Override
    public void drawFilter() {
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);
        beatDetect = new BeatDetect();

        double midx = gc.getCanvas().getWidth()/2;
        double midy = gc.getCanvas().getHeight()/2;
        double buffer =0;
        double lowspec,midspec,highspec;
        for (int i = 0;i<fft.specSize();i++){
           /** float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke(); **/
            buffer += fft.getBand(i);
        }
        lowspec=buffer*0.2;
        midspec=buffer*0.5;
        highspec=buffer*0.7;

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
