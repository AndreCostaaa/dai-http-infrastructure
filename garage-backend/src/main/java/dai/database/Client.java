package dai.database;

public class Client extends Person {
    private final String email, street, country;
    private final int streetNo, npa;

    public Client(int id,
                  String firstName,
                  String lastName,
                  String phoneCode,
                  String phoneNo,
                  String email,
                  String street,
                  int streetNo,
                  int npa,
                  String country) {
        super(id, firstName, lastName, phoneCode, phoneNo);
        this.email = email;
        this.street = street;
        this.streetNo = streetNo;
        this.npa = npa;
        this.country = country;
    }

    public String getEmail(){
        return email;
    }
    public String getStreet(){
        return street;
    }
    public int getStreetNo(){
        return streetNo;
    }
    public int getNpa(){
        return npa;
    }
    public String country(){
        return country;
    }

    //SQL requests

    static public Client fetchOne(int clientId){
        return null;
    }

    static public Client[] fetchAll(){
        return null;
    }

    static public Client[] fetchByPhone(){
        return null;
    }
}
