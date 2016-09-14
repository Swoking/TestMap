package app.swoking.fr.testmap.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

import app.swoking.fr.testmap.Game.Character;
import app.swoking.fr.testmap.Game.Inventory.Inventaire;
import app.swoking.fr.testmap.Game.Skin.Skin;
import app.swoking.fr.testmap.User;
import app.swoking.fr.testmap.Utils.App;
import app.swoking.fr.testmap.Utils.Config;
import app.swoking.fr.testmap.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(App.isLoged()){
            // profile activity
        }

        buttonRegister    = (Button)   findViewById(R.id.buttonRegister);
        editTextEmail     = (EditText) findViewById(R.id.editeTextEmail);
        editTextPassword  = (EditText) findViewById(R.id.editeTextPassword);
        textViewSignin    = (TextView) findViewById(R.id.textViewSingin);
        progressDialog    = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser(){
        String email    = editTextEmail.getText().toString().trim();
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
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start the profile activity here
                            //we save User in DataBase
                            Firebase ref = new Firebase(Config.FIREBASE_URL);

                            User user = new User();
                            user.setEmail(firebaseAuth.getCurrentUser().getEmail());
                            user.setHasCharacter(false);
                            ref.child("User").child(firebaseAuth.getCurrentUser().getUid()).setValue(user);

///////////////////////////////////////
                            Character character = new Character();
                            character.setName("");
                            character.setLvl(1);
                            character.setXp(0);
                            ref.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("Character").setValue(character);

                            Inventaire inventaire = new Inventaire();
                            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
                            hashMap.put(0, 0);
                            inventaire.setInventoryObjects(hashMap);
                            ref.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("Character").child("Inventaire").setValue(inventaire);

                            Skin skin = new Skin();
                            skin.setTete(0);
                            skin.setChaussure(0);
                            skin.setCheveux(0);
                            skin.setJambe(0);
                            skin.setPeau(0);
                            skin.setSexe(0);
                            ref.child("User").child(firebaseAuth.getCurrentUser().getUid()).child("Character").child("Skin").setValue(skin);
///////////////////////////////////////

                            //right now lets display a toast only
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Could not register... please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }

        if(view == textViewSignin){
            //Will open login activity here
        }
    }
}
