package net.weg.taskmanager.observability.enums;

public enum MDCKeys {

    LOG_LEVEL( "logLevel", "X-LOG-LEVEL");
//    USECASE("usecase", "X-LOG-USECASE");

    private String mdcKey;
    private String headerName;
    public String mdcKey(){return mdcKey;}
    public String headerName(){return headerName;}

    MDCKeys(String mdcKey, String headerName){
        this.mdcKey = mdcKey;
        this.headerName = headerName;
    }

}
