package com.paladin.hf.service.org.dto;
/**   
 * @author 黄伟华
 * @version 2019年1月30日 下午2:24:23 
 */
public class AppOrgUserSelfResumeDTO{
    
    private String reward;
    
    private String resume;
    
    private String punish;

    public String getReward()
    {
        return reward;
    }

    public void setReward(String reward)
    {
        this.reward = reward;
    }

    public String getResume()
    {
        return resume;
    }

    public void setResume(String resume)
    {
        this.resume = resume;
    }

    public String getPunish()
    {
        return punish;
    }

    public void setPunish(String punish)
    {
        this.punish = punish;
    }
    
}
