package de.tpsw.service;

import de.tpsw.adapter.BankDataAdapter;

import java.util.Arrays;
import java.util.List;

public class BankDataEvaluationService {

    public int calculateIndexChange(){
        BankDataAdapter bankDataAdapter = new BankDataAdapter();
        bankDataAdapter.requestBankData();
       List<String> blacklist =  Arrays.asList("esso","liefer","amazon","taxi","steak","lufthansa");
       List<String> whitelist =  Arrays.asList("deutsche bank","rossmann");

        return 0;
    }
}
