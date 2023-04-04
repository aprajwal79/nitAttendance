package com.nitap.attende;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nitap.attende.models.MyConfiguration;

import com.ttv.face.FaceFeatureInfo;

import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

public class MyUtils {


    public static void saveString(Context context,String key,String value) {
        SharedPreferences prefs = context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(Context context,String key) {
        SharedPreferences prefs = context.getSharedPreferences("application",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String value = prefs.getString(key, "EMPTY");
        editor.commit();
        return value;
    }
    public static void removeString(Context context,String key) {
        SharedPreferences prefs = context.getSharedPreferences("application", Context.MODE_PRIVATE);
        prefs.edit().remove(key).commit();
    }


    public static Object getObjectFromString(String jsonString, Class class1) {
        ObjectMapper mapper = new ObjectMapper();
        Object resultObject = null;
        try {
            resultObject = mapper.readValue(jsonString, class1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resultObject == null) {
            return null;
        } else {
           return resultObject;
        }

    }


    public static String getStringFromObject(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        Gson gson = builder.create();
        String jsonString = gson.toJson(object);
        return jsonString;
    }

    public static void removeAll(Context context) {
        String[] removables = {"STUDENTCONFIG","TEACHERCONFIG","ADMINCONFIG","STUDENTCONFIGBUILDER",
                "TEACHERCONFIGBUILDER","ADMINCONFIGBUILDER","USERTYPE","EMAIL","NAME"};
        for (String item : removables) {
            MyUtils.removeString(context,item);
        }
    }

    public static void clearAppData(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.clearApplicationUserData();

    }



    public static FaceFeatureInfo getFaceFeatureInfo(Context applicationContext, String faceinfoString) {
        ObjectMapper mapper = new ObjectMapper();
        FaceFeatureInfo faceFeatureInfo = new FaceFeatureInfo();
        try {
            faceFeatureInfo = mapper.readValue(faceinfoString, FaceFeatureInfo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (faceFeatureInfo == null){ return null; }
        else  { return faceFeatureInfo; }
    }







    
    public static MyConfiguration getConfiguration(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        MyConfiguration myConfiguration = new MyConfiguration();
        try {
            String jsonString = MyUtils.getString(context,"MYCONFIG");
            if (Objects.equals(jsonString, "EMPTY"))
                return null;
            myConfiguration = mapper.readValue(jsonString, MyConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (myConfiguration == null){ return null; }
        else  { return myConfiguration; }
    }

    public static MyConfiguration getConfigurationBuilder(Context context) {
        ObjectMapper mapper = new ObjectMapper();
        MyConfiguration myConfigurationBuilder = new MyConfiguration();
        try {
            String jsonString = MyUtils.getString(context,"MYCONFIGBUILDER");
            if (Objects.equals(jsonString, "EMPTY"))
                return null;
            myConfigurationBuilder = mapper.readValue(jsonString, MyConfiguration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (myConfigurationBuilder == null){ return null; }
        else  { return myConfigurationBuilder; }
    }

    public static void saveConfiguration(Context context,MyConfiguration myConfiguration) {
        String updatedString = MyUtils.getStringFromObject(myConfiguration);
        MyUtils.saveString(context,"MYCONFIG",updatedString);
    }

    public static void saveConfigurationBuilder(Context context,MyConfiguration myConfiguration) {
        String updatedString = MyUtils.getStringFromObject(myConfiguration);
        MyUtils.saveString(context,"MYCONFIGBUILDER",updatedString);
    }

    public static void removeConfiguration(Context context) {
        MyUtils.removeString(context,"MYCONFIG");
    }

    public static void removeConfigurationBuilder(Context context) {
        MyUtils.removeString(context,"MYCONFIGBUILDER");
    }

}

