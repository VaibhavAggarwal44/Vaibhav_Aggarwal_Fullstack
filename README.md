# Searchable Web Portal
This is a full stack project made with React.js, Springboot and elasticsearch.
## Features
This application enables users to:-
1. Login/Register using custom creds.
2. Create articles with all the available formatting options.
3. Make articles public or private. Private articles can be viewed by you only.
4. Search across all the articles using keywords or sentences.
5. Like/Dislike articles according to your preference.
6. Views increase on viewing an article.
7. Comment/ reply to a comment under an article.
8. View articles created by you.
9. View another user's public articles.
## Concepts Used
Many react hooks are used like useEffect, useState to manage variable states. UseEffect helps us restrict user to view webpages only if he/she is authenticated. Localstorage is used to store current username and articleId to perform fetch queries from backend.
Backend is designed with the help of springboot. Springboot application is connected to elasticsearch database. Elasticsearch database helps us perform search queries by providing inbuilt search functions. Our REST api provides various endpoints that help us perform fetch queries from frontend.

Navigation to different webpages is done through react-router-dom library which provides us with the hook 'useNavigate' to navigate to different components within same app.

## Learning Outcomes
After completion of this project, I have :-
1. Learnt to make frontend application using react and various npm libraries.
2. Use of different react hooks.
3. Perform elasticsearch queries.
4. Make multi-index backend Springboot application.
5. Develop a REST API with numerous endpoints.
6. Integrating frontend with backend.
