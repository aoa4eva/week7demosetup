# week7demosetup : The components (pun intended) for a working Week 7 project 

### To make sure you get the most out of this project, look through the following files: 

1. Dataloader.java - this sets up the database initially so that there is data in the application. Great for testing (ddl-auto = create or create-drop for this). 
2. TestController - this is where each component is 'tested' before being put in the main application. This DOES NOT replace unit tests!
3. models folder - Examine the relationships, and how they work.

### To use this project: 

1. Create an SQL database called Week7
2. Run the application (make sure you've chosen the correct SDK!) 
3. You can create users (but not assign them roles yet)... you fix that in. For now, a user can only have ONE role. 
4. Jim is a recruiter. Add jobs and assign skills to the jobs. 
5. Bob is a seeker. Add skills to his profile and see the jobs that match his skills. 


### Extend the project 
1. Add education (a user can add as many educational achievements as he or she would like) 
2. Display a summary of information entered - Education and Skills 
3. Add thymeleaf fragments, so that your index file is small, and also that you can navigate with a navbar regardless of your role. 

