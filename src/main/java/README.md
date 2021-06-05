Stable Marriage Algorithm Report

Diego Rodriguez
Thomas Nadal


#Description of the stable marriage problem in the parcoursup context
Originally, the stable marriage algorithm, as its name says, was applied to the context of men serenading
women and women choosing the men they want.
Each man and woman has respectively a complete list of all the women and man ranked according to their preferences.
So the objective of this problem is to find a stable matching between two groups of elements. That is to say,
the goal is to marry everyone and every man must marry exactly one woman and vice-versa.
The marriage must be stable. A stable marriage is a marriage that have no pair of people that prefer each other
to their spouses.
The mating ritual occurs like this :
- The procedure for finding a stable matching involves a Mating Ritual that takes place over several days
- Morning: Each woman stands on her balcony. Each man stands under the balcony of his favorite among the women
  on his list, and he serenades her. If a man has no woman left on his list, he stays home and waiting until the afternoon.
- Afternoon: Each woman who has one or more suitors serenading hey, says to her favorite among them, "We might get engaged. Come back
  tomorrow". To the other suitors, she says, "No, I will never marry you ! Take a hike!".
- Evening: Any man who is told by a woman to take a hike, crosses that woman off his list.
- Termination condition: When a day arrives in which every woman has at most one suitor, the ritual ends with
  each woman marrying her suitor, if she has one.

This scenario represents the Gale-Shapley algorithm which solves this problem by matching all elements from one group to an element
of the other group. With this algorithm, the marriage will always be stable.

This problem is used in network infrastructures and also in many fields.
In the context of this project, we will apply this stable marriage problem to imitate the algorithm
of the French platform "Parcoursup".
In the real parcoursup algorithm, students are bidding schools and schools choose the students they want.
Schools have a capacity representing how many student they can accept. If they are more students that
want a school than the school capacity, then the school need to choose the students according to
the school preferences.
The preferences are usually made thanks to the acamedic record.
The term bidding means that the entity need to look at its preferences and chose the entity
to which he wants to present himself. It's like the serenading in the basic marriage problem.

This algorithm differs a little from the Gale-Shapley algorithm because here, schools have different
capacities (obviously, they can accept more than one student).

We used the terms "Bidders" and "Choosers". Bidder is the entity which do the bidding and chooser is the entity which
chooses the entity that just made their bidding.
We can compare these terms by saying that "Bidders" are the entities serenading the other entities and "Choosers" are the entities
that receive the serenades.

#Objective of the project

This project aims to implement by ourselves the stable marriage algorithm also calles the
Gale-Shapley algorithm. We studied it during the Graph Theory lessons at Enseeiht, an engineer school
in Toulouse. Usually, the given example for this problem refers to men serenading women. In our case,
we had to create the same situation that faces parcoursup, an online platform which assigns post-bac
students into schools.
Usually, only the students do the bidding in parcoursup but for this project, the program user can
choose wether the school or the student do the bidding.
If he wants to explore more scenarios, the user can also create the CSV file by inspiring
of the pre-built CSV file we made. He can also modify the files we already made.
These source files are composed of two groups on which we apply the algorithm. On one side (lines), we have
the students and on the other side (columns), we have the schools.
On the other cells of the CSV file, we have pairs of values corresponding to the school and student
preferences. In this pair, the first number represents the student preferences. The second number
represents the school preferences.
We apply the algorithm according to this kind of file.
The user can also choose the group who does the bidding.
Then, the program show the results of the matching algorithm.

We were free to use the technologies we want to make this project. Thus, we choose Java to code because
it's a language that we master very well. It was more preferable to take our time to do the algorithm right
rather than discover a language. Python would have been really easier to use but we didn't have the time
to learn this language. But it's not a problem because Java is robust and very performing. We also should
consider trying a low-level language if we wanted to computer a really high number of elements.
We also choose to use the CSV format for the source files because it's a very
simple format to read and also an easy-to-parse type of file.
We also used opencsv, a tool to parse the csv file easily.

#How we implemented the algorithm

#How to create the source files
As we said in the introduction, we use CSV files (Comma-separated values). This kind of file is a delimited text file that uses a comma to separate values.
There are also different types of delimiters. Each line of the file is a data record. We use these lines to create our data structures.
We use this file to store only tabular data (numbers and text).

Below, you can see an example of a CSV file create for the purpose of this project.
;ENSEEIHT;INSA;POLYTECH
Diego;1,2;2,3;3,1
Kilian;2,1;3,1;1,2
Thomas;3,3;2,2;1,3

If we export this file in excel, we can see the result below:
#How to use the script