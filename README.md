# Intro
This challange proposed us to create an Android app that displays a list of upcoming sports events, allows users to display the events by sport type, filter them based on if they are favorite, and displays a countdown timer for each event that indicates when the event is scheduled to start.

# Development
## Architectures and technologies
To build the app, I followed MVVM + Clean Architecure. I also decided to use some of the following technologies and libraries:
 - Jetpack Compose to build our UI. I also made a good use of the composable States to hold data which even survived screen rotations (ex.: the expanded state);
 - Viewmodel: As a part of the MVVM architecture, it is also really helpful when holding information between activity resets (screen rotation etc)
 - Network acess: Retrofit2 which is really simple to use;
 - Local Storage: Using Room to create a cache that holds the information of every event that the user chose as their favorite. Is also really simple to use;
 - Dependency injection: No libraries were used, just by creating an app module and initializing it in the Application's onCreate was already enough for this small project;
 - Coroutine: Using StateFlow to hold the state of the data in our viewmodel so we can query and filter our data without blocking the app;
 - Unit test: Just a small one, but enough to test my filter feature.

## Challanges faced
### The favorite filter
We are getting too used to have the backend deals with all logics, and that's why the first challange I faced was the favorite filter logic.

I came back to some older projects to remember how to combine flows so I could achieve the filter feature.

The sports list data didn't have a "isFavorite" field, so I had to add it to my domain model. We could simply update the object's "isFavorite" field any time the user clicks on the star, and it would work.. but just until a reload happens. And that's why we had to use Room.

With Room I could create a local data base that simply stored the id of each event that the user adds as their favorite. Then in the viewmodel we _combine()_ the sports list with the favorite events list that we get from Room, obtaining our updated sports list. And that is enough to have a cached favorite event feature.

But I still needed to filter out unfavorite events only when the user toggles the favorite button. And it was simply resolved by creating a simple stateflow in the viewmodel which holds a list of favorite toggled sports. And finally, _combine()_ it with our updated sports list to obtain the final filtered sports list.

## Performances issues
We can notice a bad performance when trying to expand a sport that has many events. This may be due to the fact that when we expand the sport, even if we don't see all of its items, they are all getting loaded. Another reason would also be the amount of threads executing the countdown timer.

Lots of items and lots of threads and they are not being recycled.. this might be the reason.

We minimized it by moving the timer thread to the IO dispatcher, since it works better with a lot of threads in parallel. 

The definitive solution would be adding all items to a LazyVerticalGrid, creating a generic type and treating each one of them separately. Would take some time to update, the app, but would probably work.
