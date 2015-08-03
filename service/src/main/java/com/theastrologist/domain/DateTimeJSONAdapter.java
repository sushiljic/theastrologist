package com.theastrologist.domain;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;
import java.util.Date;

/**
 * Created by SAM on 03/08/2015.
 */
public class DateTimeJSONAdapter extends TypeAdapter<DateTime>{

    @Override
    public void write(JsonWriter out, DateTime dateTime) throws IOException {
        // implement write: combine firstName and lastName into name
        out.value(dateTime.toString(ISODateTimeFormat.dateTimeNoMillis()));
        // implement the write method
    }

    @Override
    public DateTime read(JsonReader in) throws IOException {
        // May be it came in formatted as a java.util.Date, so try that
        DateTime dateTime = DateTime.parse(in.nextString(), ISODateTimeFormat.dateTimeNoMillis());
        return dateTime;
    }
}
