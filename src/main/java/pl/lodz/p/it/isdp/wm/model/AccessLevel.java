package pl.lodz.p.it.isdp.wm.model;

public enum AccessLevel {

    ACCOUNT(AccessLevelKeys.ACCOUNT_KEY),
    NEWREGISTERED(AccessLevelKeys.NEWREGISTERED_KEY),
    OFFICE(AccessLevelKeys.OFFICE_KEY),
    WAREHOUSE(AccessLevelKeys.WAREHOUSE_KEY),
    ADMINISTRATION(AccessLevelKeys.ADMINISTRATION_KEY);

    private AccessLevel(final String key) {
        this.accessLevelKey = key;
    }
    private String accessLevelKey;
    private String accessLevelI18NValue;

    public String getAccessLevelKey() {
        return accessLevelKey;
    }

    public String getAccessLevelI18NValue() {
        return accessLevelI18NValue;
    }

    public void setAccessLevelI18NValue(String accessLevelI18NValue) {
        this.accessLevelI18NValue = accessLevelI18NValue;
    }

    public static class AccessLevelKeys {

        public static final String NEWREGISTERED_KEY = "access.level.newregistered";
        public static final String ACCOUNT_KEY = "access.level.account";
        public static final String ADMINISTRATION_KEY = "access.level.administration";
        public static final String OFFICE_KEY = "access.level.office";
        public static final String WAREHOUSE_KEY = "access.level.warehouse";
    }
}
