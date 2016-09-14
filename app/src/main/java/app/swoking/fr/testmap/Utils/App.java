package app.swoking.fr.testmap.Utils;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by rgab7 on 31/08/2016.
 */
public class App {

    public static boolean isLoged(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth.getCurrentUser() != null;
    }

}
