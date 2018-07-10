package me.gina.parsetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import me.gina.parsetagram.model.Post;

public class ParseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration config = new Parse.Configuration.Builder(this)
                .applicationId("parse123")
                .clientKey("parseABC")
                .server("http://gina-valderrama-fbu-instagram.herokuapp.com/parse")
                .build();

        Parse.initialize(config);
    }
}
