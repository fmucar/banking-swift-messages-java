package com.qoomon.banking.swift.submessage.field;

import com.qoomon.banking.Preconditions;

import java.util.Optional;


public class TransactionGroup {

    /**
     * @see StatementLine#FIELD_TAG_61
     */
    private final StatementLine statementLine;

    /**
     * @see InformationToAccountOwner#FIELD_TAG_86
     */
    private final InformationToAccountOwner informationToAccountOwner;


    public TransactionGroup(StatementLine statementLine, InformationToAccountOwner informationToAccountOwner) {

        Preconditions.checkArgument(statementLine != null, "statementLine can't be null");

        this.statementLine = statementLine;
        this.informationToAccountOwner = informationToAccountOwner;
    }

    public StatementLine getStatementLine() {
        return statementLine;
    }

    public InformationToAccountOwner getInformationToAccountOwner() {
        return informationToAccountOwner;
    }

}
