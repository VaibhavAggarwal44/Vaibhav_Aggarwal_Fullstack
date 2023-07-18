import { useAutocomplete } from "@mui/material";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../NavBar/Navbar";
import { Typography } from "@mui/material";
import Navbar1 from "../NavBar/Navbar";

function AnotherUserArticles() {
  const [articles, setArticles] = useState([]);
  const navigate = useNavigate();
  let user2 = localStorage.getItem("user2");

  useEffect(() => {
    let username = localStorage.getItem("username");

    let user2 = localStorage.getItem("user2");
    // console.log(username)
    if (username === "" || username === null) {
      navigate("/login");
    } else if (user2 == username) {
      navigate("/view/userArticles");
    } else {
      let user2 = localStorage.getItem("user2");
      fetch(`http://localhost:8081/apis/${user2}/getPublic`)
        .then((response) => {
          // console.log(response.json());
          return response.json();
        })
        .then((data) => {
          setArticles(data);
          // console.log(data);
        });
    }
  }, []);

  return (
    <>
      <Navbar1 />
      <div className="container">
        <Typography sx={{ margin: "5%" }} variant="h3" align="center">
          Articles By {user2}:
        </Typography>

        <div className="container w-75">
          {articles.length > 0 &&
            articles.map((article, i) => (
              <div class="card my-4">
                <div class="card-header">
                  <h4>{article.heading}</h4>
                </div>
                <div class="card-body">
                  <h6>
                    LIKES: {article.likes}&nbsp;&nbsp; VIEWS:{article.views}
                  </h6>
                  <p class="card-text">
                    {article.articleBody
                      ? article.articleBody.substring(0, 300) + "..."
                      : ""}
                  </p>
                  <a
                    onClick={() => {
                      localStorage.setItem("articleId", article.id);
                      navigate("/view/article");
                    }}
                    class="btn btn-primary table-row"
                  >
                    View Post
                  </a>
                </div>
              </div>
            ))}
        </div>
      </div>
    </>
  );
}

export default AnotherUserArticles;
