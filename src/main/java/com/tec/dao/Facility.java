package com.tec.dao;

import java.util.HashMap;

public class Facility {
    final private String locName;
    final private LocPurpDescTypes locPurpDesc;
    final private LocQtyTypes locQty;
    final private FlowIndTypes flowInd;
    final private int dc;
    final private int opc;
    final private int tsq;
    final private int oac;
    final private boolean it;
    final private boolean authOverrunInd;
    final private boolean nomCapExceedInd;
    final private boolean allQtyAvail;
    final private String qtyReason;

    enum LocPurpDescTypes {
        M2,
        MQ
    }

    enum LocQtyTypes {
        RPQ,
        DPQ
    }

    enum FlowIndTypes {
        R,
        D
    }

    public Facility(String locName, String locPurpDesc, String locQti, String flowInd, int dc, int opc, int tsq, int oac, boolean it, boolean authOverrunInd, boolean nomCapExceedInd, boolean allQtyAvail, String qtyReason) {
        this.locName = locName;
        this.locPurpDesc = purpDescTypes.get(locPurpDesc);
        this.locQty = qtyTypes.get(locQti);
        this.flowInd = flowTypes.get(flowInd);
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

    private HashMap<String, LocPurpDescTypes> purpDescTypes = new HashMap<>(){{
        put("MQ", LocPurpDescTypes.MQ);
        put("M2", LocPurpDescTypes.M2);
    }};

    private HashMap<String, LocQtyTypes> qtyTypes = new HashMap<>(){{
        put("RPQ", LocQtyTypes.RPQ);
        put("DPQ", LocQtyTypes.DPQ);
    }};

    private HashMap<String, FlowIndTypes> flowTypes = new HashMap<>(){{
        put("R", FlowIndTypes.R);
        put("D", FlowIndTypes.D);
    }};

    public String getLocName() {
        return locName;
    }

    public LocPurpDescTypes getLocPurpDesc() {
        return locPurpDesc;
    }

    public LocQtyTypes getLocQty() {
        return locQty;
    }

    public FlowIndTypes getFlowInd() {
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

    public boolean isIt() {
        return it;
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
        return "Facility{" +
                "locName='" + locName + '\'' +
                ", locPurpDesc=" + locPurpDesc +
                ", locQty=" + locQty +
                ", flowInd=" + flowInd +
                ", dc=" + dc +
                ", opc=" + opc +
                ", tsq=" + tsq +
                ", oac=" + oac +
                ", it=" + it +
                ", authOverrunInd=" + authOverrunInd +
                ", nomCapExceedInd=" + nomCapExceedInd +
                ", allQtyAvail=" + allQtyAvail +
                ", qtyReason='" + qtyReason + '\'' +
                '}';
    }
}
