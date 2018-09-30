/*
Arouchian, Daryl
9/11/2018
*/
package daryl.ticketgrabber.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class FormData {

    private Properties props;
    private String propsFile;
    private List<EntryDataTVTickets> entries;

    public FormData(String propsFile) {
        props = new Properties();
        this.propsFile = propsFile;
        entries = new ArrayList<>();
    }

    public FormData init() throws IOException, InvalidFileExtensionException {
        if(!propsFile.endsWith(".properties")) {
            throw new InvalidFileExtensionException();
        }

        FileInputStream in = new FileInputStream(propsFile);
        props.load(in);
        in.close();


        int numOfEntries = (props.size() - 3) / Integer.parseInt(props.getProperty("numFields"));

        for(int i = 0; i < numOfEntries; i++) {
            entries.add(EntryDataTVTickets.getBuilder().setNumberOfTickets(props.getProperty("numOfTickets" + i))
            .setFirstName(props.getProperty("firstName" + i)).setLastName(props.getProperty("lastName" + i))
                    .setPhoneNumber(props.getProperty("phoneNum" + i)).setEmail(props.getProperty("email" + i))
            .setHowDidYouFindUs(props.getProperty("howDidYouFindUs" + i)).build());
        }

        return this;
    }

    public List<EntryDataTVTickets> getEntries() {
        return entries;
    }

}
