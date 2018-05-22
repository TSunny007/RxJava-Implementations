package com.rxjava;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Composition {

    public enum UserSecurityStatus {GUEST, MODERATOR, ADMINISTRATOR}

    private static class UserService {
        private class User {
           public String id, email;
           public UserSecurityStatus rights;
           public User(String _id, String _email, UserSecurityStatus _rights) {
               id = _id;
               email = _email;
               rights = _rights;
           }
        }

        private final ArrayList<User> allUsers;
        public UserService() {
            allUsers = new ArrayList<>();

            allUsers.add(new User("Sky", "scott@aweesome.com", UserSecurityStatus.GUEST));
            allUsers.add(new User("Jade", "jade@west.com", UserSecurityStatus.MODERATOR));
            allUsers.add(new User("Jimmy", "HYoloJimmy@he.com", UserSecurityStatus.ADMINISTRATOR));
            allUsers.add(new User("Aaron", "packerfam12@gmail.com", UserSecurityStatus.GUEST));
            allUsers.add(new User("Charles", "Kongy@apple.com", UserSecurityStatus.MODERATOR));
        }

        public List<User> fetchUserList() {
            return Collections.unmodifiableList(allUsers);
        }
    }

    public static void main(String[] args) throws Exception {
    // Create and sync on an object that we will use to make sure we don't
    // hit the System.exit(0) call before our threads have had a chance
    // to complete
    Object waitMonitor = new Object();
     synchronized (waitMonitor) {

         UserService userService = new UserService();

         // JSON formatting

         System.out.println("{ \"userList\" : [ ");

         // Call the user service and fetch a list of users/
         Observable.from(userService.fetchUserList())
         // Have to make an observable out of every user to proceed asynchronously.
         .flatMap(user -> Observable.just(user))
         // this states on which thread you are going to emit the values.
         .subscribeOn(Schedulers.computation())
         // this will filter out all Administrators.
         .filter(user -> user.rights != UserSecurityStatus.ADMINISTRATOR)
         // We will then sort users by their privilege level
         .toSortedList((user1, user2) -> user1.rights.compareTo(user2.rights))
         .doOnCompleted(() -> {
          // since we have completed we sync on the waitMonitor
          // and then call notify to wake up the "main" thread.
          synchronized (waitMonitor) {
              waitMonitor.notify();
          }
         })
         .subscribe(userList -> userList.forEach(user -> {
             try {
                 System.out.println((new ObjectMapper()).writeValueAsString(user));
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }
         }));
         waitMonitor.wait();

         System.out.println("] }");
     }
     System.exit(0);
    }
}
