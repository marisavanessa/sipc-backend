package com.loliana.sipc.controller;


import com.loliana.sipc.model.User;
import com.loliana.sipc.repository.UserRepository;
import com.loliana.sipc.service.CepService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    public class UserController {

    private final UserRepository userRepository;
    private final CepService cepService;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@PostMapping
    //public User createUser(@RequestBody User user) {
    //    return userRepository.save(user);
    //}

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        /*
        if (user.getCep() != null) {
            String endereco = cepService.buscarEnderecoPorCep(user.getCep());
            if (endereco != null) {
                user.setAddress(endereco);
            }
        }
         */

        if (userRepository.findByCpf(user.getCpf()).isPresent()) {
            return ResponseEntity.badRequest().body("CPF já cadastrado.");
        }

        if (user.getCns() != null && userRepository.findByCns(user.getCns()).isPresent()) {
            return ResponseEntity.badRequest().body("CNS já cadastrado.");
        }

        if (user.getCep() != null) {
            cepService.preencherDadosEndereco(user.getCep(), user);
        }
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setCpf(updatedUser.getCpf());
                    user.setCns(updatedUser.getCns());
                    user.setName(updatedUser.getName());
                    user.setSocialName(updatedUser.getSocialName());
                    user.setCep(updatedUser.getCep());
                    user.setAddress(updatedUser.getAddress());
                    user.setNumber(updatedUser.getNumber());
                    user.setDistrict(updatedUser.getDistrict());
                    user.setCity(updatedUser.getCity());
                    user.setUf(updatedUser.getUf());
                    user.setBirthDate(updatedUser.getBirthDate());
                    user.setGender(updatedUser.getGender());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    /*
                    if (updatedUser.getCep() != null) {
                        String endereco = cepService.buscarEnderecoPorCep(updatedUser.getCep());
                        if (endereco != null) {
                            existingUser.setAddress(endereco);
                        }
                        existingUser.setCep(updatedUser.getCep());
                    }
                     */

                    if (updatedUser.getCpf() != null &&
                            !updatedUser.getCpf().equals(existingUser.getCpf()) &&
                            userRepository.findByCpf(updatedUser.getCpf()).isPresent()) {
                        return ResponseEntity.badRequest().body("CPF já cadastrado.");
                    }

                    if (updatedUser.getCns() != null &&
                            !updatedUser.getCns().equals(existingUser.getCns()) &&
                            userRepository.findByCns(updatedUser.getCns()).isPresent()) {
                        return ResponseEntity.badRequest().body("CNS já cadastrado.");
                    }

                    if (updatedUser.getCep() != null) {
                        existingUser.setCep(updatedUser.getCep());
                        cepService.preencherDadosEndereco(updatedUser.getCep(), existingUser);
                    }

                    // Atualize outros campos como no seu PUT atual...
                    existingUser.setCpf(updatedUser.getCpf());
                    existingUser.setCns(updatedUser.getCns());
                    existingUser.setName(updatedUser.getName());
                    existingUser.setSocialName(updatedUser.getSocialName());
                    existingUser.setNumber(updatedUser.getNumber());
                    //existingUser.setDistrict(updatedUser.getDistrict());
                    //existingUser.setCity(updatedUser.getCity());
                    //existingUser.setUf(updatedUser.getUf());
                    existingUser.setBirthDate(updatedUser.getBirthDate());
                    existingUser.setGender(updatedUser.getGender());

                    User saved = userRepository.save(existingUser);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Integer id, @RequestBody User partialUpdate) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (partialUpdate.getCpf() != null) existingUser.setCpf(partialUpdate.getCpf());
                    if (partialUpdate.getCns() != null) existingUser.setCns(partialUpdate.getCns());
                    if (partialUpdate.getName() != null) existingUser.setName(partialUpdate.getName());
                    if (partialUpdate.getSocialName() != null) existingUser.setSocialName(partialUpdate.getSocialName());
                    if (partialUpdate.getCep() != null) existingUser.setCep(partialUpdate.getCep());
                    if (partialUpdate.getAddress() != null) existingUser.setAddress(partialUpdate.getAddress());
                    if (partialUpdate.getNumber() != null) existingUser.setNumber(partialUpdate.getNumber());
                    if (partialUpdate.getDistrict() != null) existingUser.setDistrict(partialUpdate.getDistrict());
                    if (partialUpdate.getCity() != null) existingUser.setCity(partialUpdate.getCity());
                    if (partialUpdate.getUf() != null) existingUser.setUf(partialUpdate.getUf());
                    if (partialUpdate.getBirthDate() != null) existingUser.setBirthDate(partialUpdate.getBirthDate());
                    if (partialUpdate.getGender() != null) existingUser.setGender(partialUpdate.getGender());

                    User saved = userRepository.save(existingUser);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

     */

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchUser(@PathVariable Integer id, @Valid @RequestBody User partialUpdate) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    /*
                    if (partialUpdate.getCep() != null) {
                        existingUser.setCep(partialUpdate.getCep());
                        String endereco = cepService.buscarEnderecoPorCep(partialUpdate.getCep());
                        if (endereco != null) {
                            existingUser.setAddress(endereco);
                        }
                    }
                     */

                    if (partialUpdate.getCpf() != null &&
                            !partialUpdate.getCpf().equals(existingUser.getCpf()) &&
                            userRepository.findByCpf(partialUpdate.getCpf()).isPresent()) {
                        return ResponseEntity.badRequest().body("CPF já cadastrado.");
                    }

                    if (partialUpdate.getCns() != null &&
                            !partialUpdate.getCns().equals(existingUser.getCns()) &&
                            userRepository.findByCns(partialUpdate.getCns()).isPresent()) {
                        return ResponseEntity.badRequest().body("CNS já cadastrado.");
                    }

                    if (partialUpdate.getCep() != null) {
                        existingUser.setCep(partialUpdate.getCep());
                        cepService.preencherDadosEndereco(partialUpdate.getCep(), existingUser);
                    }

                    if (partialUpdate.getCpf() != null) existingUser.setCpf(partialUpdate.getCpf());
                    if (partialUpdate.getCns() != null) existingUser.setCns(partialUpdate.getCns());
                    if (partialUpdate.getName() != null) existingUser.setName(partialUpdate.getName());
                    if (partialUpdate.getSocialName() != null) existingUser.setSocialName(partialUpdate.getSocialName());
                    if (partialUpdate.getNumber() != null) existingUser.setNumber(partialUpdate.getNumber());
                    //if (partialUpdate.getDistrict() != null) existingUser.setDistrict(partialUpdate.getDistrict());
                    //if (partialUpdate.getCity() != null) existingUser.setCity(partialUpdate.getCity());
                    //if (partialUpdate.getUf() != null) existingUser.setUf(partialUpdate.getUf());
                    if (partialUpdate.getBirthDate() != null) existingUser.setBirthDate(partialUpdate.getBirthDate());
                    if (partialUpdate.getGender() != null) existingUser.setGender(partialUpdate.getGender());

                    User saved = userRepository.save(existingUser);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}