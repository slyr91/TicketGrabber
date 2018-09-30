/*
Arouchian, Daryl
9/26/2018
*/
package daryl.ticketgrabber.workers;

import daryl.ticketgrabber.data.EntryDataTVTickets;

public interface WebDriverService {

    SimpleWebResult navigateTo(String url);

    SimpleWebResult refreshPage();

    boolean lookForShow(String show);

    boolean enterInformationAndSubmit(EntryDataTVTickets entry);

    boolean terminateSession();

}
