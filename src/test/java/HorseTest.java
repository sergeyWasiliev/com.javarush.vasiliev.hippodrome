import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Test
    public void testConstrThrowsExceptionWhenNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, Math.random());
        });
    }

    @Test
    public void testConstrThrowsExceptionMsgWhenNameIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, Math.random());
        });
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\t \n"})
    public void testConstrThrowsIAEwhenNameEmptyOrWhitespace(String input) {
        {
            assertThrows(IllegalArgumentException.class, () -> {
                new Horse(input, Math.random());
            });
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\t \n"})
    public void testConstrThrowsIAEmsgWhenNameEmptyOrWhitespace(String input) {
        {
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                new Horse(input, Math.random());
            });
            assertEquals("Name cannot be blank.", e.getMessage());

        }
    }

    @Test
    public void testConstrThrowsExceptionForNegativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", -Math.random());
        });
    }

    @Test
    public void testConstrThrowsExceptionMsgForNegativeSpeed() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", -Math.random());
        });
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void testConstrThrowsExceptionForNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", Math.random(), -Math.random());
        });
    }

    @Test
    public void testConstructorThrowsExceptionMsgForNegativeDistance() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", Math.random(), -Math.random());
        });
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    public void testGetName() {
        assertEquals("Horse", new Horse("Horse", Math.random()).getName());
    }

    @Test
    public void testGetSpeed() {
        assertEquals(5, new Horse("Horse", 5).getSpeed());
    }

    @Test
    public void testGetDistance() {
        assertEquals(5, new Horse("Horse", Math.random(), 5).getDistance());
        assertEquals(0, new Horse("Horse", Math.random()).getDistance());
    }

    @Test
    public void testMoveCallsGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", Math.random());
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10.0, 2.0, 0.3, 10.6", // distance = 10.0 + 2.0 * 0.3 = 10.6
            "20.0, 5.0, 0.5, 22.5", // distance = 20.0 + 5.0 * 0.5 = 22.5
            "15.0, 3.0, 0.9, 17.7"  // distance = 15.0 + 3.0 * 0.9 = 17.7
    })
    void testCalculateDistance(
            double initialDistance,
            double speed,
            double randomValue,
            double expectedDistance) {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
            Horse horse = new Horse("Horse", speed);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance() + initialDistance, 0.0001);
        }
    }


}
