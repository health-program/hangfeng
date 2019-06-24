package com.paladin.hf.service.assess.cycle.vo;
/**   
 * @author 黄伟华
 * @version 2019年5月24日 上午9:13:11 
 */
public class AssessCycleStatisticsVO {
    
    private String yx = "0";//优秀
    
    private String lh = "0";//良好
    
    private String hg = "0";//合格
    
    private String bhg = "0";//不合格
    
    private String bddc = "0";//不定等次
    
    public String getYx() {
        return yx;
    }
    public void setYx(String yx) {
        this.yx = yx;
    }
    public String getLh() {
        return lh;
    }
    public void setLh(String lh) {
        this.lh = lh;
    }
    public String getHg() {
        return hg;
    }
    public void setHg(String hg) {
        this.hg = hg;
    }
    public String getBhg() {
        return bhg;
    }
    public void setBhg(String bhg) {
        this.bhg = bhg;
    }
    public String getBddc() {
        return bddc;
    }
    public void setBddc(String bddc) {
        this.bddc = bddc;
    }
    
    
}
