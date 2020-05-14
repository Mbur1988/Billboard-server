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
        }
        else {
            Log.Error("User: " + username + "already authorised");
        }
    }

    /**
     * Remove a user from the list of authorised users
     * @param username the username of the user
     */
    public static void Remove(String username) {
        if (ActiveUsers.containsKey(username)) {
            ActiveUsers.remove(username);
        }
        else {
            Log.Error("User: " + username + " already authorised");
        }
    }

    /**
     * check whether a particular user is authorised and comfirm the uuid
     * @param username the username of the user
     * @param uuid the uuid of the user
     * @return a boolean value
     */
    public static boolean Check(String username, UUID uuid) {
        if (ActiveUsers.get(username).equals(uuid)) {
            return true;
        }
        else {
            return false;
        }
    }
}
