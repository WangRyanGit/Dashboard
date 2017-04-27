package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class Crc32
{
    private static int[] genTable(int size)
    {
        int ret;
        int i, j;
        int[] table = new int[size];

        for (i = 0; i < size; i++)
        {
            ret = i;
            for (j = 0; j < 8; j++)
            {
                if (ret % 2 != 0)
                    ret = 0xedb88320 ^ (ret >> 1);
                else
                    ret = ret >> 1;
            }
            table[i] = ret;
        }
        return table;
    }

    // int -> byte[]
    public static byte[] ngsteamIntToByteArray(int integer)
    {
        int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer
                : integer)) / 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
        {
            byteArray[3 - n] = (byte) (integer >>> (n * 8));
        }
        return (byteArray);
    }

    // byte[] -> int
    public static int ngsteamByteArrayToInt(byte[] b, int offset)
    {
        int value = 0;
        for (int i = 0; i < 4; i++)
        {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    public static long ngsteamCrcCalcFile(String filePath)
    {
        long checksum = 0;
        File file = new File(filePath);
        try
        {
            CheckedInputStream cis = null;
            cis = new CheckedInputStream(new FileInputStream(file), new CRC32());

            byte[] buf = new byte[1024];
            while (cis.read(buf) >= 0)
                ;
            checksum = cis.getChecksum().getValue();
            cis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            checksum = 0;
        }

        return checksum;
    }

    public static long ngsteamCrc32(String str)
    {
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
        return crc32.getValue();
    }

    /*
     * public static int ngsteamCrcCalc(byte[] buf) {
     * 
     * int[] table; int ret = 0; int i;
     * 
     * table = genTable(256);
     * 
     * ret = 0xffffffff; for (i = 0; i < buf.length; ++i) { ret = table[(ret ^
     * buf[i]) & 0xff] ^ (ret >> 8); } ret ^= 0xffffffff; return ret; }
     */
}
