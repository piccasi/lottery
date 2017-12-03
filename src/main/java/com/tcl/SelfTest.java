package com.tcl;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.CommonResult;
import com.tcl.SystemException;

import com.tcl.StringUtils;
import com.tcl.BaModel;
import com.tcl.SimpleBaModel;
import com.tcl.TradeAction;
import com.tcl.Assert;
import com.tcl.BafActionInfo;
import com.tcl.BafAuthInfo;
import com.tcl.BafCommInfo;
import com.tcl.BafVariants;
import com.tcl.BaseBafCommand;
import com.tcl.MapBafVariantsImpl;
import com.tcl.OpMode;
import com.tcl.OperatorContextHolder;
import com.tcl.SimpleBafVariants;
import com.tcl.StandBafVariants;
import com.tcl.BmsUserSug;


import common.BmsOtherInfo;

import common.Constants;


public class SelfTest {
	
    private static final Log logger = LogFactory.getLog(SelfTest.class);


    public String region = Constants.AIIP_EXEC_BMS;
    public BafCommInfo commonInfo;
    public CommonResult commonResult;
    private BaseBafCommand bafAction = new BaseBafCommand();
    private BmsOtherInfo bmsOtherInfo;

    public SelfTest() {
    }

    public SelfTest(String type) {
        region = type;
    }

    public SelfTest(String busiId, OpMode opMode, BafAuthInfo authInfo, CommonResult commonResult) {
        this(busiId, opMode, authInfo, commonResult, "", "");
    }

    public SelfTest(String busiId, OpMode opMode, BafAuthInfo authInfo, CommonResult commonResult, String busiKey,
            String optRemark) {
        commonInfo = new BafCommInfo(busiId, opMode, authInfo, busiKey, optRemark);
        this.commonResult = commonResult;
    }

    public SelfTest(String busiId, OpMode opMode, String busiKey, String optRemark) {
        BafAuthInfo authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();

        this.commonInfo = new BafCommInfo(busiId, opMode, authInfo, busiKey, optRemark);
        this.commonResult = new CommonResult();
    }

    public SelfTest(String busiId, OpMode opMode, CommonResult commonResult, String busiKey, String optRemark) {
        BafAuthInfo authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();

        this.commonInfo = new BafCommInfo(busiId, opMode, authInfo, busiKey, optRemark);
        this.commonResult = commonResult;
    }

    public void addOldBmsAcceptId(HttpServletRequest request, String busiId) {
        String oldBmsAcceptId = (String) request.getSession().getAttribute(busiId);
        if (StringUtils.getNotNullString(oldBmsAcceptId).equals("")) {
            return;
        }
        if (this.commonInfo == null) {
            return;
        }

        this.commonInfo.setOldBmsAcceptId(oldBmsAcceptId);
    }

    public void setUserRegionId(String userRegionId) {
        this.commonInfo.setUserRegionId(userRegionId);
    }

    public void setBmsOtherInfo(BmsOtherInfo bmsOtherInfo) {
        this.bmsOtherInfo = bmsOtherInfo;
        if (bmsOtherInfo != null){
        	if( bmsOtherInfo.getHandleCustId() != null && !"".equals(bmsOtherInfo.getHandleCustId())) {
                this.commonInfo.setTransactor(bmsOtherInfo.getHandleCustId());
            }
            if (bmsOtherInfo.getDevelopLev() != null
                    && !"".equals(bmsOtherInfo.getDevelopLev())) {
                this.commonInfo.setDevLevel(bmsOtherInfo.getDevelopLev());
            }
            if (bmsOtherInfo.getFirstDevelopInfo() != null
                    && !"".equals(bmsOtherInfo.getFirstDevelopInfo())) {
                this.commonInfo.setFirstDevOper(bmsOtherInfo.getFirstDevelopInfo());
            }
            if (bmsOtherInfo.getSecondDevelopInfo() != null
                    && !"".equals(bmsOtherInfo.getSecondDevelopInfo())) {
                this.commonInfo.setSecondDevOper(bmsOtherInfo.getSecondDevelopInfo());
            }
        }
                
    }

    public void setRegion(String newRegion) {
        region = newRegion;
    }

    public void setCommonResult(CommonResult commonResult) {
        this.commonResult = commonResult;
    }

    public void setCommInfo(BafCommInfo commonInfo) {
        this.commonInfo = commonInfo;
    }

    /**
     * ͨ��addAction����4�������Ҫִ�е�baf<br>
     * ��ֻ��Ҫʵ��BaModel��Ҫ��}�����getActionName��getBafVariants
     */
    public void addAction(BaModel actionModel) {
        if (actionModel == null) {
            return;
        }

        bafAction.addAction(new BafActionInfo(actionModel.getActionName(), actionModel.getBafVariants()));
    }

 
    public void addAction(String actionName, BafVariants bafVar) {
        Assert.notNull(bafVar, "bafVarΪ�գ�");
        bafAction.addAction(new BafActionInfo(actionName, bafVar));
    }

   
    public void addAction(String actionName, String actionDetail) {
        bafAction.addAction(new BafActionInfo(actionName, new SimpleBafVariants(actionDetail)));
    }

    public void removeAllAction() {
        bafAction.removeAllActions();
    }

    /**
     * ���ֱ�����ȡѭ���и��ø�manager
     */
    public void clear() {
        removeAllAction();
        commonResult.clean();
    }

    public int execute() throws Exception {
        Assert.notNull(commonResult, "commonResult测试");
        Assert.notNull(commonInfo, "commonInfo测试2");

        bafAction.setCommInfo(commonInfo);
        
        logger.debug("bafAction.toString()=" + bafAction.toString());
       
        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
        	int begin = 0;
        	int end = 0;
        	String errorMessage = commonResult.getMessage();
        	if(commonResult.getMessage().indexOf("BMS-2002")!=-1 || commonResult.getMessage().indexOf("BMS-2001")!=-1){
             	errorMessage = errorMessage.substring(14,commonResult.getMessage().indexOf("File"));
         	}
        	throw new Exception(errorMessage);
//            return Constants.ERROR;
        }
        else if (commonResult.isError()) {
        	throw new Exception(commonResult.getMessage());
//            return Constants.ERROR;
        }

        return Constants.OK;

    }
    
    public int executePrint() {

        // add by yansd 20090523
    	bafAction.setCommInfo(this.commonInfo);
    	
        logger.debug("��ӡ bafAction.toString()=" + bafAction.toString());

        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
            return Constants.ERROR;
        }
        else if (commonResult.isError()) {
            return Constants.ERROR;
        }

        return Constants.OK;

    }
    
    
    public static void main(String[] args) throws Exception 
    {
    	//��һ�ַ�ʽ���� 
    
    	BmsUserSug bmsUserSug = new BmsUserSug();
	        bmsUserSug.setRegionId("A");
	        bmsUserSug.setServiceType("1001");
	        bmsUserSug.setSubscriptionId("131234234");
	        bmsUserSug.setRemark("zhoumz");
	        String aa=executeBaf(bmsUserSug);
	        System.out.println("zhoumz===============================");
	    	System.out.println(aa);
	    	StandBafVariants result = MapBafVariantsImpl.getInstance(aa);
			String account_id = result.getParameter("ACCOUNT_ID");
			result.addParameter("ZHOUMZ", "123456");
			String zmz = result.getParameter("ZHOUMZ");
			System.out.println(account_id+"    "+zmz);
	       
    }
    

    public int getActionSize() {
        return bafAction.getActionSize();
    }

    public String getTclStr() {
        Assert.notNull(commonResult, "commonResult测试");
        Assert.notNull(commonInfo, "commonInfo测试");

        bafAction.setCommInfo(commonInfo);
        return bafAction.getTclStr();
    }

    public String getBatTclStr() {
        return bafAction.getActionsTclStr();
    }

    /**
     * ����action�ĵ���ֱ��ʹ�øýӿڼ��ɡ�����Ҫ��ô�鷳��
     * @throws Exception 
     * 
     */
    public static String executeBaf(BaModel actionModel) throws Exception {
        return executeBaf(actionModel,null);
    }
    
  
    public static String executeBaf(BaModel actionModel,OpMode opmode) throws Exception {
    	if(opmode==null){
    		opmode=OpMode.SUBMIT;
    	}
    	BafAuthInfo authInfo = new BafAuthInfo("A", "A00", "22342", "43643");
         
        CommonResult commonResult = new CommonResult();
         SelfTest manager = new SelfTest("10031", opmode, authInfo, commonResult, "13098776567", "");
         manager.addAction(actionModel);
        if (manager.execute() != Constants.OK) {
            // ���쳣����Ϊ���Ļ�����
            logger.error("ִ��baf��?����ԭ��" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }

        return commonResult.getMessage();
    }
    
    /**
     *  add by xupp
     *  
     *  ����ӆ�β�ԃ
     * @param actionModel
     * @param opmode
     * @return
     * @throws Exception 
     */
    public static String executeBafForOrder(String busiId,BaModel actionModel,OpMode opmode) throws Exception {
    	if(opmode==null){
    		opmode=OpMode.SUBMIT;
    	}
        BafAuthInfo authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();

        CommonResult commonResult = new CommonResult();
        SelfTest manager = new SelfTest(busiId, opmode, authInfo, commonResult, "", "");
        manager.addAction(actionModel);

        if (manager.execute() != Constants.OK) {
            // ���쳣����Ϊ���Ļ�����
            logger.error("ִ��baf��?����ԭ��" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }

        return commonResult.getMessage();
    }
    
    public static String getId(String idPrefix) throws Exception {
        Assert.hasLength(idPrefix, "idPrefix����Ϊ��");

        if (!StringUtils.hasText(idPrefix) || idPrefix.length() > 16) {
            throw new SystemException("90123", String.format("��ȡid��ǰ׺(%s):������16���ڵ�", idPrefix));
        }

        BaModel actionModel = new SimpleBaModel("Get_Id", "{ID_PREFIX " + idPrefix + "}");
        String result = executeBaf(actionModel);
        return new MapBafVariantsImpl(result).getParameter("ID");
    }

    public CommonResult getCommonResult() {
        return commonResult;
    }

    public String doAction() {
        Assert.notNull(commonResult, "commonResult�����������ʼ����");
        if (commonResult == null) {
            this.commonResult = new CommonResult();
        }

        bafAction.setCommInfo(commonInfo);
        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
            logger.error("ִ��baf��?����ԭ��" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }
        else if (commonResult.isError()) {
            logger.error("ִ��baf��?����ԭ��" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }
        return commonResult.getMessage();
    }

    /**
     * @param bafAction
     * @deprecated ��ʹ��addAction����
     */
    public void setBafAction(BaseBafCommand bafAction) {
        this.bafAction = bafAction;
    }

    public BmsOtherInfo getBmsOtherInfo() {
        return bmsOtherInfo;
    }

}
