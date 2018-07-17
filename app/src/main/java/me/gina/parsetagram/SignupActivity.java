package me.gina.parsetagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.IOException;

import me.gina.parsetagram.model.BitmapScaler;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button createAccountBtn;
    private ImageView profPic;
    private Bitmap imageBitmap;

    public final static int PICK_PHOTO_CODE = 1046;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.etSignupUsername);
        passwordInput = findViewById(R.id.etSignupPW);
        emailInput = findViewById(R.id.etSignupEmail);
        createAccountBtn = findViewById(R.id.btnCreateAccount);
        profPic = findViewById(R.id.ivProfilesignup);

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String email  = emailInput.getText().toString();

                createAccount(username, password, email);
            }
        });

    }

    private void createAccount(String username, String password, String email){
        ParseUser.getCurrentUser().logOut();
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.d("SignupActivity", "successful sign up");
                    final Intent i = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    e.printStackTrace();
                    Log.e("SignupActivity", "signup not succesful");
                }
            }
        });
    }
    public void profLaunchGallery(View v){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_PHOTO_CODE) {
            if (data != null) {
                Uri photoUri = data.getData();
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageBitmap = BitmapScaler.scaleToFitHeight(imageBitmap, 100);

                profPic.setImageBitmap(imageBitmap);
            }
        }
    }
}
