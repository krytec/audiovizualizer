package Filter;

import Main.Controller;
import ddf.minim.analysis.BeatDetect;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BackgroundFilter extends Filter {
    private Controller controller;
    private GraphicsContext gc;
    private FFT fft;
    private BeatDetect beatDetect;

    public BackgroundFilter(Controller controller,GraphicsContext gc){
        this.controller=controller;
        this.gc=gc;
    }

    @Override
    public void drawFilter() {
        fft = new FFT(controller.getAudio().bufferSize(), controller.getAudio().sampleRate());
        fft.forward(controller.getAudio().mix);
        beatDetect = new BeatDetect();

        double midx = gc.getCanvas().getWidth()/2;
        double midy = gc.getCanvas().getHeight()/2;
        double buffer =0;
        double lowspec,midspec,highspec;
        for (int i = 0;i<fft.specSize();i++){
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
            buffer += fft.getBand(i);


        }
        lowspec=buffer*0.2;
        midspec=buffer*0.5;
        highspec=buffer*0.7;
        if(beatDetect.isKick()){
            gc.setFill(Color.WHITE);
            gc.fill();
            gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        }
        if(beatDetect.isSnare()){
            gc.setFill(Color.WHITE);
            gc.fill();
            gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        }
        if(beatDetect.isHat()){
            gc.setFill(Color.WHITE);
            gc.fill();
            gc.fillRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        }
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
