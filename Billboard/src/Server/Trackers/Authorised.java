package Server.Trackers;

import CustomExceptions.ActiveViewerException;
import Tools.Log;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Authorised {

    private static Map<String, UUID> ActiveUsers = new TreeMap<String, UUID>();

    /**
     * Add a new user to the list of authorised users
     * @param username the username of the user
     * @param uuid the uuid of the user
     * @throws ActiveViewerException
     */
    public static void Add(String username, UUID uuid) {
        if (!ActiveUsers.containsKey(username)) {
            ActiveUsers.put(username, uuid);
            Log.Message("User: " + username + " added to authorised user list");
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Remove(username);
                        }
                    },
                    86400000
            );
            Log.Message("Started timer task to remove authorised user " + username + " after 24h");
        }
        else {
            Log.Error("User: " + username + " already authorised");
        }
    }

    /**
     * Remove a user from the list of authorised users
     * @param username the username of the user
     */
    public static void Remove(String username) {
        if (ActiveUsers.containsKey(username)) {
            ActiveUsers.remove(username);
            Log.Message("User: " + username + " removed from authorised user list");
        }
        else {
            Log.Error("User: " + username + " not authorised");
        }
    }

    /**
     * check whether a particular user is authorised and comfirm the uuid
     * @param username the username of the user
     * @param uuid the uuid of the user
     * @return a boolean value
     */
    public static boolean Check(String username, UUID uuid) {
        try {
            if (ActiveUsers.get(username).equals(uuid)) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }
}
