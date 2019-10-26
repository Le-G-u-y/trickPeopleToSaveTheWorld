package de.tpsw.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tpsw.adapter.BankDataAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BankDataEvaluationService {

    public int calculateIndexChange(){
        BankDataAdapter bankDataAdapter = new BankDataAdapter();
       String json =  bankDataAdapter.requestBankData();
       List<String> blacklist =  Arrays.asList("esso","liefer","amazon","taxi","steak","lufthansa");
       List<String> whitelist =  Arrays.asList("deutsche bank","rossmann");

        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

//convert json file to map
        Map<?, ?> map = null;
        try {
            map = objectMapper.readValue(new FileInputStream(json), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

//iterate over map entries and print to console
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }

        return 0;
    }
}
