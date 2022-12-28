package Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.stereotype.Service;

@Service
public class Basement {

    public String getBasementDirectory() {
        // String rootDirectory = new File("").getAbsolutePath();
        if (getApplicationHostName().equals("LAPTOP")) {
            return "C:\\Users\\Michail Sitmalidis\\berishvili_3.0";
        } else if (getApplicationHostName().equals("DESKTOP-O61QDDT")) {
            return "D:\\berishvili";
        } else {
            return "/home/admin/basement";
        }
    }

    public String getApplicationHostName() {
        InetAddress ip;
        String hostname = "Ν/Α";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            // System.out.println("Your current IP address : " + ip);
            //System.out.println("Your current Hostname : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }
}
