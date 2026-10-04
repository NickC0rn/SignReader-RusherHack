
# SignReader - RusherHack Plugin

Prints the contents of any sign to chat when it enters render distance. Useful for spotting stash signs and base coords on 2b2t.

## Features
- Prints sign text to chat in bright blue
- Toggle on/off in the RusherHack module list
- Optional coords display (off by default, useful for streamers)
- Ignores blank signs
- Won't spam — each sign only prints once per session

## Install
1. Drop the .jar into `.minecraft/rusherhack/plugins/`
2. Add `-Drusherhack.enablePlugins=true` to your JVM arguments
3. Launch and find SignReader in the RusherHack module list

## Requirements
- RusherHack
- Minecraft 1.21.4