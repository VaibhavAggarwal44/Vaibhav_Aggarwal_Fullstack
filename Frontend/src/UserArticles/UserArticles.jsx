import { useAutocomplete } from "@mui/material";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../NavBar/Navbar";
import { Typography } from "@mui/material";
import Navbar1 from "../NavBar/Navbar";
import {
  FaThumbsUp,
  FaThumbsDown,
  FaComment,
  FaEyeSlash,
} from "react-icons/fa";
import { FiEdit } from "react-icons/fi";
import { IconContext } from "react-icons/lib";

function UserArticles() {
  const [articles, setArticles] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    let username = localStorage.getItem("username");
    // console.log(username)
    if (username === "" || username === null) {
      navigate("/login");
    } else {
      fetch(`http://localhost:8081/apis/checker/${username}`)
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
          Your Articles:
        </Typography>

        <div className="container w-75">
          {articles.length > 0 &&
            articles.map((article, i) => (
              <div class="card my-4">
                <div class="card-header">
                  <h4>{article.heading}</h4>
                </div>
                <div class="card-body">
                  <p class="card-text">
                    {article.articleBody
                      ? article.articleBody.substring(0, 300) + "..."
                      : ""}
                  </p>
                  <h6>
                    LIKES: {article.likes}&nbsp;&nbsp; VIEWS:{article.views}&nbsp;&nbsp;
                    {article.isPublic == false &&
                        article.createdBy ==
                          localStorage.getItem("username") && (
                          <IconContext.Provider value={{ color: "", size: 20 }}>
                            <FaEyeSlash />
                          </IconContext.Provider>
                    )}
                  </h6>
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

export default UserArticles;
