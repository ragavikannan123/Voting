package Management;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface Management {
	
	public JSONObject viewProfile(int id) throws SQLException, JSONException;
	public JSONArray getElections(int orgId) throws SQLException, JSONException;
	public JSONArray getCandidates(int elecId) throws SQLException, JSONException ;
	
}
