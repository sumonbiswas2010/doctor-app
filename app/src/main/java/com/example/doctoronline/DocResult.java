package com.example.doctoronline;

import org.json.JSONArray;
import org.json.JSONObject;

public class DocResult {


    private String[] name;
    private String[] id;
    private String[] chember;
    private String[] speciality;
    private String[] email;
    private String[] phone;

    public String count;


    public String [] getPhone() {
        return phone;
    }
    public String [] getSpeciality() {
        return speciality;
    }
    public String [] getChember() {
        return chember;
    }

    public String getCount() {
            return count;
        }
        public String[] getName() {
            return name;
        }
        public String[] getId() {
        return id;
    }
    }


