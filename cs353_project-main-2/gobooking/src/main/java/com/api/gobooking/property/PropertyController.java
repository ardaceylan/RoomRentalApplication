package com.api.gobooking.property;

import com.api.gobooking.http.DoubleTimeData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("gobooking/property")
@AllArgsConstructor
@CrossOrigin("*")
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<Property> getProperties(){
        return propertyService.getProperties();
    }


    @GetMapping(path = "sort_by_rating")
    public List<PropertyResponse> getPropertiesSortByRating(){
        return propertyService.getPropertiesSort(1);
    }

    @GetMapping(path = "sort_by_date")
    public List<PropertyResponse> getPropertiesSortByDate(){
        return propertyService.getPropertiesSort(2);
    }

    @GetMapping(path = "sort_by_booked")
    public List<PropertyResponse> getPropertiesSortByBooked(){
        return propertyService.getPropertiesSort(3);
    }

    @GetMapping(path = "doesownerhaveproperty/{propertyId}")
    public boolean doesOwnerHaveProperty(@PathVariable(name = "propertyId") int propertyId){
        return propertyService.propertyExistsbyOwnerId(propertyId);

    }

    @GetMapping(path = "{propertyId}")
    public Property getPropertyById(@PathVariable(name = "propertyId") int propertyId){
        return propertyService.getProperty(propertyId);
    }

    @GetMapping(path = "getbyownerid/{ownerId}")
    public List<Property> getPropertyByOwnerId(@PathVariable(name = "ownerId") int ownerId){
        return propertyService.getPropertyByOId(ownerId);
    }

    @GetMapping(path = "/getbyfiltering")
    public List<Property> getPropertyByFiltering(@RequestParam(value = "city", required = false) String city, @RequestParam("maxGuests") Integer maxGuests, @RequestParam(value = "type", required = false) PropertyType type){
        Integer filterType = 3;
        if (city != null && maxGuests != null && type != null) {
            filterType = 0;
        }
        else if(city != null && maxGuests != null) {
            filterType = 1;
        }
        else if(type != null && maxGuests != null) {
            filterType = 2;
        }
        else if(maxGuests != null) {
            filterType = 3;
        }
        return propertyService.getPropertyByFiltering(city, maxGuests, type, filterType);
    }

    @DeleteMapping(path = "{property_id}")
    public void deleteProperty(@PathVariable("property_id") Integer property_id){
        propertyService.deleteProperty(property_id);
    }

    @PutMapping(path = "{property_id}")
    public void updateProperty( @PathVariable("property_id") Integer id,
                                @RequestBody PropertyRequest propertyRequest)
    {
        propertyService.updateProperty(id, propertyRequest);
    }

    @PostMapping
    public void addNewProperty(@RequestBody PropertyRequest propertyRequest){
        propertyService.addProperty(propertyRequest);
    }


    @GetMapping(path = "count_property={mode}")
    public List<DoubleTimeData> countProperty(@PathVariable("mode") Integer mode){
        return propertyService.countProperty(mode);
    }

    @PutMapping(path = "update_rating/{property_id}")
    public void updateRating(@PathVariable("property_id") Integer property_id, @RequestParam Double rating){
        propertyService.updateRating(property_id, rating);
    }


}
