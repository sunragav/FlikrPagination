# FlikrPagination
An Android app that searches images based on text using flickr api and displays them in a recyclerview. The project is designed using android architecture components.

The project is split into two gradle projects, one for UI and the other for data, to clearly separate the concerns.

It uses RecyclerView.
It uses Glide for image loading and caching.
It uses Volley for the web service calls.


It uses Android architecture components.
Room, LiveData, ViewModel ( which can withstand configuration changes and activity lifecycles).
It uses the Clean architecture by introducing an abstraction in each three mainlayers of the application namely Presentation, ViewModel(or Business logic) and the repository for the data layer. The Repository layer seemlessly supplies data either from the webservice layer or local persistence layer. The search query response data fetched from webservice (Flickr API) is persisted in the local storage(SQL Lite via Room) and serves the app as needed. Room is used an ORM tool to interact between the java and the SQL Lite world.

Room:
![Room](https://github.com/sunragav/FlikrPagination/blob/master/Room.JPG)

Architecture:
![Architecture](https://github.com/sunragav/FlikrPagination/blob/master/Architecture.JPG)

Screenshots:

![FirstScreen](https://github.com/sunragav/FlikrPagination/blob/master/FirstScreen.png)
![Search](https://github.com/sunragav/FlikrPagination/blob/master/search.png)
![Results](https://github.com/sunragav/FlikrPagination/blob/master/results.png)

Contact: 
sunragav@gmail.com
+91 8655444565
