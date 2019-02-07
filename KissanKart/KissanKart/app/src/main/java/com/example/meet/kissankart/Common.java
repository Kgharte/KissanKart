package com.example.meet.kissankart;

/**
 * Created by ankit on 11/20/2017.
 */
public class Common
{
    private static final String  BASEURL =
            "http://192.168.43.13/kissankart/";
    public static String GetBaseServiceUrl()
    {
        return BASEURL + "web_service/";
    }
    public static String GetBaseImageUrl()
    {
        return BASEURL + "images/";
    }
}
