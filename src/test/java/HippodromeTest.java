import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {
    @Mock
    Hippodrome hippodrome;

    @Test
    void isConstructorFirstArgumentNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });

        String expectedMessage = "Horses cannot be null";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void isConstructorFirstArgumentNotEmptyList() {
        List<Horse> horsesList= new ArrayList<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(horsesList);
        });

        String expectedMessage = "Horses cannot be empty";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void isGetHorsesReturnsCorrectArgs() {
        List<Horse> horsesList= new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horsesList.add(new Horse("Horse" + i, 1));
        }
        Hippodrome hippodrome = new Hippodrome(horsesList);
        assertArrayEquals(horsesList.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    void isMoveReturnsCorrectArgs() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(Mockito.spy(new Horse("Horse" + i, 1)));
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses){
            Mockito.verify(horse).move();
        }
    }

    @Test
    void isGetWinnerReturnsCorrectArgs() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(new Horse("Horse" + i, 1, i));
        }
        hippodrome = new Hippodrome(horses);
        assertEquals(49, hippodrome.getWinner());
    }
}
