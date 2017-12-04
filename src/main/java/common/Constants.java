package common;

public final class Constants {
    public static int OK = 0;
    public static int ERROR = -1;

    /** 错误的超级密码 */
    public static int ERROR_SUPER_PWD = -2;

    /** 错误的用户密码校验 */
    public static int ERROR_PWD = -3;

    /** 错误的用户业务校验，即该用户无权做该业务 */
    public static int ERROR_BUSIRULE = -4;
    /** 赵志明加入开始 */
    public static String subscriputionIProvisionAction="60241";
    public static String chgAccPayNumAction="10535";
    public static String qryChgarAction="10135";
    public static String uniAccPayNewAction="10162";
    public static String removeAccPayNewAction="10161";
    public static String uniCustomerAction="10540";
    public static String chgarAction="10102";
    public static String chgAccPayNewAction="10505";
    /** 赵志明加入结束 */
    public static String AIIP_EXEC_BMS = "aiip_exec_BMS";
    public static String AIIP_EXEC_ACCT = "aiip_exec_ACCT";
    public static String AIIP_EXEC_QHF = "aiip_exec_QHF";
    public static String AIIP_EXEC_ABS = "aiip_exec_ABS";
    public static String AIIP_EXEC_CDR = "aiip_exec_CDR";
    public static String AIIP_EXEC_SYS = "aiip_exec_SYS";
    public static String AIIP_EXEC_SYNC = "aiip_exec_SYNC";
    public static String AIIP_EXEC_INQ = "aiip_exec_INQ";

    // SESSION KEY值
    public static String RANDOM_TOKEN = "com.asiainfo.frame.web.RANDOM";
    public static String LOGIN_TOKEN = "com.asiainfo.frame.web.LOGIN";
    
    public static String BUSI_ACCEPT = "busi.accept";
    //主产品/节点列表key值
    public static String Node_PdtList="node_pdtlist";
    public static String Node_Set="node_set";
    //子产品/节点列表key值
    public static String c_Node_PdtList="c_node_pdtlist";
    public static String c_Node_Set="c_node_set";
    
    //活动/节点列表key值
    public static String Node_SchemeList="node_scheme_list";
    public static String Node_SchemeSet="node_scheme_set";
    
    // ERROR KEY值
    public static String ERROR_KEY = "com.asiainfo.frame.web.ERROR";

    // AppStack KEY值
    public static String STACK = "stack";

    // 错误页面跳转name
    public static String STACKJSP = "errpage";

    // 统一报错jsp跳转页面nextPage的名称
    public static String COMMONRESULTJSP = "commonResultjsp";
    // 缴费成功jsp跳转页面nextPage的名称
    public static String DEPOSITOK = "depositOK";

    // 统一报错信息对象名称
    public static String COMMONRESULT = "commonResult";
    // 打印票据对象名称
    public static String DEPOSITSHOUJU = "depositShouju";
    // 工单信息
    public static String APPWFINFO = "wfInfoPrint";
    
    
    
    

    /** 正确页面跳转name */
    public static String SUCCESS = "success";
    public static String PAGE_SUCCESS = "okpagecommonResult";
    /** 统一模式成功页面 */
    public static String PAGE_OKPAGE_COMMON_RESULT = "okpagecommonResult";
    /** 统一模式提示页面 */
    public static String PAGE_OKPAGE_COMMON_NOTICE = "commonOkNotice";
    /** 统一模式成功页面Pop弹出窗口 */
    public static String PAGE_OKPAGE_COMMON_RESULT_POP = "okpagecommonResultPop";
    /** 统一模式报错页面 */ 
    public static String PAGE_ERRPAGE_COMMON_RESULT = "errpagecommonResult";
    /** AJAX提交统一模式报错页面 */ 
    public static String PAGE_ERRPAGE_COMMON_AJAX_RESULT = "errpagecommonAjaxResult";
    /** 统一模式报错页面Pop弹出窗口 */
    public static String PAGE_ERRPAGE_COMMON_RESULT_POP = "errpagecommonResultPop";
    /** 验证报错但是照常显示用户信息 */
    public static String PAGE_ERRPAGE_COMMON_SHOWUSERINFO="checkErrorButShowUserInfo";
    /** 验证报错但是照常显示智能网用户信息 */
    public static String PAGE_ERRPAGE_COMMON_SHOWPPSUSERINFO="checkErrorButShowPPSUserInfo";
    
    /** 针对具体用户不允许做某业务的报错页面 */
    public static String PAGE_ERROR_BUSIRULE = "errorBusiRule";
    /** 免填单页面 */
    public static String PAGE_WF_INFOPRINT = "wfInfoPrint";
    
    /** 批量任务成功提交显示页面 */
    public static String PAGE_BATJOBOK = "batJobOK";
    /** 费用相关 */
    //标示费用动作  1：改费 2：收费
    public static String CAL_MODI_FEE_FLAG_1 = "1";
    public static String CAL_MODI_FEE_FLAG_2 = "2";
    //费用标志  Y：已付；（营业不欠费）N：未付；（欠费） T：转帐务未付；（营业欠费，但不处理，等帐务收到费修改标志为S） S：转帐务已付；

    public static String FEE_FLAG_ALL = "Y,N,T,S";
    public static String FEE_FLAG_Y = "Y";
    public static String FEE_FLAG_N = "N";
    public static String FEE_FLAG_T = "T";
    public static String FEE_FLAG_S = "S";
    
  // 发票标志 1：发票未打；2：预打发票；3：正常打发票；
    public static String IS_PRE_PRINT_1 = "1";
    public static String IS_PRE_PRINT_2 = "2";
    public static String IS_PRE_PRINT_3 = "3";
    /** 批量业务上传文件的公共页面 */
    public static String PAGE_BATCH_BUSI_UPFILE = "batchBusiUpFile"; 
    public static String PAGE_BATCH_BUSI_MODE = "batchbusimode"; 
    
    /* 批量任务传递的VO 对象名称 com.asiainfo.crm.service.batch.BatchJobVO.java */
    public static String BATCHJOBVO = "BATCHJOBVO"; 
   
//  打印标志，0为预打，1为正常打，2为重打，3为补打
    public static String PAY_FLAG_0 = "0";
    public static String PAY_FLAG_1 = "1";
    public static String PAY_FLAG_2 = "2";
    public static String PAY_FLAG_3 = "3";
    /** 批量任务 数据上传方式 */
    public static String JOB_DATA_TYPE_FILE_ONE = "0"; //单文件方式
    public static String JOB_DATA_TYPE_HAODUAN = "1"; //号段方式
    public static String JOB_DATA_TYPE_FILE_MUL = "2"; //多文件方式
    public static String JOB_DATA_TYPE_FILE_FIVE = "5"; //后台在处理文件方式
    
    
    /** 文件上传保留方式 */
    public static String FILE_TYPE_MID = "MID"; //中间文件， 以后可以删除。
    public static String FILE_TYPE_AR = "AR"; //归档文件， 永久保留
    
    //收费方式         0收费打印发票 "1"收费不打印发票"2"预打发票 "3"不收费不打印发票 
    public static String PAY_OR_INVOICE_0 = "0";
    public static String PAY_OR_INVOICE_1 = "1";
    public static String PAY_OR_INVOICE_2 = "2";
    public static String PAY_OR_INVOICE_3 = "3";
    
    /** 业务受理成功（含费用票据打印）页面 */
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
    
    /** 服务类型 */
    //GSM单模
    public static final String GSM_MN = "1001";
    //GSM智能网
    public static final String GSM_IN = "1011";
    //GSM双模
    public static final String GSM_DB = "1301";
    //C网单模
    public static final String CDMA_MN = "2001";
    //C网智能网
    public static final String CDMA_IN = "2011";
    //C网双模
    public static final String CDMA_DB = "2301";
    //IP注册业务
    public static final String NIP = "4001";
    //193计费话单
    public static final String N193 = "5001";
    
    //详单已经被屏蔽
    public static final String DETAIL_IS_SHIELD = "1";
    //详单屏蔽取消
    public static final String DETAIL_IS_SHIELD_CANCEL = "2";
    //详单状态修改
    public static final String DETAIL_STAUTS = "1";
    //详单状态屏蔽
    public static final String DETAIL_SHIELD = "2";
    //详单有效状态
    public static final String DETAIL_STAUTS_ENABLED = "1";
    //165在线用户清除
    public static final String CLEAR_ONLINE_165="aaa";
    
  
    /**  文档资料维护使用的  **/
    public static final String FILE_SEP = System.getProperty("file.separator");
    
    //session中储存的备选的服务号码
    public static final String GLOAB_SVCNUM = "GLOAB_SVCNUM";
    //session中存储的备选的服务密码
    public static final String GLOAB_PASSWORD = "GLOAB_PASSWORD";
    //request.getParameter()中参数key,代表是否自动提交号码入口下一步
    public static final String AUTO_SVCNUM_NEXT_KEY = "framePara.autoSvcNumNext";
    public static final String AUTO_SVCNUM_NEXT_VALUE = "1";
    public final static String BAF_INFO_IN_SESSION = "BAF_INFO";
    //订单状态
    public final static String CHECK_FAIL_STATUS = "37";//事前审批不通过
    

    //国际漫游包类型
    public final static String ROAM_PACKAGE_CATA = "1";
    public final static String ROAM_PACKAGE_DEPOSIT_TYPE = "K07G_001";
    //国际长途包类型
    public final static String LONG_PACKAGE_CATA = "2";
    public final static String LONG_PACKAGE_DEPOSIT_TYPE = "K07G_002";

    //订单处理状态:0 预订单生成;3 延时生效;4 已作废;5 待自检;8 正在执行;9 已竣工;51 自检通过;52 自检驳回 begin,31 等待提交审批
    public final static String ACCEPT_STATUS_CREATE = "0";//预订单生成
    public final static String ACCEPT_STATUS_COMPLETE = "9";//已竣工
    public final static String ACCEPT_STATUS_FOR_COMMIT_CHECK_STATUS = "31";//等待提交审批
    //end
    
    //套餐类型 
    public final static String PACK_TYPE_BEGIN = "1000";
    public final static String PACK_TYPE_END = "2000";
    //end
   
    
    // 开户新装  begin
    public final static String OPEN_NEW = "10202";
    
    // end 
    private Constants() {
    }
}