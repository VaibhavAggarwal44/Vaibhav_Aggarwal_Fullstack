import React, { useEffect, useState } from "react";
import "./SearchPage.css";
import Navbar from "../NavBar/Navbar";
import { useNavigate } from "react-router-dom";
import Navbar1 from "../NavBar/Navbar";
import { IconContext } from "react-icons/lib";
import { FaSearch } from "react-icons/fa";

const SearchBar = () => {
  const url = "http://localhost:3000/view/article";
  const [users, setUsers] = useState([]);
  let username = localStorage.getItem("username");

  const [message, setMessage] = useState("");

  const [updated, setUpdated] = useState(message);

  const handleChange1 = (e) => {
    setMessage(e.target.value);
  };

  const navigate = useNavigate();

  useEffect(() => {
    let username = localStorage.getItem("username");
    // console.log(username)
    if (username === "" || username === null) {
      navigate("/login");
    }
  }, []);

  const handleChange2 = (e) => {
    e.preventDefault();
    setUsers([]);
    handleChange();
  };

  const articleView = (id) => {
    localStorage.setItem("articleId", id);
  };

  const handleChange = async () => {
    var str = message.trim();
    if (str === "") {
      return;
    }
    let username = localStorage.getItem("username");

    var query = str.replace(" ", "--");
    const dat = await fetch(
      `http://localhost:8081/apis/search/${query}/${username}`
    )
      .then((response) => {
        // console.log(response.json());
        return response.json();
      })
      .then((data) => {
        setUsers(data);
        console.log(users);
        // console.log(data);
      });
  };

  return (
    <div>
      <Navbar1 />
      <div className="container w-50">
        <form>
          <div class="input-group input-group-lg">
            <input
              type="text"
              id="message"
              name="message"
              onChange={handleChange1}
              value={message}
              className="form-control h-50 border border-primary my-3"
              aria-label="Default"
              aria-describedby="inputGroup-sizing-default"
            />
            <button
              onClick={(e) => {
                handleChange2(e);
              }}
              className="button-checker2 h-125"
            >
              <IconContext.Provider value={{ color: 'white', size: 20, fontWeight:100 }}>
                <FaSearch/>
              </IconContext.Provider>
            </button>
          </div>
          <h2>Query: {message}</h2>

          
        </form>
      </div>

      <div className="container w-75">
        {users.length > 0 &&
          users.map(
            (article, i) =>
              (article.isPublic || article.createdBy === username) && (
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
              )
          )}
      </div>
    </div>
  );
};

export default SearchBar;
