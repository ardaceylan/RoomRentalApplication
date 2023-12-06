package com.api.gobooking.property;

import com.api.gobooking.http.DoubleTimeData;
import com.api.gobooking.user.UserService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserService userService;

    public boolean propertyExists(Integer id){
        return propertyRepository.findById(id).isPresent();
    }
    public boolean propertyExistsbyOwnerId(Integer id){
        return (!propertyRepository.findByPropertyOwnerId(id).isEmpty());
    }

    public List<Property> getProperties(){
        return propertyRepository.findAll();
    }

    public Property getProperty(Integer id){

        if (!propertyExists(id)){
            throw new IllegalStateException(String.format("getProperty: property with id (%s) does not exist", id));
        }

        return propertyRepository.findById(id).get();
    }

    public List<Property> getPropertyByOId(Integer id){

        if (!userService.userExists(id)){//UserExists
            throw new IllegalStateException(String.format("getProperty: property with id (%s) does not exist", id));
        }

        return propertyRepository.findByPropertyOwnerId(id);
    }

    public List<Property> getPropertyByFiltering(String city, Integer max_people, PropertyType type, Integer filterType){

        return propertyRepository.findByFiltering(city, max_people, type, filterType);
    }

    public boolean addProperty(PropertyRequest propertyRequest){
        boolean success = false;
        Property property = new Property(propertyRequest);
        success =propertyRepository.save(property, propertyRequest);
        return success;
    }

    public boolean deleteProperty(Integer id){
        boolean success = false;
        Optional<Property> optionalReview = propertyRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("deleteProperty: property with id (%s) does not exist", id));
        }

        propertyRepository.deleteById(id);
        success = true;
        return success;
    }

    public boolean updateProperty(Integer id, PropertyRequest propertyRequest){
        boolean success = false;
        Optional<Property> optionalReview = propertyRepository.findById(id);

        if (optionalReview.isEmpty()){
            throw new IllegalStateException(String.format("updateProperty: Property with id (%s) does not exist", id));
        }

        Property property = optionalReview.get();
        propertyRepository.updateProperty(property, propertyRequest);
        success = true;
        return success;
    }


    public List<PropertyResponse> getPropertiesSort(Integer sortMode) {
        return propertyRepository.getPropertiesSort(sortMode);
    }

    public List<DoubleTimeData> countProperty(Integer mode) {
        return propertyRepository.countProperty(mode);
    }

    public boolean updateRating(Integer id, Double rating){
        Optional<Property> optionalProperty = propertyRepository.findById(id);

        if (optionalProperty.isEmpty()){
            throw new IllegalStateException(String.format("updateBalance: AppUser with id (%s) does not exist", id));
        }

        propertyRepository.updateRating(id, rating);

        return true;
    }

}
