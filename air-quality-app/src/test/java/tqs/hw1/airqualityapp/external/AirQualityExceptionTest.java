package tqs.hw1.airqualityapp.external;

import org.junit.jupiter.api.Test;
import tqs.hw1.airqualityapp.external.AirQualityException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AirQualityExceptionTest {

    @Test
    public void testConstructor() {

        String expected = "Testing the Constructor";
        AirQualityException exception = new AirQualityException(expected);

        assertEquals(expected, exception.getMessage());
    }

}
