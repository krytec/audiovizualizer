package Filter;

import Main.Controller;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Freqfilter extends Filter {

    private Controller controller;
    private FFT fft;
    private GraphicsContext gc;
    private double r;

    public Freqfilter(Controller controller, GraphicsContext gc){
        this.controller=controller;
        this.gc=gc;
    }

    @Override
    public void drawFilter() {
        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        double points = fft.specSize();

        double slice = 2*Math.PI/points;
        float spread = map(450, 0, (float) points, 1, 21.5f);

        for (int i = 0; i < points; i += spread) {

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
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setFill(Color.hsb(rgb, 1, 1));
            gc.fill();
            gc.fillRect(newX, newY, (buffer > 5) ? buffer /5 : buffer, (buffer > 5) ? buffer /5 : buffer);




        }
    }


}
