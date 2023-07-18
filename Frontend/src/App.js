import React, { useEffect, useState } from "react";
import Form from "./Form/InsertForm";
import SearchBar from "./Search/SearchPage";
import Home from "./Home";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import AllArticles from "./AllArticles";
import ArticleBody from "./ArticleBody";
import Login from "./Authentication/Login";
import Register from "./Authentication/Register";
import UserArticles from "./UserArticles/UserArticles";
import EditArticle from "./Edit/EditArticle";
import AnotherUserArticles from "./UserArticles/AnotherUserArticles";


const App = () => {

  return (
    <BrowserRouter>
      <Routes>
          <Route path="/login" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/" element={<Home />} />
          <Route path="/create" element={<Form />}/>
          <Route path="/search" element={<SearchBar />}/>
          <Route path="/edit" element={<EditArticle/>}/>

          <Route path="/display" element={<AllArticles/>}/>
          <Route path="/view/article" element={<ArticleBody/>}/>
          <Route path="/view/userArticles" element={<UserArticles/>}/>
          <Route path="/view/user2Articles" element={<AnotherUserArticles/>}/>
          <Route path="*" element={<h1>404 page not found</h1>}/>

        </Routes> 
    </BrowserRouter>
  );
}

export default App;