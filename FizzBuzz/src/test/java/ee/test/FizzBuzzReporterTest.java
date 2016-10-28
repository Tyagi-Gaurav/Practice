package ee.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FizzBuzzReporterTest {
    private FizzBuzzReporter fizzBuzzConsoleReporter;

    @Mock
    private FizzBuzz fizzBuzz;

    private final String expectedFizzBuzzString = "1 2 lucky 4 buzz fizz 7 8 fizz buzz 11 fizz lucky 14 " +
            "fizzbuzz 16 17 fizz 19 buzz";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(fizzBuzz.printFizzBuzzForNumbersBetween(1, 20))
                .thenReturn(expectedFizzBuzzString);
    }

    @Test
    public void produceReportCategorizedByKey() throws Exception {
        // Given
        fizzBuzzConsoleReporter = new FizzBuzzReporter(fizzBuzz);
        ByteArrayOutputStream out = mockStandardOutput();

        StringBuilder expectedOutput = getExpectedReport();

        // When
        fizzBuzzConsoleReporter.reportFizzBuzzBetween(1, 20);

        // Then
        assertThat(out.toString()).isEqualTo(expectedOutput.toString());
    }

    private ByteArrayOutputStream mockStandardOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    private StringBuilder getExpectedReport() {
        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append(expectedFizzBuzzString + "\n");
        expectedOutput.append("fizz: 4\n");
        expectedOutput.append("buzz: 3\n");
        expectedOutput.append("fizzbuzz: 1\n");
        expectedOutput.append("lucky: 2\n");
        expectedOutput.append("integer: 10\n");
        return expectedOutput;
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }
}