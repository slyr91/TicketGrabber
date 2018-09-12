/*
Arouchian, Daryl
9/11/2018
*/
package daryl.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FormData {

    private Properties props;
    private String propsFile;
    private List<EntryDataTVTickets> entries;

    public FormData(String propsFile) {
        props = new Properties();
        this.propsFile = propsFile;
        entries = new ArrayList<>();
    }

    public FormData init() {
        try (FileInputStream in = new FileInputStream(propsFile)) {
          props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int numOfEntries = props.size() - 3 / Integer.parseInt(props.getProperty("numFields"));

        for(int i = 0; i < numOfEntries; i++) {
            entries.add(EntryDataTVTickets.getBuilder().setNumberOfTickets(props.getProperty("numOfTickets" + i))
            .setFirstName("firstName" + i).setLastName("lastName" + i).setPhoneNumber("phoneNum" + i).setEmail("email" + i)
            .setHowDidYouFindUs("howDidYouFindUs" + i).build());
        }

        return this;
    }

    public List<EntryDataTVTickets> getEntries() {
        return entries;
    }

}
