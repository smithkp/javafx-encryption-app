import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;



public class Enkript{

    private KeyGenerator keyGen;
    private static SecretKey key;
    private static String encodedKey;
    private static String encodedIV;
    private static Cipher cipher;
    private static String mode;
    private static IvParameterSpec iv;

    /*
    *CONSTRUCTOR: Generates 128-bit key, Initialization vector and instance of cipher with mode selected in interface
    *             Base64 encoded key and IV are both created for writing to text file and user input during decryption
    */

    public Enkript(String mode) throws Exception {
        keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        key = keyGen.generateKey();
        encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        byte[]ivBytes = generateIV();
        iv = new IvParameterSpec(ivBytes);
        encodedIV = Base64.getEncoder().encodeToString(iv.getIV());
        this.mode = mode;

        cipher = Cipher.getInstance("AES/"+ mode +"/PKCS5Padding");

    }

    /*
     *   grab file method used to locate file specified by the user and return it as
     *   an array of bytes for encryption. Finds the file using file path placed in
     *   textfield.
     */

    public byte[] grabFile(String filePath){

        File f = new File(filePath);
        byte[] fileData = null;

        InputStream inStream = null;

        try {
            inStream = new FileInputStream(f);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            fileData = new byte[inStream.available()];
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try{
            inStream.read(fileData);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return fileData;
    }

    /*
     *Encrypts file data from grabFile method and saves it
     */

    public void encryptFile(byte[] fileData,String path,String fileName){

        byte[] encrypted;

        try{
            if(mode=="ECB"){
                cipher.init(Cipher.ENCRYPT_MODE, key);
                encrypted = cipher.doFinal(fileData);
                saveFile(encrypted,path,fileName);
            }
            else {
                cipher.init(Cipher.ENCRYPT_MODE, key,iv);
                encrypted = cipher.doFinal(fileData);
                saveFile(encrypted,path,fileName);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /*
     *Decrypts file data from grabFile method and saves it. Decodes key and iv from base64 to use for decryption.
     */

    public void decryptFile(byte[] encryptedFileData, String mode,String path,String fileName,String k,String iv) {
        byte[] decrypted = null;

        byte[] decodedKey = Base64.getDecoder().decode(k);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        byte[] decodedIV = Base64.getDecoder().decode(iv);
        IvParameterSpec originalIV = new IvParameterSpec(decodedIV,0,decodedIV.length);



        try {
            if(mode=="ECB"){
                cipher.init(Cipher.DECRYPT_MODE, originalKey);
                decrypted = cipher.doFinal(encryptedFileData);
                saveFile(decrypted,path,fileName);
            }
            else{
                cipher.init(Cipher.DECRYPT_MODE, originalKey,originalIV);
                decrypted = cipher.doFinal(encryptedFileData);
                saveFile(decrypted,path,fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*
     *Savefile method is called after encrypting/decrypting is done in above methods.
     * Creates a directory containing the encrypted file and a text file defining
     * key and iv.
     */

    public static void saveFile(byte[] fileBytes, String path, String fileName) throws IOException{

        File f = new File(path+"/output");
        f.mkdir();
        FileOutputStream outFile = new FileOutputStream(path+"/output/"+fileName);
        PrintWriter kFile = new PrintWriter(path+"/output/kee.txt");
        kFile.println("key: " + encodedKey +"\n\n" + "IV: " + encodedIV);
        kFile.close();
        outFile.write(fileBytes);
        outFile.close();
    }

    /*
     *Generate iv method creates 16 byte iv using SecureRandom
     */

    private static byte[] generateIV(){
        SecureRandom rand = new SecureRandom();
        byte bytes[] = new byte[16];
        rand.nextBytes(bytes);
        return bytes;
    }
}

