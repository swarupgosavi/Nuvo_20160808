package xyz.theapptest.nuvo.adapter;

import java.util.ArrayList;

import xyz.theapptest.nuvo.pojo.Userinformation;

/**
 * Created by trtcpu007 on 8/8/16.
 */

public class Producer_Ongoing {
    String firstname,lastname,rating,description,created_on,views,expiry_date,attachments,job_title,job_id,user_id,audid,jobstatus,demo,onlinestatus;
    ArrayList<Userinformation> userinfo;

    public String getFirstname() {
        return firstname;
    }

    public String getDemo() {
        return demo;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public ArrayList<Userinformation> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<Userinformation> userinfo) {
        this.userinfo = userinfo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getJobstatus() {

        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getAudid() {
        return audid;
    }

    public void setAudid(String audid) {
        this.audid = audid;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
