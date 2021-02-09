package com.osvalr.minesweeper;

import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.google.firebase.FirebaseApp.initializeApp;

@Component
public class FirebaseApplication {
    private final FirebaseOptions firebaseOptions;

    public FirebaseApplication(FirebaseOptions firebaseOptions) {
        this.firebaseOptions = firebaseOptions;
    }

    @PostConstruct
    public void initApp() {
        initializeApp(firebaseOptions);
    }
}
