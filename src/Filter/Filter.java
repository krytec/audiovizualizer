package Filter;

/**
 * @author Florian Ortmann, Lea Haugrund
 * Abstrakte Filter Klasse für Filter zu Visualisierung von Musik
 */
public abstract class  Filter{

     private String name;

    /**
     * Jeder Filter besitzt einen Namen
     * @param name Name des Filters
     */
     public Filter(String name){this.name=name;}

     /**
     * Methode zum zeichnen des Filters
     */
     public abstract void drawFilter();

    /**
     * Map Funktion zum Skalieren von Werten, wird zum umrechnen von beispielsweise Radius benötigt
     * Skaliert value von istart bis istop in einer Spanne von ostart bis ostop
     * @param value der Wert der skaliert werden soll
     * @param istart Startwert des Skalierens
     * @param istop Endwert des Skalierens
     * @param ostart Startwert der Range
     * @param ostop Endwert der Range
     * @return
     */
     public final float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
     }

    /**
     * toString Methode des Objects
     * @return Name des Objects als String
     */
     @Override
     public String toString(){
         return this.name;
     }





}