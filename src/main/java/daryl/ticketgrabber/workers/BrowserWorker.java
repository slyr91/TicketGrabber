/*
Arouchian, Daryl
9/26/2018
*/
package daryl.ticketgrabber.workers;

import daryl.ticketgrabber.data.MainDataControl;

public class BrowserWorker implements Runnable {

    private MainDataControl mainDataControl;
    private String url;
    private String show;

    public BrowserWorker(MainDataControl mainDataControl) {
        this.mainDataControl = mainDataControl;
        url = mainDataControl.getWebURL();
        show = mainDataControl.getShow();
    }

    @Override
    public void run() {

    }
}
