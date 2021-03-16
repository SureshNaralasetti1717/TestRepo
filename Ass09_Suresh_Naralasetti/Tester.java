package Assignment9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tester {

	public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        List<Movie> movie=new ArrayList<>();
        File file=new File("D:\\Core Java\\mov.txt");
        boolean bool=true;
        while(bool)
        {
        	System.out.println("Enter your choice");
        	System.out.println("1.Read data from mov file\n"
        			         + "2.Store all movie details into database\n"
        			         + "3.Add new movie in the list\n"
        			         + "4.Serialize movie data to file\n"
        			         + "5.Deserialize movies from given file\n"
        			         + "6.Find movies released in entered year\n"
        			         + "7.Find the list of movies by actor\n"
        			         + "8.Update movie rating\n"
        			         + "9.Update business done by movie\n"
        			         + "10.sort the movies by total business greater than given amount\n"
        			         + "11.Quit\n");
        	
        	int choice=scan.nextInt();
        	switch(choice)
        	{
        	case 1:
        		movie=Service.populateMovies(file);
        		System.out.println("Read data from file movie completed");
        	    break;
        	case 2:
        		Service.allAllMoviesInDb(movie);
        		System.out.println("Store all movie details into database Completed");
        		break;
        	case 3:
        		Movie m=new Movie();
        		m.setMovieId(scan.nextInt());
        		m.setMovieName(scan.nextLine());
        		m.setMovieType(scan.nextLine());
        		m.setMovieLang(scan.nextLine());
        		m.setReleaseDate(scan.nextLine());
        		ArrayList<String> ar=new ArrayList<>();
        		ar.add(scan.next());
        		ar.add(scan.next());
        		m.setCasting(ar);
        		m.setRating(scan.nextDouble());
        		m.setTotalBusinessDone(scan.nextDouble());
        		
        		Service.addMovie(m,movie);
        		break;
        	case 4:
        		Service.serializeMovies( movie,"D:\\Core Java\\m2.txt");
        		break;
        	case 5:
        		Service.deserializeMovie("D:\\Core Java\\m2.txt");
        		break;
        	case 6:
        		System.out.println("Enter year");
        		int year=scan.nextInt();
        		Service.getMoviesRealeasedInYear(movie, year);
        		break;
        	case 7:
        		List<String> actor=new ArrayList<String>();
        		actor.add(scan.nextLine());
        		Service.getMoviesByActor(movie, actor);
        		break;
        	case 8:
        		System.out.println("enter movie name and movie rating");
        		String moviename=scan.next();
        		double rating=scan.nextDouble();
        		Service.updateRatings(moviename,rating ,movie);
        		break;
        	case 9:
        		System.out.println("enter movie name and movie rating");
        		String mname=scan.next();
        		double amount=scan.nextDouble();
        		Service.updateBusiness(mname,amount,movie);
        		break;
        	case 10:
        		System.out.println("enter amount to sort the total bussiness greter than amount");
        		double amt=scan.nextDouble();
        		Service.businessDone(movie,amt);
        		break;
        	case 11:
        		bool=false;
        		break;
             
        	}
        }
	}

}
