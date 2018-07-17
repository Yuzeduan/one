package com.yuzeduan.bean;

/**
 * Created by YZD on 2018/5/24.
 */

public class Constant {
    public static final int READING = 0;
    public static final int MUSIC = 1;
    public static final int MOVIE = 2;
    public static final int INSET = 3;
    public static final String READINGLIST_URL = "http://v3.wufazhuce.com:8000/api/channel/reading/more/12808?channel=wdj&version=4.0.2&platform=android";
    public static final String MUSICLIST_URL = "http://v3.wufazhuce.com:8000/api/channel/music/more/13046?platform=android";
    public static final String MOVIELIST_URL = "http://v3.wufazhuce.com:8000/api/channel/movie/more/13758?platform=android";
    public static final String INSETID_URL = "http://v3.wufazhuce.com:8000/api/hp/idlist/2065?version=3.5.0&platform=android";
    public static final String NEW_READINGLIST_URL = "http://v3.wufazhuce.com:8000/api/channel/reading/more/0?channel=wdj&version=4.0.2&platform=android";
    public static final String NEW_MUSICLIST_URL = "http://v3.wufazhuce.com:8000/api/channel/music/more/0?platform=android";
    public static final String NEW_MOVIELIST_URL = "http://v3.wufazhuce.com:8000/api/channel/movie/more/0?platform=android";
    public static final String NEW_INSETID_URL = "http://v3.wufazhuce.com:8000/api/hp/idlist/0?version=3.5.0&platform=android";
    private static StringBuilder mBuilder = new StringBuilder();
    /**
     * 获取插画详细的URL
     * @param id 获取插画的具体id
     * @return 返回获取插画的URL
     */
    public static String createInsetUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/hp/detail/";
        String tail = "?version=3.5.0&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    /**
     * 用于获取阅读列表URL
     * @param id
     * @return
     */
    public static String createReadListUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/channel/reading/more/";
        String tail = "?channel=wdj&version=4.0.2&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createReadingUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/essay/";
        String tail = "?platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createMusicUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/music/detail/";
        String tail = "?version=3.5.0&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createMovieUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/movie/";
        String tail = "/story/1/0?platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createReadingCommentUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/essay/";
        String tail = "/0?&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createMusicCommentUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = "http://v3.wufazhuce.com:8000/api/comment/praiseandtime/music/";
        String tail = "/0?&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }

    public static String createMovieCommentUrl(String id){
        mBuilder.delete(0, mBuilder.length());
        String head = " http://v3.wufazhuce.com:8000/api/comment/praiseandtime/movie/";
        String tail = "/0?&platform=android";
        return mBuilder.append(head).append(id).append(tail).toString();
    }
}
