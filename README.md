# BAYMAX

Baymax frees your mind of having to remember things to do, giving you more time to learn! It's

- text-based
- simple to pick-up
- incredibly fast to use

## Requirements to Run Application

Java 17 installed on device

## How to Run Application

1. Download the latest release file (.jar file)
2. Copy the .jar file into a preferably empty folder
3. Double click the .jar file to run the application.
   (*Alternatively, on terminal, change directory to the folder where the .jar file is and input `java -jar baymax.jar` to run)

### Adding tasks

You can add 3 types of tasks - todo, deadline, event

1) Add Todo task

Use this format: todo [description]
Example: `todo read book`

```
I have added this task:
    [T][] read book
```

2) Add Deadline task

Use this format: deadline [description] /by [yyyy-mm-dd HHmm]
Example: `deadline homework /by 2025-02-23 2359`

```
I have added this task:
    [D][] homework (by: Feb 23, 2025, 11:59pm)
```

3) Add Event task

Use this format: event [description] /on [yyyy-mm-dd] /from [HHmm] /to [HHmm]
Example: `event party /on 2025-02-28 /from 2100 /to 2330`

```
I have added this task:
    [E][] party (on: Feb 28, 2025 from: 9:00pm to: 11:30pm)
```

### Marking tasks

You can mark your tasks as done or not done!
(provide the index of your task on the list)

Example: `mark 1`

```
Good job on completing this task:
    [T][X] read book
```

Example: `unmark 1`

```
Oh seems like you have not completed this task:
    [T][] read book
```

### Viewing a list of your tasks

You can view a list of all of your tasks!

Example: `list`

```
Here are all your tasks:
1. [T][X] read book 
```

* if list is empty:
```
You have no tasks in your list!
```

### Finding tasks

You can search for your tasks using a keyword!

Example: `find book`

```
Here are all the matching tasks:
1. [T][X] read book 
```

* if there is no match:
```
There aren't any matching tasks!
```

### Deleting tasks

You can delete tasks that you do not want to see on your list!

Example: `delete 1`
(provide the index of your task on the list)

```
I have removed the task:
    [T][X] read book
```

### Undoing tasks

You can undo your latest command! (except for 'find' and 'list' commands)

Example: `undo` (for mark/unmark commands)

```
Undo succesful! The status of this task is reverted:
```

Example: `undo` (for other commands)

```
Undo succesful! Your last command has been reverted.
```

### Exiting app

You can leave the app by typing a command

Example: `bye`

```
Byeee! Take care :D
```
