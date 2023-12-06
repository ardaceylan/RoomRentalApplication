package com.api.gobooking.user.appuser;

import com.api.gobooking.http.NameValueResponse;
import com.api.gobooking.http.TimeData;
import com.api.gobooking.user.User;
import com.api.gobooking.user.UserRepository;
import com.api.gobooking.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<AppUser> getAppUsers(){
        return appUserRepository.findAll();
    }

    public AppUser getAppUser(Integer id){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("getAppUser: AppUser with id (%s) does not exist", id));
        }

        return optionalAppUser.get();
    }

    public boolean addAppUser(AppUserRequest appUserRequest){

        Optional<User> optionalUser = userRepository.findByEmail(appUserRequest.getEmail());

        if (optionalUser.isPresent()){
            throw new IllegalStateException(String.format("addAppUser: User with email (%s) already exists", appUserRequest.getEmail()));
        }

        appUserRequest.setPassword(userService.encodePassword(appUserRequest.getPassword()));
        AppUser appUser = new AppUser(appUserRequest);


        appUserRepository.save(appUser);

        return true;
    }

    public boolean deleteAppUser(Integer id){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("deleteAppUser: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.deleteById(id);

        return true;
    }

    public boolean updateAppUser(Integer id, String name, String surname, String password, Timestamp birthDate){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("updateAppUser: AppUser with id (%s) does not exist", id));
        }

        AppUser appUser = optionalAppUser.get();

        if (name != null){
            appUser.setName(name);
        }
        if (surname != null){
            appUser.setSurname(surname);
        }
        if (password != null){
            appUser.setPassword(userService.encodePassword(password));
        }
        if (birthDate != null){
            appUser.setBirthDate(birthDate);
        }

        appUserRepository.updateAppUser(appUser);

        return true;
    }

    public boolean setIsBlocked(Integer id, Boolean isBlocked){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("setIsBlocked: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.setIsBlocked(id, isBlocked);

        return true;
    }

    public boolean setIsBannedFromBooking(Integer id, Boolean isBannedFromBooking){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("setIsBannedFromBooking: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.setIsBannedFromBooking(id, isBannedFromBooking);

        return true;
    }

    public boolean setIsBannedFromPosting(Integer id, Boolean isBannedFromPosting){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("setIsBannedFromPosting: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.setIsBannedFromPosting(id, isBannedFromPosting);

        return true;
    }

    public boolean updateBalance(Integer id, Double balance){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("updateBalance: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.updateBalance(id, balance);

        return true;
    }

    public boolean addToBalance(Integer id, Double balance){
        boolean success = false;
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("addToBalance: AppUser with id (%s) does not exist", id));
        }

        AppUser appUser = optionalAppUser.get();

        appUserRepository.updateBalance(id, appUser.getBalance() + balance);

        success = true;
        return success;
    }

    public boolean updateCity(Integer id, String city){
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("updateCity: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.updateCity(id, city);

        return true;
    }

    public boolean updateTaxNumber(Integer id, String taxNumber) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);

        if (optionalAppUser.isEmpty()){
            throw new IllegalStateException(String.format("updateTaxNumber: AppUser with id (%s) does not exist", id));
        }

        appUserRepository.updateTaxNumber(id, taxNumber);

        return true;
    }

    public List<NameValueResponse> topUserLocation() {
        return appUserRepository.topUserLocation();
    }

    public List<TimeData> countUsers(Integer mode) {
        return appUserRepository.countUsers(mode);
    }
}
