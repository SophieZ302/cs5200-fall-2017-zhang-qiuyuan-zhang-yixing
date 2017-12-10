package edu.neu.cs5200.orm.jpa.jsonwebservice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonWebServiceClient {
	
	public static String urlApi = "http://www.omdbapi.com/?apikey=7599430e&t=";
	public static String urlApiByID = "http://www.omdbapi.com/?apikey=7599430e&i=";
	
	public ApiMovieData fetchDataById(String movieId) {
		ApiMovieData movie = new ApiMovieData();
		String urlString = urlApiByID + movieId;
		//System.out.println(urlString);
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream str = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(str);
			BufferedReader bf = new BufferedReader(reader);
		
			String line;
			String json = "";
			while((line = bf.readLine())!=null) {
				json +=line;
			}
			
			//System.out.println(json);
			
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(json);
			
			movie.setId((String)root.get("imdbID"));
			movie.setTitle((String)root.get("Title"));
			movie.setPlot((String)root.get("Plot"));
			movie.setActors((String)root.get("Actors"));
			movie.setDirectors((String)root.get("Director"));
			movie.setImageUrl((String)root.get("Poster"));
			String rating = (String) root.get("imdbRating");
			movie.setRating(Double.valueOf(rating));
			
			//System.out.println(movie.toString());
			

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return movie;
	}
	
	public ApiMovieData fetchData (String movieName) {
		ApiMovieData movie = new ApiMovieData();
		movieName = movieName.replaceAll(" ", "+");
		String urlString = urlApi + movieName;
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream str = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(str);
			BufferedReader bf = new BufferedReader(reader);
		
			String line;
			String json = "";
			while((line = bf.readLine())!=null) {
				json +=line;
			}
			
			//System.out.println(json);
			
			JSONParser parser = new JSONParser();
			JSONObject root = (JSONObject) parser.parse(json);
			
			movie.setId((String)root.get("imdbID"));
			movie.setTitle((String)root.get("Title"));
			movie.setPlot((String)root.get("Plot"));
			movie.setActors((String)root.get("Actors"));
			movie.setDirectors((String)root.get("Director"));
			movie.setImageUrl((String)root.get("Poster"));
			String rating = (String) root.get("imdbRating");
			movie.setRating(Double.valueOf(rating));
			
			//System.out.println(movie.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movie;
	}
	
	
	public static void main(String[] args) {
		JsonWebServiceClient cl = new JsonWebServiceClient();
		//ApiMovieData mv = cl.fetchData("Blade Runner");
		//System.out.println(mv);
		
		ApiMovieData mv2 = cl.fetchDataById("tt2380307");
		System.out.println(mv2);
	}

}
