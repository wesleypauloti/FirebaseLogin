package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ClassLoader classLoader = DemoApplication.class.getClassLoader();

		File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());

		// Verifica se o FirebaseApp já foi inicializado
		if (FirebaseApp.getApps().isEmpty()) {
			try {
				FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
				
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://protoon-249ed-default-rtdb.firebaseio.com")
						.build();

				FirebaseApp.initializeApp(options);
			} catch (FileNotFoundException e) {
				// Trate a exceção de alguma forma, como imprimir uma mensagem de erro
				e.printStackTrace();
			} catch (IOException e) {
				// Trate a exceção de alguma forma, como imprimir uma mensagem de erro
				e.printStackTrace();
			}
		}

		SpringApplication.run(DemoApplication.class, args);

		System.out.println("\nRodando...\n");

	}

}
