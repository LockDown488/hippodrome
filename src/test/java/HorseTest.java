import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HorseTest {
    @Mock
    Horse horse;

    @Test
    void isConstructorFirstArgumentNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 2.5);
        });

        String expectedMessage = "Name cannot be null";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void isConstructorFirstArgumentContainsSpaceSymbols(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 2.5);
        });

        String expectedMessage = "Name cannot be blank";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void isConstructorSecondArgumentNegativeNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", -2.5);
        });

        String expectedMessage = "Speed cannot be negative";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void isConstructorThirdArgumentNegativeNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("name", 2.5, -25);
        });

        String expectedMessage = "Distance cannot be negative";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void isGetNameReturnsCorrectName() {
        String expectedName = "Horse";
        Horse horse = new Horse(expectedName, 2.5);
        String actualName = horse.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    void isGetSpeedReturnsCorrectSpeed() {
        double expectedSpeed = 2.5;
        Horse horse = new Horse("Horse", expectedSpeed);
        double actualSpeed = horse.getSpeed();

        assertEquals(expectedSpeed, actualSpeed);
    }

    @Test
    void isGetDistanceReturnsCorrectDistanceInConstructorWithThreeArgs() {
        double expectedDistance = 25;
        Horse horse = new Horse("Horse", 2.5, expectedDistance);
        double actualDistance = horse.getDistance();

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void isMethodGetDistanceReturnsCorrectDistanceInConstructorWithTwoArgs() {
        double expectedDistance = 0;
        Horse horse = new Horse("Horse", 2.5);
        double actualDistance = horse.getDistance();

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    void isMoveCallsGetRandomWithCorrectArguments() {
        try(MockedStatic<Horse> mockedObject = Mockito.mockStatic(Horse.class)) {
            horse.move();
            mockedObject.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {1, 2})
    void isMoveCheckGetRandomSum(double number) {
        try(MockedStatic<Horse> mockedObject = Mockito.mockStatic(Horse.class)) {
            mockedObject.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(number);
            double distance = horse.getDistance();
            horse.move();
            assertEquals(distance + horse.getSpeed() * number, horse.getDistance());
        }
    }
}
