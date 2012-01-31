
public class ContactID
    implements ContactData
{
    private Long id = null;

    public ContactID(long id)
    {
        this.id = new Long(id);
    }

    public void fromString(String data)
    {
        id = new Long(Long.parseLong(data));
    }

    public String toString()
    {
        return id.toString();
    }

    public long toLong()
    {
        return id;
    }
}
