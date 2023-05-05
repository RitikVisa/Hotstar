package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.SubscriptionType;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
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
public class WebSeriesService {


   @Autowired
    WebSeriesRepository webSeriesRepository;
    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo
        List<WebSeries> webSeriesList = webSeriesRepository.findAll();
        for(WebSeries webs: webSeriesList){
            if(webs.getSeriesName().equals(webSeriesEntryDto.getSeriesName())){
                throw new Exception("Series is already present");
            }
        }


            WebSeries w =new WebSeries();
            w.setSeriesName(webSeriesEntryDto.getSeriesName());
            w.setRating(webSeriesEntryDto.getRating());
            w.setAgeLimit(webSeriesEntryDto.getAgeLimit());
            w.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

            List<ProductionHouse> productionHouseList = productionHouseRepository.findAll();
            for(ProductionHouse p : productionHouseList){
                if(p.getId()==webSeriesEntryDto.getProductionHouseId()){
                    p.getWebSeriesList().add(w);
                    w.setProductionHouse(p);

                    double allRating= 0;

                    for(WebSeries web : p.getWebSeriesList()){

                        allRating += web.getRating();

                    }
                    p.setRatings(allRating/p.getWebSeriesList().size());

                    p.setRatings(w.getRating());

                    productionHouseRepository.save(p);
                    return w.getId();
                }
            }












  return 0;

    }

}
