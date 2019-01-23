"# ExoCustomPlayerMVP" 

Description​: This is a simple online video player Application which is developed with MVP Architecture and JAVA.
 
● The app Entry screen is sign up screen, in which the user has to sign in using his/her google account.

● Firebase google authentication is used for the sign up.

● In the main page, Videos are listed from URL ​https://interview-e18de.firebaseio.com/media.json?print=pretty​)

● If user Click any item from the list, it will navigate to the detail page which shows the clicked items information and related Videos.

● Related videos is the same list of items from the main page except the clicked item.

● In detail page, when play button clicked will play the video.

● Once the video is completed playing, it will play the next video in the queue. Continue watching have be implemented.

●E.g., A video duration is 2mins. If you played the video for 30 secs and come back from the player page. and play the video again, it will start play from the 30 secs.

●These informations are stored in Sqlite using Room Persistance Library)

● For Playing videos, Google's ExoPlayer has been used.


● Libraries Used, ExoPlayer, Room, Retrofit, Firebase Authendication.



APK Link:->>>>  https://www.dropbox.com/s/dts2d0evrdq6a7x/MVideo_Player_v1.apk?dl=1
