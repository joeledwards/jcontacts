package contact;

public class Name
    implements ContactData
{
    private String firstName = null;
    private String lastName = null;
    private String middleName = null;
    private String nickName = null;
    private String prefix = null;
    private String suffix = null;

    public Name() {;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String toString()
    {
        ArrayList<String> parts = new ArrayList<String>();
        if (prefix      != null) parts.add(prefix);
        if (firstName   != null) parts.add(firstName);
        if (middleName  != null) parts.add(middleName);
        if (nickName    != null) parts.add("\"" +nickName+ "\"");
        if (lastName    != null) parts.add(lastName);
        if (suffix      != null) parts.add(suffix);
        return 
    }
}
