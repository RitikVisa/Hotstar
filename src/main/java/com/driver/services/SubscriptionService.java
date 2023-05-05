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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {




    SubscriptionRepository subscriptionRepository= new SubscriptionRepository() {
        @Override
        public List<Subscription> findAll() {
            return null;
        }

        @Override
        public List<Subscription> findAll(Sort sort) {
            return null;
        }

        @Override
        public List<Subscription> findAllById(Iterable<Integer> iterable) {
            return null;
        }

        @Override
        public <S extends Subscription> List<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Subscription> S saveAndFlush(S s) {
            return null;
        }

        @Override
        public void deleteInBatch(Iterable<Subscription> iterable) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Subscription getOne(Integer integer) {
            return null;
        }

        @Override
        public <S extends Subscription> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends Subscription> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public Page<Subscription> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Subscription> S save(S s) {
            return null;
        }

        @Override
        public Optional<Subscription> findById(Integer integer) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Integer integer) {
            return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {

        }

        @Override
        public void delete(Subscription subscription) {

        }

        @Override
        public void deleteAll(Iterable<? extends Subscription> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public <S extends Subscription> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Subscription> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Subscription> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Subscription> boolean exists(Example<S> example) {
            return false;
        }
    };


    UserRepository userRepository= new UserRepository() {
        @Override
        public List<User> findAll() {
            return null;
        }

        @Override
        public List<User> findAll(Sort sort) {
            return null;
        }

        @Override
        public List<User> findAllById(Iterable<Integer> iterable) {
            return null;
        }

        @Override
        public <S extends User> List<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends User> S saveAndFlush(S s) {
            return null;
        }

        @Override
        public void deleteInBatch(Iterable<User> iterable) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public User getOne(Integer integer) {
            return null;
        }

        @Override
        public <S extends User> List<S> findAll(Example<S> example) {
            return null;
        }

        @Override
        public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
            return null;
        }

        @Override
        public Page<User> findAll(Pageable pageable) {
            return null;
        }

        @Override
        public <S extends User> S save(S s) {
            return null;
        }

        @Override
        public Optional<User> findById(Integer integer) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Integer integer) {
            return false;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Integer integer) {

        }

        @Override
        public void delete(User user) {

        }

        @Override
        public void deleteAll(Iterable<? extends User> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public <S extends User> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends User> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends User> boolean exists(Example<S> example) {
            return false;
        }
    };

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        if(!userRepository.findById(subscriptionEntryDto.getUserId()).isPresent()){
            return 0;
        }
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        Subscription newSubscription= new Subscription();
        newSubscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        newSubscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        newSubscription.setUser(user);
        user.setSubscription(newSubscription);
        String s= "BASIC";
      if(subscriptionEntryDto.getSubscriptionType().equals(SubscriptionType.BASIC)){
          return 200;
      }
        if(subscriptionEntryDto.getSubscriptionType().equals(SubscriptionType.PRO)){
            return 800;
        }
        if(subscriptionEntryDto.getSubscriptionType().equals(SubscriptionType.ELITE)){
            return 1000;
        }








        return 0;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository
        if(!userRepository.findById(userId).isPresent()){
            throw new Exception("user not present");
        }

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


           return 200 ;
        }
        if(user.getSubscription().getSubscriptionType().equals(SubscriptionType.BASIC)){
            Subscription sub = subscriptionRepository.findById(user.getSubscription().getId()).get();
            sub.setSubscriptionType(SubscriptionType.PRO);
            sub.setTotalAmountPaid(800);
            sub.setStartSubscriptionDate(new Date());
            user.setSubscription(sub);
            return 300;
        }

        return null;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        List<Subscription> subscriptionList= subscriptionRepository.findAll();
        int revenue=0;
        for(Subscription sub:subscriptionList){
            revenue += sub.getTotalAmountPaid();


        }


        return revenue;
    }

}
