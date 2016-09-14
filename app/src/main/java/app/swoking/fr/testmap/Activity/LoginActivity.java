package app.swoking.fr.testmap.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import app.swoking.fr.testmap.Game.Character;
import app.swoking.fr.testmap.Game.Skin.Skin;
import app.swoking.fr.testmap.User;
import app.swoking.fr.testmap.Utils.App;
import app.swoking.fr.testmap.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if(App.isLoged()){
            final User user = User.getUserFromId(firebaseAuth.getCurrentUser().getUid(), this);
            progressDialog.setMessage("Login in...");
            progressDialog.show();
        }

        buttonSignIn    = (Button)   findViewById(R.id.buttonSignin);
        editTextEmail     = (EditText) findViewById(R.id.editeTextEmail);
        editTextPassword  = (EditText) findViewById(R.id.editeTextPassword);
        textViewSignup    = (TextView) findViewById(R.id.textViewSingup);

        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            //stopping the function
            return;
        }
        //if validation are OK

        //we will first show a progressbar
        progressDialog.setMessage("Login in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignIn) {
            userLogin();
        }
        if(view == textViewSignup) {
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void loginUser(User user, Character character, Skin skin) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("character", character);
        intent.putExtra("skin", skin);
        startActivity(intent);
    }
}
