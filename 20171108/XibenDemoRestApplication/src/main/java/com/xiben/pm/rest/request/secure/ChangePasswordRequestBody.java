package com.xiben.pm.rest.request.secure;

public class ChangePasswordRequestBody {
    private String phone;
    private String code;
    private String newpass;
    public void setPhone(String phone) {
         this.phone = phone;
     }
     public String getPhone() {
         return phone;
     }

    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public void setNewpass(String newpass) {
         this.newpass = newpass;
     }
     public String getNewpass() {
         return newpass;
     }
}
