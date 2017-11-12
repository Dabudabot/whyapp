package com.inno.dabudabot.whyapp.controller;

import com.inno.dabudabot.whyapp.listener.ReceiveContentView;
import com.inno.dabudabot.whyapp.wrappers.ReadingWrapper;

import java.util.ArrayList;
import java.util.List;

import Util.Settings;

/**
 * Created by dabudabot on 11.11.17.
 */

public class ReceiveContentController {

    private static List<ReceiveContentView> listeners;

    public static void subscribe(ReceiveContentView listener) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
        sendAll(listener);
    }

    public static void unsubscribe(ReceiveContentView listener) {
        listeners.remove(listener);
    }

    private static void sendAll(ReceiveContentView listener) {
        new ReadingWrapper().runReading(
                listener.getSender(),
                listener.getReceiver(),
                Settings.getInstance().getMachine());
        listener.onReceiveSuccess();
    }

    public static void sendNotify(Integer mine, Integer other) {
        if (listeners != null) {
            for (ReceiveContentView listener : listeners) {
                if (listener.getSender().equals(mine)
                        || listener.getReceiver().equals(mine)
                        || listener.getSender().equals(other)
                        || listener.getReceiver().equals(other)) {
                    new ReadingWrapper().runReading(
                            listener.getSender(),
                            listener.getReceiver(),
                            Settings.getInstance().getMachine());
                    listener.onReceiveSuccess();
                }
            }
        }
    }
}
