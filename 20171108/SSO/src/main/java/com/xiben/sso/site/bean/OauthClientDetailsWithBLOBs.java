package com.xiben.sso.site.bean;

public class OauthClientDetailsWithBLOBs extends OauthClientDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_client_details.public_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    private String publicKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column oauth_client_details.private_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    private String privateKey;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_client_details.public_key
     *
     * @return the value of oauth_client_details.public_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_client_details.public_key
     *
     * @param publicKey the value for oauth_client_details.public_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey == null ? null : publicKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column oauth_client_details.private_key
     *
     * @return the value of oauth_client_details.private_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column oauth_client_details.private_key
     *
     * @param privateKey the value for oauth_client_details.private_key
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey == null ? null : privateKey.trim();
    }
}