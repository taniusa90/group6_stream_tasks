package com.cydeo.service;


import com.cydeo.model.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.cydeo.service.DataGenerator.*;


public class Implementation {


    /**
     * Read all student users.
     *
     * @return all the student users {@link User} and {@link Role}
     */
    public static List<User> readAllStudents() {

        return fillUsers().stream()
                .filter(user -> user.getRole().getName().equalsIgnoreCase("student"))
                .collect(Collectors.toList());

    }

    /**
     * Read all admin users.
     *
     * @return all the admin users {@link User} and {@link Role}
     */

    public static List<User> readAllAdmins() {

        return fillUsers().stream()
                .filter(user -> user.getRole().getName().equalsIgnoreCase("ADMIN"))
                .collect(Collectors.toList());

    }

    /**
     * Read all manager users.
     *
     * @return all the manager users {@link User} and {@link Role}
     */
    public static List<User> readAllManagers() {
        return fillUsers().stream()
                .filter(user -> user.getRole().getName().equalsIgnoreCase("manager"))
                .collect(Collectors.toList());

    }

    /**
     * Read all SUSPENDED users.
     *
     * @return all the suspended users {@link User} and {@link UserState}
     */
    public static List<User> readAllSuspendedUsers() {
        return fillUsers().stream()
                .filter(user -> user.getState().name().equalsIgnoreCase("SUSPENDED"))
                .collect(Collectors.toList());
    }

    /**
     * Read all CONFIRMED users.
     *
     * @return all the confirmed users {@link User} and {@link UserState}
     */
    public static List<User> readAllConfirmedUsers() {

        return fillUsers().stream()
                .filter(user -> user.getState().name().equalsIgnoreCase("CONFIRMED"))
                .collect(Collectors.toList());
    }

    /**
     * Read all PENDING users.
     *
     * @return all the pending users {@link User} and {@link UserState}
     */
    public static List<User> readAllPendingUsers() {
        return fillUsers().stream()
                .filter(user -> user.getState().name().equalsIgnoreCase("PENDING"))
                .collect(Collectors.toList());
    }

    /**
     * Count all courses.
     *
     * @return number of courses {@link Course}
     */
    public static Long countCourses() {
        return(long) fillCourses().size();

    }


    /**
     * Sum the all duration of courses.
     *
     * @return sum of duration {@link Course}.
     */
    public static Integer sumDurationForAllData() {
        return fillCourses().stream()
                .map(course -> course.getDuration())
                .reduce(Integer::sum)
                .get();
    }

    /**
     * Find Courses attended by a specific user.
     *
     * @param id userId information is necessary.
     * @return all courses with user id {@link User} and {@link Course}
     */
    public static List<Course> findCoursesByUserId(Integer id) {
        User specificUser = findUserById(id);
        return fillCoursesAssigned().stream()
                .filter(courseAssigned -> courseAssigned.getUser().equals(specificUser))
                .map(CourseAssigned::getCourse)
                .collect(Collectors.toList());
    }


    /**
     * This method converts the duration of course to the weeks based on user information.
     * One week has 40 hours working time. Divider should be 40.
     *
     * @param id userId information is necessary.
     * @return converted duration to week. {@link BigDecimal}
     */
    public static Integer divideToWeek(Integer id) {
        return fillCoursesAssigned().stream()
                .filter(p -> p.getUser().equals(findUserById(id)))
                .map(p -> p.getCourse().getDuration()).mapToInt(Integer::intValue).sum()/40;

    }

    /**
     * This method groups all the CourseAssigned based on their status
     * and numbers. (ex. 4 courses are IN_PROGRESS)
     *
     * @return all CourseStatus with number of courses in that status as a Map<CourseStatus, Long>
     * {@link CourseStatus}
     */
    public static Map<CourseStatus, Long> countCoursesByStatus() {
        //TODO
        return fillCoursesAssigned().stream()
                .collect(Collectors.groupingBy(CourseAssigned::getStatus,Collectors.counting()));

    }

    /**
     * This method finds the courses which has max duration
     * and min duration. Use teeing method.
     *
     * @return Map in this format (ex. Max Duration=1000)
     */

    public static Map<String, Integer> getMinMaxCourseDuration() {
        //TODO
        return fillCourses().stream().collect(Collectors.teeing(
        Collectors.maxBy(Comparator.comparing(Course::getDuration)),
                (Collectors.minBy(Comparator.comparing(Course::getDuration))),
                (r1,r2)->{
                    Map<String,Integer> map = new HashMap<>();
                    map.put("Max Duration",r1.get().getDuration());
                    map.put("Min Duration",r2.get().getDuration());
                    return map;
    }
        ));
    }

    /**
     * This method finds the first user
     * who is first name starts with J
     *
     * @return the first user depending on first name
     */
    public static User findFirstWithFirstName() {
        //TODO
        return fillUsers().stream()
                .filter(user -> user.getFirstName().startsWith("J"))
                .findAny().get();
    }

    /**
     * This method finds any user whose
     * last name is Wooden. If no user exists with this last name
     * then throw exception.
     *
     * @return the first user depending on first name
     */
    public static User findAnyWithLastName(){
        //TODO
        return fillUsers().stream()
                .filter(user -> user.getLastName().equalsIgnoreCase("Wooden"))
                .findFirst().orElseThrow(()->new NoSuchElementException("No user exists"));
        }

    /**
     * This method partitions the users
     * depending on whether user state is CONFIRMED
     *
     * @return map
     */
    public static Map<Boolean, List<User>> partitionOfConfirmedUsers() {
        //TODO
        return fillUsers().stream()
                .collect(Collectors.partitioningBy(user -> user.getState().equals(UserState.CONFIRMED)));
    }

    /**
     * This method groups all CourseAssigned depending on
     * CourseStatus
     *
     * @return map
     */

    public static Map<CourseStatus, List<CourseAssigned>> groupOfCourseAssigned() {
        //TODO
        return fillCoursesAssigned().stream()
                .collect(Collectors.groupingBy(CourseAssigned::getStatus));
    }
}