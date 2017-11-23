package com.xiben.pm.rest.request.secure;

public class ChangePaypassRequestBody {
    private String secureunid;
    private int optype;
    private String oldpaypass;
    private String paypassword;
    private int securitylevel;
    
    
    
    public String getOldpaypass() {
		return oldpaypass;
	}
	public void setOldpaypass(String oldpaypass) {
		this.oldpaypass = oldpaypass;
	}
	public void setSecureunid(String secureunid) {
         this.secureunid = secureunid;
     }
     public String getSecureunid() {
         return secureunid;
     }

    public void setOptype(int optype) {
         this.optype = optype;
     }
     public int getOptype() {
         return optype;
     }

    public void setPaypassword(String paypassword) {
         this.paypassword = paypassword;
     }
     public String getPaypassword() {
         return paypassword;
     }

    public void setSecuritylevel(int securitylevel) {
         this.securitylevel = securitylevel;
     }
     public int getSecuritylevel() {
         return securitylevel;
     }
}
