import { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Navbar from "../NavBar/Navbar";
import JoditEditor from "jodit-react";
import { useRef } from "react";
import ReactSwitch from "react-switch";
import Navbar1 from "../NavBar/Navbar";

function EditArticle() {
  const [article, setArticle] = useState({});
  const [checked, setChecked] = useState(true);
  const editor = useRef(null);
  const [content, setContent] = useState("");
  const url = "http:/localhost:8081/apis/insert";
  const navigate = useNavigate();
  const [heading, setHeading] = useState("");
  const [articleBody, setarticleBody] = useState("");
  const [message, setMessage] = useState("");

  useEffect(() => {
    let username = localStorage.getItem("username");
    let articleId = localStorage.getItem("articleId");

    if (articleId == null || articleId == "") {
      navigate("/home");
    }
    // console.log(username)
    if (username === "" || username === null) {
      navigate("/login");
    } else {
      console.log("fetcher");
      fetch(`http://localhost:8081/apis/${articleId}`)
        .then((response) => {
          // console.log(response);
          return response.json();
        })
        .then((data) => {
          setArticle(data);
          setContent(data.displayBody);
          setHeading(data.heading);
          setarticleBody(data.articleBody);
          console.log(article);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, []);

  function togglePublic(val) {
    console.log(val);
    setChecked(val);
  }

  let flag = false;

  function removeTags(str) {
    if (str === null || str === "") return false;
    else str = str.toString();
    return str.replace(/(<([^>]+)>)/gi, "");
  }


  const handleSubmit = (e) => {
    e.preventDefault();
    setHeading(heading.trim());
    if (heading.length > 0 && content.length > 0 && heading !== "") {
      fetch("http://localhost:8081/apis/update", {
        method: "POST",
        headers: {
          Accept: "application/json, text/plain",
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id: article.id,
          views: article.views,
          likes: article.likes,
          dislikes: article.dislikes,
          likedBy: article.likedBy,
          dislikedby: article.dislikedBy,
          articleBody: removeTags(content),
          heading: heading,
          createdBy: localStorage.getItem("username"),
          displayBody: content,
          isPublic: checked,
        }),
      })
        .then((response) => {
          console.log(response);
          if (response.status == 200) {
            flag = true;
            setMessage("article inserted successfully");
            setTimeout(() => {
              navigate("/");
            }, 2000);
          } else {
            setMessage("some error occured");

            setTimeout(() => {
              navigate("/");
            }, 2000);
          }
        })
        .catch((err) => {
          console.log(err);
        });

      console.log(heading + " " + articleBody);
    } else {
      flag = false;
      setMessage("please fill all the details");
      setTimeout(() => setMessage(""), 2000);
    }
  };

  return (
    <>
      <Navbar1 />
      <div>
        <div className="checker">
          <form className="container" onSubmit={(e) => handleSubmit(e)}>
            <input
              className="my-3"
              type="text"
              value={heading}
              placeholder="Heading"
              onChange={(e) => setHeading(e.target.value)}
            />
            <JoditEditor
              ref={editor}
              value={content}
              onChange={(newContent) => setContent(newContent)}
            />

            <div style={{ textAlign: "center" }}>
              <h4>Keep The Article Public:</h4>
              <ReactSwitch checked={checked} onChange={togglePublic} />
            </div>
            <button
              type="submit"
              className="my-3 insert-button btn btn-primary"
            >
              UPDATE
            </button>

            <div className="message">{message ? <p>{message}</p> : null}</div>
          </form>
        </div>
      </div>
    </>
  );
}

export default EditArticle;
