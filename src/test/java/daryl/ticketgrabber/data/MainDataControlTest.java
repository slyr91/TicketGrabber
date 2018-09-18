package daryl.ticketgrabber.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MainDataControlTest {
    private String propFile;
    private List<EntryDataTVTickets> entries;

    private MainDataControl mainDataControl;

    @Before
    public void setUp() throws Exception {
        mainDataControl = MainDataControl.getInstance();
        propFile = ClassLoader.getSystemResources("sampledata.properties").nextElement().getFile();
    }

    @After
    public void tearDown() throws Exception {
        Field instance = MainDataControl.class.getDeclaredField("ourInstance");
        instance.setAccessible(true);
        instance.set(null, null);

        entries = null;
    }

    @Test
    public void getInstanceTest() {
        assertEquals(mainDataControl, MainDataControl.getInstance());
    }

    @Test(expected = IOException.class)
    public void initWithWrongFile() throws IOException {
        try {
            mainDataControl.init("WrongFile.properties");
        } catch (AlreadyInitializedException e) {
            fail("The instance has yet to be initialized.");
        } catch (InvalidFileExtensionException e) {
            fail("The file extension is correct.");
        }
    }

    @Test(expected = InvalidFileExtensionException.class)
    public void initWithInvalidFileExtension() throws InvalidFileExtensionException {
        try {
            mainDataControl.init("wrongextension.txt");
        } catch (AlreadyInitializedException e) {
            fail("The instance has yet to be initialized.");
        } catch (IOException e) {
            fail("It should not have gotten pass the Invalid File Extension check.");
        }

    }

    @Test(expected = AlreadyInitializedException.class)
    public void initTwice() throws AlreadyInitializedException {
        try {
            mainDataControl.init(propFile);
        } catch (AlreadyInitializedException e) {
            fail("This is the first initialization so this exception should not be thrown.");
        } catch (IOException e) {
            fail("The file does exist.");
        } catch (InvalidFileExtensionException e) {
            fail("The file extension is correct.");
        }

        //This will throw the expected exception.
        try {
            mainDataControl.init(propFile);
        } catch (IOException e) {
            fail("The file does exist.");
        } catch (InvalidFileExtensionException e) {
            fail("The file extension is correct.");
        }
    }

    @Test(expected = UninitializedInstanceException.class)
    public void getEntryPriorToInitialization() throws UninitializedInstanceException {
        mainDataControl.getEntry();
    }

    @Test
    public void getEntryAfterInitialization() {
        try {
            mainDataControl.init(propFile);
        } catch (AlreadyInitializedException e) {
            fail("Only initialized once.");
        } catch (IOException e) {
            fail("The file does exist.");
        } catch (InvalidFileExtensionException e) {
            fail("The file extension is correct.");
        }

        try {
            assertEquals("Sample data for this field is always 1",
                    "1", mainDataControl.getEntry().getNumberOfTickets());
        } catch (UninitializedInstanceException e) {
            fail("The instance has been fully initialized.");
        }
    }

    @Test
    public void getEntryNotConcurrentTest() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);
        EntryDataTVTickets entry1 = mainDataControl.getEntry();
        EntryDataTVTickets entry2 = mainDataControl.getEntry();

        assertNotEquals(entry1, entry2);
    }

    @Test
    public void getEntryConcurrentTest() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);
        entries = Collections.synchronizedList(new ArrayList<>());
        int maxTimeoutSeconds = 3;

        final int numThreads = 2;
        final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());
        final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
        try {
            final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
            final CountDownLatch afterInitBlocker = new CountDownLatch(1);
            final CountDownLatch allDone = new CountDownLatch(numThreads);
            for (int i = 0; i < numThreads; i++) {
                threadPool.submit(new Runnable() {
                    public void run() {
                        allExecutorThreadsReady.countDown();
                        try {
                            afterInitBlocker.await();
                            entries.add(mainDataControl.getEntry());
                        } catch (final Throwable e) {
                            exceptions.add(e);
                        } finally {
                            allDone.countDown();
                        }
                    }
                });
            }
            // wait until all threads are ready
            assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent", allExecutorThreadsReady.await(numThreads * 10, TimeUnit.MILLISECONDS));
            // start all test runners
            afterInitBlocker.countDown();
            assertTrue("Reached nax timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            fail("The CountDownLatch for awaiting for all executor threads to be ready was interrupted.");
        } finally {
            threadPool.shutdownNow();
        }
        assertTrue("Test failed with exception(s)" + exceptions, exceptions.isEmpty());

        assertNotEquals("Both entries are the same.", entries.get(0), entries.get(1));
    }

    @Test
    public void results() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);
        EntryDataTVTickets entry = mainDataControl.getEntry();
        mainDataControl.results(entry, false);

        assertEquals("Entry was returned to the pool and should be immediately available for use again.",entry, mainDataControl.getEntry());

        mainDataControl.results(entry, true);

        assertNotEquals("The first entry was marked as successful and removed from the queue.",entry, mainDataControl.getEntry());

    }

    @Test
    public void isEmpty() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);

        mainDataControl.results(mainDataControl.getEntry(), true);
        mainDataControl.results(mainDataControl.getEntry(), true);

        assertTrue("The queue, at this point, should be empty.", mainDataControl.isEmpty());
    }

    @Test
    public void getShow() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);

        assertEquals("The show field does not match the sample data.",
                "America's Funniest Videos", mainDataControl.getShow());
    }

    @Test
    public void getWebURL() throws AlreadyInitializedException, IOException, InvalidFileExtensionException {
        mainDataControl.init(propFile);

        assertEquals("The show field does not match the sample data.",
                "https://www.tvtickets.com/fmi/tickets/addrecord.php", mainDataControl.getWebURL());
    }
}