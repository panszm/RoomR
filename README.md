# RoomR
A simple console program to randomly assign people to groups

The program uses text file from its main directory "config.txt".
The file's structure looks like this:<br>
  `SizeOfFirstGroup,SizeOfAnotherGroup,SizeOfThirdGroup`<br>
  `NameOfFirstGroup,NameOfAnotherGroup,NameOfThirdGroup`<br>
  `NameOffFirstPerson,CategorynameOfFirstPerson`<br>
  `NameOfAnotherPerson,CategorynameOfAnotherPerson`<br>
  `and so the list of people's names goes on...`<br>
The first two lines are rather self-explanatory, then we have people list.<br>
It goes like 'Name,GroupName', name is just a name of the person, CategoryName depends on how we want to divide our list.<br>
For example, we could divide all the people by their sex, so two categories could be 'F' (female), and 'M' (Male), the program won't mix these categories in the same groups.<br>
The program has no biases, therefore it's totally random. It finds all the possibilities for dividing the categories into certain groups, and then picks one at random. Then puts every name into one random group of its category.<br>
Results are saved into 'result.txt' file.<br>
We start the program using 'Main.java' file. It can be easily modified.
