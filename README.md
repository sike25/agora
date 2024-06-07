# Agora
Agora is a marketplace built in Android, for students to find and list items for sale.

### Project Specification
The front-end of this project was built entirely in Jetpack Compose, and the back-end is supported by Firebase. Elements of the application include:
1. **User Management:**   
   User management is provided by Firebase authentication. The first screen users are presented with after the splash is a login page, which also links (optionally) to a register page. User management is important for access, but also for functionality within the application-- in linking listings and purchases to particular users.

![user_management](https://github.com/sike25/agora/assets/97693483/90686696-0c6b-4708-8fcc-2c91f6dc5aed)
 
3. **List Item:**  
   Users can list items for sale by uploading an image, filling in text fields for properties of the listing, select the item's category off a spanner menu and add tags (phrases which drive our search functionality). Saving the listing uploads it to Firebase which, has we said, provides this application's backend.
   
4. **Feed:**  
   The feed lets users scroll through and listings to see their details page (which also holds the "Acquire" button). The feed is populated from Firebase. A stretch goal is to personalise the feed using users' purchase and search behaviours.
   
5. **Search:**  
   Above the feed sits a search bar. Search functionality in Agora is currently done by checking the query against text-based properties of the listing (notable, the tags but also the title, description and even category). Stretch goals would be to utilise an ML-based search API (looking at Algolia) to extend this functionality to synonyms, related words and misspellings, and also checking the uploaded images against the query.
   
6. **Acquire Item:**
   Selecting a listing from the feed or search items result page takes you to a landing page which lists details of the item and an "Acquire" button. Clicking the "Acquire" button drafts from the email client configured on the device in use to the user who had listed the item.
   
