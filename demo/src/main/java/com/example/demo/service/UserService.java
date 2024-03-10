package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    public String createUser(User user) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> future = dbFirestore.collection("spring_firebase").add(user);
        DocumentReference documentReference = future.get();
        String id = documentReference.getId();
        user.setId(id);
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("spring_firebase").document(id).set(user);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public User getUser(String documentId) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("spring_firebase").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user;
        if(document.exists()) {
            user = document.toObject(User.class);
            return user;
        }
        return null;
    }
   public String updateUser(User user) throws ExecutionException, InterruptedException {
       Firestore dbFirestore = FirestoreClient.getFirestore();
       ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("spring_firebase").document(user.getName()).set(user);
       return collectionApiFuture.get().getUpdateTime().toString();
   }
   public String deleteUser(String documentId) {
       Firestore dbFirestore = FirestoreClient.getFirestore();
       System.out.println("Deleting document with ID: " + documentId);
       ApiFuture<WriteResult> writeResult = dbFirestore.collection("spring_firebase").document(documentId).delete();
       return "Deletado" + documentId;
   }
}
