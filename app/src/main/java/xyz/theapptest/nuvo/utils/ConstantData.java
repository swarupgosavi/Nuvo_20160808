package xyz.theapptest.nuvo.utils;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.theapptest.nuvo.pojo.AgentArtistListItemPojo;

/**
 * Created by trtcpu007 on 8/7/16.
 */

public class ConstantData {
    static ConstantData instance;
    String roleofuser;
    String jobdetailsartist;
String Sourcejobartist;


    public String getSourceflagjobs() {
        return Sourceflagjobs;
    }

    public void setSourceflagjobs(String sourceflagjobs) {
        Sourceflagjobs = sourceflagjobs;
    }

    String auditionartistdetails;
    String Sourceauditionartist;
    String Sourceflagjobs;

    public String getAuditionartistdetails() {
        return auditionartistdetails;
    }

    public void setAuditionartistdetails(String auditionartistdetails) {
        this.auditionartistdetails = auditionartistdetails;
    }

    public String getSourceauditionartist() {
        return Sourceauditionartist;
    }

    public void setSourceauditionartist(String sourceauditionartist) {
        Sourceauditionartist = sourceauditionartist;
    }

    public String getSourcejobartist() {
        return Sourcejobartist;
    }

    public void setSourcejobartist(String sourcejobartist) {
        Sourcejobartist = sourcejobartist;
    }

    public String getFindartistdetails() {
        return findartistdetails;
    }

    public void setFindartistdetails(String findartistdetails) {
        this.findartistdetails = findartistdetails;
    }

    String Sourceflagartist;
    String findartistdetails;


    public String getSourceflagartist() {
        return Sourceflagartist;
    }

    public void setSourceflagartist(String sourceflagartist) {
        Sourceflagartist = sourceflagartist;
    }

    public String getJobdetailsartist() {
        return jobdetailsartist;
    }

    public void setJobdetailsartist(String jobdetailsartist) {
        this.jobdetailsartist = jobdetailsartist;
    }

    String requestproducerflag;

    public String getRequestproducerflag() {
        return requestproducerflag;
    }

    public void setRequestproducerflag(String requestproducerflag) {
        this.requestproducerflag = requestproducerflag;
    }

    public String getAuditionproducerdetails() {
        return auditionproducerdetails;
    }

    public void setAuditionproducerdetails(String auditionproducerdetails) {
        this.auditionproducerdetails = auditionproducerdetails;
    }

    String auditionproducerdetails;

    public String getPastwebresponse() {
        return pastwebresponse;
    }

    public void setPastwebresponse(String pastwebresponse) {
        this.pastwebresponse = pastwebresponse;
    }

    String ongoingWebResponse;
    String pastwebresponse;

    public String getFindjobresponse() {
        return findjobresponse;
    }

    public String getAuditionresponse() {
        return auditionresponse;
    }

    public void setAuditionresponse(String auditionresponse) {
        this.auditionresponse = auditionresponse;
    }

    public void setFindjobresponse(String findjobresponse) {
        this.findjobresponse = findjobresponse;

    }

    String findjobresponse;


    String auditionresponse;

    ArrayList<String> selectedArtist;
    String source_flag, audition_flag = "";

    public String getRoleofuser() {
        return roleofuser;
    }

    public void setRoleofuser(String roleofuser) {
        this.roleofuser = roleofuser;
    }

    public int getCharacteristicvalue() {
        return characteristicvalue;
    }

    public int getRecordingvalue() {
        return recordingvalue;
    }

    public void setRecordingvalue(int recordingvalue) {
        this.recordingvalue = recordingvalue;
    }

    public void setCharacteristicvalue(int characteristicvalue) {
        this.characteristicvalue = characteristicvalue;
    }

    public int getJobcategoryvalue() {
        return jobcategoryvalue;
    }

    public void setJobcategoryvalue(int jobcategoryvalue) {
        this.jobcategoryvalue = jobcategoryvalue;
    }

    String firstname, lastname, email, password;
    int characteristicvalue, jobcategoryvalue, recordingvalue;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String gender, age, langauge, phonenumber, description;

    public int getGvalue() {
        return gvalue;
    }

    public int getAgevalue() {
        return agevalue;
    }

    public void setAgevalue(int agevalue) {
        this.agevalue = agevalue;
    }

    public void setGvalue(int gvalue) {
        this.gvalue = gvalue;

    }

    int gvalue, agevalue;

    public int getLvaue() {
        return lvaue;
    }

    public void setLvaue(int lvaue) {
        this.lvaue = lvaue;
    }

    int lvaue;

    ArrayList<HashMap<String, String>> agelist;

    ArrayList<HashMap<String, String>> characteristics;

    ArrayList<HashMap<String, String>> arrgender;

    ArrayList<HashMap<String, String>> job_category;

    public ArrayList<HashMap<String, String>> getRecording_methods() {
        return recording_methods;
    }

    public void setRecording_methods(ArrayList<HashMap<String, String>> recording_methods) {
        this.recording_methods = recording_methods;
    }

    ArrayList<HashMap<String, String>> language;


    ArrayList<HashMap<String, String>> recording_methods;

    public ArrayList<HashMap<String, String>> getLanguage() {
        return language;
    }

    public void setLanguage(ArrayList<HashMap<String, String>> language) {
        this.language = language;
    }


    public ArrayList<HashMap<String, String>> getJob_category() {
        return job_category;
    }

    public void setJob_category(ArrayList<HashMap<String, String>> job_category) {
        this.job_category = job_category;
    }


    public ArrayList<HashMap<String, String>> getgenders() {
        return arrgender;
    }

    public void setgenders(ArrayList<HashMap<String, String>> genders) {
        this.arrgender = genders;
    }

    public ArrayList<HashMap<String, String>> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(ArrayList<HashMap<String, String>> characteristics) {
        this.characteristics = characteristics;
    }

    public ArrayList<HashMap<String, String>> getAgelist() {
        return agelist;
    }

    public void setAgelist(ArrayList<HashMap<String, String>> agelist) {
        this.agelist = agelist;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLangauge() {

        return langauge;
    }

    public void setLangauge(String langauge) {
        this.langauge = langauge;
    }

    public String getAge() {
        return age;

    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String userImagePath;
    String userid;

    public String getTokenid() {
        return tokenid;
    }

    public void setTokenid(String tokenid) {
        this.tokenid = tokenid;
    }

    String tokenid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private ConstantData() {

    }


    public static ConstantData getInstance() {
        if (instance == null) {
            instance = new ConstantData();
        }
        return instance;
    }

    public void setInstance(ConstantData instance) {
        this.instance = instance;
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

    public String getUserImagePath() {
        return userImagePath;
    }

    public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getOngoingWebResponse() {
        return ongoingWebResponse;
    }

    public void setOngoingWebResponse(String ongoingWebResponse) {
        this.ongoingWebResponse = ongoingWebResponse;
    }


    String requestproducer;
    String ongoingproducer;

    public String getRequestproducer() {
        return requestproducer;
    }

    public void setRequestproducer(String requestproducer) {
        this.requestproducer = requestproducer;
    }

    public String getOngoingproducer() {
        return ongoingproducer;
    }

    public void setOngoingproducer(String ongoingproducer) {
        this.ongoingproducer = ongoingproducer;
    }

    public String getSeletedArtists() {
        String artists = "";

        if (this.selectedArtist != null) {
            for (int i = 0; i < this.selectedArtist.size(); i++) {
                if (this.selectedArtist.get(i) != null)
                    artists = this.selectedArtist.get(i).toString() + ",";
            }

            if (artists.length() > 0)
                artists = artists.substring(0, artists.length() - 1);
        }
        return artists;
    }

    public void setselectedArtist(ArrayList<String> pselectedArtist) {
        this.selectedArtist = pselectedArtist;
    }

    public String getSource_flag() {
        return this.source_flag;
    }

    public void setSource_flag(String source) {
        this.source_flag = source;
    }


    public String getAudition_flag() {

        return this.audition_flag;
    }

    public void setAudition_flag(String source) {
        this.audition_flag = source;
    }


    public String onGoingJobsJsonString;

    public String getOnGoingJobsJsonString() {
        return onGoingJobsJsonString;
    }

    public void setOnGoingJobsJsonString(String onGoingJobsJsonString) {
        this.onGoingJobsJsonString = onGoingJobsJsonString;
    }

    String jobType;

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    AgentArtistListItemPojo agentArtistListItemPojo;

    public AgentArtistListItemPojo getAgentArtistListItemPojo() {
        return agentArtistListItemPojo;
    }

    public void setAgentArtistListItemPojo(AgentArtistListItemPojo agentArtistListItemPojo) {
        this.agentArtistListItemPojo = agentArtistListItemPojo;
    }

    int GenderFilter;
    public int getGenderFilter() {
        return GenderFilter;
    }

    public void setGenderFilter(int GenderFilter) {
        this.GenderFilter = GenderFilter;
    }

    boolean findArtist = false;

    public boolean isFindArtist() {
        return findArtist;
    }

    public FindJobs getFindJobsPojo() {
        return findJobsPojo;
    }

    public void setFindJobsPojo(FindJobs findJobsPojo) {
        this.findJobsPojo = findJobsPojo;
    }

    public void setFindArtist(boolean findArtist) {
        this.findArtist = findArtist;
    }

    FindJobs findJobsPojo;

}
