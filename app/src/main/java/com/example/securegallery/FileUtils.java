package com.example.securegallery;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtils {

    public static File getSecureDir(Context context) {
        File dir = new File(context.getFilesDir(), "secure_gallery");
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    // 🔥 MÉTHODE MANQUANTE
    public static File copyToSecureGallery(Context ctx, Uri uri) throws Exception {

        String name = getFileName(ctx, uri);
        if (name == null) {
            name = "file_" + System.currentTimeMillis();
        }

        File outFile = new File(getSecureDir(ctx), name);

        try (InputStream in = ctx.getContentResolver().openInputStream(uri);
             FileOutputStream out = new FileOutputStream(outFile)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }
        return outFile;
    }
    public static File createIntruderFile(Context ctx) {
        File dir = getSecureDir(ctx);
        String fileName = "intruder_" + System.currentTimeMillis() + ".jpg";
        return new File(dir, fileName);
    }

    private static String getFileName(Context ctx, Uri uri) {
        Cursor c = ctx.getContentResolver()
                .query(uri, null, null, null, null);

        if (c != null && c.moveToFirst()) {
            int index = c.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            String name = c.getString(index);
            c.close();
            return name;
        }
        return null;
    }
}
