package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.Controller;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorFilter extends Filter {
    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;
    private SimpleFloatProperty rgb = new SimpleFloatProperty();

    public ColorFilter(String name,PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade=playerFassade;
        this.gc=gc;
    }

    @Override
    public void drawFilter() {
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);



        for (int i = 0;i<fft.specSize();i++){
             rgb.set( map(fft.getFreq(i), 0, 256, 0, 360) * 2);
             gc.setStroke(Color.hsb(rgb.get(), 1, 1, 1));
             gc.setFill(Color.hsb(rgb.get(),1,1,1));
        }
    }

    public SimpleFloatProperty rgbProperty() {
        return rgb;
    }
}
