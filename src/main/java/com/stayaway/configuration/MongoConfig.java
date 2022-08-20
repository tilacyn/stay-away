package com.stayaway.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.stayaway.dao.model.converter.BoardStateReadingConverter;
import com.stayaway.dao.model.converter.BoardStateWritingConverter;
import com.stayaway.dao.model.converter.PlayerReadingConverter;
import com.stayaway.dao.model.converter.PlayerWritingConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new ConnectionString(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return "stayaway";
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(List.of(
                new PlayerWritingConverter(),
                new PlayerReadingConverter(),
                new BoardStateReadingConverter(),
                new BoardStateWritingConverter()
        ));
    }
}
