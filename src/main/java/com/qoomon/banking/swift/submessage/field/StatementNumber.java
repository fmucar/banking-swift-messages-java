package com.qoomon.banking.swift.submessage.field;

import com.qoomon.banking.Lists;
import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.notation.FieldNotationParseException;
import com.qoomon.banking.swift.notation.SwiftNotation;

import java.util.List;

/**
 * <b>Statement Number</b>
 * <p>
 * <b>Field Tag</b> :28C:
 * <p>
 * <b>Format</b> 5n[/5n]
 * <p>
 * <b>SubFields</b>
 * <pre>
 * 1: 5n    - Statement Number
 * 2: [/5n] - Sequence Number
 * </pre>
 */
public class StatementNumber implements SwiftField {

    public static final String FIELD_TAG_28C = "28C";

    public static final SwiftNotation SWIFT_NOTATION = new SwiftNotation("5n[/5n]");

    private final String statementNumber;

    private final String sequenceNumber;


    public StatementNumber(String statementNumber, String sequenceNumber) {

        Preconditions.checkArgument(statementNumber != null, "statementNumber can't be null");
        Preconditions.checkArgument(statementNumber.length() >= 1 && statementNumber.length() <= 5, "expected statementNumber length to be between 1 and 5, but was " + statementNumber.length());
        Preconditions.checkArgument(sequenceNumber == null || sequenceNumber.length() >= 1 && sequenceNumber.length() <= 5, "expected sequenceNumber length to be between 1 and 5, but was " + (sequenceNumber != null ? sequenceNumber.length() : null));

        this.statementNumber = statementNumber;
        this.sequenceNumber = sequenceNumber;
    }

    public static StatementNumber of(GeneralField field) throws FieldNotationParseException {
        Preconditions.checkArgument(field.getTag().equals(FIELD_TAG_28C), "unexpected field tag '%s'", field.getTag());

        List<String> subFields = SWIFT_NOTATION.parse(field.getContent());

        String value = subFields.get(0);
        String sequenceNumber = subFields.get(1);

        return new StatementNumber(value, sequenceNumber);
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    @Override
    public String getTag() {
        return FIELD_TAG_28C;
    }

    @Override
    public String getContent() {
        try {
            return SWIFT_NOTATION.render(Lists.newArrayList(statementNumber, sequenceNumber));
        } catch (FieldNotationParseException e) {
            throw new IllegalStateException("Invalid field values within " + getClass().getSimpleName() + " instance", e);
        }
    }
}
