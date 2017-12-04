package common;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcl.Assert;
import com.tcl.BaModel;
import com.tcl.BafActionInfo;
import com.tcl.BafAuthInfo;
import com.tcl.BafCommInfo;
import com.tcl.BafVariants;
import com.tcl.BaseBafCommand;
import com.tcl.CommonResult;
import com.tcl.MapBafVariantsImpl;
import com.tcl.OpMode;
import com.tcl.OperatorContextHolder;
import com.tcl.SimpleBaModel;
import com.tcl.SimpleBafVariants;
import com.tcl.StandBafVariants;
import com.tcl.StringUtils;
import com.tcl.SystemException;
import com.tcl.TradeAction;




public class TradeManager {
	
    private static final Log logger = LogFactory.getLog(TradeManager.class);

    /** 表明是aiip_exec_BMS、 aiip_exec_ACCT、 aiip_exec_ABS等，默认是bms */
    public String region = Constants.AIIP_EXEC_BMS;
    public BafCommInfo commonInfo;
    public CommonResult commonResult;
    private BaseBafCommand bafAction = new BaseBafCommand();
    private BmsOtherInfo bmsOtherInfo;

    public TradeManager() {
    }

    public TradeManager(String type) {
        region = type;
    }

    public TradeManager(String busiId, OpMode opMode, BafAuthInfo authInfo, CommonResult commonResult) {
        this(busiId, opMode, authInfo, commonResult, "", "");
    }

    public TradeManager(String busiId, OpMode opMode, BafAuthInfo authInfo, CommonResult commonResult, String busiKey,
            String optRemark) {
        commonInfo = new BafCommInfo(busiId, opMode, authInfo, busiKey, optRemark);
        this.commonResult = commonResult;
    }

    public TradeManager(String busiId, OpMode opMode, String busiKey, String optRemark) {
        BafAuthInfo authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();

        this.commonInfo = new BafCommInfo(busiId, opMode, authInfo, busiKey, optRemark);
        this.commonResult = new CommonResult();
    }

    public TradeManager(String busiId, OpMode opMode, CommonResult commonResult, String busiKey, String optRemark) {
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
     * 通过addAction方法来添加你需要执行的baf<br>
     * 你只需要实现BaModel需要的两个方法，getActionName，getBafVariants
     */
    public void addAction(BaModel actionModel) {
        if (actionModel == null) {
            return;
        }

        bafAction.addAction(new BafActionInfo(actionModel.getActionName(), actionModel.getBafVariants()));
    }

    /**
     * <li>actionName = Res_Sim_Sell
     * <li> {cardNum 1111} {acctId 222}
     */
    public void addAction(String actionName, BafVariants bafVar) {
        Assert.notNull(bafVar, "bafVar为空！");
        bafAction.addAction(new BafActionInfo(actionName, bafVar));
    }

    /**
     * 简单的调用方式
     * <li>actionName = Res_Sim_Sell
     * <li>actionDetail = {cardNum 1111} {acctId 222}
     */
    public void addAction(String actionName, String actionDetail) {
        bafAction.addAction(new BafActionInfo(actionName, new SimpleBafVariants(actionDetail)));
    }

    public void removeAllAction() {
        bafAction.removeAllActions();
    }

    /**
     * 清除部分变量，争取循环中复用该manager
     */
    public void clear() {
        removeAllAction();
        commonResult.clean();
    }

    public int execute() {
        Assert.notNull(commonResult, "commonResult必须在外面初始化！");
        Assert.notNull(commonInfo, "commonInfo必须在外面初始化,并且有意义！");

        bafAction.setCommInfo(commonInfo);
        
        logger.debug("bafAction.toString()=" + bafAction.toString());

        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
            return Constants.ERROR;
        }
        else if (commonResult.isError()) {
            return Constants.ERROR;
        }

        return Constants.OK;

    }
    
    public int executePrint() {

        // add by yansd 20090523
    	bafAction.setCommInfo(this.commonInfo);
    	
        logger.debug("打印 bafAction.toString()=" + bafAction.toString());

        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
            return Constants.ERROR;
        }
        else if (commonResult.isError()) {
            return Constants.ERROR;
        }

        return Constants.OK;

    }
    
    
    public static void main(String[] args) 
    {
    	//第一种方式调用 
    
    	BmsUserSugDef bmsUserSugDef = new BmsUserSugDef();
	        bmsUserSugDef.setRegionId("A");
	        bmsUserSugDef.setServiceType("1001");
	        bmsUserSugDef.setSubscriptionId("131234234");
	        bmsUserSugDef.setRemark("zhoumz");
	        String aa=executeBaf(bmsUserSugDef);
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
        Assert.notNull(commonResult, "commonResult必须在外面初始化！");
        Assert.notNull(commonInfo, "commonInfo必须在外面初始化,并且有意义！");

        bafAction.setCommInfo(commonInfo);
        return bafAction.getTclStr();
    }

    public String getBatTclStr() {
        return bafAction.getActionsTclStr();
    }

    /**
     * 单个action的调用直接使用该接口即可。不需要那么麻烦了
     * 
     */
    public static String executeBaf(BaModel actionModel) {
        return executeBaf(actionModel,null);
    }
    
  
    public static String executeBaf(BaModel actionModel,OpMode opmode) {
    	if(opmode==null){
    		opmode=OpMode.SUBMIT;
    	}
    	BafAuthInfo authInfo = new BafAuthInfo("A", "A00", "22342", "43643");
         
        CommonResult commonResult = new CommonResult();
         TradeManager manager = new TradeManager("10031", opmode, authInfo, commonResult, "13098776567", "");
         manager.addAction(actionModel);
        if (manager.execute() != Constants.OK) {
            // 走异常，因为出错的机会很少
            logger.error("执行baf出错，错误原因：" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }

        return commonResult.getMessage();
    }
    
    /**
     *  add by xupp
     *  
     *  用于訂單查詢
     * @param actionModel
     * @param opmode
     * @return
     */
    public static String executeBafForOrder(String busiId,BaModel actionModel,OpMode opmode) {
    	if(opmode==null){
    		opmode=OpMode.SUBMIT;
    	}
        BafAuthInfo authInfo = OperatorContextHolder.getOperatorContext().getBafAuthInfo();

        CommonResult commonResult = new CommonResult();
        TradeManager manager = new TradeManager(busiId, opmode, authInfo, commonResult, "", "");
        manager.addAction(actionModel);

        if (manager.execute() != Constants.OK) {
            // 走异常，因为出错的机会很少
            logger.error("执行baf出错，错误原因：" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }

        return commonResult.getMessage();
    }
    
    public static String getId(String idPrefix) {
        Assert.hasLength(idPrefix, "idPrefix不能为空");

        if (!StringUtils.hasText(idPrefix) || idPrefix.length() > 16) {
            throw new SystemException("90123", String.format("获取id的前缀(%s):必须是16以内的", idPrefix));
        }

        BaModel actionModel = new SimpleBaModel("Get_Id", "{ID_PREFIX " + idPrefix + "}");
        String result = executeBaf(actionModel);
        return new MapBafVariantsImpl(result).getParameter("ID");
    }

    public CommonResult getCommonResult() {
        return commonResult;
    }

    public String doAction() {
        Assert.notNull(commonResult, "commonResult必须在外面初始化！");
        if (commonResult == null) {
            this.commonResult = new CommonResult();
        }

        bafAction.setCommInfo(commonInfo);
        if (TradeAction.execute(region, bafAction, commonResult) == Constants.ERROR) {
            logger.error("执行baf出错，错误原因：" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }
        else if (commonResult.isError()) {
            logger.error("执行baf出错，错误原因：" + commonResult.getMessage());
            throw new SystemException("9013", commonResult.getMessage());
        }
        return commonResult.getMessage();
    }

    /**
     * @param bafAction
     * @deprecated 请使用addAction方法
     */
    public void setBafAction(BaseBafCommand bafAction) {
        this.bafAction = bafAction;
    }

    public BmsOtherInfo getBmsOtherInfo() {
        return bmsOtherInfo;
    }

}
