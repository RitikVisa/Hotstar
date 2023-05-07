package com.driver.services;


import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.model.WebSeries;
import com.driver.repository.UserRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {



    @Autowired
    UserRepository userRepository;
    @Autowired
    WebSeriesRepository webSeriesRepository;



    public Integer addUser(User user){

       User u = userRepository.save(user);

       return u.getId();
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
//
        User user = userRepository.findById(userId).get();

        List<WebSeries> allWebSeries = webSeriesRepository.findAll();
        int count =0;

        for(WebSeries web : allWebSeries){
            if(web.getAgeLimit()<=user.getAge() && user.getSubscription().getSubscriptionType()==SubscriptionType.ELITE){
                count ++;
            }if(web.getAgeLimit()<=user.getAge() && user.getSubscription().getSubscriptionType()==SubscriptionType.PRO && (web.getSubscriptionType()==SubscriptionType.BASIC || web.getSubscriptionType()==SubscriptionType.PRO) ){
                count++;
            }if(web.getAgeLimit()<=user.getAge() && user.getSubscription().getSubscriptionType()==SubscriptionType.BASIC && web.getSubscriptionType()==SubscriptionType.BASIC){
                count++;
            }
        }

        return count;


    }


}
