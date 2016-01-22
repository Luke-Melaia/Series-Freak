#Series Freak
Simple but powerful series manager.

##Winning Features

###Categories
**Have your entries sorted into categories as you add and edit them.**

The application has several predefined categories that your entries are sorted into.
The categories are located at the top right of the application. Clicking on one
will select the category and filter entries.

###Custom Categories

**Create your own named categories and add/remove entries to them.**

Right click on an entry, scroll down to "categories", choose whether
you want to add or remove the entry to a category and click the category
in the list. You will need to have at least one custom category for this to work.

To create a custom category: in the categories window (top right), right click
anywhere in the list box or click the green plus icon on top of the list.
A small window will appear, asking for a name for the category, give the
category a name and hit enter.

You can edit and remove the category by right clicking it or by selected it and
using the category control buttons (three buttons located on top of the categories).

- The green plus icon adds a category.
- The yellow circular arrow icon edits the selected category.
- The red cross icon removes the selected category.

###Auto Watch
**Open the entries next "watchable" file right from the application**

Series Freak allows you to set, what i call "episode files" for an entry.
When set, you can double click an entry inside the table(middle-left)
to open the next episode file.

To set the episode files: when creating an entry, you have two tabs, the first,
titled "General" is where you set the basic required information for the entry,
the second, titled "Episodes" is where you set the episode files. Inside the tab,
you have a text field titled "Root file", this is defaut folder/file to open if
Series Freak fails to open the next episode file, or, if the entry doesn't
contain any episode files. Next to the text field are two buttons with three dots
on them, the first will set a file as the root file (this is onlyreally useful for
movies and one episode series), the second will set a folder as the root file, when
you set a folder, Series Freak automaticly walks the files and/or folders inside the
root folder and adds any non-hidden files (not folders) to the episodes list (underneath
the root folder text field). Series Freak will only walk the first set of folders inside
the root folder, any nested folders (folders inside folders) will not be walked. For example:
say you have a folder tree like this:

- C:
  - Videos
    - Series
      - Some Series Name
        - Season one
          - Episode 1
          - Episode 2
          - Episode 3
          - Episode ...
          - Episode n
        - Season two
          - Episode 1
          - Episode 2
          - Episode 3
          - Episode ...
          - Episode n
        - Season three
          - Episode 1
          - Episode 2
          - Episode 3
          - Episode ...
          - Episode n
      - Another Series
      - ...
      - n
    - Movies
      - ...
      - n

If you set the folder `C:\Videos\Series\Some Series Name` as the root folder, then
all the files inside "Season one", "Season two" and "Season three" will be added as
episode files to the entry.

Its best to play around with this feature a bit to get the feel for it.

Now we get to the episode files part. Beneath the root folder text field is a list,
inside this list is where all the episode files are shown. This list is orded by
episode, that is the files inside the list should be orded `episode one, episode
two, episode three` and so on. If the order of the list is inconsistent then the
feature will fail to work.

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

## License
GNU General Public License.
