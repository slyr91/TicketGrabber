/*
Arouchian, Daryl
9/11/2018
*/
package daryl.data;

import java.util.ArrayList;
import java.util.List;

public class EntryDataTVTickets {

    private String numberOfTickets;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String howDidYouFindUs;

    private EntryDataTVTickets() {}

    public String getNumberOfTickets() {
        return numberOfTickets;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getHowDidYouFindUs() {
        return howDidYouFindUs;
    }

    public static EntryDataTVTBuilder getBuilder() {
        return new EntryDataTVTBuilder();
    }

    public static class EntryDataTVTBuilder {

        private EntryDataTVTickets data = new EntryDataTVTickets();
        private List<Boolean> allSet = new ArrayList<>();

        EntryDataTVTBuilder() {
            for(int i = 0; i < 6; i++) {
                allSet.add(false);
            }
        }

        public EntryDataTVTBuilder setNumberOfTickets(String numberOfTickets) {
            data.numberOfTickets = numberOfTickets;
            allSet.set(0, true);
            return this;
        }

        public EntryDataTVTBuilder setFirstName(String firstName) {
            data.firstName = firstName;
            allSet.set(1, true);
            return this;
        }

        public EntryDataTVTBuilder setLastName(String lastName) {
            data.lastName = lastName;
            allSet.set(2, true);
            return this;
        }

        public EntryDataTVTBuilder setPhoneNumber(String phoneNumber) {
            data.phoneNumber = phoneNumber;
            allSet.set(3, true);
            return this;
        }

        public EntryDataTVTBuilder setEmail(String email) {
            data.email = email;
            allSet.set(4, true);
            return this;
        }

        public EntryDataTVTBuilder setHowDidYouFindUs(String howDidYouFindUs) {
            data.howDidYouFindUs = howDidYouFindUs;
            allSet.set(5, true);
            return this;
        }

        public EntryDataTVTickets build() {

            if(allSet.contains(false)) {
                System.out.println("All fields are required.");
                return null;
            } else {
                return data;
            }
        }
    }
}
