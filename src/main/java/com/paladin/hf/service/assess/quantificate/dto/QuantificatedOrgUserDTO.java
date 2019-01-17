package com.paladin.hf.service.assess.quantificate.dto;


/**   
* @author jisanjie
* @version 2019年1月17日 上午10:24:17 
*/
public class QuantificatedOrgUserDTO extends UserDTO {
      
      /**
       * serialVersionUID
       */
      private static final long serialVersionUID = 8413843767414932615L;


      //是否被量化考评
      private Integer isAssessed;


      public Integer getIsAssessed() {
            return isAssessed;
      }


      public void setIsAssessed(Integer isAssessed) {
            this.isAssessed = isAssessed;
      }
}
