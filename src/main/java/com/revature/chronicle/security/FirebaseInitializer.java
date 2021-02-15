package com.revature.chronicle.security;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;

import lombok.extern.slf4j.Slf4j;

/**
 * service class used to initialize the connection between the server and firebase when the server starts up
 */
@Service
@Slf4j
public class FirebaseInitializer {

//	private static Logger log = Logger.get
	private static String token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjJjMmVkODQ5YThkZTI3ZTI0NjFlNGJjM2VmMDZhYzdhYjc4OGQyMmIiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiU29tZSBUaGluZyIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS90cmFpbmluZy10ZWFtLTI1MzkxNiIsImF1ZCI6InRyYWluaW5nLXRlYW0tMjUzOTE2IiwiYXV0aF90aW1lIjoxNjEyNTYzNjE1LCJ1c2VyX2lkIjoibGJrR1BVMjNKMlg0aEpteTR1ZGpWSldyU29lMiIsInN1YiI6Imxia0dQVTIzSjJYNGhKbXk0dWRqVkpXclNvZTIiLCJpYXQiOjE2MTI1NjM2MTUsImV4cCI6MTYxMjU2NzIxNSwiZW1haWwiOiJzb21ldGhpbmdAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInNvbWV0aGluZ0BnbWFpbC5jb20iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.zFk_QLxhSSv_ATZ4Xox3-hvxgpClbt5X2s4_2C4cTY7o0YC62a_dvdzucV4ykfuJ8NiiJ4OmLI5aU5ZHZvsB_0gEHbbNDnX158aHqOYCwoM8Q_bOrVLtZLnZSvdH7LMJAJEQbkhzX8dUBDFBthDR-6uCaC28JAqyBbV99VHqHajGCwqqrW9n9dUP1z_BjZ3xrYj3z6XPg43btDl094NNFbWCF2RuEa36h6HQorlyosbsV89bgExqtt2zs4zPVPo70VAIPyp77hknoNmaRaOSTzkA53mjRLfxdaBjNCM8yBUl9JOTyjbE376gBlRafdkNBzREbjhIww9g_qXqsmokdw";
    /**
     * runs immediately after the service is initialized. tries to call initializeFirebaseApp
     */
    @PostConstruct
    public void onStart() {
        log.info("Initializing Firebase Application");
        try {
            this.initializeFirebaseApp();
        } catch (IOException e) {
            log.error("Initializing Firebase App {}", e);
        } catch (FirebaseAuthException e) {
        	
        }
    }

    /**
     * attempts to set the connection with Firebase that will be used to authenticate the clients' JWTs. Does so using
     * the credentials in firebase-service-credentials.json
     * @throws IOException - could be thrown if google credentials cannot read the credentials file as a stream
     */
    private void initializeFirebaseApp() throws IOException, FirebaseAuthException{

        if (FirebaseApp.getApps() == null || FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials.fromStream(
                                    //new FileInputStream(System.getenv("FIREBASE_ACCOUNT"))
                            		returnResourceAsStream("/secrets/firebase-service-credentials.json")
                            )
                    )
                    .build();

            FirebaseApp.initializeApp(options);
        }

    }

    /**
     * returns an InputStream reading the given resource
     * @param resource The resource to be read
     * @return the stream reading "resource"
     */
    protected InputStream returnResourceAsStream(String resource) {
        return FirebaseInitializer.class.getResourceAsStream(resource);
    }

}
