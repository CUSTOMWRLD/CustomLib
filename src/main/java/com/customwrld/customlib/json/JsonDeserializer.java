package com.customwrld.customlib.json;

public interface JsonDeserializer<T> {

    T deserialize(String string);

}
