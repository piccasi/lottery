package common;

public class BmsOtherInfo {
    
    /**
     * 经办人编号
     */ 
    private String handleCustId;
    
    private String developLev;        //发展级别
    
    private String firstDevelopInfo;        //第一发展信息
    
    private String secondDevelopInfo;        //第二发展信息
    
    /**
     * 菜单ID
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