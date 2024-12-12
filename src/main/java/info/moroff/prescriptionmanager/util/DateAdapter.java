package info.moroff.prescriptionmanager.util;

import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public Date unmarshal(String dateString) throws Exception {
        return new Date(Long.parseLong(dateString));
    }

    @Override
    public String marshal(Date localDate) throws Exception {
        return Long.toString(localDate.getTime());
    }
}