package com.pngelectoral.ui.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;


public class OfflineTVLM {

    @JsonProperty("message")
    String message;

    @JsonProperty("status")
    boolean status;

    @JsonProperty("data")
    public OffLineTransferInVotersData offLineTransferInVotersData;


    public static class OffLineTransferInVotersData {

        @JsonProperty("result")
        public ArrayList<DownloadedVotersList> offLineTransferInVotersList;

        @JsonProperty("total")
        public int total = 0;

    }

}