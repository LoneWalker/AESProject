package aes;

/**
 * Created by azhar on 11/13/16.
 */
public class Constants {
    public static final String YES="Yes";
    public static final String NO="No";
    public static final String CBC="CBC";
    public static final String ECB="ECB";

    public static final String COMPARISON_MODE_ENCRYPT="Encrypt";
    public static final String COMPARISON_MODE_DECRYPT="Decrypt";

    public static final String KEY_SIZE_128="128";
    public static final String KEY_SIZE_192="192";

    public static int blockSizeinBytes=16;

    public static byte padkMarker = (byte) 0x80;
}
