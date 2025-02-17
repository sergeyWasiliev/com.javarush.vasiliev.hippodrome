import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @Test
    public void testConstrThrowsExceptionWhenHorsesIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
    }

    @Test
    public void testConstrThrowsExceptionMsgWhenHorsesIsNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void testConstrThrowsExceptionWhenListHorseBlank() {
        List<Horse> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(list);
        });
    }

    @Test
    public void testConstrThrowsExceptionMsgWhenListHorseBlank() {
        List<Horse> list = new ArrayList<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(list);
        });
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    public void testGetHorsesReturnsSameObjectList() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse" + i, i * 1.1));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        List<Horse> resultList = hippodrome.getHorses();
        assertEquals(horseList, resultList);
    }

    @Test
    public void testCallsMoveOnAllHorses() {
        List<Horse> horseMockList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horseMockL = Mockito.mock(Horse.class);
            horseMockList.add(horseMockL);
        }
        Hippodrome hippodrome = new Hippodrome(horseMockList);
        hippodrome.move();
        for (Horse horseMock : horseMockList) {
            verify(horseMock, times(1)).move();
        }
    }

    @Test
    void testGetWinner() {
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("Horse" + i, 0, i * 1.1));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Horse horseWinner = hippodrome.getWinner();
        assertSame(horseList.get(horseList.size() - 1), horseWinner);
    }


}
