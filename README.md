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
**_Horrible_** programmer at the time... hopefully I'm not
any more), I decided to make a better one. It didn't
even get off the drawing board before I set off to make an even
**better** one... I deleted all the source code for this one
after a bug that just seamed to break _everything_. After
calming down, regaining my sanity, taking a little break
from computers, I sat down and got to work on, what I
though would be the _final_ version/remake. This one I
actually finished! It had most of the features this one
has, it looked okay (until you saw this one). All in all,
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

##Winning Features

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
well let me show you:

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
try to confuse the application, create weird folder trees and so. You will figure
out how it works really quickly. Don't worry about damaging any folders on your
computer, the application can't modify any files other than the files you save to.

This pretty much covers the auto watch feature. I'm positive you will enjoy it :)

##Installation
Download the installer from the releases page and follow the instructions.

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
