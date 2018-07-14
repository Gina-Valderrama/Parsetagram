package me.gina.parsetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText emailInput;
    private Button createAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.etSignupUsername);
        passwordInput = findViewById(R.id.etSignupPW);
        emailInput = findViewById(R.id.etSignupEmail);
        createAccountBtn = findViewById(R.id.btnCreateAccount);

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
}
