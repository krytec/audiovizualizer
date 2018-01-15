package Filter;

import Main.Controller;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
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
        double width = gc.getCanvas().getWidth()/2;
        width = width>111?width:112;
        double x = gc.getCanvas().getWidth()/6;
        double y = gc.getCanvas().getHeight()/2-gc.getCanvas().getHeight()/10;
        float spread = map(200, 0,  width<points? (float)width:(float)points, 1, (float) x/20);

        for (int i = 0; i < (width<points/2? (float)width:(float)points/2); i+=spread) {

            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            double buffer = Math.abs(fft.getBand(i));
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;

            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
            gc.strokeOval(i + midx-x, midy+y - (buffer / 2), 7, buffer);
            gc.setFill(Color.hsb(rgb, 1, 1));
            gc.fill();
            gc.fillOval(i + midx-x, midy+y- (buffer/2), 7, buffer);
        }

    }

}
