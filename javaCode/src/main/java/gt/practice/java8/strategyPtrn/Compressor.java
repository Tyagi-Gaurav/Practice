package gt.practice.java8.strategyPtrn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Compressor {
    private final CompressionStrategy compressionStartegy;

    public Compressor(CompressionStrategy compressionStartegy) {
        this.compressionStartegy = compressionStartegy;
    }

    public void compress(Path inFile, File outFile) throws IOException {
        try (OutputStream outputStream = new FileOutputStream(outFile)) {
            Files.copy(inFile, compressionStartegy.compres(outputStream));
        }
    }
}
