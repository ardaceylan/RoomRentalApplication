package com.api.gobooking.user.appuser;


import com.api.gobooking.http.NameValueResponse;
import com.api.gobooking.http.TimeData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "gobooking/appuser")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AppUserController {
    private final AppUserService appUserService;

    @GetMapping
    public List<AppUser> getAppUsers(){
        return appUserService.getAppUsers();
    }

    @GetMapping(path = "{appUserId}")
    public AppUser getAppUser(@PathVariable("appUserId") Integer appUserId){
        return appUserService.getAppUser(appUserId);
    }

    @PostMapping
    public void addNewAppUser(@RequestBody AppUserRequest appUserRequest){
        appUserService.addAppUser(appUserRequest);
    }

    @DeleteMapping(path = "{appUserId}")
    public void deleteAppUser(@PathVariable("appUserId") Integer appUserId){
        appUserService.deleteAppUser(appUserId);
    }

    @PutMapping(path = "{appUserId}")
    public void updateAppUser( @PathVariable("appUserId") Integer appUserId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String surname,
                               @RequestParam(required = false) String password,
                               @RequestParam(required = false) Timestamp birthDate
                            )
    {
        appUserService.updateAppUser(appUserId, name, surname, password, birthDate);
    }

    @PutMapping(path = "set_is_blocked/{appUserId}")
    public void setIsBlocked(@PathVariable("appUserId") Integer appUserId, @RequestParam Boolean isBlocked){
        appUserService.setIsBlocked(appUserId, isBlocked);
    }

    @PutMapping(path = "set_is_banned_from_booking/{appUserId}")
    public void setIsBannedFromBooking(@PathVariable("appUserId") Integer appUserId, @RequestParam Boolean isBannedFromBooking){
        appUserService.setIsBannedFromBooking(appUserId, isBannedFromBooking);
    }

    @PutMapping(path = "set_is_banned_from_posting/{appUserId}")
    public void setIsBannedFromPosting(@PathVariable("appUserId") Integer appUserId, @RequestParam Boolean isBannedFromPosting){
        appUserService.setIsBannedFromPosting(appUserId, isBannedFromPosting);
    }

    @PutMapping(path = "update_balance/{appUserId}")
    public void updateBalance(@PathVariable("appUserId") Integer appUserId, @RequestParam Double balance){
        appUserService.updateBalance(appUserId, balance);
    }

    @PutMapping(path = "add_to_balance/{appUserId}")
    public void addToBalance(@PathVariable("appUserId") Integer appUserId, @RequestParam Double balance){
        appUserService.addToBalance(appUserId, balance);
    }

    @PutMapping(path = "update_city/{appUserId}")
    public void updateCity(@PathVariable("appUserId") Integer appUserId, @RequestParam String city){
        appUserService.updateCity(appUserId, city);
    }

    @PutMapping(path="update_tax_number/{appUserId}")
    public void updateTaxNumber(@PathVariable("appUserId") Integer appUserId, @RequestParam String taxNumber){
        appUserService.updateTaxNumber(appUserId, taxNumber);
    }

    @GetMapping(path = "top_user_location")
    public List<NameValueResponse> topUserLocation(){
        return appUserService.topUserLocation();
    }

    @GetMapping(path = "count_users={mode}")
    public List<TimeData> countUsersYear(@PathVariable("mode") Integer mode){
        return appUserService.countUsers(mode);
    }
}
