package com.utils;

import com.caen.RFIDLibrary.*;

import java.util.Arrays;
import java.util.HashSet;

public class RFID {
    public RFID() {
    }

    public HashSet<String> read() {
        HashSet<String> result = new HashSet<>();
        CAENRFIDReader reader = new CAENRFIDReader();
        try {
            reader.Connect(CAENRFIDPort.CAENRFID_TCP, "192.168.1.2");
            CAENRFIDLogicalSource MySource = reader.GetSource("Source_0");
            System.out.println("Starting...");

            CAENRFIDReaderInfo Info = reader.GetReaderInfo();
            String Model = Info.GetModel();
            String SerialNumber = Info.GetSerialNumber();
            String FWRelease = reader.GetFirmwareRelease();
            int power = reader.GetPower();

            System.out.println("Model: " + Model);
            System.out.println("SerialNumber: " + SerialNumber);
            System.out.println("FWRelease: " + FWRelease);
            System.out.println("power: " + power);

            MySource.SetSession_EPC_C1G2(CAENRFIDLogicalSourceConstants.EPC_C1G2_SESSION_S1);

            CAENRFIDTag[] MyTags = MySource.InventoryTag();
            if (MyTags != null)
                if (MyTags.length > 0) {
                    for (CAENRFIDTag myTag : MyTags) {
                        System.out.println("EPC: " + hex(myTag.GetId()) + " | Antenna : " + myTag.GetAntenna() + " | TID:" + (Arrays.toString(myTag.GetTID())) + " | RSSI : " + (int) myTag.GetRSSI());
                        String id = hex(myTag.GetId());
                        result.add(id);
                    }
                }
            reader.Disconnect();
        } catch (CAENRFIDException e) {
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : bytes)
            result.append(String.format("%02x", aByte));
        return result.toString().toUpperCase();
    }

}
