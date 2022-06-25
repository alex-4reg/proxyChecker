import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class ProxyCheckRunner {
    public static void main(String[] args) {
        //ProxyChecker proxyChecker = new ProxyChecker("E:\\java\\ip.txt", 0);
        ProxyChecker proxyChecker = new ProxyChecker("src\\ipres\\ip.txt", 1);
        System.out.printf("Будет использоваться метод \"%s\"\n", proxyChecker.getMultiThreadMethod());
        proxyChecker.checkProxy();
    }
}

class ProxyChecker {
    String path;
    int multiThreadMethod;//1-class, 2-interface, else - anonymous class

    public ProxyChecker() {
    }

    public ProxyChecker(String path, int multiThreadMethod) {
        this.path = path;
        this.multiThreadMethod = multiThreadMethod;
    }

    public String getMultiThreadMethod() {
        return multiThreadMethod == 1 ? "class" : multiThreadMethod == 2 ? "interface" : "anonymous class";
    }

    public String getPath() {
        return path;
    }

    public void checkProxy() {
        try {
            //FileInputStream fis = new FileInputStream("E:\\java\\ip.txt");
            FileInputStream fis = new FileInputStream(path);
            int i;
            String result = "";
            while ((i = fis.read()) != -1) {
                if (i == 13) continue;//возврат каретки
                else if (i == 10) {//перенос строки
                    //result += i;
                    String[] ipAndPort = result.split(":");
                    String ip = ipAndPort[0];
                    int port = Integer.parseInt(ipAndPort[1]);
                    Thread thread = methodSwitcher(multiThreadMethod, ip, port);
                    thread.start();

                    //Thread thread = new Thread((new CheckPoxyThread(ip, port)));
                    //thread.start();
                    result = "";
                } else if (i == 9) {
                    result += (":");
                } else result += ((char) i);

                //result.append(i);
            }
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Thread methodSwitcher(int method, String ip, int port) {
        String usedMethod = "";
        switch (method) {
            case 1 -> {
                return new CheckPoxyRunnableClass(ip, port);
            }
            case 2 -> {
                return new Thread((new CheckPoxyThread(ip, port)));
            }
            default -> {
                return new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Run.run(ip, port);
                    }
                });
            }
        }
    }
}

class CheckPoxyThread implements Runnable {
    String ip;
    int port;

    public CheckPoxyThread(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Run.run(ip, port);
    }
}

class CheckPoxyRunnableClass extends Thread {
    String ip;
    int port;

    public CheckPoxyRunnableClass(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        Run.run(ip, port);
    }
}

class Run {
    public static void run(String ip, int port) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            URL url = new URL("https://vozhzhaev.ru/test.php");
            URLConnection urlConnection = url.openConnection(proxy);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println(inputLine);
                SaveIPToFile.writeIPToFile("src\\ipres\\workingIP.txt", inputLine);
            }
        } catch (Exception e) {
            System.out.println(ip + " - не работает");
        }
    }
}

class SaveIPToFile {
    public static void writeIPToFile(String destination, String string) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new FileWriter(destination, true)))) {
            writer.println(string);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
