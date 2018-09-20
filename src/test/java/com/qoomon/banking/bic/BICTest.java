package com.qoomon.banking.bic;

import org.junit.Assert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

/**
 * Created by qoomon on 19/07/16.
 */
public class BICTest {

    @Test
    public void of_WHEN_valid_bic_RETURN_bic() throws Exception {

        // Given
        String bicText = "HASPDEHHXXX";

        // When
        BIC bic = BIC.of(bicText);

        // Then
        assertThat(bic).isNotNull();
        assertThat(bic.getInstitutionCode()).isEqualTo("HASP");
        assertThat(bic.getCountryCode()).isEqualTo("DE");
        assertThat(bic.getLocationCode()).isEqualTo("HH");
        assertThat(bic.getBranchCode()).contains("XXX");

    }

    @Test
    public void of_WHEN_valid_bic_without_branche_code_RETURN_bic() throws Exception {

        // Given
        String bicText = "HASPDEHH";

        // When
        BIC bic = BIC.of(bicText);

        // Then
        assertThat(bic).isNotNull();
        assertThat(bic.getInstitutionCode()).isEqualTo("HASP");
        assertThat(bic.getCountryCode()).isEqualTo("DE");
        assertThat(bic.getLocationCode()).isEqualTo("HH");
        assertThat(bic.getBranchCode()).isNull();
    }

}