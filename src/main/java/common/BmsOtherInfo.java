package common;

public class BmsOtherInfo {
    
    /**
     * �����˱��
     */ 
    private String handleCustId;
    
    private String developLev;        //��չ����
    
    private String firstDevelopInfo;        //��һ��չ��Ϣ
    
    private String secondDevelopInfo;        //�ڶ���չ��Ϣ
    
    /**
     * �˵�ID
     */
    private String menuId;

    public String getHandleCustId() {
            return handleCustId;
    }

    public void setHandleCustId(String handleCustId) {
            this.handleCustId = handleCustId;
    }

    public String getMenuId() {
            return menuId;
    }

    public void setMenuId(String menuId) {
            this.menuId = menuId;
    }

    public String getDevelopLev() {
            return developLev;
    }

    public void setDevelopLev(String developLev) {
            this.developLev = developLev;
    }

    public String getFirstDevelopInfo() {
            return firstDevelopInfo;
    }

    public void setFirstDevelopInfo(String firstDevelopInfo) {
            this.firstDevelopInfo = firstDevelopInfo;
    }

    public String getSecondDevelopInfo() {
            return secondDevelopInfo;
    }

    public void setSecondDevelopInfo(String secondDevelopInfo) {
            this.secondDevelopInfo = secondDevelopInfo;
    }
    
    
}