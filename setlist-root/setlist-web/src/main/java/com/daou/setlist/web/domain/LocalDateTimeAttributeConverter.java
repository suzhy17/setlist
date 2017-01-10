package com.daou.setlist.web.domain;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
    	if (localDateTime == null) {
    		return null;
    	}
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date date) {
    	if (date == null) {
    		return null;
    	}
    	return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
