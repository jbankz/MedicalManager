# Medical Manager
This is a simple mobile application that enables users to store the list of their medications and also reminds them when to take their medications.
The app makes use of the following components for development.
•	ROOM – A database for offline persistent
•	ButterKnife – For initializing views
•	Firebase Authentication – For users’ authentication
•	Esspresso – For recording UI tests

The final app can be downloaded here.

Prerequisites

In other to finish build this, you’ll need the following 
1.	A computer (Laptop or Desktop) 
2.	Android studios 3.0 up to the latest
Login Page
The user will first be presented with a login page where they are required to authenticate themselves using google social authentication (Email and Password Method) and also be presented with options of registering for a new account if they have no previous account and also an option to request for new password if the previous password is being discarded or forgotten. 
 
MainActivity Page
This page shows gives the users the options to:
1.	Add Medication and sets its alarm
2.	Remove medications 
3.	Search for medications by name
4.	Categorize medications by month
 
Sign Out/Delete account
The user can sign out but this does not clear any settings made by the user such as medications added but if the users gets to delete the account, all users’ settings will be deleted.
