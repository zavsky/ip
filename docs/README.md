# Bluebird User Guide

![alt text][logo]

[logo]: https://github.com/zavsky/ip/raw/master/docs/icon.png "product logo"

Roses are red, violets are blue
I've spent too much time, and so did you too

Bluebird is not exactly the nicest program to talk to. For that, you have chatGPT. But hey, if its snarky behaviour isn't too off-putting, it may help you get all your work done in a more timely manner.

In all seriousness, it aims to organise and collect all your outstanding tasks, be it events or deadlines, so that you can better plan your day and accomplish more without forgetting a single detail. Hope you don't mind CLI, that is. Here's what you need to know to get started.


## Adding Tasks

Creation of a new task begins with the command `create` (or `c` for short). If no parameters are specified, the follow-up prompts will guide you to furnish the required (but arduous) information. A necessary evil.

### Add Todos

Todos are tasks without a specific date or time attached. Append the `add` command with `todo`/`t` or string them together to reduce keystrokes. Similarly, you may string the task description behind to create a todo in one command. Easy.

Syntax: `create todo [task_description]`

Example: `c t read the room`

### Add Deadlines

Deadlines attach a date to the created event. Type either `deadline` or `d` to specify this option. Optionally, include the task description and end date in a single line with the keyword `/by` or `/b` as a delimiter between the date and description.

Syntax: `create deadline [event_or_task_name] [/by end_date]`

Example: `c d fix the leak /b end of the week`

### Add Events

Events include a start and end time. The `event` or `e` option selects this task type. Task description follows the option with keywords `from`/`f` and `to`/`t` to separate the start and end time respectively. The order of the last two flags is interchangeable.

Syntax: `create event [event_name] [/from start_time] [/to end_time]`

Example: `c e fart concerto /f wed 5pm /to 8pm`

#### Note: leaving blanks

If certain details are not known during task creation, just leave them blank.


## Deleting Tasks

Tasks can be deleted via their indices shown when the `delete` option (or `d`) is specified. Alternatively, if you already know which task to remove, append the option with the index.

Example: `d 5`

### Batch delete

You may delete multiple tasks at the same time by separating the indices via spaces.

Syntax: `delete task_index [task_index ...]`

## Marking (and unmarking)

Mark a task's completion with `mark` or `m`, followed by the task index.

Example: `m 6`

### Batch marking

String the tasks for marking with a space delimiter to mark or unmark multiple tasks simultaneously.

Example: `mark 8 4 5 10`


## Undo last (accidental) commands

Use the `z` option to quickly undo the last task modification. Use it again to redo the undo. Strangely, the cycle continues forever.
Still curious about how it works? Deleted tasks will be added, marked tasks will be unmarked, you get it.


## Show task lists

The `list` command (or `l`) will present all saved tasks formatted as a list. If something horrible shows up, or you suddenly get a realisation why you were particularly free at that moment, it's not my fault.


## Get help string

Need help? All these syntax examples are accessible via the `help` keyword. You probably guessed it, but just `h` is fine too.


## Exiting the program

After a long day and the list has piled up, simply type `quit` to quit the program. No, you shall not shorthand with `q`, which will force quit all your unsaved apps. Just kidding.


## Future improvements

Modify existing tasks (in-place)
Batch delete all marked tasks
Search tasks via keyword
Basic search with filter options
Suppress query prompts (and fail quietly when insufficient or incorrect info is given)


// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```
