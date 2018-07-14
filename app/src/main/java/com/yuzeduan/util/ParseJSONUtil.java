package com.yuzeduan.util;

import com.yuzeduan.bean.Comment;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.bean.InsetId;
import com.yuzeduan.bean.Movie;
import com.yuzeduan.bean.MovieList;
import com.yuzeduan.bean.Music;
import com.yuzeduan.bean.Reading;
import com.yuzeduan.bean.ReadingMusicList;
import com.yuzeduan.db.InsetDao;
import com.yuzeduan.db.MovieDao;
import com.yuzeduan.db.MovieListDao;
import com.yuzeduan.db.MusicDao;
import com.yuzeduan.db.MusicListDao;
import com.yuzeduan.db.ReadingDao;
import com.yuzeduan.db.ReadingListDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.yuzeduan.activity.MainActivity.READING;


/**
 * 解析从api获取的JSON数据,并将解析结果存进数据库中
 * Created by YZD on 2018/5/18.
 */

public class ParseJSONUtil {
    /**
     * 对获取的阅读列表数据或者音乐列表数据进行解析,并将结果封装成对象,存放在容器中返回
     * @param jsonData 表示从api中获取的关于阅读列表和音乐列表的JSON数据
     * @param flag 表示存进来的数据是阅读列表的还是阅读列表的标志
     * @return 返回存放了封装了解析结果数据的对象的容器
     */
    public static ArrayList<ReadingMusicList> parseReadingMusicList(String jsonData, int flag){
        try {
            ReadingListDao readingListDao = new ReadingListDao();
            MusicListDao musicListDao = new MusicListDao();
            ArrayList<ReadingMusicList> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            for(int i = 0; i < dataArray.length(); i++) {
                JSONObject data = dataArray.getJSONObject(i);
                String id = data.getString("id");
                String item_id = data.getString("item_id");
                String title = data.getString("title");
                String forward = data.getString("forward");
                String img_url = data.getString("img_url");
                String last_update_date = data.getString("last_update_date");
                JSONObject author = data.getJSONObject("author");
                String user_name = author.getString("user_name");
                String desc = author.getString("desc");
                ReadingMusicList element = new ReadingMusicList();
                element.setId(id);
                element.setmItemId(item_id);
                element.setmTitle(title);
                element.setmForword(forward);
                element.setmImgUrl(img_url);
                element.setmLastUpdateDate(last_update_date);
                element.setmUserName(user_name);
                element.setmDesc(desc);
                list.add(element);
                if(flag == READING){
                    readingListDao.addReadingList(element);
                }
                else{
                    musicListDao.addMusicList(element);
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对从api获取的影视列表的JSON数据进行解析,并封装为一个个对象,存放在容器中返回
     * @param jsonData 表示从api中获取的影视列表的JSON数据
     * @return 返回存放了封装了JSON数据解析后的数据Bean对象的容器
     */
    public static ArrayList<MovieList> parseMovieList(String jsonData){
        try {
            MovieListDao movieListDao = new MovieListDao();
            ArrayList<MovieList> MovieList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            for(int i = 0; i < dataArray.length(); i++) {
                JSONObject data = dataArray.getJSONObject(i);
                String item_id = data.getString("item_id");
                String title = data.getString("title");
                String forward = data.getString("forward");
                String img_url = data.getString("img_url");
                String last_update_date = data.getString("last_update_date");
                String subtitle = data.getString("subtitle");
                JSONObject author = data.getJSONObject("author");
                String user_name = author.getString("user_name");
                String desc = author.getString("desc");
                MovieList movie = new MovieList();
                movie.setmItemId(item_id);
                movie.setmTitle(title);
                movie.setmForword(forward);
                movie.setmImgUrl(img_url);
                movie.setmLastUpdateDate(last_update_date);
                movie.setmSubtitle(subtitle);
                movie.setmUserName(user_name);
                movie.setmDesc(desc);
                MovieList.add(movie);
                movieListDao.addMovieList(movie);
            }
            return MovieList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对获取的插画id的JSON数据进行解析
     * @param jsonData 从api中获取的存放了10个插画id的JSON数据
     * @return 返回存放了JSON数据进行解析10个获得的插画id封装成Bean对象的容器
     */
    public static ArrayList<InsetId> parseInsetId(String jsonData){
        try {
            ArrayList<InsetId> idList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray data = jsonObject.getJSONArray("data");
            for(int i = 0; i < data.length(); i++){
                String id = data.getString(i);
                InsetId insetId = new InsetId();
                insetId.setmInsetId(id);
                idList.add(insetId);
            }
            return idList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析关于阅读详情的JSON数据,并将解析结果封装成对象,作为返回值
     * @param jsonData 表示从api中获取的关于阅读的JSON数据
     * @return 返回封装了解析结果数据的对象
     */
    public static Reading parseReading(String jsonData){
        try {
            ReadingDao readingDao = new ReadingDao();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String itemId = data.getString("content_id");
            String title = data.getString("hp_title");
            String authorName = data.getString("hp_author");
            String content = data.getString("hp_content");
            String lastUpdateDate = data.getString("last_update_date");
            Reading reading = new Reading();
            reading.setmItemId(itemId);
            reading.setmTitle(title);
            reading.setmAuthorName(authorName);
            reading.setmContent(content);
            reading.setmContent(content);
            reading.setmLastUpdateDate(lastUpdateDate);
            readingDao.addReading(reading);
            return reading;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对音乐的JSON数据进行解析,并将解析后的数据封装为一个Bean对象返回
     * @param jsonData 表示从api中获取的音乐的JSON数据
     * @return 返回封装了解析后的数据的对象
     */
    public static Music parseMusic(String jsonData){
        try {
            MusicDao musicDao = new MusicDao();
            Music music = new Music();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String itemId = data.getString("id");
            String musicTitle = data.getString("title");
            String cover = data.getString("cover");
            String storyTitle = data.getString("story_title");
            String storyContent = data.getString("story");
            String lastUpdateDate = data.getString("last_update_date");
            JSONObject author = data.getJSONObject("author");
            String musicUserName = author.getString("user_name");
            JSONObject storyAuthor = data.getJSONObject("story_author");
            String storyAuthorName = storyAuthor.getString("user_name");
            music.setmItemId(itemId);
            music.setmMusicTitle(musicTitle);
            music.setmCover(cover);
            music.setmStoryTitle(storyTitle);
            music.setmStoryContent(storyContent);
            music.setmLastUpdateDate(lastUpdateDate);
            music.setmMusicUserName(musicUserName);
            music.setmStoryAuthorName(storyAuthorName);
            musicDao.addMusic(music);
            return music;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *解析影视的JSON数据,将其封装为Bean对象,并返回
     * @param jsonData 从api中获取的影视JSON数据
     * @return 返回封装了解析后的影视数据的影视对象
     */
    public static Movie parseMovie(String jsonData) {
        try {
            MovieDao movieDao = new MovieDao();
            Movie movie = new Movie();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray dataArray = data.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject datas = dataArray.getJSONObject(i);
                String itemId = datas.getString("movie_id");
                String title = datas.getString("title");
                String content = datas.getString("content");
                String inputDate = datas.getString("input_date");
                JSONObject user = datas.getJSONObject("user");
                String userName = user.getString("user_name");
                movie.setmItemId(itemId);
                movie.setmTitle(title);
                movie.setmContent(content);
                movie.setmInputDate(inputDate);
                movie.setmAuthorName(userName);
            }
            movieDao.addMovie(movie);
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析插画的JSON数据
     * @param jsonData 表示从api中获取的插画JSON数据
     * @return 将解析的JSON数据封装成一个插画Bean对象,并返回
     */
    public static Inset parseInset(String jsonData){
        try {
            InsetDao insetDao = new InsetDao();
            Inset inset = new Inset();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            String title = data.getString("hp_title");
            String imgUrl = data.getString("hp_img_url");
            String hpAuthor = data.getString("hp_author");
            String content = data.getString("hp_content");
            String lastUpdateDate = data.getString("last_update_date");
            String imageAuthor = data.getString("image_authors");
            inset.setmTitle(title);
            inset.setmImgUrl(imgUrl);
            inset.setmHpAuthor(hpAuthor);
            inset.setmContent(content);
            inset.setmLastUpdateDate(lastUpdateDate);
            inset.setmImageAuthor(imageAuthor);
            insetDao.addInset(inset);
            return inset;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对网络获取的关于评论的JSON数据进行解析,
     * @param jsonData 表示从api中获取的JSON数据
     * @return 解析获得的数据封装为一个个Bean对象,并存放在容器中作为返回值
     */
    public static ArrayList<Comment> parseComment(String jsonData) {
        try {
            ArrayList<Comment> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray data1 = data.getJSONArray("data");
            for (int i = 0; i < data1.length(); i++) {
                JSONObject datas = data1.getJSONObject(i);
                String content = datas.getString("content");
                String createdTime = datas.getString("created_at");
                JSONObject user = datas.getJSONObject("user");
                String userName = user.getString("user_name");
                Comment comment = new Comment();
                comment.setmContent(content);
                comment.setmCreateTime(createdTime);
                comment.setmUserName(userName);
                list.add(comment);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
