package Service;

import org.springframework.stereotype.Service;

@Service
public class StaticsDispatcher {

    private static boolean uploading;

    public static boolean isUploading() {
        return uploading;
    }

    public static void setUploading(boolean uploading) {
        StaticsDispatcher.uploading = uploading;
    }
    
    
}
