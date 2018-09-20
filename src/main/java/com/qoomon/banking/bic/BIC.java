package com.qoomon.banking.bic;


import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.notation.FieldNotationParseException;
import com.qoomon.banking.swift.notation.SwiftNotation;

import java.util.List;

/**
 * <b>Business Identifier Codes</b>
 * <p>
 * <b>Format</b> 4!a2!a2!c[3!c]
 * <p>
 * <b>SubFields</b>
 * <pre>
 * 1: 4!a   - Institution Code
 * 2: 2!a   - Country Code
 * 3: 2!c   - Location Code
 * 4: [3!c] - Branch Code
 * </pre>
 *
 * @see <a href="http://www.sepaforcorporates.com/single-euro-payments-area/sepa-iban-number-the-definitive-guide/">http://www.sepaforcorporates.com/single-euro-payments-area/sepa-iban-number-the-definitive-guide/</a>
 * @see <a href="https://en.wikipedia.org/wiki/ISO_9362">https://en.wikipedia.org/wiki/ISO_9362</a>
 */
public class BIC {

    public static SwiftNotation NOTATION = new SwiftNotation("4!a2!a2!c[3!c]");

    private final String institutionCode;
    private final String countryCode;
    private final String locationCode;
    private final String branchCode;

    public BIC(String institutionCode, String countryCode, String locationCode, String branchCode) {

        Preconditions.checkArgument(institutionCode != null, "institutionCode can't be null");
        Preconditions.checkArgument(countryCode != null, "countryCode can't be null");
        Preconditions.checkArgument(locationCode != null, "locationCode can't be null");

        this.institutionCode = institutionCode;
        this.countryCode = countryCode;
        this.locationCode = locationCode;
        this.branchCode = branchCode;

        String bicText = this.institutionCode + this.countryCode + this.locationCode + (this.branchCode == null ? "" : this.branchCode);
        ensureValid(bicText);
    }

    public static BIC of(String value) {
        try {
            List<String> subfieldList = NOTATION.parse(value);
            String institutionCode = subfieldList.get(0);
            String countryCode = subfieldList.get(1);
            String locationCode = subfieldList.get(2);
            String branchCode = subfieldList.get(3);
            return new BIC(institutionCode, countryCode, locationCode, branchCode);
        } catch (FieldNotationParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void ensureValid(String bicText) {
        Preconditions.checkArgument(bicText != null, "bic can't be null");
        try {
            NOTATION.parse(bicText);
        } catch (FieldNotationParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public String getBranchCode() {
        return branchCode;
    }
}
