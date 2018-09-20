package com.qoomon.banking.swift.message.block;

import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.message.block.exception.BlockFieldParseException;

/**
 * <b>Input Application Header Block</b>
 * <p>
 * <b>Fixed Length Format</b>
 * <pre>
 * 1:  1  - Mode - I = Input, O = Output
 * ...
 * ..
 * .
 * </pre>
 *
 * @see ApplicationHeaderInputBlock
 * @see ApplicationHeaderOutputBlock
 */
public class ApplicationHeaderBlock implements SwiftBlock {

    public static final String BLOCK_ID_2 = "2";

    public final Type type;

    private final ApplicationHeaderInputBlock input;

    private final ApplicationHeaderOutputBlock output;


    public ApplicationHeaderBlock(ApplicationHeaderInputBlock input) {
        type = Type.INPUT;
        this.input = input;
        this.output = null;
    }

    public ApplicationHeaderBlock(ApplicationHeaderOutputBlock output) {
        type = Type.OUTPUT;
        this.input = null;
        this.output = output;
    }

    public static ApplicationHeaderBlock of(GeneralBlock block) throws BlockFieldParseException {
        Preconditions.checkArgument(block.getId().equals(BLOCK_ID_2), "unexpected block id '%s'", block.getId());

        if (block.getContent().startsWith("I")) {
            ApplicationHeaderInputBlock input = ApplicationHeaderInputBlock.of(block);
            return new ApplicationHeaderBlock(input);
        }

        if (block.getContent().startsWith("O")) {
            ApplicationHeaderOutputBlock output = ApplicationHeaderOutputBlock.of(block);
            return new ApplicationHeaderBlock(output);
        }

        throw new IllegalArgumentException("Block '" + block.getId() + "' unknown Type " + "");

    }

    public ApplicationHeaderInputBlock getInput() {
        return input;
    }

    public ApplicationHeaderOutputBlock getOutput() {
        return output;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getId() {
        return BLOCK_ID_2;
    }

    @Override
    public String getContent() {
        if (getInput() != null) {
            return getInput().getContent();
        } else {
            return getOutput().getContent();
        }
    }

    enum Type {
        INPUT,
        OUTPUT
    }
}
