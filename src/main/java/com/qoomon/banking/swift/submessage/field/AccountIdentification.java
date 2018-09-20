package com.qoomon.banking.swift.submessage.field;

import com.qoomon.banking.Lists;
import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.notation.FieldNotationParseException;
import com.qoomon.banking.swift.notation.SwiftNotation;

import java.util.List;

/**
 * <b>Account Identification</b>
 * <p>
 * <b>Field Tag</b> :25:
 * <p>
 * <b>Format</b> 35x
 * <p>
 * <b>SubFields</b>
 * <pre>{@literal
 * 1: 35x - Value
 * }</pre>
 */
public class AccountIdentification implements SwiftField {

    public static final String FIELD_TAG_25 = "25";

    public static final SwiftNotation SWIFT_NOTATION = new SwiftNotation("35x");

    private final String content;


    public AccountIdentification(String content) {
        Preconditions.checkArgument(content != null, "content can't be null");
        this.content = content;
    }

    public static AccountIdentification of(GeneralField field) throws FieldNotationParseException {
        Preconditions.checkArgument(field.getTag().equals(FIELD_TAG_25), "unexpected field tag '%s'", field.getTag());

        List<String> subFields = SWIFT_NOTATION.parse(field.getContent());

        String value = subFields.get(0);

        return new AccountIdentification(value);
    }


    @Override
    public String getTag() {
        return FIELD_TAG_25;
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
