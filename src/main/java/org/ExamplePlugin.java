package org.example;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class ExamplePlugin extends Plugin {

    @Override
    public void onLoad() {
        this.getLogger().info("SignReader loaded!");
        RusherHackAPI.getModuleManager().registerFeature(new SignReaderModule());
    }

    @Override
    public void onUnload() {
        this.getLogger().info("SignReader unloaded!");
    }

}