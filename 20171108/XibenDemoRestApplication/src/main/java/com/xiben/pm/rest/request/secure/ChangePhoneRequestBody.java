package com.xiben.pm.rest.request.secure;

public class ChangePhoneRequestBody {
    private String secureunid;
    private String newphone;
    private String newcode;
    public void setSecureunid(String secureunid) {
         this.secureunid = secureunid;
     }
     public String getSecureunid() {
         return secureunid;
     }

    public void setNewphone(String newphone) {
         this.newphone = newphone;
     }
     public String getNewphone() {
         return newphone;
     }

    public void setNewcode(String newcode) {
         this.newcode = newcode;
     }
     public String getNewcode() {
         return newcode;
     }
}
