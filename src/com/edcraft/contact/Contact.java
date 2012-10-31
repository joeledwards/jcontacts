package com.edcraft.contact;

import java.util.Hashtable;

public class Contact
{
    private long id = -1;
    private Hashtable<String, String> table = null;

    public Contact(long id)
    {
        this.id = id;
        this.table = new Hashtable<String, String>();
    }

    public Contact(long id, Hashtable<String, String> table)
    {
        this.table = table;
    }

    public long getId()
    {
        return id;
    }

    public void set(String key, String value)
    {
        table.put(key, value);
    }

    public String get(String key)
    {
        return table.get(key);
    }
}

