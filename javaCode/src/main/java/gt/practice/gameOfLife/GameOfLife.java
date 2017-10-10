package gt.practice.gameOfLife;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GameOfLife {
    Predicate<Integer> liveCell = x -> (x == 2 || x == 3);
    Predicate<Integer> deadToLive = x -> x == 3;

    private Set<Cell> seed;

    public GameOfLife(Set<Cell> seed) {
        this.seed = seed;
    }

    public Set<Cell> nextGeneration() {
        Set<Cell> newGenFromLiveCells = seed
                .stream()
                .filter(x -> liveCell.test(this.getLiveNeighborCount(x)))
                .collect(Collectors.toSet());

        Set<Cell> newGenFromDeadCells = getAllDeadNeighbors()
                .stream()
                .filter(x -> deadToLive.test(this.getLiveNeighborCount(x)))
                .collect(Collectors.toSet());

        newGenFromLiveCells.addAll(newGenFromDeadCells);
        return newGenFromLiveCells;
    }

    private Integer getLiveNeighborCount(Cell cell) {
        HashSet<Cell> neighbors = getAllNeighbors(cell);
        neighbors.retainAll(seed);
        return neighbors.size();
    }

    private Set<Cell> getAllDeadNeighbors() {
        Set<HashSet<Cell>> collect = seed.stream()
                .map(this::getAllNeighbors)
                .collect(Collectors.toSet());

        return collect.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    private HashSet<Cell> getAllNeighbors(Cell cell) {
        return new HashSet<>(Arrays.asList(
                new Cell(cell.x() - 1, cell.y() - 1),
                new Cell(cell.x(), cell.y() - 1),
                new Cell(cell.x() + 1, cell.y() - 1),
                new Cell(cell.x() + 1, cell.y()),
                new Cell(cell.x() + 1, cell.y() + 1),
                new Cell(cell.x(), cell.y() + 1),
                new Cell(cell.x() - 1, cell.y() + 1),
                new Cell(cell.x() - 1, cell.y())
        ));
    }
}
