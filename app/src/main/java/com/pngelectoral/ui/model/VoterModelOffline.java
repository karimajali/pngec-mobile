package com.pngelectoral.ui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class VoterModelOffline {

    @JsonProperty("status")
    public boolean status;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public NewVoterDownloadModelOffline data;
}


