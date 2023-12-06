package com.api.gobooking.user;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean userExists(Integer id){
        return userRepository.findById(id).isPresent();
    }

    public boolean userExists(String email){return userRepository.findByEmail(email).isPresent(); }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Integer id){

        if (!userExists(id)){
            throw new IllegalStateException(String.format("getUser: user with id (%s) does not exist", id));
        }

        return userRepository.findById(id).get();
    }

    public int saveUser(User user){
        return userRepository.save(user);
    }
    public User getUserByEmail(String email){
        if (!userExists(email)){
            return null;
        }
        return userRepository.findByEmail(email).get();
    }

    public void setPassword(Integer id, String password){
        User user = getUser(id);

        user.setPassword(encodePassword(password));

        userRepository.updateUser(user);
    }

    public String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean passwordMatches(String rawPassword, String encodedPassword){
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
