package camelride.transform;

/**
 * Author: sazal
 * Date: 5/8/17.
 */
public class PersonToCsvBean {

    public String map(String customPerson) {
        String[] personDetails = customPerson.split(":");
        String name = personDetails[0];
        String age = personDetails[1];
        String email = personDetails[2];
        StringBuilder personCsv = new StringBuilder();
        personCsv.append(name);
        personCsv.append(",").append(age);
        personCsv.append(",").append(email);
        return personCsv.toString();
    }

}
