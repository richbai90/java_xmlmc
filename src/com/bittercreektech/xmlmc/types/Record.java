package com.bittercreektech.xmlmc.types;

import com.bittercreektech.xmlmc.ComplexParam;

import javax.xml.parsers.ParserConfigurationException;

/*****************************************
 * Author : rich
 * Date : 9/2/16
 * Assignment: Record
 ******************************************/
public class Record implements SwType {
    private final ComplexParam param;
    private final String tableName;


    public Record(String tableName) throws ParserConfigurationException {
        param = new ComplexParam("data");
        this.tableName = tableName;
    }

    public void addField(String field, String value) {
        param.addParameter(field, value);
    }

    public String getTableName() {
        return tableName;
    }


    @Override
    public ComplexParam buildXml() {
        return param;
    }
}
