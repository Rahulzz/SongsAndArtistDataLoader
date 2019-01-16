package com.ias.songs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class FileToDB {
	public static void main(String[] args) throws Exception {
		DatabaseConnection databaseConnection = new DatabaseConnection();
		File f = new File("songs.txt");
		if (f.exists()){
			InputStream is = new FileInputStream("songs.txt");
			String jsonTxt = IOUtils.toString(is, "UTF-8");
			System.out.println(jsonTxt);
			JSONObject json = new JSONObject(jsonTxt);

			JSONArray jsonArray = json.getJSONArray("items");
			Connection connection = databaseConnection.openConnection(); 

			try {
				for(int i=0; i<jsonArray.length(); i++) {
					JSONObject currentItem = jsonArray.getJSONObject(i);
					JSONObject artistJson = (JSONObject) currentItem.get("artist");

					databaseConnection.addArtist(connection, artistJson.getLong("id"), artistJson.getString("name"));
					databaseConnection.addSong(connection, currentItem.getLong("id"), currentItem.getString("title"), artistJson.getLong("id"), currentItem.getString("imagePath"), currentItem.getBoolean("available"), currentItem.getLong("songxCount"));
				}
				System.out.println("TRANSACTION BEING COMMITTED");
				connection.commit();
			}
			catch(Exception exception) {
				System.out.println(exception.getMessage());
				System.out.println("TRANSACTION BEING ROLLED BACK");
				connection.rollback();
			}

			databaseConnection.closeConnection(connection);
		}
	}
}