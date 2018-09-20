package com.qoomon.banking.swift.submessage.mt942;

import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.submessage.PageSeparator;
import com.qoomon.banking.swift.submessage.field.*;
import com.qoomon.banking.swift.submessage.field.subfield.DebitCreditMark;
import org.joda.money.CurrencyUnit;

import java.util.List;
import java.util.Optional;

import static com.qoomon.banking.swift.submessage.field.FieldUtils.swiftTextOf;

/**
 *
 */
public class MT942Page {

    public static final String MESSAGE_ID_942 = "942";

    /**
     * @see TransactionReferenceNumber#FIELD_TAG_20
     */
    private final TransactionReferenceNumber transactionReferenceNumber;

    /**
     * @see RelatedReference#FIELD_TAG_21
     */
    private final RelatedReference relatedReference;

    /**
     * @see AccountIdentification#FIELD_TAG_25
     */
    private final AccountIdentification accountIdentification;

    /**
     * @see StatementNumber#FIELD_TAG_28C
     */
    private final StatementNumber statementNumber;

    /**
     * @see FloorLimitIndicator#FIELD_TAG_34F
     */
    private final FloorLimitIndicator floorLimitIndicatorDebit;

    /**
     * @see FloorLimitIndicator#FIELD_TAG_34F
     */
    private final FloorLimitIndicator floorLimitIndicatorCredit;

    /**
     * @see DateTimeIndicator#FIELD_TAG_13D
     */
    private final DateTimeIndicator dateTimeIndicator;

    /**
     * @see StatementLine#FIELD_TAG_61
     * @see InformationToAccountOwner#FIELD_TAG_86
     */
    private final List<TransactionGroup> transactionGroupList;

    /**
     * @see TransactionSummary#FIELD_TAG_90D
     */
    private final TransactionSummary transactionSummaryDebit;

    /**
     * @see TransactionSummary#FIELD_TAG_90C
     */
    private final TransactionSummary transactionSummaryCredit;

    /**
     * @see InformationToAccountOwner#FIELD_TAG_86
     */
    private final InformationToAccountOwner informationToAccountOwner;


    public MT942Page(
            TransactionReferenceNumber transactionReferenceNumber,
            RelatedReference relatedReference,
            AccountIdentification accountIdentification,
            StatementNumber statementNumber,
            FloorLimitIndicator floorLimitIndicatorDebit,
            FloorLimitIndicator floorLimitIndicatorCredit,
            DateTimeIndicator dateTimeIndicator,
            List<TransactionGroup> transactionGroupList,
            TransactionSummary transactionSummaryDebit,
            TransactionSummary transactionSummaryCredit,
            InformationToAccountOwner informationToAccountOwner) {

        Preconditions.checkArgument(transactionReferenceNumber != null, "transactionReferenceNumber can't be null");
        Preconditions.checkArgument(accountIdentification != null, "accountIdentification can't be null");
        Preconditions.checkArgument(statementNumber != null, "statementNumber can't be null");
        Preconditions.checkArgument(floorLimitIndicatorDebit != null, "floorLimitIndicatorDebit can't be null");
        if (floorLimitIndicatorDebit.getDebitCreditMark() != null) {
            DebitCreditMark debitCreditMark = floorLimitIndicatorDebit.getDebitCreditMark();
            Preconditions.checkArgument(debitCreditMark == DebitCreditMark.DEBIT, "floorLimitIndicatorDebit type can't " + debitCreditMark);
        }
        if (floorLimitIndicatorCredit != null && floorLimitIndicatorCredit.getDebitCreditMark() != null) {
            DebitCreditMark debitCreditMark = floorLimitIndicatorCredit.getDebitCreditMark();
            Preconditions.checkArgument(debitCreditMark == DebitCreditMark.CREDIT, "floorLimitIndicatorCredit type can't " + debitCreditMark);
        }
        Preconditions.checkArgument(dateTimeIndicator != null, "dateTimeIndicator can't be null");
        Preconditions.checkArgument(transactionGroupList != null, "transactionGroupList can't be null");


        // ensure matching currency
        CurrencyUnit statementCurrency = floorLimitIndicatorDebit.getAmount().getCurrencyUnit();
        String statementFundsCode = statementCurrency.getCode().substring(2, 3);

        if (floorLimitIndicatorCredit != null) {
            CurrencyUnit currency = floorLimitIndicatorCredit.getAmount().getCurrencyUnit();
            Preconditions.checkArgument(currency.equals(statementCurrency), "floorLimitCreditCurrency '" + currency + "' does not match statement currency'" + statementCurrency + "'");
        }

        for (TransactionGroup transactionGroup : transactionGroupList) {
            if (transactionGroup.getStatementLine().getFundsCode() != null) {
                String fundsCode = transactionGroup.getStatementLine().getFundsCode();
                Preconditions.checkArgument(fundsCode.equals(statementFundsCode), "statementLineFundsCode '" + fundsCode + "' does not match statement currency'" + statementCurrency + "'");
            }
        }

        if (transactionSummaryDebit != null) {
            CurrencyUnit currency = transactionSummaryDebit.getAmount().getCurrencyUnit();
            Preconditions.checkArgument(currency.equals(statementCurrency), "transactionSummaryDebitCurrency '" + currency + "' does not match statement currency'" + statementCurrency + "'");
        }

        if (transactionSummaryCredit != null) {
            CurrencyUnit currency = transactionSummaryCredit.getAmount().getCurrencyUnit();
            Preconditions.checkArgument(currency.equals(statementCurrency), "transactionSummaryCreditCurrency '" + currency + "' does not match statement currency'" + statementCurrency + "'");
        }

        this.transactionReferenceNumber = transactionReferenceNumber;
        this.relatedReference = relatedReference;
        this.accountIdentification = accountIdentification;
        this.statementNumber = statementNumber;
        this.floorLimitIndicatorDebit = floorLimitIndicatorDebit;
        this.floorLimitIndicatorCredit = Optional.ofNullable(floorLimitIndicatorCredit).orElse(floorLimitIndicatorDebit);
        this.dateTimeIndicator = dateTimeIndicator;
        this.transactionGroupList = transactionGroupList;
        this.transactionSummaryDebit = transactionSummaryDebit;
        this.transactionSummaryCredit = transactionSummaryCredit;
        this.informationToAccountOwner = informationToAccountOwner;
    }

    public TransactionReferenceNumber getTransactionReferenceNumber() {
        return transactionReferenceNumber;
    }

    public RelatedReference getRelatedReference() {
        return relatedReference;
    }

    public AccountIdentification getAccountIdentification() {
        return accountIdentification;
    }

    public StatementNumber getStatementNumber() {
        return statementNumber;
    }

    public FloorLimitIndicator getFloorLimitIndicatorDebit() {
        return floorLimitIndicatorDebit;
    }

    public FloorLimitIndicator getFloorLimitIndicatorCredit() {
        return floorLimitIndicatorCredit;
    }

    public DateTimeIndicator getDateTimeIndicator() {
        return dateTimeIndicator;
    }

    public List<TransactionGroup> getTransactionGroupList() {
        return transactionGroupList;
    }

    public InformationToAccountOwner getInformationToAccountOwner() {
        return informationToAccountOwner;
    }

    public TransactionSummary getTransactionSummaryDebit() {
        return transactionSummaryDebit;
    }

    public TransactionSummary getTransactionSummaryCredit() {
        return transactionSummaryCredit;
    }

    public String getContent() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(swiftTextOf(transactionReferenceNumber)).append("\n");
        if (relatedReference != null) {
            contentBuilder.append(swiftTextOf(relatedReference)).append("\n");
        }
        contentBuilder.append(swiftTextOf(accountIdentification)).append("\n");
        contentBuilder.append(swiftTextOf(statementNumber)).append("\n");
        contentBuilder.append(swiftTextOf(floorLimitIndicatorDebit)).append("\n");
        if (!floorLimitIndicatorDebit.getAmount().equals(floorLimitIndicatorCredit.getAmount())) {
            contentBuilder.append(swiftTextOf(floorLimitIndicatorCredit)).append("\n");
        }
        contentBuilder.append(swiftTextOf(dateTimeIndicator)).append("\n");
        for (TransactionGroup transactionGroup : transactionGroupList) {
            contentBuilder.append(swiftTextOf(transactionGroup.getStatementLine())).append("\n");
            if (transactionGroup.getInformationToAccountOwner() != null) {
                contentBuilder.append(swiftTextOf(transactionGroup.getInformationToAccountOwner())).append("\n");
            }
        }
        if (transactionSummaryDebit != null) {
            contentBuilder.append(swiftTextOf(transactionSummaryDebit)).append("\n");
        }
        if (transactionSummaryCredit != null) {
            contentBuilder.append(swiftTextOf(transactionSummaryCredit)).append("\n");
        }
        if (informationToAccountOwner != null) {
            contentBuilder.append(swiftTextOf(informationToAccountOwner)).append("\n");
        }
        contentBuilder.append(PageSeparator.TAG);
        return contentBuilder.toString();
    }
}
