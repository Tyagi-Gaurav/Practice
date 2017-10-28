package gt.practice.java8.strategyPtrn;

import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class CompressionDemo {
    public static void main(String[] args) {
        Compressor compressor1 = new Compressor(GZIPOutputStream::new);
        Compressor compressor2 = new Compressor(ZipOutputStream::new);

    }
}
