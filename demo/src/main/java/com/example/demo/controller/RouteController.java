package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import java.util.concurrent.ExecutionException;


@Controller
public class RouteController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @GetMapping("")
    public String index() {

        return "index.html";
    }

    @GetMapping("/home")
    public String home() {
        return "index.html";
    }

    @GetMapping("/cadastrar")
    public String cadastro(Model model) {
        model.addAttribute("user", new User());
        return "cadastro.html";
    }

    @PostMapping("/cadastrando")
    public String salvandoDados(@RequestBody User user) throws ExecutionException, InterruptedException {
        userService.createUser(user);
        return "cadastrado";
    }

    @PostMapping("/login")
    public String submitLogin(@RequestParam("name") String name, @RequestParam("password") String password, Model model) {
        if (!userExistsInFirebase(name, password)) {
            model.addAttribute("message", "Usuário não encontrado");
            return "redirect";
        }
        model.addAttribute("nomeUsuario", name);
        return "sucesso";
    }

    public boolean userExists(String name, String password) {
        User user = userRepository.findByName(name);
        return user != null && user.getPassword().equals(password);
    }

    public boolean userExistsInFirebase(String name, String password) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference users = dbFirestore.collection("spring_firebase");
        Query query = users.whereEqualTo("name", name).whereEqualTo("password", password);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                // Se encontrou um documento com o nome de usuário e senha correspondentes, retorna true
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}

