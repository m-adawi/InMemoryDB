package DB.Attributes;

public class GPA extends DoubleAttribute {

    public GPA() {
        value = 0.0;
    }

    public GPA(double value) {
        setValue(value);
    }

    public GPA(String strVal) {
        super(strVal);
    }

    @Override
    public String getStrValue() {
        // return the string in the format x.xx
        return String.format("%1.2f", value);
    }
}
