package Filter;

import Testing.Controller;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LineFilter extends Filter {

    private Controller controller;
    private GraphicsContext gc;
    private FFT fft;

    public LineFilter(Controller controller, GraphicsContext gc){
        this.controller=controller;
        this.gc=gc;
    }

    public void drawFilter(){
        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        double points = fft.specSize();
        double midx =  gc.getCanvas().getWidth()/2;
        double midy =  gc.getCanvas().getHeight()/2;
        float spread = map(450, 0, (float) points, 1, 21.5f);
        for (int i = 0; i < points; i += spread) {
            double buffer = Math.abs(fft.getBand(i));
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;

            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
            gc.strokeOval(i + midx-250 - (7 / 2), midy+340 - (buffer / 2), 7, buffer);
            gc.setFill(Color.hsb(rgb, 1, 1));
            gc.fill();
            gc.fillOval(i + midx-250 - (7 / 2), midy+340 - (buffer / 2), 7, buffer);
        }
    }

}
