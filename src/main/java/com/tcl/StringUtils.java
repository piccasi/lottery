package com.tcl;

import java.util.StringTokenizer;

import com.tcl.ByteList;

/**
 * �������� : Dec 24, 2008 7:38:16 PM<br>
 * ���� : yaoq<br>
 * ģ�� : <br>
 * ���� : <br>
 * �޸���ʷ: ��� ���� �޸��� �޸�ԭ�� <br>
 * 1 �ع���ʼ<br>
 * 2 <br>
 */
public class StringUtils {
    /**
     * @return ����Ż�
     */
    public static String getFavour(String s) {
        if (s == null || s.equals(""))
            return "";
        StringTokenizer st = new StringTokenizer(s, "$");
        int cnt = 0;
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken().trim();
            int i = tmp.indexOf(",");
            if (i != -1) {
                try {
                    cnt = cnt + Integer.parseInt(tmp.substring(i + 1));
                }
                catch (Exception e) {
                    // ignore
                }
            }
        }
        return String.valueOf(cnt);
    }

    /**
     * ����д��ĸ��д
     * @param str
     * @return
     */
    public static String getFirstUpper(String str) {
        String tmp = "";
        str = trim(str);
        if (!str.equals("")) {
            if (str.length() > 1)
                tmp = toUpperCase(str.substring(0, 1)) + str.substring(1, str.length());
            else
                tmp = toUpperCase(str);
        }

        return tmp;
    }

    /**
     * ת���ɴ�д
     * @param str
     * @return
     */
    public static String toUpperCase(String str) {
        return trim(str).toUpperCase();
    }

    /**
     * ת����Сд
     * @param str
     * @return
     */
    public static String toLowerCase(String str) {
        return trim(str).toLowerCase();
    }

    public static String trim(String s) {
        return getNotNullString(s);
    }

    /*
     * ��StringΪnull��"",��ת��Ϊ{}
     */
    public static String blankToBracket(String str) {
        if (str == null || str.equals(""))
            return "{}";
        return str;
    }

    /*
     * ��StringΪnull��"",��ת��Ϊ{}
     */
    public static String blankToZero(String str) {
        if (str == null || str.equals(""))
            return "0";
        return str;
    }

    /**
     * ���һ��ǰ�߲�n��var��src�ֶ�
     * @param src
     * @param n
     * @param var
     * @return
     */
    public static String getTrimString(String src, int n, String var) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < n; i++) {
            buf.append(var);
        }
        buf.append(src);

        return buf.toString();
    }

    /**
     * ���һ�����κ�Ķ����ִ�<br>
     * ע������=conzt�ִ��ĳ���
     * @param conzt �����ִ�
     * @param var �����ִ�
     * @return String
     */
    public static String getTrimString(String conzt, String var) {
        String ret = conzt + var;
        return ret.substring(var.length());
    }

    /**
     * ���һ������Ϊn���ֶ�<br>
     * ����<br>
     * 1��src�ĳ���<n������src�󲹿ո�<br>
     * 2��src==null,�򷵻�n���ո�<br>
     * 3��src.length>=n,�򷵻��ִ�����=src.length
     * @param src
     * @param n
     * @return
     */
    public static String getTrimRight(String src, int n) {
        StringBuffer buf = new StringBuffer();

        int lngth = src != null ? src.length() : 0;
        buf.append(src);
        for (int i = 0; i < n - lngth; i++) {
            buf.append(" ");
        }

        return buf.toString();
    }

    /**
     * ���һ�����κ�Ķ����ִ�<br>
     * ע������=conzt�ִ��ĳ���
     * @param conzt �����ִ�
     * @param var �����ִ�
     * @return String
     */
    public static String getTrimString(String conzt, int var) {
        String ret = conzt + var;
        return ret.substring(ret.length() - conzt.length());
    }

    /**
     * �������ִ������´��� 1���ִ��д��ڡ�|�����ÿո���棻 2���ִ��д��ڡ�\n������\\n���棻 3���ִ��д��ڡ�\r\n������\r\\n���棻
     * 4���ִ�Ϊ�գ�ֱ�ӷ��ؿ��ִ���
     * @param src
     * @return
     */
    public static String getTrimString(String src) {
        if (src == null) {
            return "";
        }
        byte[] bytes = src.getBytes();
        ByteList bList = new ByteList(bytes.length);
        byte word;
        for (int i = 0; i < bytes.length; i++) {
            switch (word = bytes[i]) {
            case '\r':
                if ((word = bytes[i + 1]) == '\n') {
                    bList.addAll("\\n".getBytes());
                    i++;
                }
                break;
            case '\n':
                bList.addAll("\\n".getBytes());
                break;
            case '|':
                bList.addAll(" ".getBytes());
                break;
            default:
                bList.add(word);
            }
        }

        return bList.toString();
    }

    /**
     * ��ȡ�ǿ��ִ�<br>
     * ��������������ִ�sΪnull������Ĭ���ִ�sdefault�����򷵻��ִ�s
     * @param s
     * @param sdefault Ĭ��ֵ
     * @return
     */
    public static String getNotNullString(String s, String sdefault) {
        return s != null ? s.trim() : sdefault;
    }

    /**
     * ��ȡ�ǿ��ִ� ��������������ִ�sΪnull�����ؿ��ִ������򷵻��ִ�s
     * @param s
     * @return
     */
    public static String getNotNullString(String s) {
        return s != null ? s.trim() : "";
    }

    /**
     * ���������͵�String����""ת��Ϊ0
     * @param s
     * @return
     */
    public static String getDoubleString(String s) {
        if (s == null)
            s = "0";
        if (s.trim().equals(""))
            s = "0";
        return s;
    }

    /**
     * ����ֵ����ת�����ִ�<br>
     * �����������������ֵiData����Ĭ��ֵiNull�����ؿմ������򷵻ؽ�iData��Ϊ�ִ�����
     * @param iData
     * @param iNull
     * @return
     */
    public static String getInt2String(int iData, int iNull) {
        return iData != iNull ? String.valueOf(iData) : "";
    }

    /**
     * ����ֵ����ת�����ִ�<br>
     * �����������������ֵlData����Ĭ��ֵlNull�����ؿմ������򷵻ؽ�lData��Ϊ�ִ�����
     * @param lData
     * @param lNull
     * @return
     */
    public static String getLong2String(long lData, long lNull) {
        return lData != lNull ? String.valueOf(lData) : "";
    }

    /**
     * ����ֵ����ת�����ִ�<br>
     * �����������������ֵfData����Ĭ��ֵfNull�����ؿմ������򷵻ؽ�fData��Ϊ�ִ�����
     * @param fData
     * @param fNull
     * @return
     */
    public static String getFloat2String(float fData, float fNull) {
        return fData != fNull ? String.valueOf(fData) : "";
    }

    /**
     * ����ֵ����ת�����ִ�<br>
     * �����������������ֵdData����Ĭ��ֵdNull�����ؿմ������򷵻ؽ�dData��Ϊ�ִ�����
     * @param dData
     * @param dNull
     * @return
     */
    public static String getDouble2String(double dData, double dNull) {
        return dData != dNull ? String.valueOf(dData) : "";
    }

    public static int getString2Int(String str) {
        int t = -1;
        try {
            t = Integer.parseInt(str);
        }
        catch (Exception e) {
            t = -1;
        }
        return t;

    }

    /*
     * ��double����ȥ��С��������0����.0��.00������£� dData ��ת����double
     */
    public static String doubleRemove0(double dData) {
        String tmp = Double.toString(dData);
        if (tmp.length() >= 3) {
            String a = tmp.substring(tmp.length() - 2, tmp.length());
            if (".0".equals(a))
                tmp = tmp.substring(0, tmp.length() - 2);
        }
        if ("0".equals(tmp))
            tmp = "";

        return tmp;
    }

    /**
     * �������е�����ת����sql�����,���� field in ('a ','b ')
     * @param feildname �ֶ�����
     * @param arrays ����('a','b',...)
     * @param fieldtype ��������("CHAR")
     * @param fieldlen �ֶγ���
     * @return
     */
    public static String getSqlIn(String feildname, String[] arrays, String fieldtype, int fieldlen) {
        String tmp = "";
        if (feildname != null) {
            for (int i = 0; i < arrays.length; i++) {
                if (fieldtype.equals("CHAR")) {
                    if (i == 0)
                        tmp = tmp + "'" + getTrimRight(arrays[i], fieldlen) + "'";
                    else
                        tmp = tmp + ",'" + getTrimRight(arrays[i], fieldlen) + "'";
                }
                else {
                    if (i == 0)
                        tmp = tmp + arrays[i];
                    else
                        tmp = tmp + "," + arrays[i];
                }
            }
        }

        tmp = " " + feildname + " in (" + tmp + ")";
        return tmp;
    }

    /**
     * ���ַ���ת��������YYYY-MM-DD HH24:MI:SS
     * @param str �����ַ���YYYYMMDDHH24MISS
     * @return
     */
    public static String toFormatDate(String str) {
        String sRet;
        if (str != null) {
            str = str.trim();
            if (str.trim().length() == 8)
                sRet = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
            else if (str.trim().length() == 14)
                sRet = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8) + " "
                        + str.substring(8, 10) + ":" + str.substring(10, 12) + ":" + str.substring(12, 14);
            else
                sRet = str;
        }
        else {
            sRet = " ";
        }
        return sRet;
    }

    /**
     * ����ǰ���0������
     * @param i
     * @param length
     * @return
     */
    public static String getFormatInt(int i, int length) {
        String sRet = Integer.toString(i);
        String sI = "";
        sI = Integer.toString(i);
        if (sI.length() < length) {
            for (int k = 0; k < length - sI.length(); k++) {
                sRet = "0" + sRet;
            }
        }

        return sRet;
    }

    /**
     * ���ָ�����ֵ�����
     * @param str ������ַ���
     * @param splitstr �ָ���
     * @return
     */
    public static String[] splitStr(String str, String splitstr) {
        String[] tmp = new String[0];
        str.replaceAll(splitstr, " " + splitstr);
        if (!StringUtils.getNotNullString(str).equals("")) {
            StringTokenizer tst = new StringTokenizer(str, splitstr);
            tmp = new String[tst.countTokens()];
            int i = 0;
            while (tst.hasMoreTokens()) {
                tmp[i] = StringUtils.getNotNullString(tst.nextToken());
                i++;
            }
        }
        return tmp;
    }

    /**
     * �ַ����滻��(��ΪString.replaceAll������Щ�ַ��᲻���滻).���ʺϴ����������滻
     * @param strSource
     * @param strFrom
     * @param strTo
     * @return
     */
    public static java.lang.String replace(java.lang.String strSource, java.lang.String strFrom, java.lang.String strTo) {
        java.lang.String strDest = "";
        int intFromLen = strFrom.length();
        int intPos;

        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            strDest = strDest + strSource.substring(0, intPos);
            strDest = strDest + strTo;
            strSource = strSource.substring(intPos + intFromLen);
        }
        strDest = strDest + strSource;

        return strDest;
    }

    /**
     * yq added ������0�����繤��ID�����ɹ����ǣ����ڣ�sequence 200605 �� 00000000�� 1
     * 
     * @param str
     * @param size
     * @param padChar
     * @return
     */
    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        }
        int pads = size - str.length();
        if (pads <= 0) {
            return str;
        }
        return padding(pads, padChar).concat(str);
    }

    private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if (repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        }
        final char[] buf = new char[repeat];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = padChar;
        }
        return new String(buf);
    }

    /**
     * ���ܣ��������Ƴ��Ƚ�ȡ�ַ������ַ����а������֣�һ�����ֵ��������ַ���
     * @param strParameter Ҫ��ȡ���ַ���
     * @param limitLength ��ȡ�ĳ���
     * @return ��ȡ����ַ���
     */
    public static String getStrByLen(String strParameter, int limitLength) {
        String return_str = strParameter;// ���ص��ַ���
        int temp_int = 0;// ������ת���������ַ�����ַ�������
        int cut_int = 0;// ��ԭʼ�ַ�����ȡ�ĳ���
        byte[] b = strParameter.getBytes();// ���ַ���ת�����ַ�����

        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0) {
                temp_int = temp_int + 1;
            }
            else {
                temp_int = temp_int + 2;// һ�����ֵ��������ַ�
                i++;
            }
            cut_int++;

            if (temp_int >= limitLength) {
                if (temp_int % 2 != 0 && b[temp_int - 1] < 0) {
                    cut_int--;
                }
                return_str = return_str.substring(0, cut_int);
                break;
            }
        }
        return return_str;

    }

    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    public static void main(String[] args) {
        try {
            System.out.println(StringUtils.getStrByLen("��ABC��DEF", 6));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String copyChar(String c, int n) {
        if (c == null || n < 1)
            return "";
        String rtnStr = "";
        for (int i = 0; i < n; i++) {
            rtnStr = rtnStr + c;
        }
        return rtnStr;
    }

    public static String substring(String in, int start, int end) {
        String out = substrNoAppend(in, start, end);

        if (in.length() > end) {
            out += ADD_MESSAGE;
        }
        return out;
    }

    public static String substrNoAppend(String in, int start, int end) {
        if (in == null) {
            return "";
        }
        String out = org.apache.commons.lang.StringUtils.substring(in, start, end);

        return out;
    }

    public static boolean isEmpty(String in) {
        if (in == null)
            return true;
        if (in.trim().equals(""))
            return true;
        return false;
    }

    /**
     * 
     * ������������\t���ִ���; split������\t\t ֻ�ܵõ�length=0; bug fixed at 080119
     * 
     * @param inSTR
     * @param splitChar
     * @return
     */
    public static String[] split(final String inSTR, char splitChar) {
        String temp = inSTR.replaceAll("" + splitChar, " " + splitChar + " ");
        return temp.split(String.valueOf(splitChar));
    }

    private final static String ADD_MESSAGE = "...";
}
