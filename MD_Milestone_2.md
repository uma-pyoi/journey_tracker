# IY4113 Milestone 2

| Assessment Details | Please Complete All Details                                      |
| ------------------ | ---------------------------------------------------------------- |
| Group              | A                                                                |
| Module Title       | Applied Software Engineering using Object Orientated Programming |
| Assessment Type    | Java Fundamentals Part 2                                         |
| Module Tutor Name  | Jonathan Shore                                                   |
| Student ID Number  | P479574                                                          |
| Date of Submission | 01/02/2026                                                       |
| Word Count         | 2225                                                             |

- [x] *I confirm that this assignment is my own work. Where I have referred to academic sources, I have provided in-text citations and included the sources in
  the final reference list.*

- [x] *Where I have used AI, I have cited and referenced appropriately.

------------------------------------------------------------------------------------------------------------------------------

### Algorithm Design

------------------------------------------------------------------------------------------------------------------------------

##### Flowchart

Main program

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/MAIN_program_logic_flowchart.jpg)

Print menu

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Print_menu_flowchart.jpg)

Activate menu action

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Activate_menu_action_flowchart.jpg)

Add journey

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Add_journey_flowchart.jpg)

Remove journey

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Remove_journey_flowchart.jpg)

List journeys

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/List_journeys_flowchart.jpg)

Activate list filter action

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/List_filter_flowchart.jpg)

View Daily Summaries 

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/View_daily_sum_flowchart.jpg)

All types summary

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/All_types_sum_flowchart.jpg)

Totals by passenger type summary

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Passenger_type_sum_flowchart.jpg)

Category count summary

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Category_sum_flowchart.jpg)

Reset day

![](/Users/assemyeleussiz/Downloads/milestone2_flowchart/Reset_day_flowchart.jpg)

--- 



##### Class Diagram

![](/Users/assemyeleussiz/Downloads/CLASS_DIAGRAM_wnotes_milestone2.jpg)

------------------------------------------------------------------------------------------------------------------------------

### Research

---

##### Phonebook Application

Name of program: Phonebook Application

Reference (link): [GitHub - ChogirmaliYigit/phone_data_collector: a simple command-line phonebook application implemented in Python. It allows users to manage a list of contacts, including adding, editing, and searching for contacts. (Based-on https://docs.google.com/document/d/1dIH7lY05hNLSluZgOYsRyTrvLmyz4CnNEtJFFXBbS-c/edit)](https://github.com/ChogirmaliYigit/phone_data_collector.git)

What it does well (2-3 features that work effectively):

1. Being a console based program, the application outputs text to the console in a pretty, formatted way (for example, the program even has a special function called 'print_window_title' that accepts one string parameter and surrounds it with 15 '=' characters before and after the passed string). Such formatting allows the user to navigate through the user interface more easily, since the user's eyes will not confuse previous console output with the new one, because it will be separated with a line of '=' characters.

2. The program has the feature of listing contacts in the contacts book. This application paginates the list, showing only 10 contacts on one page. For that it has a special function called 'paginate_data'. When viewing the pages, user can enter 'n' to view the next page and 'p' to view the previous page. Splitting the list into smaller parts and letting the user go through the pages themselves helps the user to navigate through the list easier. 

3. The application allows for searching through the contacts. In order to find someone who works at "China Company" the user can enter a certain part of the organisation's name, so entering "hina com" will give you all of the contacts that work at a company that contains the string "hina com" in its name (Could be "China Company", "Hina Com", "Abchina Comabc" etc). Such a feature is convenient for the user a certain part of the name instead of the full name reduces the chance of the user making typos and allows the user to search even if they do not remember the full name of the organization/full name of the contact/full phone number (searching for phone numbers works similarly to searching for names, since, presumably, they are stored as strings)

What it does poorly (at least 1 feature):

1. The searching feature does not allow for any typos, so searching for "Chna" would not show you someone who works at "China Company"

2. In the case when the user searches for contacts but does not find any the program does not notify the user that no contacts were found, instead outputting the pagination UI that says "Page 0 of 0. Press q to quit, n for next page, p for previous page:". The program also allows the user to enter 'p' and 'n' in this case even though they do not do anything. So feedback to the user, while present in most parts of the program, is sometimes absent in certain cases.

3. In some places, for example when searching for contacts, the application does not add any extra spaces

Key design ideas you could reuse (e.g., layout, navigation, input/output, program structure):

1. The function to automatically format and print a window name surrounded with characters to make it more readable is useful and could be easily replicated in my program.

2. The pagination UI for outputting the contacts is a nice and clear way to allow the user to navigate through a list, especially when the list is very long. It resembles the feature in our program that lists all of the user's journeys. Since the journeys we will be outputting are the journeys for the entire day and multiple passenger types, adding a pagination UI would be incredibly useful.

Screenshot (showing the interface/output):

main menu:

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-01-31-18-28-55-image.png)

pagination UI:

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-01-31-18-27-23-image.png)



---

##### Habit Progress Tracker

Name of program: Habit Progress Tracker

Reference (link): [GitHub - smartIU/habit-tracker: A simple console application to define your personal habits and track their completion status.](https://github.com/smartIU/habit-tracker#)

What it does well (2-3 features that work effectively):

1. When the database of habits is empty, the program gives the user an option to insert a set of predefined habits with various stats, which can be incredibly useful for testing purposes. 

2. The application has a feature of listing the user's habit and filtering through them by the lenght of the habit. It allows the user to enter the lenght of the period in number of days, one week or one month. If the user enters a number of days that is bigger than one week/month, the program accounts for that and outputs the habits with the entered length.

3. The program has funtions for validating various types of inputs, as well as a function 'get_confirmation' to ask the user to confirm their actions. The functions get called in the main loop of the program numerous times, proving to be useful.

What it does poorly (at least 1 feature):

1. When presented with a number of options, there is no separate line asking the user to enter the option. As we can see in the 'main menu' screenshot, the '2' entered by the user is simply a character on the new line right under the menu, which greatly hinders readability when viewing previous actions in the terminal. 

Key design ideas you could reuse (e.g., layout, navigation, input/output, program structure):

1. Having functions to validate various types of inputs is a good idea, as our journey tracking program requires a lot of different kinds of inputs from the user. Just like the habit tracking program, my program could have a function to get the user's choice for when they need to choose a number corresponding to a certain option (in the main menu, when choosing passenger type, when choosing filtering options, etc.)

2. Having a dedicated confirmation function is also a great point since it will be used at numerous points in our program (when removing a journey, resetting a day, etc.)

Screenshot (showing the interface/output):

main menu:

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-01-31-19-18-20-image.png)

listing of all habits:

![](/Users/assemyeleussiz/IdeaProjects/T0479574_IY4113_java/habit_tracker_screenshots/habit_listing_all.jpg)

listing of habits with filter - 7 (days)

![](/Users/assemyeleussiz/Library/Application%20Support/marktext/images/2026-01-31-19-24-58-image.png)

listing of habits with filter - month

![](/Users/assemyeleussiz/Library/Containers/ru.keepcoder.Telegram/Data/tmp/telegram-cloud-document-2-5472020804503901418.jpg)

------------------------------------------------------------------------------------------------------------------------------

### Updated Gantt Chart

------------------------------------------------------------------------------------------------------------------------------

![Gantt chart should be here](/Users/assemyeleussiz/Documents/Ganttchart_milestone2.png)

------------------------------------------------------------------------------------------------------------------------------

### Diary Entries

------------------------------------------------------------------------------------------------------------------------------

### 26/01/2026 - Diary Entry 4 - Gantt Chart change & Research of Similar Programs

Today I started milestone 2. Since I have already planned the order of execution of tasks for every stage of the project, including milestone 2, on the Gantt chart, the first thing I did was open the chart to see where I should start from. Upon taking a closer look at the 5 tasks for this part of the assignment, I realized that the order for milestone 2 I planned for back when I first created the chart was incorrect. In my previous plan, the design development and creation of the diagram came before the research of similar programs (and the subsequent documentation and referencing for said research). In this order, researching similar programs would be pointless, since I would have already created the design and the diagram for my project. This is why I decided to move the research & the documentation and referencing tasks to be the first ones in the list. 

I was also able to solve the problem of colouring of the Gantt chart progress blocks by changing the conditional formatting of the cells according to my tasks. The new progress blocks created by me, however, have white grid lines in between them, unlike the default ones. As the grid lines are much less important than the colour, I will leave the Gantt chart as it is for now. Perhaps I will look into this issue in the future.

Finding similar programs seemed challenging at first — I searched for “journey tracker” programs most of which were too advanced and thus analysing the code and finding design patterns I could reuse for this project was nearly impossible. Discouraged, I tried looking up “simple console based” projects, which led to me finding 2 applications perfect for my researching purposes. Both of them are written in Python, run in terminal and keep track of records that the user can modify and search through. I cloned both of the repositories and ran the projects in VScode, taking screenshots of the features I mentioned in my research. I read through some of the code and talked about some of the functions I found useful with the hopes of recreating them in my own project.



### 29/01/2026 - Diary Entry 5 - Developing the Overall Design of the Program

Today I started developing the overall design of my program. For this task I had to produce flow chart diagrams and class diagrams. I decided to think of the class diagram first, since knowing what entities exist before thinking of the flow of the program seemed more logical to me. As such, I referred to the ‘Core Program Functionality’ section of my milestone 1 report and tried breaking down the program into classes. I ended up creating 4 classes — Journey, PassengerType, Zone and JourneyTracker. the first 3 are self-descriptive and do not carry out any special functions, whereas the JourneyTracker class is the class responsible for the operation of the entire program, having all the core methods such as functions for ‘add journey’, ‘list journeys’, etc. as well as functions for calculations built into it. I was unsure if there is a need for the Zone class, as I still have not seen the Java class provided by the assignment, but I decided to leave the Zone class for now since it is easier to remove something than add it in the future.

For the flow chart, I had a conceptual understanding of it in my mind that was based on the procedural programming paradigm I was taught during term 1. Since I am still very new to Object Oriented Programming, I will create the flow chart based on my current understanding of the program flow.



### 30/01/2026 - Diary Entry 6 - Diagram Creation

For the creation of the class diagram I considered using [draw.io](http://draw.io), just like my teacher in class, however being already familiar with Miro I stuck to it instead. Every class in the class diagram has to have states and methods for it, and while states are pretty intuitive, I had trouble with deciding what class a certain method should belong to, namely, all of the methods related to the core program functionality. I used the class diagram my teacher created for the library scenario as a reference, and so I put all of the core functionality methods into the JourneyTracker class (just like class Library carries out all of the features in my teacher’s example). One interesting thing I found was that my program does not need a class for a user, like the library scenario needs a class for a member, because my program has no authorisation/authentication features. When connecting my classes with the relationship arrrows I, again, referred to my teacher’s example, and ended up using 3 composition relationships and 2 associations. For some of the methods I had to return more than one value, and in those cases, not having much knowledge about Java, I used arrays and 2D arrays. Once I begin the actual implementation of this, it might need to be changed.

When making a flow chart, I looked at the flow charts I created for my Python classes during term 1, which probably made my flow charts lean even more into the procedural programming paradigm. While creating the logic itself was quite easy, actually drawing the flow charts proved to be tremendously tedious. I spent 3 hours on each type of the diagram (class diagram and flow chart), and while spending such a huge amount of time on the class diagram can be explained by it being a new concept to me, the 3 hours I spent on the flow chart diagrams made me question my own work. I felt like, perhaps, I was adding too many details for something that should be there implicitly. In the end, I was left with 12 flow chart diagrams. In total I have spent 12,5 hours on milestone 2. I plan to ask my teacher if I am wasting my time by going into such details with the flowchart and the research of similar programs as that task alone took me 6,5 hours.

------------------------------------------------------------------------------------------------------------------------------
