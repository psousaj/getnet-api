package com.psousaj.getnetapi.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import jakarta.annotation.PostConstruct;

@Configuration
public class FirebaseInitialization {
	
		public Firestore firestore() throws IOException {
			FileInputStream serviceAccount = new FileInputStream("./serviceAccountKey.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
			        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
			        .setDatabaseUrl("https://taxcontabilidade-cb26b-default-rtdb.firebaseio.com")
			        .build();
			
			FirebaseApp.initializeApp(options);
			
			return FirestoreClient.getFirestore();
		}
    }
