package Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.stereotype.Service;

@Service
public class Basement {

    public String getBasementDirectory() {
        if (getApplicationHostName().equals("LAPTOP")) {
            return "C:\\Users\\Michail Sitmalidis\\berishvili_3.0";
        } else if (getApplicationHostName().equals("DESKTOP-O61QDDT")) {
            return "D:\\berishvili";
        } else if (getApplicationHostName().equals("DESKTOP-RJIL951")) {
            return "D:\\berishvili";
        } else if (getApplicationHostName().equals("amazon-server")) {
            return "C:\\berishvili";
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
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostname;
    }
}
