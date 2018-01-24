package Filter;

import Mp3Player.PlayerFassade;
import ddf.minim.analysis.FFT;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpiralFilter extends Filter {
    private PlayerFassade playerFassade;
    private GraphicsContext gc;
    private FFT fft;
    public SpiralFilter(String name, PlayerFassade playerFassade, GraphicsContext gc){
        super(name);
        this.playerFassade = playerFassade;
        this.gc=gc;
    }
    @Override
    public void drawFilter() {

        fft = new FFT(playerFassade.getAudio().bufferSize(), playerFassade.getAudio().sampleRate());
        fft.forward(playerFassade.getAudio().mix);
        double r;
        double points = (fft.specSize()/2)-1;
        double slice = 2 * Math.PI / points;

        float spread = map(450, 0, (float) points, 1, 21.5f);
        for (int i = 0; i < points; i += spread) {
            float rgb = map(fft.getFreq(i), 0, 256, 0, 360) * 2;
            gc.setStroke(Color.hsb(rgb, 1, 1, 1));
            gc.stroke();
        }

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
        int j=0;
        int k=0;

        for(int i=0;i<points*2;i++){
            double midx =  gc.getCanvas().getWidth()/2;
            double midy =  gc.getCanvas().getHeight()/2;
            r = gc.getCanvas().getHeight()/6;
            double x = midx + r * Math.cos(slice*i);
            double y = midy + r * Math.sin(slice*i);

            double buffer = newBand[i];
            double newX = x + (buffer*i/5 * Math.cos(slice*i));
            double newY = y + (buffer*i/5 * Math.sin(slice*i));
            if(i>points){
                buffer = newBand[j];
                newX = newX + (buffer*j/5 * Math.cos(slice*i));
                newY = newY + (buffer*j/5 * Math.sin(slice*i));
                j++;
            }
            if(j>points){
                buffer = newBand[k];
                newX = newX + (buffer*k/5 * Math.cos(slice*i));
                newY = newY + (buffer*k/5 * Math.sin(slice*i));
                k++;
            }


            gc.fillRect(newX, newY, (buffer > 5) ? buffer /5 : buffer, (buffer > 5) ? buffer /5 : buffer);

        }
    }
}
