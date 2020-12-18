package asmr.dev_.dbv2;
import asmr.dev_.dbv2.options.ArgSplit;

import java.io.IOException;

public class Runnable {
    public static void main(String... args) throws IOException {
        Main starter = new Main(ArgSplit.Builder.of(args));
        starter.start();
    }
}