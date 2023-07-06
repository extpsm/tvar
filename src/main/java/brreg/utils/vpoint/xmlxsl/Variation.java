package brreg.utils.vpoint.xmlxsl;

import java.util.ArrayList;
import java.util.List;


public class Variation {
    private String variationName;
    private List<String> variants = new ArrayList<String>();

    public Variation(String name) {
        variationName = name;
    }

    public String getVariationName() {
        return variationName;
    }

    public List<String> getVariants() {
        return variants;
    }

}
