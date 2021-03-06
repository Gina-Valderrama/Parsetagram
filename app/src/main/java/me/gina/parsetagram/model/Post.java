package me.gina.parsetagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_TIME = "time";

    public String getCaption(){
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption){
        put(KEY_CAPTION, caption);
    }



    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }



    public static class Query extends ParseQuery<Post>{
        public Query () {
            super(Post.class);
            include("time");
        }

        public Query getTop(){
            setLimit(40);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }

        public Query createdBy(ParseUser user) {
            whereEqualTo(KEY_USER, user);
            return this;
        }
    }


}
