package Filter;

import Main.Controller;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleFilter extends Filter{

    private Controller controller;
    private GraphicsContext gc;
    private FFT fft;
    private double oldx,oldy;


    public CircleFilter(Controller controller, GraphicsContext gc){
        this.gc = gc;
        this.controller=controller;
    }


    public void drawFilter(double[] oldX,double[] oldY){
        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        double r;
        double points = fft.specSize();
        double slice = 2 * Math.PI / points;
        int point=0;
        float spread = map(450, 0, (float) points, 1, 21.5f);
        for (int i = 0; i < points; i += spread) {
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
        }



        /** Kreis!
         *
         */
        for (int i = 0; i < points/4; i++) {
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = map((fft.getBand(i)>5) ? (float) (fft.getBand(i)*0.2):(float)(fft.getBand(i)*5),
                    0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);
            double x2 = midx - r * Math.cos(slice*i);
            double y2 = midy - r * Math.sin(slice*i);


                gc.strokeLine(oldX[0], oldY[0], x2, y2);
                oldX[0] = x2;
                oldY[0] = y2;
                point=i;

        }
        for (int i = 0; i < points/4; i++) {
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = map((fft.getBand(i)>5) ? (float) (fft.getBand(i)*0.2):(float)(fft.getBand(i)*5),
                    0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);
            double x2 = midx - r * Math.cos(slice*point);
            double y2 = midy - r * Math.sin(slice*point);
            point++;

            gc.strokeLine(oldX[0], oldY[0], x2, y2);
            oldX[0] = x2;
            oldY[0] = y2;


        }
        for (int i = 0; i < points/4; i++) {
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = map((fft.getBand(i)>5) ? (float) (fft.getBand(i)*0.2):(float)(fft.getBand(i)*5),
                    0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);
            double x2 = midx - r * Math.cos(slice*point);
            double y2 = midy - r * Math.sin(slice*point);
            point++;

            gc.strokeLine(oldX[0], oldY[0], x2, y2);
            oldX[0] = x2;
            oldY[0] = y2;


        }
        for (int i = 0; i < points/4; i++) {
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = map((fft.getBand(i)>5) ? (float) (fft.getBand(i)*0.2):(float)(fft.getBand(i)*5),
                    0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);
            double x2 = midx - r * Math.cos(slice*point);
            double y2 = midy - r * Math.sin(slice*point);
            point++;

            gc.strokeLine(oldX[0], oldY[0], x2, y2);
            oldX[0] = x2;
            oldY[0] = y2;


        }
        oldx =  oldX[0];
        oldy =  oldY[0];
    }


    @Override
    public void drawFilter() {

    }

    public double getOldx(){
        return oldx;
    }
    public double getOldy(){
        return oldy;
    }
}
