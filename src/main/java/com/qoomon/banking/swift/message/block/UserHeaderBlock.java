package com.qoomon.banking.swift.message.block;

import com.qoomon.banking.ImmutableMap;
import com.qoomon.banking.Preconditions;
import com.qoomon.banking.swift.message.block.exception.BlockFieldParseException;
import com.qoomon.banking.swift.message.block.exception.BlockParseException;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>User Header Block</b>
 * <p>
 * <b>Sub Blocks</b>
 * <pre>
 * 1: 113 - Banking Priority Code of 4 alphanumeric characters - Optional
 * 2: 108 - Indicates the Message User Reference (MUR) value, which can be up to 16 characters, and will be returned in the ACK
 * </pre>
 * <b>Example</b><br>
 * {113:SEPA}{108:ILOVESEPA}
 *
 * @see <a href="https://www.ibm.com/support/knowledgecenter/SSBTEG_4.3.0/com.ibm.wbia_adapters.doc/doc/swift/swift72.htm">https://www.ibm.com/support/knowledgecenter/SSBTEG_4.3.0/com.ibm.wbia_adapters.doc/doc/swift/swift72.htm</a>
 */
public class UserHeaderBlock implements SwiftBlock {

    public static final String BLOCK_ID_3 = "3";

    public final String bankingPriorityCode;

    public final String messageUserReference;

    public final Map<String, GeneralBlock> additionalSubblocks;


    public UserHeaderBlock(String bankingPriorityCode, String messageUserReference, Map<String, GeneralBlock> additionalSubblocks) {

        Preconditions.checkArgument(messageUserReference != null, "messageUserReference can't be null");
        Preconditions.checkArgument(additionalSubblocks != null, "additionalSubblocks can't be null");

        this.bankingPriorityCode = bankingPriorityCode;
        this.messageUserReference = messageUserReference;
        this.additionalSubblocks = ImmutableMap.copyOf(additionalSubblocks);
    }

    public static UserHeaderBlock of(GeneralBlock block) throws BlockFieldParseException {
        Preconditions.checkArgument(block.getId().equals(BLOCK_ID_3), "unexpected block id '%s'", block.getId());

        SwiftBlockReader blockReader = new SwiftBlockReader(new StringReader(block.getContent()));

        String bankingPriorityCode = null;
        String messageUserReference = null;
        Map<String, GeneralBlock> additionalSubblocks = new HashMap<>();

        try {
            GeneralBlock subblock;
            while ((subblock = blockReader.readBlock()) != null) {
                switch (subblock.getId()) {
                    case "113":
                        bankingPriorityCode = subblock.getContent(); // TODO regex check
                        break;
                    case "108":
                        messageUserReference = subblock.getContent(); // TODO regex check
                        break;
                    default:
                        additionalSubblocks.put(subblock.getId(), subblock);
                        break;
                }
            }
        } catch (BlockParseException e) {
            throw new BlockFieldParseException("Block '" + block.getId() + "' content error", e);
        }

        return new UserHeaderBlock(bankingPriorityCode, messageUserReference, additionalSubblocks);
    }

    public String getBankingPriorityCode() {
        return bankingPriorityCode;
    }

    public String getMessageUserReference() {
        return messageUserReference;
    }

    @Override
    public String getId() {
        return BLOCK_ID_3;
    }

    @Override
    public String getContent() {
        StringBuilder contentBuilder = new StringBuilder();
        if (bankingPriorityCode != null) {
            contentBuilder.append(BlockUtils.swiftTextOf("113", bankingPriorityCode));
        }
        contentBuilder.append(BlockUtils.swiftTextOf("108", messageUserReference));
        for (GeneralBlock subblock : additionalSubblocks.values()) {
            contentBuilder.append(BlockUtils.swiftTextOf(subblock.getId(), subblock.getContent()));
        }
        return contentBuilder.toString();
    }
}
