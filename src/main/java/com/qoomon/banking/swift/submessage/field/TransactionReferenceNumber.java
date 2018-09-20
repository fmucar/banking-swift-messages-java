package com.qoomon.banking.swift.submessage.field;

import com.qoomon.banking.Lists;
import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.notation.FieldNotationParseException;
import com.qoomon.banking.swift.notation.SwiftNotation;

import java.util.List;


/**
 * <b>Transaction Reference Number</b>
 * <p>
 * <b>Field Tag</b> :20:
 * <p>
 * <b>Format</b> 20x
 * <p>
 * <b>SubFields</b>
 * <pre>
 * 1: 20x    - Value
 * </pre>
 */
public class TransactionReferenceNumber implements SwiftField {

    public static final String FIELD_TAG_20 = "20";

    public static final SwiftNotation SWIFT_NOTATION = new SwiftNotation("20x");

    private final String content;


    public TransactionReferenceNumber(String content) {

        Preconditions.checkArgument(content != null, "content can't be null");

        this.content = content;
    }

    public static TransactionReferenceNumber of(GeneralField field) throws FieldNotationParseException {
        Preconditions.checkArgument(field.getTag().equals(FIELD_TAG_20), "unexpected field tag '%s'", field.getTag());

        List<String> subFields = SWIFT_NOTATION.parse(field.getContent());

        String value = subFields.get(0);

        return new TransactionReferenceNumber(value);
    }


    @Override
    public String getTag() {
        return FIELD_TAG_20;
    }

    @Override
    public String getContent() {
        try {
            return SWIFT_NOTATION.render(Lists.newArrayList(content));
        } catch (FieldNotationParseException e) {
            throw new IllegalStateException("Invalid field values within " + getClass().getSimpleName() + " instance", e);
        }
    }
}
