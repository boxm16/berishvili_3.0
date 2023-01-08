package Service;

import org.springframework.stereotype.Service;

@Service
public class StaticsDispatcher {

    private static boolean uploading;
    private static String uploadingProgressMessage;

    public static boolean isUploading() {
        return uploading;
    }

    public static void setUploading(boolean uploading) {
        StaticsDispatcher.uploading = uploading;
    }

    public static String getUploadingProgressMessage() {
        return uploadingProgressMessage;
    }

    public static void setUploadingProgressMessage(String uploadingProgressMessage) {
        StaticsDispatcher.uploadingProgressMessage = uploadingProgressMessage;
    }

}
