package Server;

import java.util.*;

class ClientTracker {

    static Map<String, ClientHandler> activeClients = new TreeMap<String, ClientHandler>();

    /**
     * Add a new client to the vector of client handlers and increment numConnected count
     * @param client
     */
    public void Add(String uuid, ClientHandler client) throws ClientTrackerException {
        if (activeClients.containsKey(uuid)) {
            throw new ClientTrackerException("UUID: " + uuid + " already exists");
        }
            else {
            activeClients.put(uuid, client);
        }
    }

    /**
     * Remove a client from the vector of client handlers and decrement numConnected count
     * @param uuid
     */
    public void Remove(String uuid) {
        activeClients.remove(uuid);
    }

    /**
     * Get the number of connected clients
     * @return the current number of connected clients
     */
    public int Number() {
        //return numConnected;
        return activeClients.size();
    }
}
