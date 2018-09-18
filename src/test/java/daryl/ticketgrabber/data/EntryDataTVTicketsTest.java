package daryl.ticketgrabber.data;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntryDataTVTicketsTest {

    private EntryDataTVTickets.EntryDataTVTBuilder builder;

    @Before
    public void setUpEach() {
        builder = EntryDataTVTickets.getBuilder();
    }

    @After
    public void destroyEach() {
        builder = null;
    }

    @Test
    public void getBuilderTest() {
        assertNotNull(builder);
    }

    @Test
    public void setNumberOfTicketsBuilderTest() {
        builder.setNumberOfTickets("1");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setFirstName("bob");
        builder.setLastName("smith");
        builder.setPhoneNumber("3235551234");
        builder.setEmail("bob@smith.com");
        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setNumberOfTickets("1");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }

    @Test
    public void setFirstNameBuilderTest() {
        builder.setFirstName("bob");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setNumberOfTickets("1");
        builder.setLastName("smith");
        builder.setPhoneNumber("3235551234");
        builder.setEmail("bob@smith.com");
        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setFirstName("bob");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }

    @Test
    public void setLastNameBuilderTest() {
        builder.setLastName("smith");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setNumberOfTickets("1");
        builder.setFirstName("bob");
        builder.setPhoneNumber("3235551234");
        builder.setEmail("bob@smith.com");
        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setLastName("smith");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }

    @Test
    public void setPhoneNumberBuilderTest() {
        builder.setPhoneNumber("3235551234");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setNumberOfTickets("1");
        builder.setLastName("smith");
        builder.setFirstName("bob");
        builder.setEmail("bob@smith.com");
        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setPhoneNumber("3235551234");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }

    @Test
    public void setEmailBuilderTest() {
        builder.setEmail("bob@smith.com");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setNumberOfTickets("1");
        builder.setLastName("smith");
        builder.setPhoneNumber("3235551234");
        builder.setFirstName("bob");
        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setEmail("bob@smith.com");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }

    @Test
    public void setHowDidYouFindUsBuilderTest() {
        builder.setHowDidYouFindUs("something");
        EntryDataTVTickets edt = builder.build();
        assertNull("The builder constructed the object without all the mandatory parameters set.", edt);

        builder = EntryDataTVTickets.getBuilder();

        builder.setNumberOfTickets("1");
        builder.setLastName("smith");
        builder.setPhoneNumber("3235551234");
        builder.setEmail("bob@smith.com");
        builder.setFirstName("bob");
        edt = builder.build();
        assertNull("The builder constructed the object without the NumberOfTickets field set.", edt);

        builder.setHowDidYouFindUs("something");
        edt = builder.build();
        assertNotNull("The builder did not construct the object despite all the values being set.", edt);

    }


}