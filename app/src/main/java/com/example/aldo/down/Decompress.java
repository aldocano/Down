package com.example.aldo.down;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author cano
 */
public class Decompress {
    private String _zipFile;
    private String _location;

    public Decompress(String filePath, String destination) {
        _zipFile = filePath;
        _location = destination;
        _dirChecker("");
    }

    public void unzip() {

        try {
            FileInputStream inputStream = new FileInputStream(_zipFile);
            ZipInputStream zipStream = new ZipInputStream(inputStream);
            ZipEntry zEntry = null;
            while ((zEntry = zipStream.getNextEntry()) != null) {
                Log.d("Unzip", "U ekstraktua " + zEntry.getName() + " vendodhja: "
                        + _location);

                if ( zEntry.isDirectory() ) {
                    _dirChecker(zEntry.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(
                            _location + "/" + zEntry.getName());
                    BufferedOutputStream bufout = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zipStream.read(buffer)) != -1) {
                        bufout.write(buffer, 0, read);
                    }

                    zipStream.closeEntry();
                    bufout.close();
                    fout.close();
                }
            }
            zipStream.close();
            Log.d("Unzip", "Ekstraktimi me sukses. vendodhja :  " + _location);
        } catch (Exception e) {
            Log.d("Unzip", "Ekstraktimi deshtoi");
            e.printStackTrace();
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(this._location + dir);
        if ( !f.isDirectory() ) {
            f.mkdirs();
        }
    }
}