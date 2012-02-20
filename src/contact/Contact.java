package contact;

import java.util.Hashtable;

public class Contact
{
    private Hashtable<String, ContactData> table = null;

    public Contact(long id)
    {
        table = new Hashtable<String, ContactData>();
        table.put("id", new ContactID(id));
    }

    public Contact(Hashtable<String, ContactData> table)
    {
        this.table = table;
    }

    public Contact(long id, Hashtable<String, ContactData> table)
    {
        this.table = table;
        table.put("id", new ContactID(id));
    }

    public long getId()
    {
        return ((ContactID)(table.get("id"))).toLong();
    }

    public void set(String key, ContactData data)
    {
        table.put(key, data);
    }

    public ContactData get(String key)
    {
        return table.get(key);
    }
}

