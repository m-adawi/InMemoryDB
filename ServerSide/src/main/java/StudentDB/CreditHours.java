package StudentDB;

import DB.IntegerAttribute;

public class CreditHours extends IntegerAttribute {

    public CreditHours() {
    }

    public CreditHours(int value) {
        super(value);
    }

    public CreditHours(String strVal) {
        super(strVal);
    }
}
