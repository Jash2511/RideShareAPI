package com.epicman.rideshare.service;

import com.epicman.rideshare.model.UserModel;
import org.bson.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AnalyticsService {

    private final MongoTemplate template;

    public AnalyticsService(MongoTemplate template) {
        this.template = template;
    }

    @Cacheable(value = "driver_earnings", key = "#driverId")
    public Double totalEarnings(String driverId) {
        MatchOperation match = match(Criteria.where("driverId").is(driverId));
        GroupOperation group = group().sum("fare").as("total");
        Aggregation agg = newAggregation(match, group);

        Document result = template.aggregate(agg, "rides", Document.class)
                .getUniqueMappedResult();

        return result != null ? result.getDouble("total") : 0.0;
    }
}
