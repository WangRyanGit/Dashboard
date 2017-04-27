package com.ibb.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{

    /**
     * @param 获得随机验证码
     * @return
     */
    public static String generateVerifyCode() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }
    /**
     * 
     * 
     * @param str
     * @param suffix
     * @return
     */
    public static String removeSuffix(String str, String suffix)
            throws Exception
    {
        if (null == str)
            return null;
        if ("".equals(str.trim()))
            return "";

        if (null == suffix || "".equals(suffix))
            return str;

        if (str.endsWith(suffix))
        {
            return str.substring(0, str.length() - suffix.length());
        }

        throw new Exception(str + " 娌℃湁鎸夋寚瀹氬瓧绗︿覆" + suffix + "缁撳熬");
    }

    /**
     * Check the String is blank or not
     * 
     * @param str
     * @return
     * @throws UtilException
     */
    public static boolean isBlank(String str)
    {
        return null == str || "".equals(str.trim());
    }

    public static boolean isBlank(Long str)
    {
        return null == str;
    }

    /**
     * 灏嗗璞¤浆鎴怱tring
     * 
     * @param obj
     * @return
     */
    public static String toString(Object obj)
    {
        if (obj == null)
        {
            return "";
        }
        return obj.toString().trim();
    }

    /**
     * 
     * @param str
     * @return
     */
    public static String getString(String str)
    {
        if (null == str)
            return "";
        return str;

    }

    /**
     * 璁＄畻涓や釜鏁板瓧瀛楃涓茬殑鍜�
     * 
     * @param augend
     * @param addend
     * @return
     * @throws UtilException
     */
    public static String getSum(String augend, String second, String addend)
    {
        if (augend == null)
            augend = "0";
        if (second == null)
            second = "0";
        if (addend == null)
            addend = "0";
        int sum = Integer.parseInt(augend) + Integer.parseInt(second)
                + Integer.parseInt(addend);
        return new Integer(sum).toString();
    }

    public static String change(String str, int n, boolean isLeft)
    {
        if (str == null || str.length() >= n)
            return str;
        StringBuffer s = new StringBuffer();
        for (int i = str.length(); i < n; i++)
            s.append('0');
        if (isLeft)
            return s.append(str).toString();
        else
            return s.insert(0, str).toString();
    }

    public static String getInString(String str)
    {
        if (str == null)
            return null;
        int len = str.length();
        StringBuffer buf = new StringBuffer(len << 1).append('\'');
        for (int i = 0; i < len; i++)
        {
            char Char = str.charAt(i);
            if (',' == Char)
                buf.append("','");
            else
                buf.append(Char);
        }
        return buf.append('\'').toString();
    }

    /**
     * 鏍规嵁鏍囪瘑鑾峰彇str涓渶鍚庝竴涓猣lag鍚庣殑鍐呭
     * 
     * @param str
     * @param flag
     * @return
     */
    public static String getLastStr(String str, String flag)
    {
        if (isBlank(str))
            return null;
        int index = str.lastIndexOf(flag);
        if (index < 0)
        {
            return str;
        }
        else
        {
            return str.substring(index + flag.length());
        }

    }

    /**
     * 鑾峰彇姝ｅ垯琛ㄨ揪寮忓尮閰嶇殑瀛楃涓诧紝灏�绗﹀鐞嗕竴涓嬶紝涓嶇劧鍖归厤鏃朵細璁や綔鍒嗙粍
     * 
     * @param str
     * @return
     */
    public static String getRegexStr(String str)
    {
        String ret = "";
        if (isBlank(str))
            return "";
        if (str.indexOf('$', 0) > -1)
        {
            while (str.length() > 0)
            {
                if (str.indexOf('$', 0) > -1)
                {
                    ret += str.subSequence(0, str.indexOf('$', 0));
                    ret += "\\$";
                    str = str.substring(str.indexOf('$', 0) + 1, str.length());
                }
                else
                {
                    ret += str;
                    str = "";
                }
            }

        }
        else
        {

            ret = str;
        }

        return ret;

    }

    /**
     * 姣旇緝涓や釜String瀵硅薄鍊兼槸鍚︾浉绛�
     * 
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compareString(String str1, String str2)
    {
        if (null == str1)
        {
            str1 = "";
        }
        if (null == str2)
        {
            str2 = "";
        }
        if (str1.trim().equals(str2.trim()))
        {
            return true;
        }
        return false;
    }

    /**
     * 灏唍ame杞崲鎴愰瀛楁瘝澶у啓
     * 
     * @param name
     * @return
     */
    public static String trunUpName(String name)
    {
        if (StringUtil.isBlank(name))
        {
            return name;
        }
        else
        {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
    }

    // 鍘绘帀鍓嶅瀛楃 0 鍜�绌烘牸锛岃嚦灏戜繚鐣欐渶鍙宠竟涓�綅
    public static String ltrim(String inStr)
    {
        StringBuffer sb = new StringBuffer();
        if (inStr == null || inStr.length() == 0)
            return inStr;
        for (int i = 0; i < inStr.length(); i++)
        {
            if ((i == inStr.length() - 1) || inStr.charAt(i) != '0'
                    && inStr.charAt(i) != ' ')
                sb.append(inStr.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 鎶婂瓧绗︿覆鐢ㄧ壒瀹氬瓧绗﹁ˉ鎴愬浐瀹氬瓧鑺傞暱搴︾殑瀛楃涓�
     * 
     * @param src
     *            鍘熷瓧绗︿覆
     * @param temp
     *            琚ˉ鐨勫瓧绗�
     * @param sumLength
     *            琛ュ厖鍚庣殑瀛楄妭闀垮害
     * @param direction
     *            琛ョ殑鏂瑰悜锛孡锛氬乏琛ワ紝R锛氬彸琛ワ紝榛樿鏄乏琛ワ紝蹇界暐澶у皬鍐�
     * @return
     */
    private static String fillStringByByte(String src, char temp,
            int sumLength, String direction)
    {
        String dest = "";
        src = src != null ? src.trim() : "";
        while (--sumLength >= src.getBytes().length)
        {
            dest += temp;
        }
        return "R".equalsIgnoreCase(direction) ? src + dest : dest + src;
    }

    /**
     * 鐢ㄧ壒瀹氱殑瀛楃宸﹁ˉ鎴愬浐瀹氬瓧鑺傞暱搴︾殑瀛楃涓�
     * 
     * @param src
     *            鍘熷瓧绗︿覆
     * @param temp
     *            琚ˉ鐨勫瓧绗�
     * @param sumLength
     *            琛ュ厖鍚庣殑瀛楄妭闀垮害
     * @return
     */
    public static String lFillString(String src, char temp, int sumLength)
    {
        return fillStringByByte(src, temp, sumLength, "L");
    }

    /**
     * 鐢ㄧ壒瀹氱殑瀛楃鍙宠ˉ鎴愬浐瀹氬瓧鑺傞暱搴︾殑瀛楃涓�
     * 
     * @param src
     *            鍘熷瓧绗︿覆
     * @param temp
     *            琚ˉ鐨勫瓧绗�
     * @param sumLength
     *            琛ュ厖鍚庣殑瀛楄妭闀垮害
     * @return
     */
    public static String rFillString(String src, char temp, int sumLength)
    {
        return fillStringByByte(src, temp, sumLength, "R");
    }

    /**
     * 瀵笹BK缂栫爜鐨勫瓧绗︿覆杞崲鎴怳TF-8鐨勫瓧绗︿覆锛屽苟淇濊瘉涓嶄骇鐢熶贡鐮� 杞崲鍚庣殑UTF-8瀛楃涓插彲浠ュ埄鐢╪ew
     * String(UTF-8.getByte(),"GBK")杞洖gbk
     * 
     * @param gbk
     * @return
     */
    public static String convertGBK2UTF8(String gbk)
    {
        String utf8 = "";
        try
        {
            utf8 = new String(gbk2utf8(gbk), "UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return utf8;
    }

    public static byte[] gbk2utf8(String chenese)
    {
        char c[] = chenese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++)
        {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++)
            {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

    public static String replaceBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        char[] chars = dest.toCharArray();
        StringBuffer buffer = new StringBuffer();
        if (chars != null && chars.length > 0)
        {
            for (int i = 0; i < chars.length; i++)
            {
                if ((int) chars[i] != 160)
                {
                    buffer.append(chars[i]);
                }
            }
            dest = buffer.toString();
        }
        return dest;

    }

    /***
     * Delete empty char
     * 
     * @param inputStr
     * @return
     */
    public static String killspace(String inputStr)
    {
        inputStr = (inputStr == null ? "" : inputStr);
        inputStr = inputStr.trim();
        return inputStr;
    }

    public static String killNull(String inputStr)
    {
        inputStr = (inputStr == null ? "" : inputStr);
        return inputStr;
    }

    public static String substring(String string, int index)
    {
        char[] charArray = string.toCharArray();
        int number = 0;
        String substring = "";
        for (int i = 0, j = 0; i < index && j < string.length();)
        {
            char tempChar = charArray[j];
            if (Character.getType(tempChar) == Character.OTHER_LETTER)
            {
                i = i + 2;
                if (i > index)
                {
                    if (i == (index + 1))
                    {
                        continue;
                    }
                }
            }
            else
            {
                i++;
            }
            j++;
            number++;
        }
        substring = string.substring(0, number);
        return substring;
    }

    public static boolean isValidImsi(String imsi)
    {
        if (imsi != null && imsi.length() > 5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    //    
    // public static void main(String [] args){
    // System.out.println(StringUtil.substring("瀹跺洯7锛氳吹鏃�, 7));
    // }
}
