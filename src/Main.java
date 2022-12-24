import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        GameProgress progressOne = new GameProgress(19, 2, 4, 22.3);
        GameProgress progressTwo = new GameProgress(54, 5, 11, 76.2);
        GameProgress progressThree = new GameProgress(80, 24, 44, 65.1);

        saveGame("E://Game/savegames/save1.dat", progressOne);
        saveGame("E://Game/savegames/save2.dat", progressTwo);
        saveGame("E://Game/savegames/save3.dat", progressThree);

        zipFiles1("E://Game/savegames/zip.zip", "E://Game/savegames/save1.dat",
                                                          "E://Game/savegames/save2.dat",
                                                          "E://Game/savegames/save3.dat");

        deleteFiles("E://Game/savegames/save1.dat",
                    "E://Game/savegames/save2.dat",
                    "E://Game/savegames/save3.dat");


    }

    public static void saveGame(String save, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(save);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void zipFiles(String zip, String... files) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zip))){
            for (String file : files) {
                File fileToZip = new File(file);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry entry = new ZipEntry(fileToZip.getName());
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void deleteFiles(String ... files){
        for (String file : files){
            File deleteFile = new File(file);
            deleteFile.delete();
        }
    }


    public static void zipFiles1(String zip, String... files) {
        try {
            FileOutputStream fos = new FileOutputStream(zip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            for (String file : files) {
                File fileToZip = new File(file);
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zipOut.putNextEntry(zipEntry);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
            zipOut.close();
            fos.close();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }
}