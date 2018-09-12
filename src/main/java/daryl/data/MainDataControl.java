package daryl.data;

import java.util.List;

public class MainDataControl {
    private static MainDataControl ourInstance = new MainDataControl();

    public static MainDataControl getInstance() {
        return ourInstance;
    }

    private List<EntryDataTVTickets> entries;

    private MainDataControl() {
        entries = new FormData("sampledata.properties").init().getEntries();
    }

    //TODO add methods to control access to entries and ensure only one submission is permitted.
    //TODO Also add volatile field (I'm thinking arraylist) to control form submission
}
