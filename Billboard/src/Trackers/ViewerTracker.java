package Trackers;

import Handlers.ViewerHandler;

import java.util.*;

public class ViewerTracker {

    static Map<String, ViewerHandler> activeViewers = new TreeMap<String, ViewerHandler>();

    /**
     * Add a new client to the vector of client handlers and increment numConnected count
     * @param uuid
     * @param client
     */
    public void Add(String uuid, ViewerHandler client) throws ViewerTrackerException {
        if (activeViewers.containsKey(uuid)) {
            throw new ViewerTrackerException("UUID: " + uuid + " already exists");
        }
            else {
            activeViewers.put(uuid, client);
        }
    }

    /**
     * Remove a client from the vector of client handlers and decrement numConnected count
     * @param uuid
     */
    public void Remove(String uuid) {
        activeViewers.remove(uuid);
    }

    /**
     * Get the number of connected clients
     * @return the current number of connected clients
     */
    public int Number() {
        //return numConnected;
        return activeViewers.size();
    }
}
