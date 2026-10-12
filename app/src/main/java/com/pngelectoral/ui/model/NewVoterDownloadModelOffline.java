package com.pngelectoral.ui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class NewVoterDownloadModelOffline {

    @JsonProperty("result")
    public ArrayList<NewVotersList> downloadedVotersList;

    @JsonProperty("total")
    public int total = 0;
}



