package Filter;

public abstract class  Filter{
     public abstract void drawFilter();

     public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
     }





}