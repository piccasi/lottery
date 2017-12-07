package com.tydic.uniform.hh.constant;

/**
 * <p></p>
 * @author ghp 2015年9月28日 下午4:06:24
 * @ClassName HhConstants  BOSS入参常量
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public class HhConstants {
	//公共常量
	public static final String PUB_REQ="PUB_REQ";
	public static final String TYPE="TYPE";
	public static final String SOO="SOO";
	public static final String SVCCONT="SvcCont";
	public static final String RESP="RESP";
	public static final String RESULT="RESULT";
	public static final String BILLRESULT="Result";
	public static final String BILLMSG="Msg";
	public static final String RESULTMAP="RESULTMAP";
	public static final String SUCCESS="0";
	public static final String ERROR="1";
	public static final String BODY = "BODY";
	public static final String BILLTYPE="Type";
	
	public static final String ESHOPRESULT="rspCode";
	public static final String ESHOPREPDATA="rspData";
	public static final String ESHOPTOPAYLINK="toPayLink";
	
	public static final String CHANNEL_CODE="CHANNEL_CODE";
	public static final String EXT_SYSTEM="EXT_SYSTEM";
	public static final String CHANNEL_CODEVALUE="10027";
	public static final String MOZU_CHANNEL_CODE="894731";
	//代理商渠道编号
	public static final String HHAGENT_CHANNEL_CODEVALUE="10022";
	public static final String EXT_SYSTEMVALUE="102";
	public static final String EXT_SYSTEM_VALUE="102";
	public static final String STAFF_IDMAVLUE="10002";
	public static final String STAFF_ID_VALUE="102";
	
	public static final String ENDPAGE="endPage";
	public static final String ENDPAGEVALUE="1";
	/** -------------------------APP调用接口失败标记----------------------------------------- */
	public static String CODE = "CODE";// 编码
	public static String MESSAGE = "MESSAGE";// 信息
	public static String SUCCESSCODE = "0000";// 成功
	public static String SUCCESSMESSAGE = "成功!";// 失败
	public static String ERRORCODE = "9999";// 失败
    public static String LOGON_ERROR_CODE = "1111";// 会话超时
    public static String LOGON_ERROR_MESSAGE = "请重新登录";// 会话超时


    public static String ERRORMESSAGE = "系统出错，请联系管理员!";// 失败
	public static String REG_ERRORMESSAGE = "校验不通过，账号已存在！";// 失败
	
	
	//2.27	号码套餐查询接口

	public static final String QRY_NUMBER="QRY_NUMBER";
	public static final String OFR_MODE="OFR_MODE";
	public static final String CUST_QRY="CUST_QRY";
	public static final String OFFER_LIST="OFFER_LIST";
	public static final String SERVICE_INFO="SERVICE_INFO";
	public static final String OFFER_DESC="OFFER_DESC";
	public static final String OFFER_NAME="OFFER_NAME";
	public static final String OFR_PRICE="OFR_PRICE";
	
	//2.16   号码资源查询
	public static final String CITY="CITY";
	public static final String NBR_LEVEL="NBR_LEVEL";
	public static final String LAST_NBR="LAST_NBR";
	public static final String TELE_TYPE="TELE_TYPE";
	public static final String NUMBER="NUMBER";
	public static final String CITY_NAME="CITY_NAME";
	public static final String MIN_CONSUME="MIN_CONSUME";
	public static final String PRE_DEPOSIT="PRE_DEPOSIT";
	public static final String SERVICE_NUM="SERVICE_NUM";
	public static final String AREA_CODE="AREA_CODE";
	public static final String NORMAL_BALANCE="NORMAL_BALANCE";
	public static final String PROVINCE_CODE="PROVINCE_CODE";
	
	
	//2.15 号码预占、释放
	public static final String RES_NBR="RES_NBR";
	public static final String CUST_ID="CUST_ID";
	public static final String OPT_TYPE="OPT_TYPE";
	public static final String RES_CODE="RES_CODE";
	
	//2.12 新增订单接口
	//1订单信息
	public static final String BUSINESS_TYPE="BUSINESS_TYPE";
	public static final String EXT_ORDER_ID="EXT_ORDER_ID";
	public static final String ORDER_TYPE="ORDER_TYPE";
	public static final String PAY_STATUS="PAY_STATUS";
	public static final String SALE_ORDER_ID="SALE_ORDER_ID";
	public static final String APPENDIX="APPENDIX";
	public static final String SEL_IN_ORG_ID="SEL_IN_ORG_ID";
	public static final String SALE_ORDER_INST="SALE_ORDER_INST";
	
	//2套餐信息
	public static final String NEW_FLAG="NEW_FLAG";
	public static final String OFFER_ID="OFFER_ID";
	public static final String OFFER_INST_ID="OFFER_INST_ID";
	public static final String OFFER_TYPE_ID="OFFER_TYPE_ID";
	
	public static final String SALE_OFFER_INST="SALE_OFFER_INST";
	
	//3套餐信息
	public static final String ACCT_ITEM_TYPE="ACCT_ITEM_TYPE";
	public static final String AMOUNT="AMOUNT";
	public static final String OBJ_INST_ID="OBJ_INST_ID";
	public static final String OBJ_INST_TYPE="OBJ_INST_TYPE";
	
	public static final String FEE_ITEM="FEE_ITEM";
	
	//4客户信息
	
	public static final String AUTH_FLAG="AUTH_FLAG";
	public static final String CERT_NBR="CERT_NBR";
	public static final String CERT_TYPE="CERT_TYPE";
	public static final String CUST_NAME="CUST_NAME";
	public static final String BIRTHDATE="BIRTHDATE";
	public static final String CUST_FLAG="CUST_FLAG";
	public static final String EMAIL="EMAIL";
	
	public static final String SALE_CUST="SALE_CUST";
	
	//6产品信息
	
	public static final String ACC_NBR="ACC_NBR";
	public static final String PRODUCT_ID="PRODUCT_ID";
	public static final String PROD_INST_ID="PROD_INST_ID";
	
	public static final String SALE_PROD_INST="SALE_PROD_INST";
	
	//7产品实例属性
	
	public static final String ATTR_ID="ATTR_ID";
	public static final String ATTR_NAME="ATTR_NAME";
	public static final String ATTR_VALUE="ATTR_VALUE";
	
	public static final String SALE_PROD_INST_ATTR="SALE_PROD_INST_ATTR";
	
	//8 收货信息
	
	public static final String ADDRESS="ADDRESS";
	public static final String MOBILE="MOBILE";
	public static final String RECEIVE_NAME="RECEIVE_NAME";
	
	public static final String RECEIVE_INFO="RECEIVE_INFO";
	
	//9NUMBER_INFO号码信息
	
	public static final String CITY_CODE="CITY_CODE";
	public static final String NUMBER_LEVEL="NUMBER_LEVEL";
	public static final String PROVICE_NAME="PROVICE_NAME";
	
	public static final String NUMBER_INFO="NUMBER_INFO";
	
	//2.6会员资料查询
	
	public static final String QRY_TYPE="QRY_TYPE";
	public static final String BLACKLIST="BLACKLIST";
	public static final String CERTI_ADDR="CERTI_ADDR";
	public static final String CERTI_NBR="CERTI_NBR";
	public static final String CERTI_TYPE="CERTI_TYPE";
	public static final String CHANNEL_NAME="CHANNEL_NAME";
	public static final String CONTACT_ADDR="CONTACT_ADDR";
	public static final String CUST_NBR="CUST_NBR";
	public static final String CUST_TYPE="CUST_TYPE";
	public static final String CUST_VIP_LEVEL="CUST_VIP_LEVEL";
	public static final String JOIN_DATE="JOIN_DATE";
	public static final String MOBILE_170="MOBILE_170";
	public static final String NICK_NAME="NICK_NAME";
	public static final String PAY_PWD="PAY_PWD";
	public static final String PROD_INST="PROD_INST";
	public static final String MAIN_FLAG="MAIN_FLAG";
	public static final String SEX="SEX";
	public static final String PRODUCT_NAME="PRODUCT_NAME";
	public static final String PROD_OFFER_NAME="PROD_OFFER_NAME";
	public static final String STATUS_CD="STATUS_CD";
	public static final String PROD_OFFER_INST="PROD_OFFER_INST";
	public static final String OFFER_CLASS="OFFER_CLASS";
	public static final String OFFER_TYPE="OFFER_TYPE";
	public static final String PROD_OFFER_INST_ID="PROD_OFFER_INST_ID";
	public static final String PROD_OFFER_ID="PROD_OFFER_ID";
	public static final String EXP_DATE="EXP_DATE";
	public static final String MARKET_PRICE="MARKET_PRICE";
	public static final String EFF_DATE="EFF_DATE";
	public static final String CUST_PAY="CUST_PAY";
	
	//2.14 登录类型
	public static final String LOGIN_TYPE="LOGIN_TYPE";
	public static final String PWD="PWD";
	public static final String CUST_LOGIN="CUST_LOGIN";
	public static final String LOGON_NBR_TYPE="LOGON_NBR_TYPE";
	public static final String CHECK_RESULT="CHECK_RESULT";
	public static final String LOGIN_NBR="LOGIN_NBR";
	public static final String ORG_ID = "ORG_ID";
	public static final String ORG_CODE = "ORG_CODE";
	//2.13注册类型

	public static final String SERVICE_NBR="SERVICE_NBR";
	public static final String CUST="CUST";
	public static final String PUB_RES="PUB_RES";
	
	//2.2修改密码或密码重置
	public static final String MSG="MSG";
	public static final String RESET_TYPE="RESET_TYPE";
	public static final String OLD_PWD="OLD_PWD";
	public static final String NEW_PWD="NEW_PWD";
	public static final String PWD_TYPE="PWD_TYPE";
	//套餐变更
	public static final String SERVICE_OFFER_ID ="SERVICE_OFFER_ID";
	//1.10会员余额查询
	public static final String MEMBERID="MemberId";
	public static final String QRYTYPE="QryType";
	public static final String BALTYPE="BalType";
	public static final String SYSTEMID="SystemID";
	public static final String BALANCEQRY_REQ="BALANCEQRY_REQ";
	
	//2.9订单查询
	public static final String ORDER="ORDER";
	public static final String ORDER_ID="ORDER_ID";
	public static final String START_TIME="START_TIME";
	public static final String END_TIME="END_TIME";
	public static final String PROCESS_STS="PROCESS_STS";
	public static final String PAGE="PAGE";
	public static final String PAGE_NO="PAGE_NO";
	public static final String CREATE_TIME="CREATE_TIME";	
	public static final String ORDER_PRICE="ORDER_PRICE";
	public static final String STATUS_NAME="STATUS_NAME";
	public static final String DEVICE_NUMBER="DEVICE_NUMBER";
	public static final String LOG_NBR="LOG_NBR";
	public static final String LOG_CAMP="LOG_CAMP";
	public static final String ORDER_DESC="ORDER_DESC";
	public static final String EXT_ORDER_LINK="EXT_ORDER_LINK";
	public static final String USER_EVENT_CODE="USER_EVENT_CODE";
	
	public static final String CASH="Cash";
	public static final String FROZENBALANCE="FrozenBalance";
	public static final String WITHHOLDBALANCE="WithholdBalance";
	public static final String OWEFEE="OweFee";
	public static final String XINGCOIN="XingCoin";
	
	public static final String RESUNIT="ResUnit";
	public static final String EXPIRYDATE="ExpiryDate";
	public static final String VALUE="Value";
	public static final String COUNT="Count";
	public static final String EFFECTIVEDATE="EffectiveDate";
	public static final String RESOURCESID="ResourcesID";
	public static final String RESOURCENAME="ResourceName";
	public static final String RESOURCES="Resources";

	public static final String USEBALANCE="UseBalance";
	public static final String REALBALANCE="RealBalance";
	public static final String TOTALBALANCE="TotalBalance";
	public static final String RESOURCESNAME="ResourcesName";
	public static final String RESOURCESUSE="ResourcesUse";
	
	public static final String ACCTBALANCEID="AcctBalanceId";
	public static final String BALANCENAME="BalanceName";
	public static final String BALANCETYPEID="BalanceTypeId";
	public static final String BALANCE="Balance";
	public static final String BALANCECEIL="BalanceCeil";
	public static final String BALANCEFLOOR="BalanceFloor";
	public static final String USABLEBAL="UsableBal";
	public static final String BALDETAIL="BalDetail";
	//1.5 会员账单查询
	public static final String BILLCYCLEID="BillCycleId";
	public static final String BILLINFO_REQ="BILLINFO_REQ";
	public static final String Msg="Msg";
	public static final String Result="Result";
	public static final String BillInfo="BillInfo";
	public static final String AcctInfo="AcctInfo";
	public static final String ResourceId="ResourceId";
	public static final String ResourceName="ResourceName";
	public static final String BillName="BillName";
	public static final String FeeInfo="FeeInfo";
	public static final String Tips="Tips";
	public static final String ServiceNbr="ServiceNbr";
	public static final String CustName="CustName";
	public static final String BillItemName="BillItemName";
	public static final String BillItemValue="BillItemValue";
	public static final String Value="Value";
	public static final String Type="Type";
	public static final String BillCycleId="BillCycleId";
	public static final String SystemId="SystemId";
	public static final String Code="Code";
	public static final String Resources="Resources";
	public static final String EffectiveDate="EffectiveDate";
	public static final String Cash="Cash";
	public static final String ExpiryDate="ExpiryDate";
	public static final String XingCoin="XingCoin";
	//业务受理验证
	public static final String SERVICE_CODE="SERVICE_CODE";
	public static final String BUSI_CHECK="BUSI_CHECK";
	//1.2会员充值
	public static final String PAY_AMOUNT="PAY_AMOUNT";
	public static final String REQUEST_ID="REQUEST_ID";
	public static final String PAY_METHOD="PAY_METHOD";
	public static final String PAY_TYPE="PAY_TYPE";
	public static final String BANK_CODE="BANK_CODE";
	public static final String BANK_SERIAL_NBR="BANK_SERIAL_NBR";
	public static final String STAFF_ID="STAFF_ID";
	public static final String SYSTEM_ID="SYSTEM_ID";
	public static final String RECHARGE_INFO = "RECHARGE_INFO";
	public static final String MEMBER_ID = "MEMBER_ID";
	
	//2.10订单详情
	public static final String ORDER_TYPE_NAME="ORDER_TYPE_NAME";
	
	public static final String COMMODITY="COMMODITY";
	public static final String COMM_ID="COMM_ID";
	public static final String COMM_NAME="COMM_NAME";
	public static final String COMM_NUM="COMM_NUM";
	public static final String DISCOUNT="DISCOUNT";
	public static final String PRICE="PRICE";
	public static final String SERVICE_OFFER_NAME="SERVICE_OFFER_NAME";

	public static final String LOGIS="LOGIS";
	public static final String LOG_CODE="LOG_CODE";
	public static final String LOG_NAME="LOG_NAME";
	
	public static final String OFFER_INST="OFFER_INST";
	public static final String OFFER_SUB_TYPE_ID="OFFER_SUB_TYPE_ID";
	public static final String OFFER_SUB_TYPE_NAME="OFFER_SUB_TYPE_NAME";
	public static final String OFFER_TYPE_NAME="OFFER_TYPE_NAME";
	
	public static final String ORDER_AUDIT="ORDER_AUDIT";
	
	public static final String PAYMENT="PAYMENT";
	public static final String DEDU_AMT="DEDU_AMT";
	public static final String PAYMENT_CODE="PAYMENT_CODE";
	public static final String PAYMENT_SERIAL="PAYMENT_SERIAL";

	//基础业务查询
	public static final String DEPENTENTPRODDTOLIST="DepententProdDtoList";
	public static final String DEPENTENTATTRDTOLIST="depententAttrDtoList";
	public static final String MUTEXPRODUCTIDS="mutexProductIds"; 
	public static final String SERVICEATTRS="serviceAttrs";
	//会员资料修改
	public static final String WX_ACCT="WX_ACCT";
	//判断是否为青牛170号码
	public static final String ACC_NBR_170="ACC_NBR_170";
	public static final String PROD="PROD";
	public static final String QRY_PROD="QRY_PROD";
	//停复机
	public static final String SAVE_SALE_ORDER_INST="SAVE_SALE_ORDER_INST";
	public static final String SAVE_SALE_PROD_INST="SAVE_SALE_PROD_INST";

	//1.10 会员充值记录查询
	public static final String PAGENUMBER="PAGENUMBER";
	public static final String PAGESIZE="PAGESIZE";
	public static final String PAYMENTINFOQUERY="PAYMENTINFOQUERY";
	public static final String BILLVALUE="Value";
	public static final String BILLPAGENUMBER="PageNumber";
	public static final String BILLPAGESIZE="PageSize";
	public static final String LEFTBALANCE="LeftBalance";
	public static final String PAYAMOUNT="PayAmount";
	public static final String PAYCHANNEL="PayChannel";
	public static final String PAYDATE="PayDate";
	public static final String PAYSERIALNBR="PayserialNbr";
	public static final String PREBALANCE="PreBalance";
	public static final String SYSTEM_USER_CODE="system_user_code";
	public static final String BALANCEUSEDQRY_REQ="BALANCEUSEDQRY_REQ";
	
	//可订购套餐查询
	public static final String DNR_CHECK="DNR_CHECK";
	public static final String OFR_DESC="OFR_DESC";
	public static final String OFR_ID="OFR_ID";
	public static final String OFR_NAME="OFR_NAME";
	public static final String ORDERID="ORDERID";
	public static final String OFR_LIST="OFR_LIST";
	//资源类型转换 HhConstants
	public static final String RESTRANSTYPE="ResTransType";
	public static final String RESTRANSRATIO="ResTransRatio";
	public static final String RESTYPEID="ResTypeId";
	public static final String RESTYPENAME="ResTypeName";
	//充值卡充值
	public static final String REQUEST_USER="REQUEST_USER";
	public static final String REQUEST_TIME="REQUEST_TIME";
	public static final String CARD_PIN="CARD_PIN";
	public static final String DESTINATION_ID="DESTINATION_ID";
	public static final String ACCESS_TYPE="ACCESS_TYPE";
	public static final String CARD_RECHARGE="CARD_RECHARGE";
	public static final String CARDRECHARGE="CARDRECHARGE";
	public static final String PUBSEQ="PUBSEQ";
	//会员充值
	public static final String CASH_INFO="CASH_INFO";
	//去支付
	public static final String ORDERNUMBER="orderNumber";
	public static final String PAYMODE="payMode";
	public static final String RETURNURL="returnUrl";
	
	//充值订单
	public static final String ORDERFROM="orderFrom";
	public static final String ORDERFROMVIEW="orderFromView";
	public static final String ORDERPAYMENTS="orderPayments";
	public static final String PAYMODEVIEW="payModeView";
	public static final String ORDERRECHARGE="orderRecharge";
	public static final String RCGAMT="rcgAmt";
	public static final String RCGMOBILE="rcgMobile";
	public static final String ORDERREMARK="orderRemark";
	public static final String ORDERSOURCE="orderSource";
	public static final String ORDERSOURCEVIEW="orderSourceView";
	public static final String PAYAMT="payAmt";
	public static final String PROMOTIONAMT="promotionAmt";
	public static final String TOTALAMT="totalAmt";
	public static final String TOTALAMTVIEW="totalAmtView";
	public static final String RECHARGETYPE="type";
	public static final String TYPEVIEW="typeView";
	public static final String USERACCOUNT="userAccount";
	public static final String ORDERFLAG="orderFlag";	
	public static final String ESHOPCRMORDERNUMBER="crmOrderNumber";
	//签到
	public static final String SIGNYEAR="signYear";
	public static final String SIGNMONTH="signMonth";
	public static final String SIGNTYPE="signType";
	public static final String PARAM = "param";
	
	public static final String SIGNAMOUNT="signAmount";
	public static final String SIGNFLOW = "signFlow";
	public static final String SIGNID="signId";
	public static final String SIGNTIME = "signTime";
	public static final String CHANNELCODE = "channelCode";
	public static final String RSPMSG = "rspMsg";
	public static final String RSPERRCODE = "rspErrCode";
	public static final String CUSTID="custId";
	
	public static final String PRIVILEGERESULT="Result";
	public static final String SIGNNUMBER="signNumber";
	
	//代理商基本信息查询
	public static final String ACCOUNTNO="accountNo";
	public static final String AGENT_QRY = "AGENT_QRY";
	public static final String AGENT = "AGENT";
	public static final String ACCT_ID = "ACCT_ID";
	public static final String ACCT_NAME = "ACCT_NAME";
	public static final String ACCT_TYPE = "ACCT_TYPE";
	public static final String AGENT_ADDR = "AGENT_ADDR";
	public static final String AGENT_CODE = "AGENT_CODE";
	public static final String AGENT_ID = "AGENT_ID";
	public static final String AGENT_LEVEL = "AGENT_LEVEL";
	public static final String AGENT_NAME = "AGENT_NAME";
	public static final String AGENT_TYPE = "AGENT_TYPE";
	public static final String BANK_ACCT = "BANK_ACCT";
	public static final String BANK_NAME = "BANK_NAME";
	public static final String BASIC_INFO_ID = "BASIC_INFO_ID";
	public static final String CONTACTOR = "CONTACTOR";
	public static final String CONTACT_TEL_NBR = "CONTACT_TEL_NBR";
	public static final String CRT_DATE = "CRT_DATE";
	public static final String JION_DATE = "JION_DATE";
	public static final String ORG_CERTIF_NBR = "ORG_CERTIF_NBR";
	public static final String ORG_CERTIF_TYPE_ID = "ORG_CERTIF_TYPE_ID";
	public static final String ORG_NAME = "ORG_NAME";
	public static final String PAYMENT_TYPE_ID = "PAYMENT_TYPE_ID";
	public static final String STATUS = "STATUS";
	public static final String TAX_CHARTER_NBR = "TAX_CHARTER_NBR";
	public static final String TAX_REGISTER_DATE = "TAX_REGISTER_DATE";
	public static final String TAX_REGISTER_NBR = "TAX_REGISTER_NBR";
	
	//首页订单查询类型
	public static final String ORDER_QRY = "ORDER_QRY";
	
	// 空中充值交易冲正
		public static final String PAYACCT = "PAYACCT";
		public static final String REQUESTSOURCE = "REQUESTSOURCE";
		public static final String REQUESTUSER = "REQUESTUSER";
		public static final String REQUESTID = "REQUESTID";
		public static final String REQUESTTIME = "REQUESTTIME";
		public static final String OLDREQUESTID = "OLDREQUESTID";
		public static final String DESTINATIONID = "DESTINATIONID";
		public static final String DESTINATIONATTR = "DESTINATIONATTR";
		public static final String OBJTYPE = "OBJTYPE";
		public static final String RECHARGEAMOUNT = "RECHARGEAMOUNT";
	//返档OCR
	public static final String NAME = "NAME";
	//人脸对比后获取的分数
	public static final String CONFIDENCE = "CONFIDENCE";
}