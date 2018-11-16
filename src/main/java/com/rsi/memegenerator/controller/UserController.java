package com.rsi.memegenerator.controller;


import org.springframework.web.bind.annotation.*;
import static com.rsi.memegenerator.constant.URLConstants.API;
import static com.rsi.memegenerator.constant.URLConstants.USER;

//@Controller
//@RestController
//@RequestMapping(API + USER)
public class UserController {
//
//
//    @GetMapping("{id}")
//    User getUser(@PathVariable long id) throws UserNotFoundException {
//        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping(SIGN_UP)
//    ResponseEntity create(@RequestParam String email, @RequestParam String password) {
//        if (userRepository.findByEmail(email) == null) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setPassword(passwordEncoder.encode(password));
//            userRepository.save(newUser);
//            System.out.println(newUser);
//            return ResponseEntity.ok("account created");
//        }
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("email conflict");
//    }
//
//    @PostMapping(LOGIN)
//    ResponseEntity login(@RequestBody String email, @RequestBody String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null /*&& user.*/) {
//            return ResponseEntity.status(HttpStatus.FOUND).body(user);
//        }
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("check");
//    }

//    @PutMapping
//    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setFirstName(newUser.getFirstName());
//                    user.setLastName(newUser.getLastName());
//                    return userRepository.save(user);
//                })
//                .orElseGet(() -> {
//                    newUser.setUuid(id);
//                    return userRepository.save(newUser);
//                });
//    }
}