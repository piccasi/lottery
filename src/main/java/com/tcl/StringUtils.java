package com.tcl;

import java.util.StringTokenizer;

import com.tcl.ByteList;

/**
 * 建立日期 : Dec 24, 2008 7:38:16 PM<br>
 * 作者 : yaoq<br>
 * 模块 : <br>
 * 描述 : <br>
 * 修改历史: 序号 日期 修改人 修改原因 <br>
 * 1 重构开始<br>
 * 2 <br>
 */
public class StringUtils {
    /**
     * @return 获得优惠
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
     * 将首写字母大写
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
     * 转换成大写
     * @param str
     * @return
     */
    public static String toUpperCase(String str) {
        return trim(str).toUpperCase();
    }

    /**
     * 转换成小写
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
     * 若String为null或"",则转换为{}
     */
    public static String blankToBracket(String str) {
        if (str == null || str.equals(""))
            return "{}";
        return str;
    }

    /*
     * 若String为null或"",则转换为{}
     */
    public static String blankToZero(String str) {
        if (str == null || str.equals(""))
            return "0";
        return str;
    }

    /**
     * 获得一个前边补n个var的src字段
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
     * 获得一个修饰后的定长字串<br>
     * 注：长度=conzt字串的长度
     * @param conzt 常量字串
     * @param var 变量字串
     * @return String
     */
    public static String getTrimString(String conzt, String var) {
        String ret = conzt + var;
        return ret.substring(var.length());
    }

    /**
     * 获得一个长度为n的字段<br>
     * 规则：<br>
     * 1、src的长度<n，则在src后补空格<br>
     * 2、src==null,则返回n个空格<br>
     * 3、src.length>=n,则返回字串长度=src.length
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
     * 获得一个修饰后的定长字串<br>
     * 注：长度=conzt字串的长度
     * @param conzt 常量字串
     * @param var 变量字串
     * @return String
     */
    public static String getTrimString(String conzt, int var) {
        String ret = conzt + var;
        return ret.substring(ret.length() - conzt.length());
    }

    /**
     * 将输入字串作如下处理： 1、字串中存在【|】，用空格代替； 2、字串中存在【\n】，用\\n代替； 3、字串中存在【\r\n】，用\r\\n代替；
     * 4、字串为空，直接返回空字串。
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
     * 获取非空字串<br>
     * 满足条件：如果字串s为null，返回默认字串sdefault，否则返回字串s
     * @param s
     * @param sdefault 默认值
     * @return
     */
    public static String getNotNullString(String s, String sdefault) {
        return s != null ? s.trim() : sdefault;
    }

    /**
     * 获取非空字串 满足条件：如果字串s为null，返回空字串，否则返回字串s
     * @param s
     * @return
     */
    public static String getNotNullString(String s) {
        return s != null ? s.trim() : "";
    }

    /**
     * 返回数字型的String。将""转换为0
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
     * 将数值类型转换成字串<br>
     * 满足条件：如果数据值iData等于默认值iNull，返回空串，否则返回将iData作为字串返回
     * @param iData
     * @param iNull
     * @return
     */
    public static String getInt2String(int iData, int iNull) {
        return iData != iNull ? String.valueOf(iData) : "";
    }

    /**
     * 将数值类型转换成字串<br>
     * 满足条件：如果数据值lData等于默认值lNull，返回空串，否则返回将lData作为字串返回
     * @param lData
     * @param lNull
     * @return
     */
    public static String getLong2String(long lData, long lNull) {
        return lData != lNull ? String.valueOf(lData) : "";
    }

    /**
     * 将数值类型转换成字串<br>
     * 满足条件：如果数据值fData等于默认值fNull，返回空串，否则返回将fData作为字串返回
     * @param fData
     * @param fNull
     * @return
     */
    public static String getFloat2String(float fData, float fNull) {
        return fData != fNull ? String.valueOf(fData) : "";
    }

    /**
     * 将数值类型转换成字串<br>
     * 满足条件：如果数据值dData等于默认值dNull，返回空串，否则返回将dData作为字串返回
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
     * 将double类型去掉小数点后面的0（在.0和.00的情况下） dData 被转换的double
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
     * 将数组中的数据转换成sql的语句,比如 field in ('a ','b ')
     * @param feildname 字段名称
     * @param arrays 数组('a','b',...)
     * @param fieldtype 数据类型("CHAR")
     * @param fieldlen 字段长度
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
     * 将字符串转换成日期YYYY-MM-DD HH24:MI:SS
     * @param str 日期字符串YYYYMMDDHH24MISS
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
     * 返回前面带0的数字
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
     * 将分隔符拆分到数组
     * @param str 被拆分字符串
     * @param splitstr 分隔符
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
     * 字符串替换，(因为String.replaceAll方法有些字符会不能替换).不适合大数据量的替换
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
     * yq added 左边添加0。比如工单ID的生成规则是：日期＋sequence 200605 ＋ 00000000＋ 1
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
     * 功能：根据限制长度截取字符串（字符串中包括汉字，一个汉字等于两个字符）
     * @param strParameter 要截取的字符串
     * @param limitLength 截取的长度
     * @return 截取后的字符串
     */
    public static String getStrByLen(String strParameter, int limitLength) {
        String return_str = strParameter;// 返回的字符串
        int temp_int = 0;// 将汉字转换成两个字符后的字符串长度
        int cut_int = 0;// 对原始字符串截取的长度
        byte[] b = strParameter.getBytes();// 将字符串转换成字符数组

        for (int i = 0; i < b.length; i++) {
            if (b[i] >= 0) {
                temp_int = temp_int + 1;
            }
            else {
                temp_int = temp_int + 2;// 一个汉字等于两个字符
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
            System.out.println(StringUtils.getStrByLen("我ABC汉DEF", 6));
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
     * 避免连续两个\t出现错误; split方法对\t\t 只能得到length=0; bug fixed at 080119
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
