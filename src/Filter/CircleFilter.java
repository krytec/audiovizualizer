package Filter;

import Testing.Controller;
import ddf.minim.analysis.BeatDetect;
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
        oldx =  oldX[0];
        oldy =  oldY[0];
        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        BeatDetect beatDetect = new BeatDetect();
        beatDetect.detect(controller.getAudio().mix);
        double r;
        double points = fft.specSize();
        double slice = 2 * Math.PI / points;

        float spread = map(450, 0, (float) points, 1, 21.5f);
        for (int i = 0; i < points; i += spread) {
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
        }



        /** Kreis!
         *
         */
        for (int i = 0; i < points; i++) {
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = map(fft.getBand(i), 0, 1, (float) gc.getCanvas().getHeight()/4, (float) gc.getCanvas().getHeight()/4 + 5);
            double x2 = midx - r * Math.cos(slice*i);
            double y2 = midy - r * Math.sin(slice*i);
            if(beatDetect.isSnare() || beatDetect.isKick() || beatDetect.isHat()){
                gc.setFill(Color.WHITE);
                gc.fill();
                gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
            }
            if(beatDetect.isSnare()){
                x2 =midx + r * Math.cos(slice*i);
                y2 = midy - r * Math.sin(slice*i);
                gc.strokeLine(oldX[1], oldY[1], x2, y2);
                oldX[1] = x2;
                oldY[1] = y2;
            }
            else if
                    (beatDetect.isHat()){
                x2 =midx - r * Math.cos(slice*i);
                y2 = midy + r * Math.sin(slice*i);
                gc.strokeLine(oldX[2], oldY[2], x2, y2);
                oldX[2] = x2;
                oldY[2] = y2;
            }
            else if(beatDetect.isKick()){
                x2 =midx + r * Math.cos(slice*i);
                y2 = midy + r * Math.sin(slice*i);
                gc.strokeLine(oldX[3], oldY[3], x2, y2);
                oldX[3] = x2;
                oldY[3] = y2;
            }
            else {
                gc.strokeLine(oldX[0], oldY[0], x2, y2);
                oldX[0] = x2;
                oldY[0] = y2;
            }

        }
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
