#Series Freak
A simple, yet powerful series manager.

##The Making of Series Freak

I created this application... well actually I
created an application similar to this one, only
it had the bare minimum of features. All it could
do was keep track of the anime I watched. It did its job,
it saved me time typing up lists that I used to remember
what episode I was on, gave some sense of order to an
otherwise incomprehensible mess at the back of my mind.
After a while of using it, always fearing it would
someday blow up - corrupting all my saved data (I was a
**_Horrible_** programmer at the time... hopefully I'm better
now), I decided to make a better one. 

It didn't even get off the drawing board before I set off to
make an even **better** one... I deleted all the source code
for this one after a bug that just seamed to break _everything_.
After calming down, regaining my sanity, taking a little break
from computers, I sat down and got to work on, what I though
would be the _final_ version/remake.

This one I actually finished! It had most of the features this
one has, it looked okay (until you saw this one). All in all,
it was wonderful. From the very beginning I had intentions
to release it as open source, but unfortunately, I got a bit
tried of working on it, so, in my eyes, the code looked horrible...
I thought to myself "I can't release this... I'll never live it down".
So I just gave the application to a few friends of mine, used it for
a long time without any fear of losing data (I still made backups though).
I finally had an amazing series manager, it did everything I wanted it to,
ran smoothly, the whole nine yards. Then I got bored... I had no other
projects to lose my mind over, started running out of anime and series
to watch, had a few more ideas I wanted to implement. I **wanted** to
make another series manager, I wanted to release it, so I made this one.

This one I really enjoyed making, the code just seamed to fit perfectly,
it didn't give me too many headaches, it looked amazing (I think so at least).
It was perfect. I gave it some rough tests (it passed them), got some friends to
test it, made sure it worked well. After all the preparation and testing,
it was finally ready, after all that work... all was perfect. I released
it onto github... obviously, I wrote this README, sat back and took a deep
breath, and acknowledged my achievement.

That is the story of my favourite project. Why not give it a try? Read
through the rest of this README and see if you like it.

##Some Screenshots

Figure 1: The main window.

![Figure 1](/Screenshots/MainWindow.png?raw=true "Figure 1")


Figure 2: The entry add window's general tab.

![Figure 2](/Screenshots/AddWindowGeneral.png?raw=true "Figure 2")


Figure 3: The entry add window's episodes tab.

![Figure 3](/Screenshots/AddWindowEpisodes.png?raw=true "Figure 3")


Figure 4: The entry add window's episodes tab with populated data.

![Figure 4](/Screenshots/AddWindowEpisodesPopulated.png?raw=true "Figure 4")

##Best Features

Here we go over the best features of the application.

###Categories

**Have your entries sorted into categories as you add and edit them.**

The application has several predefined categories that your entries are automatically
sorted into.

The categories are located at the top right of the application
(Figure 1, Top Right). Clicking on one will select the category and filter the entries.

###Custom Categories

**Create your own named categories and add/remove entries to them.**

You can create custom named categories and add/remove entries to them.

To create a category: right click anywhere in the categories list, next,
click on the "Add Category" option. Alternatively, press the green plus
icon above the categories list. A small window with a text field will
appear, enter any name you wish and hit enter. A new category will now
appear in the list, but before it can be used, you have to add some entries
to them. To add an entry to the category, create a dummy entry (doesn't
have to be anything special), now right click on the entry you just created
and mouse over the "Categories" options, next mouse over the "Add To Category"
option and select the category you created. Clicking on the category now will
display the entry you just created.

Note:

- Custom categories ignore dropped entries (We'll getting to this shortly).
- Custom categories may be edited and removes freely without risk of damaging entries.
- Custom categories also include any rewatch entries linked to the added entry(Another
feature I will get to shortly).
- You cannot edit or remove the build in categories.

By now you may have noticed the three buttons on top of the categories list.

- The green plus icon adds a category (I know I'm repeating myself).
- The yellow circular arrow edits the currently selected category.
- The red cross will remove the currently selected category.

You can add, edit, and remove categories by right clicking on a custom category
in the list, rather than pressing the buttons on the top.

###Auto Watch

**Open the entries next "watchable" file right from the application**

This is a rather complicated feature, but it's the best in my opinion...
so you may want to get familiar with it.

The basic concept behind this is that you can (optionally) add files to an entry,
and have the application open a file depending on what episode/chapter you are on.

When you're creating an entry from the add window (Figures 2, 3, and 4),
you can specify a root file and episode files.

The root file can be either a file, or folder that will be opened if
the application fails to open an episode file, or if no episode files
exist. If you're adding a movie, or one-episode-series, then setting
the root file to the movie/series (file, not folder) is the best approach.

The episode files are a list of files (not folders) that are basically the
series/movie itself. Think of it like this, you have some series/movies on
your pc, they are most probably saved as media files (.mp4, .avi, .ect),
or html links (.html) that open a web page. These are the files you add
to the list. You can add any file to the list as long as your OS (operating
system) can open them. On windows your [default application]
(http://www.sevenforums.com/tutorials/12196-open-change-default-program.html)
will be used to open the file. I'm not sure if this works on linux or Mac OSX,
so a word of caution is advised.

There are 2 ways to add files to the list:

- The first, and easiest way would be to let the application
add them automatically (yes you read correctly).

Imagine you have a folder tree like this (you probably do):

- C:\
  - ...
  - ...
  - ...
  - Series
    - A Series Name
	  - Season 1
	    - Some folder (This must be a folder)
		  - Some random file
	    - Episode 1.mp4
		- Episode 2.mp4
		- Episode 3.mp4
		- Episode n.mp4
	  - Season 2
	    - Some folder (This must be a folder)
	    - Episode 1.mp4
		- Episode 2.mp4
		- Episode 3.mp4
		- Episode n.mp4
	  - Movie 1
	  - Movie 2
	- A Series Name 2
	  - Episode 1
	  - Episode 2
	  - Episode 3
	  - Episode n
	- ...
	- ...
	- n
  - ...

Take a look at Figure 3, on the right of the root folder text field,
you have two buttons, the first sets a file as the root file, the
second sets a folder, click the second button. A directory chooser
window will open, locate the directory `C:\Series\A Series Name`
and select the folder `A Series Name`, press the "Select Folder"
button (Lower right). When you do, if you did everything correctly,
your window should look similar to figure 4. Also do the name with
"A Series Name 2" to see what happens.

What happened: the application looked through all the files
inside the folder "A Series Name" and added them to the list, next,
it looked through all the folders inside the folder "A Series Name"
and added any files located within. The application only looks 1
directory deep, that is, any folders inside the "Seasons" folders will
be ignored.

The application is rather flexible with folder trees, so it should work
with however you have your files organised.

A few things to note:

- The application ignores any hidden files and folders.
- The application sorts the files alphanumerically.
- You can move the files up and down in the list.


- The second way would be to manually add files and folders to the list.

Take a look at figure 3 again, to the side of the episode files list,
you can see 5 buttons. 

To add a file to the list press the second button
from the top, locate the file you want to add and double click it.

To add a folder to the list, press the third button from the top,
locate the folder you want to add and double click the folder.
This works in exactly the same way as selecting the root folder,
only it appends the contents of the folder to the list rather
than clearing the list and then adding the contents of the file,
also it doesn't modify the root folder.

By now I'm guessing you're wondering what all the other buttons do...
well let me tell you:

1. Moves the selected file up the list by one place
2. Adds a file to the list.
3. Adds a folder to the list.
4. Removes the selected file from the list.
5. Moves the selected file down the list by one place.

Simple right?

Okay, I've shown you how to set everything up correctly, but two things
still need to be addressed. First, how do I actually use this feature?
Second, how are the episode files related to what episode I'm on?

- To address the first question, simply double click the entry in the table.

When you double click the entry, the application will open the matching episode
file and increment what episode/chapter you are on by 1, if it succeeded in
opening the file. 

- To address the second question, if you are on episode/chapter 0, then the first
file will be opened and so on.

Remember that this is all optional, you don't even have to look at the episodes
tab if you don't want to. Although I recommend you play with this feature a lot,
try to confuse the application, create weird folder trees and so on. You will figure
out how it works really quickly. Don't worry about damaging any folders on your
computer, the application can't modify any files other than the files you save to.

This pretty much covers the auto watch feature. I'm positive you will enjoy it :)

###Create Entry From Folder

**Create an entry from a folder on your computer**

The application gives you the ability to fill in entry information from a folder
on your computer.

Before trying this out, create a folder tree similar to the one in the auto watch
feature example. You only have to create "A Series Name", don't worry about the
second one.

Look at figures 2 and 3, at the bottom left, you can see a button with a folder icon,
click it and locate the folder `C:\Series\A Series Name` and select the folder.
If everything worked correctly, you should see that the name of the entry is set to
the name of the folder "A Series Name", the total episodes text field has the number
of episode files available (10 if your folder tree is the exact same as mine), and,
the episode files has been set up as though you set the root folder to `A Series Name`.

If you haven't already figured out what it does, it uses the folder you selected to try
and fill in as much information about the entry as possible from the folder. It uses the
folder name as the entries name, the amount of files inside the folder (only the ones the
auto watch feature will recognise) as the total episodes of the series, and, automatically
sets up the episode files.

With this feature you hardly have to fill in any information about the entry itself, you just
need to specify if its a seires, movie or book, set what episode you are on, and give it a
rating (if you have finished it already).

Well, that pretty much covers all best features of the application. Lets move on to how
to actually use the thing, shall we?

##How to Use

Here we go over how to use the application.

###Menu Bar

Let's start with the menu bar at the top of the main window (figure 1).

The first menu option is the file menu, use this to save to and load from files.
It's rather simple to figure out so a detailed explanation isn't necessary. 

**Important:**
When you first use the application, make sure you save, if you exit the application
without saving you will lose your work. The application does save to the last file
you saved to when it exits, you just need to give it a file to save to when you
first use it. Also, if you load a new template and exit the application, it will
overwrite your last save file, so make sure to save to a new file. It also opens
the last save file when you launch the application.

The second menu option is just some information about the application. You don't need
to worry about this.

###Control Buttons

Let's move on to the buttons just underneath the menu bar. These buttons are used to
add and modify entries, along with other related features. They are simple enough to
use, so again, a detailed exammple isn't necessary.

Here is what the buttons do:

1. Opens the entry add window so you can add an entry to the database.
2. Allows you to edit the selected entry (selected in the table) through the entry add window.
3. Removes the selected entry.
4. Completes the selected entry. When you complete the entry, you will be asked to give a rating
to the entry. If you don't specify a rating, nothing will happen. If you do give a rating, the entry
will be marked as completed, regardless of what episode/chapter you were last on.
5. Increments the selected entries episode/chapter by one. If the episode/chapter is one below the
total episodes, you will be asked to complete it.
6. Decrements the selected entries episode/chapter by one. This won't work if the entry is completed.
7. Marks the selected entry as a favourite. When an entry is marked as a favourite, it will show up in
the favourites category.
8. Creates a rewatch of the selected entry. A rewatch entry is special type of entry that acts as a linked
copy. Basically, a rewatch entry will always have the same information has the original, even if you update
the original, or, another rewatch. The only difference is, its name and episode/chapter on values are unique
to it. This is useful if you want to watch the series again, but don't want to modify the original or create
a new entry from scratch. This also allows you to modify one and have the rest update automatically.
9. Drops the selected entry. When an entry is dropped, it won't show up in any category other than the dropped
category. This is useful if you want to stop watching a series, but don't want to lose your progress. Out of
site, out of mind.
10. Writes an html document to where you installed the application. This document contains a table of all the
entries currently displayed inside the applications table.

You can right click an entry to easily modify it without having to press the buttons at the top. A nice shortcut
that saves some time.

###Search Bar

We also have a search bar to the very right of the buttons. You can search for an entry inside the selected category
with it. Hit enter after typing the name of the entry to search. The search feature ignores upper case and lower case
letters, so you don't have to type out the name exactly as it's spelt. Also it looks for entries with names that
contain the word(s)/letter(s) inside the search bar. 

###Statistics

Underneath the categories list, which is underneath the search bar, we have the statistics list. This list simply gives
you some statistics of just how much you have watched. It self explanatory, so adding any more information here is pointless.

###Info bar

Right at the very bottom of the main window, we have a progress bar, that shows us what entry we currently have selected.
If the progress bar doesn't have any text within it, no entry is selected. The progress bar tells us:
- If the entry is a rewatch or a normal entry.
- The name of the entry.
- What episode/chapter you are on.
- How many total episodes/chapters the entry has.
- Its type (series, movie, book).
- The date it was completed.
- Its rating
- If it's marked as a favourite.
- If it's dropped.

###Table

Finally, we have the table. This is where the entries are displayed. The table only really allows you to change in
what order the entries are displayed. By default the entries are displayed in the order they were added, although,
this isn't a very appealing sort order. To change the sort order: you can click on the table column titles to cycle
between ascending order, and descending order. You can also have the application sort the entries by multiple orders,
if you want the table to sort the entries by rating, and then by name, first shift click on the rating header, then,
shift click on the name header. You will see one dot under the arrow in the rating column, and two dots under the arrow
in the name column, what this means is: it's going to sort the entries by name in ascending order first, then, the 
entries will be sorted by rating in ascending order. This is my favourite sort order, but you can have any order you like.
I recommend you play with this a little bit to get the feel for how it works. Note: the sort order is saved to file when
you close the application, so you only have to specify the sort order once. 

###Adding An Entry

Let's move on to how we add an entry to the database.

Open the entry add window (figure 2) by pressing the green plus icon just under the file menu on the main window.
Make sure you have the "General" tab open (we have already covered the episodes tab). Let's start by giving the
entry a name, I'll call mine `Example Entry` ,the name cannot be empty, and it must be unique. Next we tell it
what episode we're on, I'll use `22`, this must be below or equal to the total episodes field, and above `-1`, you
can leave the field blank and let the application insert a `0` for you. Next we tell it how many total episodes
the entry has, I'll use `49` this time, this field cannot be blank or below 1, it can however contain a `?`, when
you insert a `?`, you're telling the application that the entry has an unknown number of episodes (use this for
ongoing series), this means that the episode on field can increase without limit. Next is the
rating list, this is required for an entry that is completed, if you set a rating for an entry, it will automatically
be marked as completed regardless of what episode you said you were on. Finally we have the type radio buttons, these
just tell the application if it's a series, movie, or book. This is only used for sorting and statistics reasons, it
has little effect on how the entry functions. 

I recommend you play with this a bit to get the hang of it.

That pretty much covers how to use the application. Now what are you waiting for? Give it a try!

##Installation
Download the installer from the releases page, and, install it as you would any
other application. Note: if you're on windows and you install to a directory
that requires administrative privileges to edit (e.g. `C:\Program Files`), you
will have to give the launcher administrative privileges.

##Requires
Java 8 update 65 (8u65) or any later version. [Download java] (http://java.com/),
if you don't have it.

## Contributing
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History
V1.0 - First release.

## Credits
Luke Melaia - Author.

##Special Thanks
- [Google] (https://github.com/google) for their open source libraries. Wouldn't be possible without them.
- [Dave Koelle] (http://www.davekoelle.com) for the Alphanum comparator. The application just wouldn't be the same without it.
- [Apache] (http://www.apache.org) for Log4j. Debugging and fixing crashes just isn't the same without it.

## License
GNU General Public License.
