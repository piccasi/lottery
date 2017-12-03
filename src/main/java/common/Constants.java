package common;

public final class Constants {
    public static int OK = 0;
    public static int ERROR = -1;

    /** ����ĳ������� */
    public static int ERROR_SUPER_PWD = -2;

    /** ������û�����У�� */
    public static int ERROR_PWD = -3;

    /** ������û�ҵ��У�飬�����û���Ȩ����ҵ�� */
    public static int ERROR_BUSIRULE = -4;
    /** ��־�����뿪ʼ */
    public static String subscriputionIProvisionAction="60241";
    public static String chgAccPayNumAction="10535";
    public static String qryChgarAction="10135";
    public static String uniAccPayNewAction="10162";
    public static String removeAccPayNewAction="10161";
    public static String uniCustomerAction="10540";
    public static String chgarAction="10102";
    public static String chgAccPayNewAction="10505";
    /** ��־��������� */
    public static String AIIP_EXEC_BMS = "aiip_exec_BMS";
    public static String AIIP_EXEC_ACCT = "aiip_exec_ACCT";
    public static String AIIP_EXEC_QHF = "aiip_exec_QHF";
    public static String AIIP_EXEC_ABS = "aiip_exec_ABS";
    public static String AIIP_EXEC_CDR = "aiip_exec_CDR";
    public static String AIIP_EXEC_SYS = "aiip_exec_SYS";
    public static String AIIP_EXEC_SYNC = "aiip_exec_SYNC";
    public static String AIIP_EXEC_INQ = "aiip_exec_INQ";

    // SESSION KEYֵ
    public static String RANDOM_TOKEN = "com.asiainfo.frame.web.RANDOM";
    public static String LOGIN_TOKEN = "com.asiainfo.frame.web.LOGIN";
    
    public static String BUSI_ACCEPT = "busi.accept";
    //����Ʒ/�ڵ��б�keyֵ
    public static String Node_PdtList="node_pdtlist";
    public static String Node_Set="node_set";
    //�Ӳ�Ʒ/�ڵ��б�keyֵ
    public static String c_Node_PdtList="c_node_pdtlist";
    public static String c_Node_Set="c_node_set";
    
    //�/�ڵ��б�keyֵ
    public static String Node_SchemeList="node_scheme_list";
    public static String Node_SchemeSet="node_scheme_set";
    
    // ERROR KEYֵ
    public static String ERROR_KEY = "com.asiainfo.frame.web.ERROR";

    // AppStack KEYֵ
    public static String STACK = "stack";

    // ����ҳ����תname
    public static String STACKJSP = "errpage";

    // ͳһ����jsp��תҳ��nextPage������
    public static String COMMONRESULTJSP = "commonResultjsp";
    // �ɷѳɹ�jsp��תҳ��nextPage������
    public static String DEPOSITOK = "depositOK";

    // ͳһ������Ϣ��������
    public static String COMMONRESULT = "commonResult";
    // ��ӡƱ�ݶ�������
    public static String DEPOSITSHOUJU = "depositShouju";
    // ������Ϣ
    public static String APPWFINFO = "wfInfoPrint";
    
    
    
    

    /** ��ȷҳ����תname */
    public static String SUCCESS = "success";
    public static String PAGE_SUCCESS = "okpagecommonResult";
    /** ͳһģʽ�ɹ�ҳ�� */
    public static String PAGE_OKPAGE_COMMON_RESULT = "okpagecommonResult";
    /** ͳһģʽ��ʾҳ�� */
    public static String PAGE_OKPAGE_COMMON_NOTICE = "commonOkNotice";
    /** ͳһģʽ�ɹ�ҳ��Pop�������� */
    public static String PAGE_OKPAGE_COMMON_RESULT_POP = "okpagecommonResultPop";
    /** ͳһģʽ����ҳ�� */ 
    public static String PAGE_ERRPAGE_COMMON_RESULT = "errpagecommonResult";
    /** AJAX�ύͳһģʽ����ҳ�� */ 
    public static String PAGE_ERRPAGE_COMMON_AJAX_RESULT = "errpagecommonAjaxResult";
    /** ͳһģʽ����ҳ��Pop�������� */
    public static String PAGE_ERRPAGE_COMMON_RESULT_POP = "errpagecommonResultPop";
    /** ��֤�������ճ���ʾ�û���Ϣ */
    public static String PAGE_ERRPAGE_COMMON_SHOWUSERINFO="checkErrorButShowUserInfo";
    /** ��֤�������ճ���ʾ�������û���Ϣ */
    public static String PAGE_ERRPAGE_COMMON_SHOWPPSUSERINFO="checkErrorButShowPPSUserInfo";
    
    /** ��Ծ����û���������ĳҵ��ı���ҳ�� */
    public static String PAGE_ERROR_BUSIRULE = "errorBusiRule";
    /** ���ҳ�� */
    public static String PAGE_WF_INFOPRINT = "wfInfoPrint";
    
    /** ��������ɹ��ύ��ʾҳ�� */
    public static String PAGE_BATJOBOK = "batJobOK";
    /** ������� */
    //��ʾ���ö���  1���ķ� 2���շ�
    public static String CAL_MODI_FEE_FLAG_1 = "1";
    public static String CAL_MODI_FEE_FLAG_2 = "2";
    //���ñ�־  Y���Ѹ�����Ӫҵ��Ƿ�ѣ�N��δ������Ƿ�ѣ� T��ת����δ������ӪҵǷ�ѣ����������������յ����޸ı�־ΪS�� S��ת�����Ѹ���

    public static String FEE_FLAG_ALL = "Y,N,T,S";
    public static String FEE_FLAG_Y = "Y";
    public static String FEE_FLAG_N = "N";
    public static String FEE_FLAG_T = "T";
    public static String FEE_FLAG_S = "S";
    
  // ��Ʊ��־ 1����Ʊδ��2��Ԥ��Ʊ��3��������Ʊ��
    public static String IS_PRE_PRINT_1 = "1";
    public static String IS_PRE_PRINT_2 = "2";
    public static String IS_PRE_PRINT_3 = "3";
    /** ����ҵ���ϴ��ļ��Ĺ���ҳ�� */
    public static String PAGE_BATCH_BUSI_UPFILE = "batchBusiUpFile"; 
    public static String PAGE_BATCH_BUSI_MODE = "batchbusimode"; 
    
    /* �������񴫵ݵ�VO �������� com.asiainfo.crm.service.batch.BatchJobVO.java */
    public static String BATCHJOBVO = "BATCHJOBVO"; 
   
//  ��ӡ��־��0ΪԤ��1Ϊ������2Ϊ�ش�3Ϊ����
    public static String PAY_FLAG_0 = "0";
    public static String PAY_FLAG_1 = "1";
    public static String PAY_FLAG_2 = "2";
    public static String PAY_FLAG_3 = "3";
    /** �������� �����ϴ���ʽ */
    public static String JOB_DATA_TYPE_FILE_ONE = "0"; //���ļ���ʽ
    public static String JOB_DATA_TYPE_HAODUAN = "1"; //�Ŷη�ʽ
    public static String JOB_DATA_TYPE_FILE_MUL = "2"; //���ļ���ʽ
    public static String JOB_DATA_TYPE_FILE_FIVE = "5"; //��̨�ڴ����ļ���ʽ
    
    
    /** �ļ��ϴ�������ʽ */
    public static String FILE_TYPE_MID = "MID"; //�м��ļ��� �Ժ����ɾ����
    public static String FILE_TYPE_AR = "AR"; //�鵵�ļ��� ���ñ���
    
    //�շѷ�ʽ         0�շѴ�ӡ��Ʊ "1"�շѲ���ӡ��Ʊ"2"Ԥ��Ʊ "3"���շѲ���ӡ��Ʊ 
    public static String PAY_OR_INVOICE_0 = "0";
    public static String PAY_OR_INVOICE_1 = "1";
    public static String PAY_OR_INVOICE_2 = "2";
    public static String PAY_OR_INVOICE_3 = "3";
    
    /** ҵ������ɹ���������Ʊ�ݴ�ӡ��ҳ�� */
    public static String PAGE_WF_FEEPRINT = "wfFeePrint";
    public static String PAGE_WF_INFOQUERY = "wfInfoQuery";
    public static String PAGE_WF_AUDIT = "wfAudit";
    public static String PAGE_WF_ACCT = "wfAcctPrint";

    public static final String BUSIID = "busiId";
    public static final String BUSIID_INFO = "busiIdInfo";
    public static final String ARRTNFLAG = "arrtnFlag";
    public static final String PWD_CHECKYN = "pwdCheckYn";
    public static final String SERVICE_TYPE = "serviceType";
    public static final String SERVICE_NUM = "serviceNum";
    public static final String USER_PASSWD = "userPasswd";
//    public static final String BUSI_ID = "busiId";
    public static final String ARRTN_FLAG = "arRtnFlag";
    public static final String USERARINFO = "UserArInfo";
    public static final String OPERATOR_ID = "OperatorId";
    public static final String REGION_ID = "RegionId";
    public static final String CARD_TYPE = "CardType";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CARDFEE = "CardFee";
    public static final String BUSIFEE = "BusiFee";
    public static final String FEEINPUTINFO = "FeeInputinfo";
    public static final String BMS_OHTER_INFO = "bmsOtherInfo";
    
    /** �������� */
    //GSM��ģ
    public static final String GSM_MN = "1001";
    //GSM������
    public static final String GSM_IN = "1011";
    //GSM˫ģ
    public static final String GSM_DB = "1301";
    //C����ģ
    public static final String CDMA_MN = "2001";
    //C��������
    public static final String CDMA_IN = "2011";
    //C��˫ģ
    public static final String CDMA_DB = "2301";
    //IPע��ҵ��
    public static final String NIP = "4001";
    //193�Ʒѻ���
    public static final String N193 = "5001";
    
    //�굥�Ѿ�������
    public static final String DETAIL_IS_SHIELD = "1";
    //�굥����ȡ��
    public static final String DETAIL_IS_SHIELD_CANCEL = "2";
    //�굥״̬�޸�
    public static final String DETAIL_STAUTS = "1";
    //�굥״̬����
    public static final String DETAIL_SHIELD = "2";
    //�굥��Ч״̬
    public static final String DETAIL_STAUTS_ENABLED = "1";
    //165�����û����
    public static final String CLEAR_ONLINE_165="aaa";
    
  
    /**  �ĵ�����ά��ʹ�õ�  **/
    public static final String FILE_SEP = System.getProperty("file.separator");
    
    //session�д���ı�ѡ�ķ������
    public static final String GLOAB_SVCNUM = "GLOAB_SVCNUM";
    //session�д洢�ı�ѡ�ķ�������
    public static final String GLOAB_PASSWORD = "GLOAB_PASSWORD";
    //request.getParameter()�в���key,�����Ƿ��Զ��ύ���������һ��
    public static final String AUTO_SVCNUM_NEXT_KEY = "framePara.autoSvcNumNext";
    public static final String AUTO_SVCNUM_NEXT_VALUE = "1";
    public final static String BAF_INFO_IN_SESSION = "BAF_INFO";
    //����״̬
    public final static String CHECK_FAIL_STATUS = "37";//��ǰ������ͨ��
    

    //�������ΰ�����
    public final static String ROAM_PACKAGE_CATA = "1";
    public final static String ROAM_PACKAGE_DEPOSIT_TYPE = "K07G_001";
    //���ʳ�;������
    public final static String LONG_PACKAGE_CATA = "2";
    public final static String LONG_PACKAGE_DEPOSIT_TYPE = "K07G_002";

    //��������״̬:0 Ԥ��������;3 ��ʱ��Ч;4 ������;5 ���Լ�;8 ����ִ��;9 �ѿ���;51 �Լ�ͨ��;52 �Լ첵�� begin,31 �ȴ��ύ����
    public final static String ACCEPT_STATUS_CREATE = "0";//Ԥ��������
    public final static String ACCEPT_STATUS_COMPLETE = "9";//�ѿ���
    public final static String ACCEPT_STATUS_FOR_COMMIT_CHECK_STATUS = "31";//�ȴ��ύ����
    //end
    
    //�ײ����� 
    public final static String PACK_TYPE_BEGIN = "1000";
    public final static String PACK_TYPE_END = "2000";
    //end
   
    
    // ������װ  begin
    public final static String OPEN_NEW = "10202";
    
    // end 
    private Constants() {
    }
}