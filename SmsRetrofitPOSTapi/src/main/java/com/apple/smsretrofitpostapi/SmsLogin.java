package com.apple.smsretrofitpostapi;

/**
 * Created by apple1 on 21/11/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmsLogin {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("cover_pic")
    @Expose
    private String coverPic;
    @SerializedName("medium_path")
    @Expose
    private String mediumPath;
    @SerializedName("thumbnail_path")
    @Expose
    private String thumbnailPath;
    @SerializedName("cover_path")
    @Expose
    private String coverPath;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The profilePic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     *
     * @param profilePic
     * The profile_pic
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     *
     * @return
     * The coverPic
     */
    public String getCoverPic() {
        return coverPic;
    }

    /**
     *
     * @param coverPic
     * The cover_pic
     */
    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    /**
     *
     * @return
     * The mediumPath
     */
    public String getMediumPath() {
        return mediumPath;
    }

    /**
     *
     * @param mediumPath
     * The medium_path
     */
    public void setMediumPath(String mediumPath) {
        this.mediumPath = mediumPath;
    }

    /**
     *
     * @return
     * The thumbnailPath
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    /**
     *
     * @param thumbnailPath
     * The thumbnail_path
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    /**
     *
     * @return
     * The coverPath
     */
    public String getCoverPath() {
        return coverPath;
    }

    /**
     *
     * @param coverPath
     * The cover_path
     */
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    /**
     *
     * @return
     * The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     * The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

}
