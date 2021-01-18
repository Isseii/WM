package pl.lodz.p.it.isdp.wm.model;

public enum LocationType {

    SHELF1(LocationTypeKeys.SHELF1_KEY, 60000),
    SHELF2(LocationTypeKeys.SHELF2_KEY, 30000),
    SHELF3(LocationTypeKeys.SHELF3_KEY, 20000),
    SHELF4(LocationTypeKeys.SHELF4_KEY, 15000);

    private LocationType(final String key, final int weightLimit) {
        this.locationTypeKey = key;
        this.weightLimit = weightLimit;
    }

    private LocationType(String locationTypeKey) {
        this.locationTypeKey = locationTypeKey;
    }

    private String locationTypeKey;
    private String loactionTypeI18NValue;

    private int weightLimit;

    public String getLocationTypeKey() {
        return locationTypeKey;
    }

    public String getLoactionTypeI18NValue() {
        return loactionTypeI18NValue;
    }

    public void setLoactionTypeI18NValue(String loactionTypeI18NValue) {
        this.loactionTypeI18NValue = loactionTypeI18NValue;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public static class LocationTypeKeys {

        public static final String SHELF1_KEY = "location.type.shelf.1";
        public static final String SHELF2_KEY = "location.type.shelf.2";
        public static final String SHELF3_KEY = "location.type.shelf.3";
        public static final String SHELF4_KEY = "location.type.shelf.4";
    }
}
