package Filter;

public abstract class  Filter{
     private String name;
     public Filter(String name){this.name=name;}
     public abstract void drawFilter();

     public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
     }

     @Override
     public String toString(){
         return this.name;
     }





}