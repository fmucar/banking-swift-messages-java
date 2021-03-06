package com.qoomon.banking.swift.message.block;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

/**
 * Created by qoomon on 14/07/16.
 */
public class SystemTrailerBlockTest {

    @Test
    public void of_WHEN_valid_block_is_passed_RETURN_new_block() throws Exception {

        // Given
        GeneralBlock generalBlock = new GeneralBlock(SystemTrailerBlock.BLOCK_ID_S, "{CHK:F7C4F89AF66D}{TNG:}{SAC:}{COP:P}");

        // When
        SystemTrailerBlock block = SystemTrailerBlock.of(generalBlock);

        // Then
        assertThat(block).isNotNull();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(block.getChecksum()).isEqualTo("F7C4F89AF66D");
        softly.assertThat(block.getTraining()).isEqualTo("");
        softly.assertThat(block.getAdditionalSubblocks("COP").getContent()).isEqualTo("P");
        softly.assertAll();
    }

    @Test
    public <T> void of_WHEN_block_with_invalid_id_is_passed_THROW_exception() throws Exception {

        // Given
        GeneralBlock generalBlock = new GeneralBlock("0", "\nabc\n-");

        // When
        Throwable exception = catchThrowable(() -> SystemTrailerBlock.of(generalBlock));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void getContent_SHOULD_return_input_text() throws Exception {

        // Given
        String contentInput = "{CHK:F7C4F89AF66D}{TNG:}{SAC:}{COP:P}";
        GeneralBlock generalBlock = new GeneralBlock(SystemTrailerBlock.BLOCK_ID_S, contentInput);
        SystemTrailerBlock classUnderTest = SystemTrailerBlock.of(generalBlock);

        // When
        String content = classUnderTest.getContent();

        // Then
        assertThat(content).isEqualTo(contentInput);
    }

}