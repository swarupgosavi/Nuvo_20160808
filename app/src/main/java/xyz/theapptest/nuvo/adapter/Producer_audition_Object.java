package xyz.theapptest.nuvo.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.theapptest.nuvo.pojo.Userinformation;

/**
 * Created by trtcpu007 on 8/8/16.
 */

public class Producer_audition_Object {
    String firstname,lastname,rating,jobid,audid,jobstatus,title,created_on,user_id,description,views,expiry_date,attachments,user_info,demo,onlinestatus,duration;

    ArrayList<Userinformation> userinfo;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRating() {
        return rating;
    }

    public ArrayList<Userinformation> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<Userinformation> userinfo) {
        this.userinfo = userinfo;
    }

    public String getTitle() {
        return title;
    }



    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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



    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudid() {
        return audid;
    }

    public void setAudid(String audid) {
        this.audid = audid;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFirstname() {
        return firstname;
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
}
