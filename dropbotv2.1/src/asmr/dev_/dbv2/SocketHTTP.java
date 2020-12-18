package asmr.dev_.dbv2;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketHTTP extends Socket {

    public SocketHTTP(SocketAddress socketAddress, int timeout) throws IOException {
        connect(socketAddress, timeout);
        setSoTimeout(timeout);
        connectToTarget();
    }

    private void connectToTarget() throws IOException {
        PrintStream printStream = new PrintStream(getOutputStream());
        printStream.flush();
    }
}
