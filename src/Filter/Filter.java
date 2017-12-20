package Filter;

public interface Filter{
    public void drawFilter(double[] x, double[] y);
    public void drawFilter();
    public float map(float value, float istart, float istop, float ostart, float ostop);


}