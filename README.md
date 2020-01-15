# TheWeatherApp
 
This app was developed to demonstrate a modularized base structure for Android Apps. 
 

The instruction of the app:

As a user, when I open the app I should see the weather for my current location, so I can
immediately see weather that is relevant to me.

● Screen shows current conditions (sunny, foggy, raining, etc.), temperature, wind speed
and direction.

● The weather information should be cached for future offline use.

● If I am offline, and there is weather information cached that is less than 24 hours old:

  ● The last known conditions and location should be shown along with a prominent display
    to indicate when the data was last updated.
    
  ● A button should be displayed to allow a user to refresh the data.

● If I am offline, and there are no previous conditions known, or the previous conditions are
more than 24 hours old:
  
   ● A screen should be displayed to indicate there is no previous data available.
   
   ● A button should be displayed to allow a user to refresh the data.
   
● If I refresh the data manually, and I am offline, a message should be displayed to indicate
that I need to connect to the Internet in order to get updated data.

● The app should display a loading indicator if it is fetching data.

● Use OpenWeatherMap.org, or any other weather API of your choice

● If using OpenWeatherMap, weather graphics can be found
at http://openweathermap.org/img/w/<weather.icon>.png
