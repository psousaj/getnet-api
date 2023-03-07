package com.psousaj.getnetapi.firebase;

import java.io.FileInputStream;

import org.springframework.stereotype.Service;

@Service
public class FirebaseInitialization {
    public void initialization() {

        FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://taxcontabilidade-cb26b-default-rtdb.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

    }
}
