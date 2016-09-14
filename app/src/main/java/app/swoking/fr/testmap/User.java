package app.swoking.fr.testmap;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

import app.swoking.fr.testmap.Activity.LoginActivity;
import app.swoking.fr.testmap.Game.Character;
import app.swoking.fr.testmap.Game.Inventory.Inventaire;
import app.swoking.fr.testmap.Game.Skin.Skin;

public class User implements Serializable{

    private String  email;
    private boolean hasCharacter;
    private boolean ready = false;

    public User(){
        this.ready = false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean HasCharacter() {
        return hasCharacter;
    }

    public void setHasCharacter(boolean hasCharacter) {
        this.hasCharacter = hasCharacter;
    }

    public static User getUserFromId(String id, final LoginActivity loginActivity){
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        final User       user       = new User();
        final Character  character  = new Character();
        final Inventaire inventaire = new Inventaire();
        final Skin       skin       = new Skin();

        user.setReady(false);

        firebaseDatabase.child("User").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setEmail    (                dataSnapshot.child("email")                  .getValue().toString());
                character.setLvl (Integer.valueOf(dataSnapshot.child("Character").child("lvl") .getValue().toString()));
                character.setName(                dataSnapshot.child("Character").child("name").getValue().toString());
                character.setXp  (Integer.valueOf(dataSnapshot.child("Character").child("xp")  .getValue().toString()));
                //inventaire.setInventoryObjects();
                skin.setChaussure(Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("chaussure").getValue().toString()));
                skin.setCheveux  (Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("cheveux")  .getValue().toString()));
                skin.setJambe    (Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("jambe")    .getValue().toString()));
                skin.setPeau     (Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("peau")     .getValue().toString()));
                skin.setSexe     (Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("sexe")     .getValue().toString()));
                skin.setTete     (Integer.valueOf(dataSnapshot.child("Character").child("Skin").child("tete")      .getValue().toString()));

                loginActivity.loginUser(user, character, skin);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loginActivity.getProgressDialog().dismiss();
            }
        });
        return user;
    }
}
