# FlikrPagination
Android that searches images based on text using flickr api and displays them in a recyclerview. The project is designed using android architecture components.

Screenshots:

![Screenshot1](https://photos.app.goo.gl/9QUxxMfTA2WK16mm8)
![Screenshot2](https://photos.app.goo.gl/Tt1jAPhAi86CkHwN8)
![Screenshot3](https://photos.app.goo.gl/ZKnNNC9BnQqA8GAD7)

It uses Glide for image loading and caching.
It uses Volley for the web service calls.

It uses Android architecture components.
Room, LiveData, ViewModel ( which can withstand configuration changes and activity lifecycles).
It uses a clean architecture by introducing an abstraction called repository for the data layer. The Repository seemlessly supplies data either from the webservice layer or local persistence layer. The search query response data fetched from webservice (Flickr API) is stored in the local storage and serves the app as needed. Room is used an ORM tool to interact between the java and the SQL Lite world.

Room:
![Room](https://photos.app.goo.gl/vH6SRRXMfb3iRW749)

Architecture:
![Architecture](https://photos.app.goo.gl/vxDCCo3ukzDydawQ6)
