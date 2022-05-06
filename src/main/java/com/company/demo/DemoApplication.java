package com.company.demo;

import com.company.demo.Space.SpaceResponse;
import com.company.demo.Weather.WeatherResponse;
import com.company.demo.crypto.CryptoResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Scanner;



@SpringBootApplication
public class DemoApplication {

	//API KEY for CRYPTO: 7605887F-077A-4901-AB45-E50D61B79C48

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

		Scanner scanner = new Scanner(System.in);


		//main menu

		while (true){

			System.out.println("----- Please select number between 1 to 5 from the following-----\n");
			System.out.println("1-- Weather in a City\n");
			System.out.println("2-- Location of the International Space Station (ISS)\n");
			System.out.println("3-- Weather in the Location of the ISS\n");
			System.out.println("4-- Current Cryptocurrency Prices\n");
			System.out.println("5-- Quit\n");

			int userInput = 0;
			userInput = Integer.parseInt(scanner.nextLine());



			//switch cases for user's input

			switch(userInput){
				case 1:
					//weather in a city
						weatherByCity();
					break;

				case 2:
					//Location of the ISS
					printISSLocation(issLocationResult());
					break;

				case 3:
					//weather in the location of ISS
					issWeatherResult();
					break;

				case 4:
					//crypto result
					cryptoResult();
					break;

				case 5:
					//exit the program
					System.out.println("Thank you!");
					break;

			}


		}

	}

	/*
	* WEATHER Section
	*
	*
	* */

	public static String getInputCity(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter the name of a city:\n");
		String userCityInput = scanner.nextLine();
		return userCityInput;
	}

	public static WeatherResponse getWeatherResponse(String weatherURI){

		WebClient client = WebClient.create(weatherURI);
		WeatherResponse weatherResponse = null;

		try{

			Mono<WeatherResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(WeatherResponse.class);

			weatherResponse = response.share().block();

		}
		catch (WebClientResponseException we) {

			int checkStatus = we.getRawStatusCode();
			if (checkStatus >= 400 && checkStatus <500){
				System.out.println("Wrong city name. Please enter again");
			}
			else if (checkStatus >= 500 && checkStatus <600){
				System.out.println("Ops.. try again! ");
			}
		}
	return weatherResponse;
	}

	public static void printWeatherReport(WeatherResponse weatherResponse){
		if (weatherResponse == null){
			return;
		}

		//print put the message
		System.out.println(weatherResponse.getMain());
		System.out.println("Weather Report for the following city:\n");
		System.out.println("Current Temperature: \n" + weatherResponse.getMain().getTemp());
		System.out.println("Feels Like: " + weatherResponse.getMain().getFeels_like());
		System.out.println("Temperature Range: " + weatherResponse.getMain().getTemp_min() + " - " + weatherResponse.getMain().getTemp_max());
		System.out.println("Humidity: " + weatherResponse.getMain().getHumidity());


	}

	//check weather by city
	//API Key: 444cba18cd68af1fa386f6a16a56a7da

	public static void weatherByCity(){
		WeatherResponse weatherResponse = null;
		do{
			String cityName = getInputCity();
			String weatherURI = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=444cba18cd68af1fa386f6a16a56a7da&units=imperial";
			weatherResponse = getWeatherResponse(weatherURI);

		}while(weatherResponse == null);
		printWeatherReport(weatherResponse);


	}


	/*
	* SPACE Section
	*
	*
	* */

	public static SpaceResponse getSpaceResponse() {
		String issURI = "http://api.open-notify.org/iss-now.json";
		WebClient client = WebClient.create(issURI);


		SpaceResponse spaceResponse = null;

		try{

			Mono<SpaceResponse> response = client
					.get()
					.retrieve()
					.bodyToMono(SpaceResponse.class);

			spaceResponse = response.share().block();

		}

		catch (WebClientResponseException we){
			int checkStatus = we.getRawStatusCode();
			if (checkStatus >= 400 && checkStatus <500){
				System.out.println("Error occured");
			}
			else{
				System.out.println("Oppsss...Error occured");

			}
		}

		return spaceResponse;

	}

	/*
	* ISS Location
	* */

	public static void printISSLocation(SpaceResponse spaceResponse){

		if (spaceResponse == null){
			System.out.println("Your location was not found. Please enter the name of a city");
			return;
		}

		//print coordinates
		System.out.println("These are your ISS coordinates:\n");
		System.out.println("Lat: " + spaceResponse.getIss_position().getLatitude());
		System.out.println("Lon: " + spaceResponse.getIss_position().getLongtitude());

		//Print the city and country
		// System.out.println("Location: " + weatherResponse.getName() + ", " + weatherResponse.getSys().getCountry());


	}

	// prints iss coordinates, city, country and returns weatherResponse
	//API Key
	public static SpaceResponse issLocationResult(){
		SpaceResponse spaceResponse = getSpaceResponse();
		printISSLocation(spaceResponse);

		return spaceResponse;
	}


	public static void issWeatherResult() {
		SpaceResponse spaceResponse = getSpaceResponse();
		WeatherResponse weatherResponse = null;
		if(spaceResponse != null){
			String weatherURI = "https://api.openweathermap.org/data/2.5/weather?lat="+
					spaceResponse.getIss_position().getLatitude() + "&lon=" + spaceResponse.getIss_position().getLongtitude() + "&appid=444cba18cd68af1fa386f6a16a56a7da";
			weatherResponse = getWeatherResponse(weatherURI);
			printWeatherReport(weatherResponse);
		}
	}


	/*
	* Crypto
	* */

	//getting user input for crypto
	public static String getCryptoInput()
	{
		System.out.println("Please enter a cryptocurrency symbol. (E.g. For Bitcoin, enter 'BTC')");
		Scanner scanner = new Scanner(System.in);
		String userCryptoInput = scanner.nextLine();
		return userCryptoInput;
	}

	//getting crypto response
	public static CryptoResponse getCryptoResponse(String cryptoURI) {
		WebClient client = WebClient.create(cryptoURI);
		CryptoResponse cryptoResponse = null;
		try{

			Mono<CryptoResponse[]> response = client
					.get()
					.retrieve()
					.bodyToMono(CryptoResponse[].class);

			cryptoResponse = response.share().block()[0];


		}


		catch(ArrayIndexOutOfBoundsException e){
			cryptoResponse = null;
		}
		catch (WebClientResponseException we) {
			int statusCode = we.getRawStatusCode();
			if (statusCode >=400 && statusCode < 500){
				System.out.println("Error Occured!");
			}
			else if (statusCode >= 500 && statusCode < 600){
				System.out.println("Server error occured!");
			}
		}
		return cryptoResponse;
	}

	public static void printCryptoData(CryptoResponse cryptoResponse){
		if (cryptoResponse == null){
			System.out.println("Cryptocurrency not found.");
		}
		System.out.println("Name: " + cryptoResponse.getName());
		System.out.println("Symbol: " + cryptoResponse.getAsset_id());
		System.out.format("%7s%.2f\n","Price: $", cryptoResponse.getPrice_usd()); //CHECK HERE
	}

	public static void cryptoResult(){
		//7605887F-077A-4901-AB45-E50D61B79C48
		String userID = getCryptoInput();
		String cryptoURI = "https://rest.coinapi.io/v1/assets/" + userID + "?apikey=7605887F-077A-4901-AB45-E50D61B79C48";
		CryptoResponse cryptoResponse = getCryptoResponse(cryptoURI);
		printCryptoData(cryptoResponse);

	}
}


