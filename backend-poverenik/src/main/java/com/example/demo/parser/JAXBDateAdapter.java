package com.example.demo.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JAXBDateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String marshal(Date date) throws Exception {
        synchronized (this.dateFormat) {
            return this.dateFormat.format(date);
        }
    }

    @Override
    public Date unmarshal(String string) throws Exception {
        synchronized (this.dateFormat) {
            return this.dateFormat.parse(string);
        }
    }

}