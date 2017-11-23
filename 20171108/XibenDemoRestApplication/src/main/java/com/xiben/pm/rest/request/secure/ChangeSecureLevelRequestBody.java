package com.xiben.pm.rest.request.secure;

public class ChangeSecureLevelRequestBody {
    private String secureunid;
    private int securitylevel;
    public void setSecureunid(String secureunid) {
         this.secureunid = secureunid;
     }
     public String getSecureunid() {
         return secureunid;
     }

    public void setSecuritylevel(int securitylevel) {
         this.securitylevel = securitylevel;
     }
     public int getSecuritylevel() {
         return securitylevel;
     }
}
