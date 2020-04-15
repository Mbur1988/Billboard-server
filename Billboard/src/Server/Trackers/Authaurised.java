package Server.Trackers;

import CustomExceptions.ActiveViewerException;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class Authaurised {

    private static Map<String, UUID> ActiveUsers = new TreeMap<String, UUID>();

    /**
     * Add a new user to the list of authorised users
     * @param username the username of the user
     * @param uuid the uuid of the user
     * @throws ActiveViewerException
     */
    public void Add(String username, UUID uuid) {
        if (ActiveUsers.containsKey(username)) {
            try {
                throw new ActiveViewerException("User: " + username + "already authorised");
            } catch (ActiveViewerException e) {
                e.printStackTrace();
            }
        }
        else {
            ActiveUsers.put(username, uuid);
        }
    }

    /**
     * Remove a user from the list of authorised users
     * @param username the username of the user
     */
    public void Remove(String username) {
        ActiveUsers.remove(username);
    }

    /**
     * check whether a particular user is authorised and comfirm the uuid
     * @param username the username of the user
     * @param uuid the uuid of the user
     * @return a boolean value
     */
    public boolean Check(String username, UUID uuid) {
        if (ActiveUsers.get(username) == uuid){
            return true;
        }
        else {
            return false;
        }
    }
}
