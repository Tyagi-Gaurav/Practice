package gt.gameOfLife;

import gt.practice.gameOfLife.Cell;
import gt.practice.gameOfLife.GameOfLife;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * serialA) Any live cell with fewer than 2 neighbors dies
 * parallelB) Any live cell with 2 or 3 live neighbors lives
 * parallelB) Any live cell with more than 3 live neighbors dies
 * parallelB) Any dead cell with exactly 3 live neighbors comes alive.
 *
 */
public class GameOfLifeEndToEndTest {
    private GameOfLife gol = null;

    @Test
    @DisplayName("Blinker Test")
    void blinkerTest() {
        //Given
        Set<Cell> seed = new HashSet<Cell>(Arrays.asList(
                new Cell(3,2),
                new Cell(3,3),
                new Cell(3,4)
        ));

        gol = new GameOfLife(seed);

        Set<Cell> newGeneration = new HashSet<Cell>(Arrays.asList(
                new Cell(2,3),
                new Cell(3,3),
                new Cell(4,3)
        ));

        //Then
        Assertions.assertThat(gol.nextGeneration())
                .isEqualTo(newGeneration);
    }

    @Test
    @DisplayName("Toad")
    void toadTest() {
        //Given
        Set<Cell> seed = new HashSet<Cell>(Arrays.asList(
                new Cell(3,3),
                new Cell(3,4),
                new Cell(3,5),
                new Cell(4,2),
                new Cell(4,3),
                new Cell(4,4)
        ));

        gol = new GameOfLife(seed);

        Set<Cell> newGeneration = new HashSet<Cell>(Arrays.asList(
                new Cell(2,4),
                new Cell(3,2),
                new Cell(3,5),
                new Cell(4,2),
                new Cell(4,5),
                new Cell(5,3)
        ));

        //Then
        Assertions.assertThat(gol.nextGeneration())
                .isEqualTo(newGeneration);
    }

    @Test
    @DisplayName("Block Test - Still Life")
    void stillLifeBlockTest() {
        //Given
        Set<Cell> seed = new HashSet<Cell>(Arrays.asList(
                new Cell(2,2),
                new Cell(2,3),
                new Cell(3,2),
                new Cell(3,3)
        ));

        gol = new GameOfLife(seed);

        //Then
        Assertions.assertThat(gol.nextGeneration())
                .isEqualTo(seed);
    }
}
