package daryl.ticketgrabber.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class MainDataControl {
    private static final String propsFile = "sampledata.properties";

    private static MainDataControl ourInstance;

    public static MainDataControl getInstance() {
        if(ourInstance == null) {
            ourInstance = new MainDataControl();
        }
        return ourInstance;
    }

    private LinkedList<EntryWithStatus> masterQueue;
    private final ReentrantLock lock = new ReentrantLock();
    private final static Properties props = new Properties();
    private boolean wasRun;

    private MainDataControl() {
        wasRun = false;
    }

    public void init(String propsFile) throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        if(wasRun) {
            throw new AlreadyInitializedException();
        }

        wasRun = true;
        List<EntryDataTVTickets> entries = new FormData(propsFile).init().getEntries();
        masterQueue = new LinkedList<>();
        for(EntryDataTVTickets entry: entries) {
            masterQueue.add(new EntryWithStatus(entry));
        }

        try {
            props.load(new FileInputStream(propsFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public EntryDataTVTickets getEntry() throws UninitializedInstanceException {
        if(!wasRun) {
            throw new UninitializedInstanceException();
        }
        EntryDataTVTickets entry = null;
        try {
            if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                try {
                    for (EntryWithStatus ews : masterQueue) {
                        if (!ews.inUseOrDone) {
                            entry = ews.entry;
                            ews.inUseOrDone = true;
                            break;
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return entry;
    }

    public void results(EntryDataTVTickets entry, boolean wasSuccessful) throws UninitializedInstanceException, IllegalStateException {
        if(!wasRun) {
            throw new UninitializedInstanceException();
        }
        lock.lock();

        for(EntryWithStatus ews: masterQueue) {
            if (ews.entry == entry) {
                if(ews.inUseOrDone) {
                    if(wasSuccessful) {
                        masterQueue.remove(ews);
                    } else {
                        ews.inUseOrDone = false;
                    }
                } else {
                    throw new IllegalStateException();
                }
                break;
            }
        }

        lock.unlock();
    }

    public boolean isEmpty() throws UninitializedInstanceException {
        if(!wasRun) {
            throw new UninitializedInstanceException();
        }
        return masterQueue.isEmpty();
    }

    public String getShow() throws UninitializedInstanceException {
        if(!wasRun) {
            throw new UninitializedInstanceException();
        }
        return props.getProperty("show");
    }

    public String getWebURL() throws UninitializedInstanceException {
        if(!wasRun) {
            throw new UninitializedInstanceException();
        }
        return props.getProperty("webURL");
    }

    private class EntryWithStatus {
        EntryDataTVTickets entry;
        boolean inUseOrDone = false;

        EntryWithStatus(EntryDataTVTickets entry) {
            this.entry = entry;
        }
    }

}
