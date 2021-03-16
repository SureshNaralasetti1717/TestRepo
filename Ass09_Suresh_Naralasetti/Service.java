package Assignment9;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Service {
	static List<Movie> populateMovies(File file)
	{
		List<Movie> list=new ArrayList<>();
		try {
			Scanner scan=new Scanner(file);
			while(scan.hasNext())
			{
				String[] s=scan.next().split(",");
				Movie m=new Movie();
				m.setMovieId(Integer.valueOf(s[0]));
				m.setMovieName(s[1]);
				m.setMovieType(s[2]);
				m.setMovieLang(s[3]);
				m.setReleaseDate(s[4]);
				m.setCasting(new ArrayList<>(Arrays.asList(s[5].split("/"))));
				m.setRating(Double.valueOf(s[6]));
				m.setTotalBusinessDone(Double.valueOf(s[7]));
				list.add(m);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
		
	}
	 static void allAllMoviesInDb(List<Movie> movies)
	{
		Connection cn=DBConnection.getConnection();
		try {
			Statement stmt=cn.createStatement();
			//stmt.executeUpdate("create table Movie(movieId int, movieName varchar(50), movieType varchar(30),movieLang varchar(10), releaseDate date,rating number(2,2),totalBusinessDone number(8,2))");			

            for(Movie m:movies)
            {
            	String sql="insert into Movie values(?,?,?,?,?,?,?)";
             PreparedStatement pstmt=cn.prepareStatement(sql);  
             pstmt.setInt(1,m.getMovieId());
             pstmt.setString(2, m.getMovieName());
             pstmt.setString(3, m.getMovieType());
             pstmt.setString(4, m.getMovieLang());
             
     		 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
             //pstmt.setTimestamp(1,Timestamp.valueOf(df.format(m.getReleaseDate())));
             pstmt.setDate(5, (java.sql.Date) df.parse(m.getReleaseDate()));
             pstmt.setDouble(6, m.getRating());
             pstmt.setDouble(7, m.getTotalBusinessDone());
             
             pstmt.executeUpdate();
            }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	static List<Movie> addMovie(Movie m,List<Movie> movie)
	{
		movie.add(m);
		return movie;
	}
	static void serializeMovies(List<Movie> movie, String fileName)
	{

		File f=new File(fileName);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(f);

			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(movie);
			System.out.println("Object Inserted Successfully");
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static void deserializeMovie(String filename)
	{

		FileInputStream fis=null;
		ObjectInputStream ois=null;
			try
			{
				File f=new File(filename);
				
				fis=new FileInputStream(f);
				ois=new ObjectInputStream(fis);
		
				Object object=ois.readObject();
			    ArrayList<Object> objects3 = (ArrayList<Object>) Collections.singletonList(object);
			    
			    ArrayList<Movie> al=new ArrayList<Movie>();

				for(int i=0;i<objects3.size();i++)
				{
					Movie m=(Movie)objects3.get(i);
					al.add(m);
				}
				
				ois.close();
				fis.close();
				
			}catch(Exception e){e.printStackTrace();}
		
	}
	static void getMoviesRealeasedInYear(List<Movie> movie,int year)
	{
		for(Movie temp: movie)
		{
			if(temp.getReleaseDate().equals(year))
				System.out.println(temp.toString());
		}
	}
	static void getMoviesByActor(List<Movie> movie, List<String> actors)
	{
		for(Movie temp: movie)
		{
			if(temp.getCasting().equals(actors))
				System.out.println(temp.toString());
		}
	}
	static void updateRatings(String moviename, double rating ,List<Movie> movie)
	{   int i=-1;
		for(Movie temp:movie)
		{   
			i++;
			if(temp.getMovieName().equals(moviename))
			{
				movie.get(i).setRating(rating);
			}
		}
	}
	static void updateBusiness(String moviename, double amount,List<Movie> movie)
	{
		int i=-1;
		for(Movie temp:movie)
		{   
			i++;
			if(temp.getMovieName().equals(moviename))
			{
				movie.get(i).setTotalBusinessDone(amount);
			}
		}
	}
      static void businessDone(List<Movie> movie,double amount)
	{
		Comparator<Movie>  cm=new Comparator<Movie>() {

			@Override
			public int compare(Movie a1, Movie a2) {
				double i=a1.getTotalBusinessDone();
				double j=a2.getTotalBusinessDone();
				if(i<j)
					return 1;
				else if(i==j)
				     return 0;
				else
					return -1;
			  }
		    };
		movie.sort(cm);
		
		for(Movie temp:movie)
		{
			if(temp.getTotalBusinessDone()>amount)
				System.out.println((temp.toString()));
		}
		
            
	}

}
