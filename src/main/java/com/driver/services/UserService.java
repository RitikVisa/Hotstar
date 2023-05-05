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

        userRepository.save(user);

        List<User> userList = userRepository.findAll();
        if(!userList.isEmpty()){
          for(User user1 : userList){
              if(user1.getName().equals(user.getName())){
                  return user1.getId();
              }

          }

        }
       return 0;
    }

    public Integer getAvailableCountOfWebSeriesViewable(Integer userId){

        //Return the count of all webSeries that a user can watch based on his ageLimit and subscriptionType
        //Hint: Take out all the Webseries from the WebRepository
//
        User user = userRepository.findById(userId).get();
        List<WebSeries> listweb =new ArrayList<>();
        if(userRepository.findById(userId).isPresent()){
            SubscriptionType subscriptionType=user.getSubscription().getSubscriptionType();
            List<WebSeries> allWebSeries = webSeriesRepository.findAll();


            for(WebSeries web : allWebSeries){
                if(web.getAgeLimit()<=user.getAge() && web.getSubscriptionType().equals(user.getSubscription().getSubscriptionType())){
                    listweb.add(web);
                }
            }

        }







        return listweb.size();
    }


}
