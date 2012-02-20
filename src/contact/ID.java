package contact;

public class ID
    implements ContactData
{
    private Long id = null;

    public ID(long id)
    {
        this.id = new Long(id);
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
