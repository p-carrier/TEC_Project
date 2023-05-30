package com.tec.dto;

import com.tec.dao.Facility;
import com.tec.dao.Zone;

import java.util.HashMap;

public class Location {
    final private int loc;
    final private String locZn;
    final private String locName;
    final private String locPurpDesc;
    final private String locQti;
    final private String flowInd;
    final private int dc;
    final private int opc;
    final private int tsq;
    final private int oac;
    final private boolean it;
    final private boolean authOverrunInd;
    final private boolean nomCapExceedInd;
    final private boolean allQtyAvail;
    final private String qtyReason;

    public Location(String[] info) {
        this.loc = formatNumber(info[0]);
        this.locZn = formatString(info[1]);
        this.locName = formatString(info[2]);
        this.locPurpDesc = formatString(info[3]);
        this.locQti = formatString(info[4]);
        this.flowInd = formatString(info[5]);
        this.dc = formatNumber(info[6]);
        this.opc = formatNumber(info[7]);
        this.tsq = formatNumber(info[8]);
        this.oac = formatNumber(info[9]);
        this.it = formatBoolean(info[10]);
        this.authOverrunInd = formatBoolean(info[11]);
        this.nomCapExceedInd = formatBoolean(info[12]);
        this.allQtyAvail = formatBoolean(info[13]);
        this.qtyReason = formatString(info[14]);
    }

    public Location(int loc, String locZn, String locName, String locPurpDesc, String locQti, String flowInd, int dc, int opc, int tsq, int oac, boolean it, boolean authOverrunInd, boolean nomCapExceedInd, boolean allQtyAvail, String qtyReason) {
        this.loc = loc;
        this.locZn = locZn;
        this.locName = locName;
        this.locPurpDesc = locPurpDesc;
        this.locQti = locQti;
        this.flowInd = flowInd;
        this.dc = dc;
        this.opc = opc;
        this.tsq = tsq;
        this.oac = oac;
        this.it = it;
        this.authOverrunInd = authOverrunInd;
        this.nomCapExceedInd = nomCapExceedInd;
        this.allQtyAvail = allQtyAvail;
        this.qtyReason = qtyReason;
    }

    static String removeQuotes(String value) {
        return value.replaceAll("\"", "");
    }

    private int formatNumber(String value) {
        if (value.equals("")) {
            return -1;
        }
        return Integer.parseInt(removeQuotes(value));
    }

    private String formatString(String value) {
        return removeQuotes(value).replaceAll("\r", "");
    }

    private final HashMap<String, Boolean> boolValues = new HashMap<>(){{
        put("\"Y\"", Boolean.TRUE);
        put("\"N\"", Boolean.FALSE);
    }};

    private boolean formatBoolean(String value) {
        return boolValues.get(value);
    }

    public Zone extractZone() {
        return new Zone(this.locZn);
    }

    public com.tec.dao.Location extractLocation() {
        return new com.tec.dao.Location(this.loc, this.locName, this.locZn);
    }

    public Facility extractFacility() {
        return new Facility(
                locName, locPurpDesc, locQti, flowInd, dc, opc, tsq,
                oac, it, authOverrunInd, nomCapExceedInd,
                allQtyAvail, qtyReason
        );
    }

    public int getLoc() {
        return loc;
    }

    public String getLocZn() {
        return locZn;
    }

    public boolean isIt() {
        return it;
    }

    public String getLocName() {
        return locName;
    }

    public String getLocPurpDesc() {
        return locPurpDesc;
    }

    public String getLocQti() {
        return locQti;
    }

    public String getFlowInd() {
        return flowInd;
    }

    public int getDc() {
        return dc;
    }

    public int getOpc() {
        return opc;
    }

    public int getTsq() {
        return tsq;
    }

    public int getOac() {
        return oac;
    }

    public boolean isAuthOverrunInd() {
        return authOverrunInd;
    }

    public boolean isNomCapExceedInd() {
        return nomCapExceedInd;
    }

    public boolean isAllQtyAvail() {
        return allQtyAvail;
    }

    public String getQtyReason() {
        return qtyReason;
    }

    @Override
    public String toString() {
        return loc +
                "," + locZn +
                "," + locName +
                "," + locPurpDesc +
                "," + locQti +
                "," + flowInd +
                "," + dc +
                "," + opc +
                "," + tsq +
                "," + oac +
                "," + it +
                "," + authOverrunInd +
                "," + nomCapExceedInd +
                "," + allQtyAvail +
                "," + qtyReason;
    }
}
