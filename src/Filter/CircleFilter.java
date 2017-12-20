package Filter;

import Testing.Controller;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleFilter implements Filter{

    private Controller controller;
    private GraphicsContext gc;
    private FFT fft;


    public CircleFilter(Controller controller, GraphicsContext gc){
        this.gc = gc;
        this.controller=controller;
    }

    @Override
    public void drawFilter(double[] oldX,double[] oldY){

        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        double r;
        double points = fft.specSize();
        double slice = 2 * Math.PI / points;
        double midx =  gc.getCanvas().getWidth()/2;
        double midy =  gc.getCanvas().getHeight()/2;
        float spread = map(450, 0, (float) points, 1, 21.5f);
        for (int i = 0; i < points; i += spread) {
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
        }



        for (int i = 0; i < points; i++) {

            r = map(fft.getBand(i), 0, 1, 250, 255);
            double x2 = midx - r * Math.cos(slice*i);
            double y2 = midy - r * Math.sin(slice*i);
            gc.strokeLine(oldX[0], oldY[0], x2, y2);
            oldX[0] = x2;
            oldY[0] = y2;


        }
    }

    @Override
    public void drawFilter() {

    }


    @Override
    public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }





}
