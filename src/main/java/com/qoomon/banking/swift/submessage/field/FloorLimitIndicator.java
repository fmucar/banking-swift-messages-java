package com.qoomon.banking.swift.submessage.field;

import com.qoomon.banking.Lists;
import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.notation.FieldNotationParseException;
import com.qoomon.banking.swift.notation.SwiftDecimalFormatter;
import com.qoomon.banking.swift.notation.SwiftNotation;
import com.qoomon.banking.swift.submessage.field.subfield.DebitCreditMark;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * <b>Floor Limit Indicator Debit/Credit</b>
 * <p>
 * <b>Field Tag</b> :34F:
 * <p>
 * <b>Format</b> 3!a[1!a]15d
 * <p>
 * <b>SubFields</b>
 * <pre>
 * 1: 3!a   - Currency - Three Digit Code
 * 2: [1!a] - Debit/Credit Mark - 'D' = Debit, 'C' Credit
 * 3: 15d   - Amount
 * </pre>
 */
public class FloorLimitIndicator implements SwiftField {

    public static final String FIELD_TAG_34F = "34F";

    public static final SwiftNotation SWIFT_NOTATION = new SwiftNotation("3!a[1!a]15d");

    private final DebitCreditMark debitCreditMark;

    private final BigMoney amount;


    public FloorLimitIndicator(DebitCreditMark debitCreditMark, BigMoney amount) {

        Preconditions.checkArgument(amount != null, "amount can't be null");
        Preconditions.checkArgument(amount.isPositiveOrZero(), "amount can't be negative");

        this.debitCreditMark = debitCreditMark;
        this.amount = amount;
    }

    public static FloorLimitIndicator of(GeneralField field) throws FieldNotationParseException {
        Preconditions.checkArgument(field.getTag().equals(FIELD_TAG_34F), "unexpected field tag '%s'", field.getTag());

        List<String> subFields = SWIFT_NOTATION.parse(field.getContent());

        CurrencyUnit amountCurrency = CurrencyUnit.of(subFields.get(0));
        DebitCreditMark debitCreditMark = subFields.get(1) != null ? DebitCreditMark.ofFieldValue(subFields.get(1)) : null;
        BigDecimal amountValue = SwiftDecimalFormatter.parse(subFields.get(2));
        BigMoney amount = BigMoney.of(amountCurrency, amountValue);

        return new FloorLimitIndicator(debitCreditMark, amount);
    }


    public DebitCreditMark getDebitCreditMark() {
        return debitCreditMark;
    }

    public BigMoney getAmount() {
        return amount;
    }

    public BigMoney getSignedAmount() {
        BigMoney result = null;
        if (debitCreditMark != null) {
            if (debitCreditMark.sign() < 0) {
                result = amount.negated();
            } else {
                result = amount;
            }
        }

        return result;
//        return getDebitCreditMark().map(debitCreditMark -> {
//                    if (debitCreditMark.sign() < 0) {
//                        return amount.negated();
//                    }
//                    return amount;
//                });
    }

    @Override
    public String getTag() {
        return FIELD_TAG_34F;
    }

    @Override
    public String getContent() {
        try {

            return SWIFT_NOTATION.render(Lists.newArrayList(
                    amount.getCurrencyUnit().getCode(),
                    debitCreditMark == null ? null : debitCreditMark.toFieldValue(),
                    SwiftDecimalFormatter.format(amount.getAmount())
            ));
//            return SWIFT_NOTATION.render(Lists.newArrayList(
//                    amount.getCurrencyUnit().getCode(),
//                    debitCreditMark.map(DebitCreditMark::toFieldValue).orElse(null),
//                    SwiftDecimalFormatter.format(amount.getAmount())
//            ));
        } catch (FieldNotationParseException e) {
            throw new IllegalStateException("Invalid field values within " + getClass().getSimpleName() + " instance", e);
        }
    }
}
