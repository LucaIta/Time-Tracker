# Team week project: Time Tracker

#### _A program to track time for a single task or group of tasks._
#### _May 20, 2016_

#### By
_Noah Kittleson_
_Luca Quatela_
_Nadiya Yeroshkinn_
_Inthrayuth Mahaphol_

## Description

A program to track time for a single task or group of tasks (routines). Users are be able to add, update, delete, and view all tasks and routines. Multiple tasks can be added to Routines.
Users will be able to view all Tasks that linked to Routines.

* New Tasks and Routines can't be saved if user leave the blank input field.

Databases:
* Production database is called "time_tracker"
* Development database is called "time_tracker_test". The development database should be created by duplicating from production database.
* There are tables in the production database: "runs", "tasks", "lap_times", "routines", and "routines_tasks".
* SQL database dump file is named time_tracker.sql

## Setup/Installation Requirements

* _Link to repository: https://github.com/NoahKittleson/Time-Tracker.git_
* _Download this app to your computer_
* _Java, Gradle, and Postgres apps need to be installed on your computer_
* _Run "gradle run" on command line and go to url "localhost:4567" on a web browser_

## Setup database

* Open a terminal and run "postgres" to access the Postgres server.
* Open another terminal and run "psql". These process will give you ability to create databases and tables.

  In PSQL:
*  =# CREATE DATABASE time_tracker;

*  =# \c time_tracker;
*  time_tracker=# CREATE TABLE runs (id serial PRIMARY KEY, routine_id int, task_index int, lap_id int);
*  time_tracker=# CREATE TABLE tasks (id serial PRIMARY KEY, name varchar, goal_time bigint);
*  time_tracker=# CREATE TABLE lap_times (id serial PRIMARY KEY, start_time bigint, end_time bigint, task_id int, run_id int);
);
*  time_tracker=# CREATE TABLE routines (id serial PRIMARY KEY, name varchar);
*  time_tracker=# CREATE TABLE routines_tasks (id serial PRIMARY KEY, routine_id int, task_id int);

*  time_tracker=# CREATE DATABASE band_tracker_test WITH TEMPLATE time_tracker;

## Known Bugs

* There is no feature to prevent duplication data when user input new Tasks or Routines.
* "Add Tasks to a Routine" do not have feature to prevent duplication adding.

## Support and contact details

_Feel free to contact us with questions or suggestions._
* _Noah Kittleson: noahkittleson@gmail.com_
* _Luca Quatela: lucaqq@gmail.com_
* _Nadiya Yeroshkinn: nadin.yeroshkina@gmail.com_
* _Inthrayuth Mahaphol: zign.int@gmail.com_

## Technologies Used

* _Git_
* _Github_
* _Java_
* _Postgres_
* _Gradle_
* _Spark_
* _Apache Velocity_ (with using the Velocity Template Engine)

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 Noah, Luca, Nadiya, Inthrayuth.
