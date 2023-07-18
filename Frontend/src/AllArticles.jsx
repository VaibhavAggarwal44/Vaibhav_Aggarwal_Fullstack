import React, { useEffect, useState } from "react";
import "./AllArticles.css";
import { Link, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  FaThumbsUp,
  FaThumbsDown,
  FaComment,
  FaEyeSlash,
} from "react-icons/fa";
import { FiEdit } from "react-icons/fi";
import { IconContext } from "react-icons/lib";
import Navbar from "./NavBar/Navbar";
import Navbar1 from "./NavBar/Navbar";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";

const AllArticles = () => {
  const [searchtext, setSearchtext] = useState("");
  const [articles, setArticles] = useState([]);
  let var1 = false;
  let var2 = false;
  const [flag, setflag] = useState(true);
  let username = localStorage.getItem("username");

  useEffect(() => {
    let username = localStorage.getItem("username");
    if (username === "" || username === null) {
      navigate("/login");
    } else {
      handleChange("articles/sortView/" + username);
    }
  }, []);

  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 4;
  const lastIndex = currentPage * recordsPerPage;
  const firstIndex = lastIndex - recordsPerPage;
  const records = articles.slice(firstIndex, lastIndex);
  const npage = Math.ceil(articles.length / recordsPerPage);
  const numbers = [...Array(npage + 1).keys()].slice(1);

  const navigate = useNavigate();

  function nextPage() {
    if (currentPage !== lastIndex && currentPage !== npage) {
      setCurrentPage(currentPage + 1);
    }
  }

  function prePage() {
    // if(currentPage===1){}
    if (currentPage !== firstIndex && currentPage !== 1) {
      setCurrentPage(currentPage - 1);
    }
  }

  const handleChange2 = (e) => {
    e.preventDefault();
    if (var1) {
      console.log("like");
      handleChange("articles/sortLike/" + username);
    } else if (var2) {
      console.log("view");
      handleChange("articles/sortView/" + username);
    }
  };

  const handleChange = (message) => {
    try {
      fetch(`http://localhost:8081/apis/${message}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Basic ${btoa("admin:admin")}`,
        },
      })
        .then((response) => {
          // console.log(response.json());
          return response.json();
        })
        .then((data) => {
          // console.log(data)
          if (message == "articles") setArticles(data.content);
          else setArticles(data);
          // console.log(data.type);
        });
    } catch (err) {
      console.log(err);
    }
    return true;
  };

  const handleSearchChange = async (e) => {
    e.preventDefault();
    setArticles([]);

    var str = searchtext.trim();
    if (str === "") {
      setflag(true);
      handleChange("articles/sortLike/" + username);
      return;
    }

    setflag(false);
    var query = str.replace(" ", "--");
    const dat = await fetch(
      `http://localhost:8081/apis/search/${query}/${username}`
    )
      .then((response) => {
        // console.log(response.json());
        return response.json();
      })
      .then((data) => {
        setArticles(data);
        // console.log(data);
      });
  };

  return (
    <div>
      <Navbar1 />

      <div className="justify-content-center">
        <div className="container w-50">
          <form>
            <div class="input-group input-group-lg">
              <input
                type="text"
                id="message"
                name="message"
                onChange={(e) => {
                  setSearchtext(e.target.value);
                }}
                value={searchtext}
                className="form-control h-50 border border-primary my-3"
                aria-label="Default"
                aria-describedby="inputGroup-sizing-default"
              />
            </div>

            <button
              onClick={(e) => {
                handleSearchChange(e);
              }}
              className="button-checker2 h-75"
            >
              Search
            </button>
          </form>
        </div>
        <div className="container checker2">
          {flag && (
            <div>
              <a
                className="style-button btn btn-primary  my-2 justify-content-center"
                onClick={(e) => {
                  var1 = true;
                  var2 = false;
                  handleChange2(e);
                }}
              >
                Sort by likes
              </a>
              <a
                className="style-button btn btn-primary mx-3 my-2 justify-content-center"
                onClick={(e) => {
                  var1 = false;
                  var2 = true;
                  handleChange2(e);
                }}
              >
                Sort by views
              </a>
            </div>
          )}
          {records.length > 0 &&
            records.map((article, i) => (
              <Card className="my-3" key={i}>
                <Card.Header>
                  <h3>{article.heading}</h3>
                </Card.Header>
                <Card.Body>
                  <Card.Text>
                    {article.articleBody
                      ? article.articleBody.substring(0, 250) + "..."
                      : ""}
                  </Card.Text>
                  <div className="d-flex">
                    <a
                      onClick={() => {
                        localStorage.setItem("articleId", article.id);
                        navigate("/view/article");
                      }}
                      className="btn btn-primary table-row mx-2"
                    >
                      View Post
                    </a>
                    <Card.Text className="my-2">
                      LIKES: {article.likes}&nbsp;&nbsp; VIEWS:{article.views}
                      &nbsp;&nbsp; POSTED BY:{article.createdBy} &nbsp;{" "}
                      {article.isPublic == false &&
                        article.createdBy ==
                          localStorage.getItem("username") && (
                          <IconContext.Provider value={{ color: "", size: 20 }}>
                            <FaEyeSlash />
                          </IconContext.Provider>
                        )}
                    </Card.Text>
                  </div>
                </Card.Body>
              </Card>
            ))}
        </div>

        {records.length > 0 && (
          <div className="paginator">
            <nav className="my-3">
              <ul className="pagination justify-content-center">
                <a className="page-link" onClick={prePage}>
                  <li className="page-item">Prev</li>
                </a>
                {numbers.map((n, i) => (
                  <a
                    className="page-item"
                    onClick={() => setCurrentPage(n)}
                    key={i}
                  >
                    <li
                      className={`page-link ${
                        currentPage === n ? "bg-warning" : ""
                      }`}
                      key={i}
                    >
                      {n}
                    </li>
                  </a>
                ))}
                <a className="page-link" onClick={nextPage}>
                  <li className="page-item">Next</li>
                </a>
              </ul>
            </nav>
          </div>
        )}
      </div>
    </div>
  );
};

export default AllArticles;
