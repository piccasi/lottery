package com.tcl;

import java.util.Map;

import com.tcl.BafVariants;

public interface StandBafVariants extends BafVariants {
    /**
     * <li>��Ӳ�����б���
     * <li>�����Ĳ����ǿգ�����Ϊnull�������κδ��?ֱ�ӷ��ء�
     */
    public void addParameter(final String key, final String value);

    /**
     * <li>��Ӳ�����б���
     * <li>�����Ĳ����ǿգ�����Ϊnull������Ϊ��ֵΪ"{}"�����̨��
     * <li>czjר��
     */
    public void addNecessaryParam(final String key, final String value);

    /**
     * ���ָ������key��ֵ�������ڣ��򷵻�"",����null
     */
    public String getParameter(final String key);

    /**
     * ������в�����б�
     */
    public Object getAllParameter();
    
    public Map getMapValue();

    /**
     * ��ȡ�����ܵ���Ŀ��
     */
    public int size();

}
