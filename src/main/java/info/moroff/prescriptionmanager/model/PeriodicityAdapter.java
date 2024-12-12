package info.moroff.prescriptionmanager.model;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class PeriodicityAdapter extends XmlAdapter<String, Periodicity> {

    @Override
    public Periodicity unmarshal(String stringValue) throws Exception {
        return Periodicity.valueOf(stringValue);
    }

    @Override
    public String marshal(Periodicity periodicity) throws Exception {
        return periodicity.name();
    }
}