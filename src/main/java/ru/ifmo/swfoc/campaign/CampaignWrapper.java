package ru.ifmo.swfoc.campaign;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CampaignWrapper {
    private ArrayList<Campaign> campaigns;
    private String campaignFile;
}
