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

## Demo
Login/Register
<img width="1110" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/666a3d3f-e1a1-4b6c-88e0-3df262c37fa6">
Home Page
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/ec6817a4-fa1f-4886-a7ec-af97fc2f537b">
Navbar Options
<img width="786" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/46e5420f-4829-41e9-8ba8-40594acdbcee">
Inserting article with all the options
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/45c0ff27-52c0-4dac-bcd4-5f24dcc5502f">
User's articles
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/d27b2c17-d0cd-405b-901e-2adabd74582e">
Comments and like dislike
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/3fc8f8b8-1f4f-4e36-b401-c3d3715311cd">
Edit user's articles
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/624aff29-31df-456e-a5c3-6074da684aed">
Fuzzy Search
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/50103cca-093b-46af-a461-533d9695cbcd">
Substring Match
<img width="1433" alt="image" src="https://github.com/VaibhavAggarwal44/Vaibhav_Aggarwal_Fullstack/assets/76945241/e1e18c41-d763-4c96-a857-aae8eb743e26">


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
