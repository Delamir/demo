package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobContext {

    private String fromInclusive;
    private String toExclusive;
    private String page;
    private String state;
    private List<String> urlParams;
    private String feed;
    private FeedConfig config;

    //Lavet lige ud fra hukommelse. Men vi har jo allerede de vigtige oplysninger fra frameworket.
}
