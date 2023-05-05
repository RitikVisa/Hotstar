package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;
@Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay



        Subscription newSubscription= new Subscription();
        newSubscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        newSubscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());

        userRepository.save(userRepository.findById(subscriptionEntryDto.getUserId()).get());

      if(newSubscription.getSubscriptionType().equals(SubscriptionType.BASIC)){
          return 200;
      }
        if(newSubscription.getSubscriptionType().equals(SubscriptionType.PRO)){
            return 800;
        }
        if(newSubscription.getSubscriptionType().equals(SubscriptionType.ELITE)){
            return 1000;
        }








        return 0;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        User user = userRepository.findById(userId).get();

        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.ELITE)){
            throw new Exception("Already the best Subscription");
        }
        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.PRO)){
//            user.getSubscription().setSubscriptionType(SubscriptionType.ELITE);
//            user.getSubscription().setStartSubscriptionDate(new Date());
//            user.getSubscription().setNoOfScreensSubscribed(user.getSubscription().getNoOfScreensSubscribed()+350);
//           int totalAmountToPay= 1000-user.getSubscription().getTotalAmountPaid();
//           user.getSubscription().setTotalAmountPaid(1000);

           Subscription sub = subscriptionRepository.findById(user.getSubscription().getId()).get();
           sub.setSubscriptionType(SubscriptionType.ELITE);
           sub.setTotalAmountPaid(1000);
           sub.setStartSubscriptionDate(new Date());
            user.setSubscription(sub);
//            subscriptionRepository.findById(user.getSubscription().getId()).get().setSubscriptionType(SubscriptionType.ELITE));
//            subscriptionRepository.findById(user.getSubscription().getId()).get().setTotalAmountPaid(1000);
//            subscriptionRepository.findById(user.getSubscription().getId()).get().setStartSubscriptionDate(new Date());

            userRepository.save(user);
           return 200 ;
        }
        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.BASIC)){
            Subscription sub = subscriptionRepository.findById(user.getSubscription().getId()).get();
            sub.setSubscriptionType(SubscriptionType.PRO);
            sub.setTotalAmountPaid(800);
            sub.setStartSubscriptionDate(new Date());
            user.setSubscription(sub);
            userRepository.save(user);
            return 300;
        }

        return null;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        Integer revenue =0;

        List<Subscription> subscriptionList = new ArrayList<>();
        subscriptionList= subscriptionRepository.findAll();
        for(Subscription s : subscriptionList){
            revenue += s.getTotalAmountPaid();
        }




        return revenue;
    }

}
