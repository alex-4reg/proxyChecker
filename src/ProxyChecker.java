import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class ProxyChecker {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("E:\\java\\ip.txt");
            int i;
            String result = "";
            while ((i = fis.read()) != -1) {
                if (i == 13) continue;//возврат каретки
                else if (i == 10) {//перенос строки
                    //result += i;
                    String[] ipAndPort = result.split(":");
                    String ip = ipAndPort[0];
                    int port = Integer.parseInt(ipAndPort[1]);
                    Thread thread = new Thread((new CheckPoxyThread(ip, port)));
                    thread.start();
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
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
            URL url = new URL("https://vozhzhaev.ru/test.php");
            URLConnection urlConnection = url.openConnection(proxy);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;

            while ((inputLine = bufferedReader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (Exception e) {
            System.out.println(ip + " - не работает");
        }
    }
}
