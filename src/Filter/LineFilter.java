package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineFilter extends Filter {

    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;

    public LineFilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }

    public void drawFilter(){
        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);
        double points = fft.specSize();
        double width = gc.getCanvas().getWidth()/2;

        width = width>81.5?width:81.5;

        double x = gc.getCanvas().getWidth()/8;
        double y = gc.getCanvas().getHeight()/2-gc.getCanvas().getHeight()/10;
        float spread = map(200, 0,  width<points? (float)width:(float)points, 1, (float) x/8);
        float[] band = new float[fft.specSize()];

        for(int i = 0;i< fft.specSize();i++){
            band[i]=fft.getBand(i);

        }

        for(int i = 0;i<fft.specSize();i++){
            float value = 0;
            if(i==0){
                value = band[i];
            }
            else if(i==fft.specSize()-1){
                value= (band[i-1]+band[i])/2;
            }
            else{
                float prev = band[i-1];
                float cur = band[i];
                float next = band[i+1];

                if(prev<=cur && cur >= next){
                    value=cur;
                }else{
                    value=(cur+ Math.max(next,prev))/2;

                }
            }
            value = Math.min(value+1,(float) gc.getCanvas().getHeight()/4);
            band[i]=value;
        }

        float[] newBand = new float[band.length];

        for(int i =0;i<newBand.length;i++){
            float value = 0;
            if(i==0){
                value = band[i];
            }
            else if(i==fft.specSize()-1){
                value= (band[i-1]+band[i])/2;
            }
            else{
                float prev = band[i-1];
                float cur = band[i];
                float next = band[i+1];

                if(prev<=cur && cur >= next){
                    value=cur;
                }else{
                    value=(cur/2) + Math.max(next,prev)/3 + Math.min(next,prev)/6;

                }
            }
            value = Math.min(value+1,(float) gc.getCanvas().getHeight()/4);
            newBand[i]=value;
        }



        for (int i = 0; i < width; i+=spread) {

            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;


                if(i < newBand.length-1) {
                    double buffer = Math.abs(newBand[i]);
                    float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;

                    gc.setStroke(Color.hsb(rgb, 1, 1, 1));
                    gc.stroke();
                    gc.strokeOval(i + midx - x, midy + y - (buffer / 2), 7, buffer);
                    gc.setFill(Color.hsb(rgb, 1, 1));
                    gc.fill();
                    gc.fillOval(i + midx - x, midy + y - (buffer / 2), 7, buffer);
                }


        }



    }

}
