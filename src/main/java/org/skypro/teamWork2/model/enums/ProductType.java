package org.skypro.teamWork2.model.enums;

public enum ProductType {
    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    SAVING("SAVING"),
    INVEST("INVEST");

    private final String dbValue;

    ProductType(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }
}
