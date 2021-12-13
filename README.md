# OnlineAutomaticCheckWriter
This is an simple app to help business write check automatically online. It can get information from a file to write a list of checks or can be written from prompt. A few simple changes can make it applicable/useful for any business needs, especially firms that requires a lot of manually writing check..

Workflow: create from files, get informations about bankaccountID, payeename, payeeEmail, category, categoryType, memo, issueDate from each line, create a class check to contain all the information, change check to JSON file. Check if payee and categort exist, create new ones if not exist, check Check if everything is valid. 

Technologies used: Java, API, Postman
