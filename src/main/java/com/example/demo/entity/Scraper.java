package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Entity
@Getter
@Setter
public class Scraper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String urlTemplate; //Example https://api.weather.com/v3/wx/forecast?geocode=%S,%S&format=json&apiKey=%S

    @Column
    private HttpMethod httpMethod;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name="key")
    @Column(name="value")
    @CollectionTable(name="value_attributes", joinColumns = @JoinColumn(name="key_id"))
    private Map<String, String> headerTemplateJson;

    @ElementCollection(fetch = FetchType.LAZY)
    @MapKeyColumn(name="key")
    @Column(name="value")
    @CollectionTable(name="value_attributes", joinColumns = @JoinColumn(name="key_id"))
    private Map<String, String> bodyTemplate;
}
