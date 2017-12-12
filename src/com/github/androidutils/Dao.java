package com.github.androidutils;

import java.util.List;

public interface Dao {

    public List<Object> getCountryList();

    public List<Object> getStateList(String countryNameEn,
            String countryCode);

    public List<Object> getCityList(String countryNameEn,
            String stateNameEn);

    public Object getCache();

    public void saveOrUpdateCache(Object object);
    
    public void resetRecentCache(Object[] object);
}
