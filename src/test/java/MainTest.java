import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

public class MainTest {
    @Disabled
    @Test
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    void main() {

    }
}
